package me.itache.web.command.order;

import org.apache.log4j.Logger;
import me.itache.constant.Parameter;
import me.itache.helpers.request.parameter.RequestParameter;
import me.itache.model.bean.OrderBean;
import me.itache.model.entity.Order;
import me.itache.model.entity.User;
import me.itache.service.OrderService;
import me.itache.utils.LocalizedMail;
import me.itache.utils.MailSender;
import me.itache.validation.rule.EnumValueRule;
import me.itache.validation.rule.MoreThanInclusivelyRule;
import me.itache.web.command.AbstractCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 */
public class SetOrderStatusCommand extends AbstractCommand {
    private static final Logger LOG = Logger.getLogger(SetOrderStatusCommand.class);
    public SetOrderStatusCommand(User.Role... permissions) {
        super(permissions);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.info(this.getClass().getSimpleName() + " starts");
        RequestParameter orderID = new RequestParameter(request.getParameter(Parameter.ORDER_ID),
                new MoreThanInclusivelyRule(1));
        RequestParameter status = new RequestParameter(request.getParameter(Parameter.ORDER_STATUS),
                new EnumValueRule<>(Order.Status.class));
        if (orderID.isValid() && status.isValid()) {
            Long id = Long.valueOf(orderID.getValue());
            OrderService service = new OrderService();
            if (service.setStatus(id, Order.Status.valueOf(status.getValue().toUpperCase()))) {
                OrderBean orderBean = service.getByPk(id);
                String description = orderBean.getTour().getDescription().getTitle();
                String email = orderBean.getCustomer().getEmail();
                new MailSender(
                        new LocalizedMail(LocalizedMail.Type.ORDER_STATUS_CHANGED, description), email).send();
                LOG.info("Order status was changed.ID: " + id + ", status: " + status.getValue());
            } else {
                LOG.debug("Can not set order status. ID: " + id + ", status: " + status.getValue());
            }
            response.sendRedirect(request.getHeader("referer"));
            return;
        }
        LOG.debug("Parameters not valid. Order ID: " + orderID.getValue() + ", errors - " + orderID.getErrors() +
                ". Status: " + status.getValue() + ", errors - " + status.getErrors());
        response.sendError(404);
        LOG.info(this.getClass().getSimpleName() + " ends");
    }
}
