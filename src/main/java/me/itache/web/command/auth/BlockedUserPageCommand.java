package me.itache.web.command.auth;

import org.apache.log4j.Logger;
import me.itache.model.entity.User;
import me.itache.constant.View;
import me.itache.web.command.AbstractCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Display contact form page for blocked user
 */
public class BlockedUserPageCommand extends AbstractCommand {
    private static final Logger LOG = Logger.getLogger(BlockedUserPageCommand.class);

    public BlockedUserPageCommand(User.Role... permissions) {
        super(permissions);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.info(this.getClass().getSimpleName() + " starts.");
        request.getRequestDispatcher(View.BLOCKED_USER_PAGE).forward(request, response);
        LOG.info(this.getClass().getSimpleName() + " ends.");
    }
}
