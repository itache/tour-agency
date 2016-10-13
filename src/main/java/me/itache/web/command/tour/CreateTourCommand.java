package me.itache.web.command.tour;

import me.itache.constant.Path;
import me.itache.constant.View;
import me.itache.helpers.LocaleManager;
import me.itache.helpers.request.handler.RequestHandler;
import me.itache.helpers.request.handler.UpdateTourRequestHandler;
import me.itache.utils.Notification;
import org.apache.log4j.Logger;
import me.itache.constant.Parameter;
import me.itache.model.entity.User;
import me.itache.service.TourService;
import me.itache.web.command.AbstractCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

/**
 *
 */
public class CreateTourCommand extends AbstractCommand {
    private static final Logger LOG = Logger.getLogger(CreateTourCommand.class);

    public CreateTourCommand(User.Role... permissions) {
        super(permissions);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.info(this.getClass().getSimpleName() + " starts");
        request.setAttribute(Parameter.ACTION, "create");
        request.setAttribute("uri", request.getRequestURI());
        if (request.getParameter(Parameter.ACTION) == null) {
            LOG.info("Show create tour form");
            request.getRequestDispatcher(View.EDIT_TOUR_PAGE).forward(request, response);
            return;
        }
        RequestHandler handler = new UpdateTourRequestHandler(request);
        if (handler.areParametersValid()) {
            Locale[] locales = LocaleManager.instance().getAvailableLocales().values().toArray(new Locale[]{});
            TourService service = new TourService(locales);
            Long tourID = service.create(handler.getParametersMap());
            Notification.create(
                    request.getSession(),
                    "tour.create-success",
                    Notification.Type.SUCCESS,
                    request.getParameter(Parameter.TOUR_NAME + "_" + LocaleManager.instance().getCurrentLocale()));
            response.sendRedirect(Path.TOUR_UPDATE_IMAGE + "?id=" + tourID);
        }else {
            LOG.info("Parameters not valid: " + handler.getErrors());
            Notification.create(
                    request.getSession(),
                    "tour.create-failure",
                    Notification.Type.ERROR);
            request.setAttribute("errors", handler.getErrors());
            request.getRequestDispatcher(View.EDIT_TOUR_PAGE).forward(request, response);
        }
        LOG.info(this.getClass().getSimpleName() + " ends");
    }
}
