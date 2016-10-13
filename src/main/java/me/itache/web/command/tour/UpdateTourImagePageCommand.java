package me.itache.web.command.tour;

import me.itache.validation.rule.MoreThanInclusivelyRule;
import org.apache.log4j.Logger;
import me.itache.helpers.request.parameter.RequestParameter;
import me.itache.model.entity.User;
import me.itache.service.TourService;
import me.itache.constant.Parameter;
import me.itache.constant.View;
import me.itache.web.command.AbstractCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 */
public class UpdateTourImagePageCommand extends AbstractCommand {
    private static final Logger LOG = Logger.getLogger(UpdateTourImagePageCommand.class);

    public UpdateTourImagePageCommand(User.Role... permissions) {
        super(permissions);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.info(this.getClass().getSimpleName() + " starts");
        RequestParameter tourID = new RequestParameter(request.getParameter(Parameter.TOUR_ID),new MoreThanInclusivelyRule(1));
        TourService service = new TourService();
        if(!tourID.isValid() || service.getByPk(Long.parseLong(tourID.getValue())) == null) {
            LOG.error("Tour ID not valid or tour not exits. ID:" + tourID.getValue());
            response.sendError(404);
        }
        request.getRequestDispatcher(View.TOUR_UPDATE_IMAGE_PAGE).forward(request, response);
        LOG.info(this.getClass().getSimpleName() + " ends");
    }
}
