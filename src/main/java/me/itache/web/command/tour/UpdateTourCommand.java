package me.itache.web.command.tour;

import me.itache.constant.Message;
import me.itache.constant.Path;
import me.itache.helpers.request.handler.RequestHandler;
import me.itache.helpers.request.handler.UpdateTourManagerRequestHandler;
import me.itache.helpers.request.handler.UpdateTourRequestHandler;
import me.itache.model.bean.TourBean;
import me.itache.utils.Notification;
import me.itache.validation.rule.MoreThanInclusivelyRule;
import org.apache.log4j.Logger;
import me.itache.helpers.LocaleManager;
import me.itache.helpers.request.parameter.RequestParameter;
import me.itache.model.entity.User;
import me.itache.service.TourService;
import me.itache.constant.Parameter;
import me.itache.constant.View;
import me.itache.service.UserService;
import me.itache.web.command.AbstractCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

/**
 *
 */
public class UpdateTourCommand extends AbstractCommand {
    private static final Logger LOG = Logger.getLogger(UpdateTourCommand.class);

    public UpdateTourCommand(User.Role... permissions) {
        super(permissions);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.info(this.getClass().getSimpleName() + " starts.");
        RequestParameter tourID = new RequestParameter(request.getParameter(Parameter.TOUR_ID), new MoreThanInclusivelyRule(1));
        if (!tourID.isValid()) {
            LOG.debug("Tour id not valid: " + tourID.getErrors());
            response.sendError(404);
            return;
        }
        request.setAttribute(Parameter.ACTION, "update");
        request.setAttribute("uri", request.getRequestURI() + "?" + request.getQueryString());
        if (request.getParameter(Parameter.ACTION) == null) {
            returnTour(request, response, tourID);
        } else {
            updateTour(Long.parseLong(tourID.getValue()), request, response);
        }
    }

    private void updateTour(long id, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User currentUser = new UserService(request.getSession()).getCurrentUser();
        RequestHandler requestHandler = getRequestHandler(request, currentUser.getRole());
        if (requestHandler.areParametersValid()) {
            updateTour(id, requestHandler, currentUser.getRole());
            Notification.create(
                    request.getSession(),
                    "tour.update-success",
                    Notification.Type.SUCCESS,
                    request.getParameter(getNameParameter(currentUser.getRole())));
            response.sendRedirect(Path.TOUR_LIST);
        } else {
            LOG.debug("Parameters invalid" + requestHandler.getErrors());
            request.setAttribute("errors", requestHandler.getErrors());
            Notification.create(
                    request.getSession(),
                    "tour.update-failure",
                    Notification.Type.ERROR);
            request.getRequestDispatcher(getEditTourPage(currentUser.getRole())).forward(request, response);
        }
    }

    private String getNameParameter(User.Role role) {
        if(role == User.Role.ADMIN) {
            return Parameter.TOUR_NAME + "_" + LocaleManager.instance().getCurrentLocale();
        }
        return Parameter.TOUR_NAME;
    }

    private String getEditTourPage(User.Role role) {
        if(role == User.Role.ADMIN) {
            return View.EDIT_TOUR_PAGE;
        }
        return View.EDIT_TOUR_MANAGER_PAGE;
    }

    private void updateTour(long id, RequestHandler requestHandler, User.Role role) {
        TourService service = getTourService(role);
        if(role == User.Role.ADMIN) {
            service.update(id, requestHandler.getParametersMap());
        } else if( role == User.Role.MANAGER) {
            service.updateStatusAndDiscount(id, requestHandler.getParametersMap());
        }
    }

    private RequestHandler getRequestHandler(HttpServletRequest request, User.Role role) {
        if(role == User.Role.ADMIN) {
            return new UpdateTourRequestHandler(request);
        } else if(role == User.Role.MANAGER) {
            return new UpdateTourManagerRequestHandler(request);
        }
        LOG.error(String.format(Message.ERR_COMMAND_NOT_ALLOWED, role));
        throw new IllegalStateException(String.format(Message.ERR_COMMAND_NOT_ALLOWED, role));
    }

    private void returnTour(HttpServletRequest request, HttpServletResponse response, RequestParameter tourID) throws IOException, ServletException {
        User currentUser = new UserService(request.getSession()).getCurrentUser();
        TourService service = getTourService(currentUser.getRole());
        TourBean tourBean = service.getByPk(Long.valueOf(tourID.getValue()));
        if (tourBean == null) {
            LOG.error("Tour not found. ID: " + tourID.getValue());
            response.sendError(404);
        }
        LOG.debug("Obtain tour with id: " + tourBean.getTour().getId());
        request.setAttribute("tour_bean", tourBean);
        request.getRequestDispatcher(getEditTourPage(currentUser.getRole())).forward(request, response);
    }

    private TourService getTourService(User.Role role) {
        if(role == User.Role.ADMIN) {
            Locale[] locales = LocaleManager.instance().getAvailableLocales().values().toArray(new Locale[]{});
            return new TourService(locales);
        }
        return new TourService();
    }

}
