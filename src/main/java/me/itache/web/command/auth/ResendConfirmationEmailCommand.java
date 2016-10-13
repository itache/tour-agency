package me.itache.web.command.auth;

import me.itache.constant.Message;
import me.itache.constant.Path;
import me.itache.utils.Notification;
import me.itache.validation.rule.EmailRule;
import org.apache.log4j.Logger;
import me.itache.constant.Parameter;
import me.itache.constant.View;
import me.itache.helpers.request.parameter.RequestParameter;
import me.itache.model.entity.Token;
import me.itache.model.entity.User;
import me.itache.service.UserService;
import me.itache.utils.LocalizedMail;
import me.itache.utils.MailSender;
import me.itache.web.command.AbstractCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 */
public class ResendConfirmationEmailCommand extends AbstractCommand {
    private static final Logger LOG = Logger.getLogger(ResendConfirmationEmailCommand.class);

    public ResendConfirmationEmailCommand(User.Role... permissions) {
        super(permissions);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.info(this.getClass().getSimpleName() + " starts");
        UserService service = new UserService(request.getSession());
        User user;
        if (request.getParameter(Parameter.EMAIL) != null && "POST".equals(request.getMethod())) {
            user = getUserByEmail(request, response, service);
            if (user == null) {
                request.getRequestDispatcher(View.RESEND_TOKEN_PAGE).forward(request, response);
                return;
            }
        } else {
            LOG.info("Show resend form.");
            request.getRequestDispatcher(View.RESEND_TOKEN_PAGE).forward(request, response);
            return;
        }
        Token newToken = service.createToken(user);
        String link = Token.getConfirmationLink(request, newToken);
        if (new MailSender(new LocalizedMail(LocalizedMail.Type.RESEND_CONFIRMATION, link), user.getEmail()).sendAndWait()) {
            Notification.create(request.getSession(), "user.resend-confirmation-success", Notification.Type.SUCCESS);
            response.sendRedirect(Path.ENTER);
            LOG.info("Confirmation mail successfully sent");
        } else {
            Notification.create(request.getSession(), "user.resend-confirmation-failure", Notification.Type.ERROR);
            request.getRequestDispatcher(View.RESEND_TOKEN_PAGE).forward(request, response);
            LOG.info(Message.ERR_CANNOT_SEND_MAIL);
        }
        LOG.info(this.getClass().getSimpleName() + " ends.");
    }

    private User getUserByEmail(HttpServletRequest request, HttpServletResponse response, UserService service) throws ServletException, IOException {
        User user;
        RequestParameter email = new RequestParameter(request.getParameter(Parameter.EMAIL), new EmailRule());
        if (!email.isValid()) {
            LOG.info("Email not valid: " + email.getValue());
            request.setAttribute("invalid_mail", email.getErrors());
            return null;
        }
        user = service.getByEmail(email.getValue());
        if (user == null) {
            LOG.info("Unknown email: " + email.getValue());
            request.setAttribute("unknown_mail", email.getValue());
            return null;
        }
        if (user.isEnabled()) {
            LOG.info("User enabled already. ID: " + user.getId());
            request.setAttribute("enabled", true);
            return null;
        }
        return user;
    }
}
