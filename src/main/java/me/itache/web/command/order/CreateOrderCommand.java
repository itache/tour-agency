package me.itache.web.command.order;

import me.itache.constant.Message;
import me.itache.constant.Parameter;
import me.itache.exception.CommandException;
import me.itache.helpers.LocaleManager;
import me.itache.helpers.request.parameter.RequestParameter;
import me.itache.model.bean.TourBean;
import me.itache.model.entity.Order;
import me.itache.service.TourService;
import me.itache.service.UserService;
import me.itache.utils.LocalizedMail;
import me.itache.utils.MailSender;
import me.itache.utils.Notification;
import me.itache.validation.rule.MoreThanInclusivelyRule;
import me.itache.web.command.AbstractCommand;
import org.apache.log4j.Logger;
import me.itache.model.entity.User;
import me.itache.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 */
public class CreateOrderCommand extends AbstractCommand {
    private static final Logger LOG = Logger.getLogger(CreateOrderCommand.class);

    public CreateOrderCommand(User.Role... permissions) {
        super(permissions);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.info(this.getClass().getSimpleName() + " starts");
        RequestParameter tourIdParam = new RequestParameter(request.getParameter(Parameter.ORDER_TOUR_ID), new MoreThanInclusivelyRule(1));
        User user = getUser(request);
        if (tourIdParam.isValid()) {
            OrderService orderService = new OrderService();
            TourBean tourBean = getTourBean(response, tourIdParam);
            if (tourBean == null) {
                return;
            }
            if(tourBean.isOutdated()) {
                LOG.debug("Tour is outdated. ID: " + tourIdParam.getValue());
                Notification.create(
                        request.getSession(),
                        "order.create-outdated",
                        Notification.Type.ERROR,
                        tourBean.getDescription().getTitle());
                response.sendRedirect(request.getHeader("referer"));
                return;
            }
            long tourId = Long.valueOf(tourIdParam.getValue());
            if (!orderService.orderExists(tourId, user.getId())) {
                createOrder(tourId, user, request, orderService, tourBean);
            } else {
                LOG.info("Order exists for tour with ID: " + tourIdParam.getValue());
                Notification.create(
                        request.getSession(),
                        "order.create-failure",
                        Notification.Type.ERROR,
                        tourBean.getDescription().getTitle());
            }
        }else {
            LOG.info("Tour id not valid: " + tourIdParam.getValue());
        }
        response.sendRedirect(request.getHeader("referer"));
        LOG.info(this.getClass().getSimpleName() + " ends");
    }

    private TourBean getTourBean(HttpServletResponse response, RequestParameter tourIdParam) throws IOException {
        TourService tourService = new TourService(LocaleManager.instance().getCurrentLocale());
        TourBean tourBean = tourService.getByPk(Long.valueOf(tourIdParam.getValue()));
        if (tourBean.getTour() == null) {
            LOG.debug("Can not find tour. ID: " + tourIdParam.getValue());
            response.sendError(404);
            return null;
        }
        return tourBean;
    }

    private User getUser(HttpServletRequest request) {
        UserService userService = new UserService(request.getSession());
        User user = userService.getCurrentUser();
        if (user == null) {
            LOG.error(Message.ERR_USER_NOT_SET);
            throw new CommandException(Message.ERR_USER_NOT_SET);
        }
        return user;
    }

    private void createOrder(long tourId, User user, HttpServletRequest request, OrderService orderService, TourBean tourBean) {
        Order order = orderService.create(tourId, user.getId());
        LOG.debug("Order created. ID: " + order.getId());
        String tourTitle = tourBean.getDescription().getTitle();
        Notification.create(
                request.getSession(),
                "order.create-success",
                Notification.Type.SUCCESS,
                tourTitle);
        new MailSender(new LocalizedMail(LocalizedMail.Type.ORDER_CREATED, tourTitle), user.getEmail()).send();
    }
}
