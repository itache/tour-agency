package me.itache.web.command.auth;

import me.itache.utils.Notification;
import org.apache.log4j.Logger;
import me.itache.constant.Message;
import me.itache.constant.Path;
import me.itache.helpers.request.handler.RequestHandler;
import me.itache.helpers.request.handler.SingUpRequestHandler;
import me.itache.model.entity.User;
import me.itache.model.entity.Token;
import me.itache.service.UserService;
import me.itache.constant.Parameter;
import me.itache.constant.View;
import me.itache.utils.LocalizedMail;
import me.itache.utils.MailSender;
import me.itache.web.command.AbstractCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SingUpCommand extends AbstractCommand {
    private static final Logger LOG = Logger.getLogger(SingUpCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.info(this.getClass().getSimpleName() + " starts");
        RequestHandler handler = new SingUpRequestHandler(request);
        if(!handler.areParametersValid()) {
            LOG.info("Parameters not valid: " + handler.getErrors());
            request.setAttribute(Parameter.ACTION, "sing_up");
            request.setAttribute("errors",handler.getErrors());
            request.getRequestDispatcher(View.ENTER_PAGE).forward(request, response);
            return;
        }
        UserService service = new UserService(request.getSession());
        User newUser = service.createUser(handler.getParametersMap());
        Token token = service.createToken(newUser);
        String link = Token.getConfirmationLink(request, token);
        if(new MailSender(new LocalizedMail(LocalizedMail.Type.USER_SING_UP, link), newUser.getEmail()).sendAndWait()) {
            LOG.info("Confirmation link was sent: " + newUser.getEmail());
        } else {
            LOG.debug(Message.ERR_CANNOT_SEND_MAIL + newUser.getEmail());
        }
        Notification.create(request.getSession(), "registered", Notification.Type.SUCCESS);
        response.sendRedirect(Path.DEFAULT);
        LOG.info(this.getClass().getSimpleName() + " ends.");
    }
}
