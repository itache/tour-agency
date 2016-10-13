package me.itache.web.command.order;

import me.itache.dao.modifier.Pagination;
import me.itache.dao.modifier.Sorter;
import me.itache.helpers.request.handler.OrderListRequestHandler;
import org.apache.log4j.Logger;
import me.itache.helpers.request.handler.FilterRequestHandler;
import me.itache.model.entity.User;
import me.itache.service.OrderService;
import me.itache.constant.View;
import me.itache.web.command.AbstractCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 */
public class AllOrderListCommand extends AbstractCommand {
    private static final Logger LOG = Logger.getLogger(AllOrderListCommand.class);

    public AllOrderListCommand(User.Role... permissions) {
        super(permissions);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.info(this.getClass().getSimpleName() + " starts");
        FilterRequestHandler requestHandler = new OrderListRequestHandler(request);
        Sorter sorter = requestHandler.getSorter();
        Pagination pagination = requestHandler.getPagination();
        OrderService orderService = new OrderService();
        request.setAttribute("orders", orderService.get(sorter, pagination));
        LOG.debug("Orders obtained");
        request.setAttribute("pagination", pagination);
        request.setAttribute("summary", orderService.getSummary());
        request.getRequestDispatcher(View.ORDER_LIST_PAGE).forward(request, response);
        LOG.info(this.getClass().getSimpleName() + " ends");
    }
}
