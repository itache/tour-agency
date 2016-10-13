package me.itache.web.command.auth;

import me.itache.dao.modifier.Pagination;
import org.apache.log4j.Logger;
import me.itache.constant.View;
import me.itache.helpers.request.handler.FilterRequestHandler;
import me.itache.helpers.request.handler.UserListRequestHandler;
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
public class UserListCommand extends AbstractCommand {
    private static final Logger LOG = Logger.getLogger(UserListCommand.class);

    public UserListCommand(User.Role... permissions) {
        super(permissions);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.info(this.getClass().getSimpleName() + " starts");
        FilterRequestHandler handler = new UserListRequestHandler(request);
        Pagination pagination = handler.getPagination();
        UserService service = new UserService(request.getSession());
        request.setAttribute("pagination", pagination);
        request.setAttribute("users", service.get(handler.getSorter(), pagination));
        request.getRequestDispatcher(View.USER_LIST_PAGE).forward(request, response);
        LOG.info(this.getClass().getSimpleName() + " ends.");
    }
}
