package me.itache.web.command.auth;

import me.itache.validation.rule.MoreThanInclusivelyRule;
import org.apache.log4j.Logger;
import me.itache.helpers.request.parameter.RequestParameter;
import me.itache.model.entity.User;
import me.itache.service.UserService;
import me.itache.constant.Parameter;
import me.itache.web.command.AbstractCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 */
public class BlockUserCommand extends AbstractCommand {
    private static final Logger LOG = Logger.getLogger(BlockUserCommand.class);

    public BlockUserCommand(User.Role... permissions) {
        super(permissions);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.info(this.getClass().getSimpleName() + " starts");
        RequestParameter userID = new RequestParameter(request.getParameter(Parameter.USER_ID),new MoreThanInclusivelyRule(1));
        if(userID.isValid()) {
            UserService service = new UserService(request.getSession());
            service.blockUser(Long.valueOf(userID.getValue()), true);
        }else{
            LOG.debug("User id not valid: " + userID.getErrors());
        }
        response.sendRedirect(request.getHeader("referer"));
        LOG.info(this.getClass().getSimpleName() + " ends.");
    }
}
