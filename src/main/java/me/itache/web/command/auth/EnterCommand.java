package me.itache.web.command.auth;

import com.captcha.botdetect.web.servlet.Captcha;
import org.apache.log4j.Logger;
import me.itache.constant.Parameter;
import me.itache.constant.View;
import me.itache.model.entity.User;
import me.itache.web.command.AbstractCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 */
public class EnterCommand extends AbstractCommand {
    private static final Logger LOG = Logger.getLogger(BlockUserCommand.class);

    public EnterCommand(User.Role... permissions) {
        super(permissions);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.info(this.getClass().getSimpleName() + " starts");
        String action = request.getParameter(Parameter.ACTION);
        setCaptcha(request);
        if ("login".equals(action)) {
            new LoginCommand().execute(request, response);
        } else if ("sing_up".equals(action)) {
            new SingUpCommand().execute(request, response);
        } else {
            LOG.info("Show enter page");
            request.getRequestDispatcher(View.ENTER_PAGE).forward(request, response);
        }
        LOG.info(this.getClass().getSimpleName() + " ends.");
    }

    private void setCaptcha(HttpServletRequest request) {
        Captcha captcha = Captcha.load(request, "exampleCaptcha");
        captcha.setUserInputID("captcha");
        request.setAttribute("captcha", captcha);
    }
}
