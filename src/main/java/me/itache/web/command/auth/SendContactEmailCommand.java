package me.itache.web.command.auth;

import me.itache.constant.Parameter;
import me.itache.constant.Path;
import me.itache.constant.View;
import me.itache.helpers.request.handler.RequestHandler;
import me.itache.helpers.request.parameter.RequestParameter;
import me.itache.service.UserService;
import me.itache.utils.MailSender;
import me.itache.utils.Notification;
import me.itache.validation.rule.MinLengthRule;
import me.itache.validation.rule.NotEmptyRule;
import me.itache.web.command.AbstractCommand;
import org.apache.log4j.Logger;
import me.itache.model.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SendContactEmailCommand extends AbstractCommand {
    private static final Logger LOG = Logger.getLogger(SendContactEmailCommand.class);

    public SendContactEmailCommand(User.Role... permissions) {
        super(permissions);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.info(this.getClass().getSimpleName() + " starts");
        RequestHandler handler = new RequestHandler(request) {{
            addParameter(Parameter.CONTACT_TOPIC,
                    new RequestParameter(request.getParameter(Parameter.CONTACT_TOPIC),
                            new NotEmptyRule(),
                            new MinLengthRule(10)));
            addParameter(Parameter.CONTACT_TEXT,
                    new RequestParameter(request.getParameter(Parameter.CONTACT_TEXT),
                            new NotEmptyRule(),
                            new MinLengthRule(50)));
        }};
        if (handler.areParametersValid()) {
            UserService service = new UserService(request.getSession());
            MailSender sender = new MailSender(
                    handler.getParameter(Parameter.CONTACT_TOPIC).getValue(),
                    handler.getParameter(Parameter.CONTACT_TEXT).getValue(),
                    service.getCurrentUser().getEmail()
            );
            if(sender.sendAndWait()) {
                Notification.create(
                        request.getSession(),
                        "mail.send-success",
                        Notification.Type.SUCCESS);
                response.sendRedirect(Path.DEFAULT);
                LOG.info("Mail was sent. Email: " + service.getCurrentUser().getEmail());
            } else {
                Notification.create(
                        request.getSession(),
                        "mail.send-failure",
                        Notification.Type.ERROR);
                LOG.debug("Can not send mail");
                request.getRequestDispatcher(View.BLOCKED_USER_PAGE).forward(request, response);
            }
        } else {
            LOG.info("Parameters not valid." + handler.getErrors());
            request.setAttribute("errors", handler.getErrors());
            request.getRequestDispatcher(View.BLOCKED_USER_PAGE).forward(request, response);
        }
        LOG.info(this.getClass().getSimpleName() + " ends.");
    }
}
