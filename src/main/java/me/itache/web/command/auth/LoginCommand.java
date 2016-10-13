package me.itache.web.command.auth;


import me.itache.utils.Notification;
import org.apache.log4j.Logger;
import me.itache.constant.Path;
import me.itache.helpers.request.handler.LoginRequestHandler;
import me.itache.helpers.request.handler.RequestHandler;
import me.itache.service.UserService;
import me.itache.constant.Parameter;
import me.itache.constant.View;
import me.itache.web.command.AbstractCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 */
public class LoginCommand extends AbstractCommand {
    private static final Logger LOG = Logger.getLogger(LoginCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.info(this.getClass().getSimpleName() + " starts");
        RequestHandler requestHandler = new LoginRequestHandler(request);
        request.setAttribute(Parameter.ACTION, "login");
        if (!requestHandler.areParametersValid()) {
            LOG.info("Parameters not valid: " + requestHandler.getErrors());
            request.setAttribute("errors", requestHandler.getErrors());
            request.getRequestDispatcher(View.ENTER_PAGE).forward(request, response);
            return;
        }
        UserService service = new UserService(request.getSession());
        int result = service.login(request.getParameter(Parameter.LOGIN), request.getParameter(Parameter.PASSWORD));
        if (result != 1) {
            if(result == -1) {
                LOG.info("User not found.");
                request.setAttribute("is_login_failed", true);
            } else if(result == 0){
                LOG.info("User not enabled.");
                request.setAttribute("not_enabled", true);
            }
            request.getRequestDispatcher(View.ENTER_PAGE).forward(request, response);
            return;
        }
        LOG.info("User logged in");
        Notification.create(request.getSession(), "logged_in", Notification.Type.SUCCESS);
        response.sendRedirect(Path.DEFAULT);
        LOG.info(this.getClass().getSimpleName() + " ends.");
    }
}
