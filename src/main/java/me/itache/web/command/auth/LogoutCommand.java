package me.itache.web.command.auth;

import org.apache.log4j.Logger;
import me.itache.constant.Path;
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
public class LogoutCommand extends AbstractCommand {
    private static final Logger LOG = Logger.getLogger(LogoutCommand.class);

    public LogoutCommand(User.Role... permissions) {
        super(permissions);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.info(this.getClass().getSimpleName() + " starts");
        UserService userService = new UserService(request.getSession());
        userService.logout();
        response.sendRedirect(Path.DEFAULT);
        LOG.info(this.getClass().getSimpleName() + " ends.");
    }
}
