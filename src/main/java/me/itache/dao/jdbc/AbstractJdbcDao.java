package me.itache.dao.jdbc;

import me.itache.constant.Message;
import me.itache.dao.GenericDAO;
import me.itache.dao.Identified;
import me.itache.dao.modifier.EntityCondition;
import me.itache.dao.modifier.Pagination;
import me.itache.dao.modifier.Sorter;
import me.itache.exception.DAOException;
import me.itache.model.meta.Meta;
import me.itache.utils.db.NamedPreparedStatement;
import me.itache.utils.db.pool.ConnectionPool;
import me.itache.utils.db.querybuilder.DeleteQueryBuilder;
import me.itache.utils.db.querybuilder.QueryBuilder;
import me.itache.utils.db.querybuilder.SelectQueryBuilder;
import me.itache.utils.db.querybuilder.UpdateQueryBuilder;
import org.apache.log4j.Logger;
import me.itache.model.meta.EntityField;
import me.itache.model.meta.LocalizedMeta;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 *
 */
abstract public class AbstractJdbcDao<T extends Identified> implements GenericDAO<T> {
    protected static final Logger LOG = Logger.getLogger(AbstractJdbcDao.class);

    protected ConnectionPool connectionPool;

    public AbstractJdbcDao(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    protected Locale getLocale() {
        return null;
    }

    protected String getTableName() {
        return getMeta().getTableName();
    }

    protected abstract Meta<T> getMeta();

    protected abstract T getNew();

    @Override
    public T getByPK(long id) {
        List<T> list;
        SelectQueryBuilder queryBuilder = getSelectQueryBuilder();
        HashMap<String, Object> parameters = new HashMap<>();
        putIDField(id, queryBuilder, parameters);
        list = getModels(queryBuilder, parameters);
        if (list == null || list.size() == 0) {
            LOG.warn(Message.WRN_OBJECT_NOT_FOUND + " id = " + id);
            return null;
        }
        if (list.size() > 1) {
            LOG.error(Message.ERR_TOO_MANY_OBJECTS_FOUND + " id = " + id);
            throw new DAOException(Message.ERR_TOO_MANY_OBJECTS_FOUND);
        }
        return list.get(0);
    }

    private void putIDField(long id, QueryBuilder queryBuilder, HashMap<String, Object> parameters) {
        EntityField<T> idField = getMeta().getIdField();
        queryBuilder.where(getColumnName(idField), "=", idField.getFieldName());
        parameters.put(idField.getFieldName(), id);
    }

    private List<T> getModels(SelectQueryBuilder queryBuilder, HashMap<String, Object> parameters) {
        List<T> list;
        Connection connection = connectionPool.getConnection();
        try (NamedPreparedStatement statement = new NamedPreparedStatement(connection, queryBuilder.getQuery())) {
            bindParameters(parameters, statement);
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (SQLException ex) {
            LOG.error(Message.ERR_CANNOT_EXECUTE_QUERY + "Query: " + queryBuilder.getQuery());
            throw new DAOException(Message.ERR_CANNOT_EXECUTE_QUERY, ex);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return list;
    }

    private void bindParameters(HashMap<String, Object> parameters, NamedPreparedStatement statement) throws SQLException {
        for (Map.Entry<String, Object> param : parameters.entrySet()) {
            statement.setObject(param.getKey(), param.getValue());
        }
    }

    @Override
    public List<T> getAll() {
        return getModels(getSelectQueryBuilder(), null);
    }

    @Override
    public boolean delete(long id) {
        DeleteQueryBuilder queryBuilder = QueryBuilder.delete(getTableName());
        HashMap<String, Object> parameters = new HashMap<>();
        putIDField(id, queryBuilder, parameters);
        int count = updateModels(queryBuilder, parameters);
        if (count > 1) {
            LOG.error(Message.ERR_TOO_MANY_OBJECTS_DELETED + " Query: " + queryBuilder.getQuery());
            throw new DAOException(Message.ERR_TOO_MANY_OBJECTS_DELETED + ".Count: " + count);
        }
        return count == 1;
    }

    private int updateModels(QueryBuilder queryBuilder, HashMap<String, Object> parameters) {
        Connection connection = connectionPool.getConnection();
        try (NamedPreparedStatement statement = new NamedPreparedStatement(connection, queryBuilder.getQuery())) {
            LOG.debug("Parameters to bind:" + parameters);
            bindParameters(parameters, statement);
            statement.executeUpdate();
            return statement.getUpdateCount();
        } catch (SQLException ex) {
            LOG.error(Message.ERR_CANNOT_EXECUTE_QUERY + "Query: " + queryBuilder.getQuery());
            throw new DAOException(Message.ERR_CANNOT_EXECUTE_QUERY, ex);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public boolean update(T object) {
        HashMap<String, Object> parameters = getObjectMap(object);
        UpdateQueryBuilder queryBuilder = QueryBuilder.update(getTableName(), getColumnMap());
        putIDField(object.getId(), queryBuilder, parameters);
        int count = updateModels(queryBuilder, parameters);
        if (count > 1) {
            LOG.error(Message.ERR_TOO_MANY_OBJECTS_UPDATED + " Query: " + queryBuilder.getQuery());
            throw new DAOException(Message.ERR_TOO_MANY_OBJECTS_UPDATED + ".Count: " + count);
        }
        return count == 1;
    }

    private HashMap<String, Object> getObjectMap(T object) {
        return getMeta().getObjectMap(object);
    }

    private HashMap<String, String> getColumnMap() {
        HashMap<String, String> columnMap = new HashMap<>();
        for (EntityField field : getMeta().getFields()) {
            columnMap.put(field.getColumnName(), field.getFieldName());
        }
        return columnMap;
    }

    @Override
    public T persist(T object) {
        if (object.getId() != null) {
            throw new DAOException("Object is already persist.");
        }
        HashMap<String, Object> parameters = getObjectMap(object);
        HashMap<String, String> columns = getColumnMap();
        QueryBuilder queryBuilder = QueryBuilder.insert(getTableName(), columns);
        EntityField<T> idField = getMeta().getIdField();
        parameters.put(idField.getFieldName(), object.getId());
        columns.put(idField.getColumnName(), idField.getFieldName());
        Connection connection = connectionPool.getConnection();
        try (NamedPreparedStatement statement = new NamedPreparedStatement(connection, queryBuilder.getQuery(), Statement.RETURN_GENERATED_KEYS)) {
            bindParameters(parameters, statement);
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            object.setId(rs.getLong(1));
        } catch (SQLException ex) {
            LOG.error(Message.ERR_CANNOT_EXECUTE_QUERY + "Query: " + queryBuilder.getQuery());
            throw new DAOException(Message.ERR_CANNOT_EXECUTE_QUERY, ex);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return object;
    }

    @Override
    public List<T> get(EntityCondition<T>... conditions) {
        SelectQueryBuilder builder = getSelectQueryBuilder();
        HashMap<String, Object> parameters = prepareConditions(conditions, builder);
        return getModels(builder, parameters);
    }

    private HashMap<String, Object> prepareConditions(EntityCondition<T>[] conditions, QueryBuilder builder) {
        HashMap<String, Object> parameters = new HashMap<>();
        for (EntityCondition condition : conditions) {
            EntityField field = condition.getField();
            String columnName = getColumnName(field);
            String placeholder = condition.getOperator().name() + "_" + field.getFieldName();
            if(!parameters.containsKey(placeholder)) {
                parameters.put(placeholder, condition.getValue());
                builder.where(columnName, condition.getOperator().toString(), placeholder);
            }
        }
        return parameters;
    }

    private String getColumnName(EntityField<T> field) {
        if (field.getMeta() instanceof LocalizedMeta && getLocale() != null) {
            return ((LocalizedMeta) field.getMeta()).getTableName(getLocale()) + "." + field.getColumnName();
        }
        return field.getMeta().getTableName() + "." + field.getColumnName();
    }

    protected SelectQueryBuilder getSelectQueryBuilder() {
        return QueryBuilder.select(getTableName());
    }

    @Override
    @SafeVarargs
    public final List<T> get(Sorter sorter, EntityCondition<T>... conditions) {
        SelectQueryBuilder builder = getSelectQueryBuilder();
        buildOrderConditions(sorter, builder);
        HashMap<String, Object> parameters = prepareConditions(conditions, builder);
        return getModels(builder, parameters);
    }

    @Override
    public List<T> get(Sorter sorter, Pagination pagination, EntityCondition<T>... conditions) {
        SelectQueryBuilder builder = getSelectQueryBuilder();
        buildOrderConditions(sorter, builder);
        builder.limit(pagination.getItemsOnPageCount());
        builder.offset(pagination.getItemsOnPageCount() * (pagination.getCurrentPageNumber() - 1));
        HashMap<String, Object> parameters = prepareConditions(conditions, builder);
        return getModels(builder, parameters);
    }

    private void buildOrderConditions(Sorter sorter, SelectQueryBuilder builder) {
        for (Sorter.OrderCondition condition : sorter.getConditions()) {
            builder.orderBy(condition.getField(), condition.getOrder());
        }
    }

    @Override
    public int count(EntityCondition<T>... conditions) {
        SelectQueryBuilder builder = getSelectQueryBuilder();
        builder.count();
        HashMap<String, Object> parameters = prepareConditions(conditions, builder);
        Connection connection = connectionPool.getConnection();
        try (NamedPreparedStatement statement = new NamedPreparedStatement(connection, builder.getQuery())) {
            bindParameters(parameters, statement);
            ResultSet rs = statement.executeQuery();
            rs.first();
            return rs.getInt(1);
        } catch (SQLException ex) {
            LOG.error(Message.ERR_CANNOT_EXECUTE_QUERY + "Query: " + builder.getQuery());
            throw new DAOException(Message.ERR_CANNOT_EXECUTE_QUERY, ex);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public int delete(EntityCondition<T>... conditions) {
        DeleteQueryBuilder queryBuilder = QueryBuilder.delete(getTableName());
        return updateModels(queryBuilder, prepareConditions(conditions, queryBuilder));
    }

    private List<T> parseResultSet(ResultSet rs) throws SQLException {
        List<T> list = new ArrayList<>();
        while (rs.next()) {
            list.add(createObject(rs));
        }
        return list;
    }

    private T createObject(ResultSet rs) throws SQLException {
        T object = getNew();
        getMeta().getIdField().fillObject(object, rs.getObject(getMeta().getIdField().getColumnName()));
        for (EntityField<T> field : getMeta().getFields()) {
            field.fillObject(object, rs.getObject(field.getColumnName()));
        }
        return object;
    }
}