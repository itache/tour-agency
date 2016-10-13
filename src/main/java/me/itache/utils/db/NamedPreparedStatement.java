package me.itache.utils.db;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Prepared statement wrapper which provides parameter binding by name.
 * E.g.
 * Query: SELECT * names WHERE id = @id
 * namedPreparedStatement.setInt("id", 100)
 */
public class NamedPreparedStatement implements AutoCloseable {
    private static final Logger LOG = Logger.getLogger(NamedPreparedStatement.class);
    private static final String PLACEHOLDER_MARK = "@";
    private Pattern findParametersPattern = Pattern.compile("(?<!')(@[\\w]*)(?!')");
    private PreparedStatement prepStmt;
    private List<String> fields = new ArrayList<>();

    /**
     * @param conn
     * @param sql query with placeholder to bind value
     * @throws SQLException
     */
    public NamedPreparedStatement(Connection conn, String sql) throws SQLException {
        sql = formFieldList(sql);
        prepStmt = conn.prepareStatement(sql.replaceAll(findParametersPattern.pattern(), "?"));
    }

    /**
     *
     * @param conn
     * @param sql query with placeholder to bind value.
     * @param returnGeneratedKeys a flag indicating whether auto-generated keys should be returned
     * @throws SQLException
     */
    public NamedPreparedStatement(Connection conn, String sql, int returnGeneratedKeys) throws SQLException {
        sql = formFieldList(sql);
        prepStmt = conn.prepareStatement(sql.replaceAll(findParametersPattern.pattern(), "?"), returnGeneratedKeys);
    }

    private String formFieldList(String sql) {
        Matcher matcher = findParametersPattern.matcher(sql);
        while (matcher.find()) {
            fields.add(matcher.group().substring(1));
        }
        LOG.debug("Field list was built: " + fields);
        return sql;
    }

    public PreparedStatement getPreparedStatement() {
        return prepStmt;
    }

    public ResultSet executeQuery() throws SQLException {
        return prepStmt.executeQuery();
    }

    public int executeUpdate() throws SQLException {
        return prepStmt.executeUpdate();
    }

    public void close() throws SQLException {
        prepStmt.close();
    }

    public void setObject(String name, Object x) throws SQLException {
        LOG.debug("Trying to set parameter:" + name);
        prepStmt.setObject(getIndex(name), x);
        LOG.debug(String.format("Parameter was set. Name: %s. Value: %s. Index: %s", name, x, getIndex(name)));
    }

    public void setInt(String name, int value) throws SQLException {
        prepStmt.setInt(getIndex(name), value);
    }

    private int getIndex(String name) {
        return fields.indexOf(name) + 1;
    }

    public int getUpdateCount() throws SQLException {
        return prepStmt.getUpdateCount();
    }

    public ResultSet getGeneratedKeys() throws SQLException {
        return prepStmt.getGeneratedKeys();
    }
}
