package me.itache.web.command.tour;

import me.itache.constant.Parameter;
import me.itache.helpers.LocaleManager;
import me.itache.helpers.request.parameter.RequestParameter;
import me.itache.service.TourService;
import me.itache.utils.Notification;
import me.itache.validation.rule.MoreThanInclusivelyRule;
import me.itache.web.command.AbstractCommand;
import org.apache.log4j.Logger;
import me.itache.model.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;


public class DeleteTourCommand extends AbstractCommand {
    private static final Logger LOG = Logger.getLogger(DeleteTourCommand.class);

    public DeleteTourCommand(User.Role... permissions) {
        super(permissions);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.info(this.getClass().getSimpleName() + " starts");
        RequestParameter tourID = new RequestParameter(request.getParameter(Parameter.TOUR_ID), new MoreThanInclusivelyRule(1));
        if (!tourID.isValid()) {
            LOG.debug("Tour ID not valid");
            response.sendError(404);
            return;
        }
        Locale[] locales = LocaleManager.instance().getAvailableLocales().values().toArray(new Locale[]{});
        TourService service = new TourService(locales);
        if (service.delete(Long.parseLong(tourID.getValue()))) {
            Notification.create(request.getSession(), "tour.delete-success", Notification.Type.SUCCESS, tourID.getValue());
            LOG.debug("Tour deleted. ID: " + tourID.getValue());
        }
        else {
            LOG.debug("Tour NOT deleted. ID: " + tourID.getValue());
        }
        response.sendRedirect(request.getHeader("referer"));
        LOG.info(this.getClass().getSimpleName() + " ends");
    }
}
