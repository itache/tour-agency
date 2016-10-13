package me.itache.web.command.tour;

import me.itache.model.bean.TourBean;
import org.apache.log4j.Logger;
import me.itache.helpers.LocaleManager;
import me.itache.helpers.request.parameter.RequestParameter;
import me.itache.model.entity.User;
import me.itache.service.TourService;
import me.itache.constant.Parameter;
import me.itache.constant.View;
import me.itache.validation.rule.MoreThanInclusivelyRule;
import me.itache.web.command.AbstractCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 */
public class ViewTourCommand extends AbstractCommand {
    private static final Logger LOG = Logger.getLogger(ViewTourCommand.class);

    public ViewTourCommand(User.Role... permissions) {
        super(permissions);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug(this.getClass().getSimpleName() + " starts");
        RequestParameter tourID = new RequestParameter(request.getParameter(Parameter.TOUR_ID),new MoreThanInclusivelyRule(1));
        if(!tourID.isValid()) {
            LOG.debug("Tour id not valid: " + tourID.getValue());
            response.sendError(404);
            return;
        }
        TourService service = new TourService(LocaleManager.instance().getCurrentLocale());
        TourBean bean = service.getByPk(Long.parseLong(tourID.getValue()));
        if(bean == null) {
            LOG.error("Tour not found. ID: " + tourID.getValue());
            response.sendError(404);
            return;
        }
        request.setAttribute("tour_bean", bean);
        request.getRequestDispatcher(View.TOUR_VIEW_PAGE).forward(request, response);
        LOG.info(this.getClass().getSimpleName() + " ends");
    }
}
