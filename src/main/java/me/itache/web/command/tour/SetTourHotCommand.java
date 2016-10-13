package me.itache.web.command.tour;

import org.apache.log4j.Logger;
import me.itache.helpers.request.parameter.RequestParameter;
import me.itache.model.entity.User;
import me.itache.service.TourService;
import me.itache.constant.Parameter;
import me.itache.validation.rule.MoreThanInclusivelyRule;
import me.itache.web.command.AbstractCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 */
public class SetTourHotCommand extends AbstractCommand {
    private static final Logger LOG = Logger.getLogger(SetTourHotCommand.class);

    public SetTourHotCommand(User.Role... permissions) {
        super(permissions);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.info(this.getClass().getSimpleName() + " starts");
        RequestParameter tourID = new RequestParameter(request.getParameter(Parameter.TOUR_ID),new MoreThanInclusivelyRule(1));
        if(tourID.isValid()) {
            TourService service = new TourService();
            service.setHot(Long.valueOf(tourID.getValue()));
        }else {
            LOG.info("Tour id not valid: " + tourID.getErrors());
        }
        response.sendRedirect(request.getHeader("referer"));
        LOG.info(this.getClass().getSimpleName() + " ends");
    }
}
