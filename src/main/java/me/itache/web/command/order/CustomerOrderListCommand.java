package me.itache.web.command.order;

import me.itache.constant.View;
import me.itache.dao.modifier.Pagination;
import me.itache.helpers.request.handler.FilterRequestHandler;
import me.itache.helpers.request.handler.OrderListRequestHandler;
import me.itache.model.bean.OrderBean;
import org.apache.log4j.Logger;
import me.itache.model.entity.User;
import me.itache.service.OrderService;
import me.itache.web.command.AbstractCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 *
 */
public class CustomerOrderListCommand extends AbstractCommand {
    private static final Logger LOG = Logger.getLogger(CustomerOrderListCommand.class);
    private static final String USER_SESSION_PARAM = "user";

    public CustomerOrderListCommand(User.Role... permissions) {
        super(permissions);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.info(this.getClass().getSimpleName() + " starts");
        User customer = (User) request.getSession().getAttribute(USER_SESSION_PARAM);
        OrderService service = new OrderService();
        FilterRequestHandler handler = new OrderListRequestHandler(request);
        Pagination pagination = handler.getPagination();
        List<OrderBean> orders = service.getAllForCustomer(customer.getId(), handler.getSorter(), pagination);
        request.setAttribute("orders", orders);
        request.setAttribute("pagination", pagination);
        request.getRequestDispatcher(View.ORDER_LIST_PAGE).forward(request, response);
        LOG.info(this.getClass().getSimpleName() + " ends");
    }
}
