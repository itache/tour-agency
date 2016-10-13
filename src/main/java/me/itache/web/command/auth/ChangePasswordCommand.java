package me.itache.web.command.auth;

import me.itache.constant.*;
import me.itache.helpers.request.handler.RequestHandler;
import me.itache.utils.Notification;
import me.itache.validation.rule.*;
import org.apache.log4j.Logger;
import me.itache.exception.ApplicationException;
import me.itache.helpers.request.parameter.RequestParameter;
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
public class ChangePasswordCommand extends AbstractCommand {
    private static final Logger LOG = Logger.getLogger(ChangePasswordCommand.class);

    public ChangePasswordCommand(User.Role... permissions) {
        super(permissions);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.info(this.getClass().getSimpleName() + " starts");
        String token = request.getParameter(Parameter.TOKEN);
        UserService service = new UserService(request.getSession());
        Token passwordToken = service.getToken(token);
        if (!checkToken(response, token, service, passwordToken)) {
            return;
        }
        User user = service.getByPk(passwordToken.getUserId());
        if (!checkUser(response, passwordToken, user)) {
            return;
        }
        LOG.info("Token valid. User ID: " + user.getId());
        if ("GET".equals(request.getMethod())) {
            LOG.trace("Show password change form.");
            request.getRequestDispatcher(View.CHANGE_PASSWORD_PAGE).forward(request, response);
            return;
        }
        RequestParameter password = new RequestParameter(
                request.getParameter(Parameter.PASSWORD),
                new NotEmptyRule(),
                new LatinNumericRule(),
                new MinLengthRule(Common.PASSWORD_MIN_LENGTH),
                new MaxLengthRule(Common.PASSWORD_MAX_LENGTH));
        RequestParameter passwordConfirm = new RequestParameter(
                request.getParameter(Parameter.PASSWORD_CONFIRMATION),
                new EqualsRule(request.getParameter(Parameter.PASSWORD)));
        RequestHandler handler = new RequestHandler(request) {{
            addParameter(Parameter.PASSWORD, password);
            addParameter(Parameter.PASSWORD_CONFIRMATION, passwordConfirm);
        }};
        if (!handler.areParametersValid()) {
            LOG.debug("Parameters not valid." + handler.getErrors());
            request.setAttribute("errors", handler.getErrors());
            request.getRequestDispatcher(View.CHANGE_PASSWORD_PAGE).forward(request, response);
            return;
        }
        service.changePassword(user, password.getValue());
        LOG.info("Password was changed");
        Notification.create(request.getSession(), "password.change-success", Notification.Type.SUCCESS);
        response.sendRedirect(Path.ENTER);
        LOG.info(this.getClass().getSimpleName() + " ends.");
    }

    private boolean checkUser(HttpServletResponse response, Token passwordToken, User user) throws IOException {
        if(user == null) {
            LOG.error(Message.ERR_CANNOT_FIND_USER_BY_TOKEN + passwordToken.getUserId());
            throw new ApplicationException(Message.ERR_CANNOT_FIND_USER_BY_TOKEN + passwordToken.getUserId());
        }
        if (!user.isEnabled()) {
            LOG.error("User not enabled. User ID: " + user.getId());
            response.sendError(404);
            return false;
        }
        return true;
    }

    private boolean checkToken(HttpServletResponse response, String token, UserService service, Token passwordToken) throws IOException {
        if (passwordToken == null) {
            LOG.debug("Token not found: " + token);
            response.sendError(404);
            return false;
        }
        if (service.isTokenExpired(passwordToken)) {
            LOG.debug("Token expired: " + token);
            response.sendRedirect(Path.RESET_PASSWORD + "?" + Parameter.TOKEN + "=expired");
            return false;
        }
        return true;
    }
}
