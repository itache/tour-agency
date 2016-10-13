package me.itache.web.filter;

import me.itache.constant.Path;
import me.itache.model.entity.User;
import me.itache.service.UserService;
import me.itache.web.command.Command;
import me.itache.web.command.CommandRegistry;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;


/**
 * Checks the user role before executing command. If user doesn't have required role a 403 error
 * page will be displayed. If given command not exists a 404 error page will be displayed.
 */
public class CommandAccessFilter implements Filter {
    private static final Logger LOG = Logger.getLogger(CommandAccessFilter.class);
    private static final String[] blockedUserAvailablePaths =
            {Path.USER_BLOCKED_PAGE, Path.SET_LANG, Path.LOGOUT, Path.SEND_CONTACT_MAIL};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOG.debug(this.getClass().getSimpleName() + " initialization starts");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        Command command = CommandRegistry.getCommand(httpRequest.getRequestURI());
        if (command == null) {
            LOG.debug("Command not exists: " + httpRequest.getRequestURI());
            ((HttpServletResponse) response).sendError(404);
        }
        User user = new UserService(httpRequest.getSession()).getCurrentUser();
        if (isNotAvailableForUser(httpRequest.getRequestURI(), user)) {
            LOG.debug("User blocked. User id: " + user.getId());
            ((HttpServletResponse) response).sendRedirect(Path.USER_BLOCKED_PAGE);
            return;
        }
        if (command.checkPermission(user)) {
            chain.doFilter(request, response);
        } else {
            LOG.error(String.format("Access denied for %s to the following command: %s", user, command));
            httpResponse.sendError(403);
        }
    }

    private boolean isNotAvailableForUser(String path, User user) {
        return user.isBlocked() && !Arrays.asList(blockedUserAvailablePaths).contains(path);
    }

    @Override
    public void destroy() {

    }
}
