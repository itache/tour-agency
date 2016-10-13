package me.itache.web.command;

import me.itache.constant.Path;
import org.apache.log4j.Logger;
import me.itache.model.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 */
public class DefaultCommand extends AbstractCommand {
    private static final Logger LOG = Logger.getLogger(DefaultCommand.class);

    public DefaultCommand(User.Role... permissions) {
        super(permissions);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.info(this.getClass().getSimpleName() + " starts");
        response.sendRedirect(Path.TOUR_LIST);
        LOG.info(this.getClass().getSimpleName() + " ends");
    }
}
