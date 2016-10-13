package me.itache.dao.jdbc;

import me.itache.constant.Message;
import me.itache.exception.DAOException;
import me.itache.model.entity.Order;
import me.itache.model.entity.User;
import me.itache.model.meta.Meta;
import me.itache.model.meta.OrderMeta;
import me.itache.model.meta.UserMeta;
import me.itache.utils.db.NamedPreparedStatement;
import me.itache.utils.db.pool.ConnectionPool;
import me.itache.utils.db.querybuilder.QueryBuilder;
import me.itache.utils.db.querybuilder.SelectQueryBuilder;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

public class OrderJdbcDao extends AbstractJdbcDao<Order> {
    private static final Meta<Order> metaObject = OrderMeta.instance;
    private static final String SUMMARY_QUERY =
            "SELECT login, SUM(order.price) FROM `order` JOIN `user` ON user.id = order.customer_id WHERE order.status = @status GROUP BY customer_id ORDER BY SUM(order.price) DESC";

    public OrderJdbcDao(ConnectionPool connectionPool) {
        super(connectionPool);
    }

    @Override
    protected Meta<Order> getMeta() {
        return metaObject;
    }

    @Override
    protected Order getNew() {
        return new Order();
    }

    @Override
    protected SelectQueryBuilder getSelectQueryBuilder() {
        Meta<User> userMeta = UserMeta.instance;
        return QueryBuilder.select(metaObject.getTableName()).join(userMeta.getTableName(), OrderMeta.CUSTOMER_ID.getColumnName(), UserMeta.ID.getColumnName());
    }

    public LinkedHashMap<String, Integer> sum(Order.Status status) {
        Connection connection = connectionPool.getConnection();
        LinkedHashMap<String, Integer> summary;
        try (NamedPreparedStatement statement = new NamedPreparedStatement(connection, SUMMARY_QUERY)) {
            statement.setObject("status", status.toString());
            ResultSet rs = statement.executeQuery();
            summary = formResultSet(rs);
        } catch (SQLException ex) {
            LOG.error(Message.ERR_CANNOT_EXECUTE_QUERY  +  " " + SUMMARY_QUERY);
            throw new DAOException(Message.ERR_CANNOT_EXECUTE_QUERY, ex);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return summary;
    }

    private LinkedHashMap<String, Integer> formResultSet(ResultSet rs) throws SQLException {
        LinkedHashMap<String, Integer> summary = new LinkedHashMap<>();
        while (rs.next()) {
            summary.put(rs.getString(1), rs.getInt(2));
        }
        return summary;
    }


}
