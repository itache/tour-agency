package me.itache.service;

import me.itache.constant.Message;
import me.itache.dao.DAOFactory;
import me.itache.dao.GenericDAO;
import me.itache.dao.modifier.EntityCondition;
import me.itache.dao.modifier.Pagination;
import me.itache.dao.modifier.Sorter;
import me.itache.helpers.LocaleManager;
import me.itache.model.bean.OrderBean;
import me.itache.model.bean.TourBean;
import me.itache.model.entity.Order;
import me.itache.model.entity.OrderSummary;
import me.itache.model.entity.Tour;
import me.itache.model.meta.OrderMeta;
import org.apache.log4j.Logger;
import me.itache.dao.jdbc.OrderJdbcDao;
import me.itache.helpers.DiscountManager;
import me.itache.model.entity.User;

import java.util.*;

/**
 * Provides business logic for order actions
 */
public class OrderService {
    private static final Logger LOG = Logger.getLogger(OrderService.class);

    private GenericDAO<Order> orderDAO = DAOFactory.instance().getOrderDao();
    private HashMap<Long, User> customers = new HashMap<>();
    private HashMap<Long, TourBean> tours = new HashMap<>();

    public List<OrderBean> getAllForCustomer(long customerID, Sorter sorter, Pagination pagination) {
        return get(sorter, pagination, EntityCondition.eq(OrderMeta.CUSTOMER_ID, customerID));
    }

    private TourBean getTour(long tourID) {
        if (tours.containsKey(tourID)) {
            return tours.get(tourID);
        }
        TourService service = new TourService(LocaleManager.instance().getCurrentLocale());
        TourBean tourBean = service.getByPk(tourID);
        tours.put(tourBean.getTour().getId(), tourBean);
        return tourBean;
    }

    public OrderBean getByPk(long orderId) {
        Order order = orderDAO.getByPK(orderId);
        if (order == null) {
            return null;
        }
        TourBean tourBean = getTour(order.getTourId());
        User customer = getCustomer(order.getCustomerId());
        return createOrderBean(customer, order, tourBean);
    }

    private User getCustomer(long customerID) {
        if (customers.containsKey(customerID)) {
            return customers.get(customerID);
        }
        GenericDAO<User> userDAO = DAOFactory.instance().getUserDao();
        User user = userDAO.getByPK(customerID);
        customers.put(user.getId(), user);
        return user;
    }

    private OrderBean createOrderBean(User customer, Order order, TourBean tour) {
        OrderBean orderBean = new OrderBean();
        orderBean.setOrder(order);
        orderBean.setCustomer(customer);
        orderBean.setTour(tour);
        return orderBean;
    }

    /**
     * Create order.
     *
     * @param tourId
     * @param customerId
     * @return new order or null if tour with tourId not exists
     */
    public Order create(Long tourId, Long customerId) {
        Order order = new Order();
        order.setTourId(tourId);
        order.setCustomerId(customerId);
        order.setStatus(Order.Status.RESERVED);
        order.setDate(new Date());
        GenericDAO<Tour> tourDAO = DAOFactory.instance().getTourDao();
        Tour tour = tourDAO.getByPK(tourId);
        if (tour == null) {
            LOG.error(Message.ERR_CANNOT_CREATE_ORDER + tourId);
            return null;
        }
        order.setDiscount(DiscountManager.calculateFinalDiscount(tour.getMaxDiscount(), tour.getDiscountStep()));
        order.setPrice(DiscountManager.calculateFinalPrice(tour.getPrice(), order.getDiscount()));
        return orderDAO.persist(order);
    }

    public List<OrderBean> get(Sorter sorter, Pagination pagination, EntityCondition... conditions) {
        List<Order> orders = orderDAO.get(sorter, pagination, conditions);
        pagination.setItemsCount(orderDAO.count(conditions));
        List<OrderBean> orderBeen = new ArrayList<>();
        fillOrderBeenList(orders, orderBeen);
        return orderBeen;
    }

    public boolean setStatus(Long id, Order.Status status) {
        Order order = orderDAO.getByPK(id);
        if (order == null) {
            return false;
        }
        if (order.getStatus() == status) {
            return false;
        }
        order.setStatus(status);
        return orderDAO.update(order);
    }

    public boolean orderExists(Long tourID, Long userID) {
        return orderDAO.count(
                EntityCondition.eq(OrderMeta.CUSTOMER_ID, userID),
                EntityCondition.eq(OrderMeta.TOUR_ID, tourID),
                EntityCondition.neq(OrderMeta.STATUS, Order.Status.CANCELED)) > 0;
    }

    public ArrayList<OrderSummary> getSummary() {
        HashMap<String, Integer> paid = ((OrderJdbcDao)orderDAO).sum(Order.Status.PAID);
        HashMap<String, Integer> reserved = ((OrderJdbcDao)orderDAO).sum(Order.Status.RESERVED);
        LinkedHashSet<String> logins = new LinkedHashSet<>(paid.keySet());
        logins.addAll(reserved.keySet());
        ArrayList<OrderSummary> summary = new ArrayList<>();
        for (String login : logins) {
            OrderSummary orderSummary = new OrderSummary(login, paid.get(login), reserved.get(login));
            summary.add(orderSummary);
        }
        return summary;
    }

    private void fillOrderBeenList(List<Order> orders, List<OrderBean> orderBeen) {
        for (Order order : orders) {
            TourBean tourBean = getTour(order.getTourId());
            User customer = getCustomer(order.getCustomerId());
            OrderBean orderBean = createOrderBean(customer, order, tourBean);
            orderBeen.add(orderBean);
        }
    }
}
