package me.itache.web.command.auth;

import me.itache.constant.Path;
import me.itache.utils.Notification;
import org.apache.log4j.Logger;
import me.itache.constant.Parameter;
import me.itache.model.entity.Token;
import me.itache.model.entity.User;
import me.itache.service.UserService;
import me.itache.web.command.AbstractCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 */
public class ConfirmRegistrationCommand extends AbstractCommand {
    private static final Logger LOG = Logger.getLogger(ConfirmRegistrationCommand.class);

    public ConfirmRegistrationCommand(User.Role... permissions) {
        super(permissions);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.info(this.getClass().getSimpleName() + " starts");
        UserService service = new UserService(request.getSession());
        String token = request.getParameter(Parameter.TOKEN);
        Token verificationToken = service.getToken(token);
        if (verificationToken == null) {
            LOG.debug("Token not found: " + token);
            response.sendRedirect(Path.TOKEN_RESEND + "?" + Parameter.TOKEN + "= not_exists");
            return;
        }
        if (service.isTokenExpired(verificationToken)) {
            LOG.debug("Token expired: " + token);
            response.sendRedirect(Path.TOKEN_RESEND + "?" + Parameter.TOKEN + "= expired");
            return;
        }
        service.enableUser(verificationToken.getUserId());
        Notification.create(request.getSession(), "user.enable-success", Notification.Type.SUCCESS);
        response.sendRedirect(Path.DEFAULT);
        LOG.info(this.getClass().getSimpleName() + " ends.");
    }
}
