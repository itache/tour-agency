package me.itache.service;

import me.itache.constant.Message;
import me.itache.dao.DAOFactory;
import me.itache.dao.modifier.EntityCondition;
import me.itache.dao.modifier.Pagination;
import me.itache.dao.modifier.Sorter;
import me.itache.utils.Hasher;
import me.itache.utils.db.JdbcTransactionManager;
import me.itache.utils.db.Operation;
import org.apache.log4j.Logger;
import me.itache.constant.Parameter;
import me.itache.dao.GenericDAO;
import me.itache.exception.ServiceException;
import me.itache.helpers.request.parameter.RequestParameter;
import me.itache.model.entity.Token;
import me.itache.model.entity.User;
import me.itache.model.meta.TokenMeta;
import me.itache.model.meta.UserMeta;
import me.itache.utils.db.TransactionManager;

import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Provides business logic for user actions
 */
public class UserService {
    private static final Logger LOG = Logger.getLogger(UserService.class);
    private static final String USER_SESSION_PARAM_NAME = "user";
    private static final DAOFactory factory = DAOFactory.instance();
    //TODO Singleton. Init on servlet startup???
    private HttpSession session;
    private GenericDAO<User> userDAO;
    private static final String HASH_ALGORITHM = "md5";

    public UserService(HttpSession session) {
        this.session = session;
        userDAO = DAOFactory.instance().getUserDao();
    }

    /**
     * Try to login user by login and password
     *
     * @param login
     * @param password
     * @return 1 - if user successfully logged in
     * 0 - if user exists, but not enabled
     * -1 - if not found
     */
    public int login(String login, String password) {
        List<User> users = userDAO.get(
                EntityCondition.eq(UserMeta.LOGIN, login),
                EntityCondition.eq(UserMeta.PASSWORD, hashPassword(password)));
        if (users.size() != 1) {
            return -1;
        }
        User user = users.get(0);
        if (!user.isEnabled()) {
            return 0;
        }
        session.setAttribute(USER_SESSION_PARAM_NAME, users.get(0));
        return 1;
    }

    public boolean isLoggedIn() {
        return session.getAttribute(USER_SESSION_PARAM_NAME) != null;
    }

    public User getCurrentUser() {
        User user = (User) session.getAttribute(USER_SESSION_PARAM_NAME);
        if (user == null) {
            user = new User();
            user.setRole(User.Role.ANONYMOUS);
        }
        return user;
    }

    public void logout() {
        session.invalidate();
    }

    public User createUser(HashMap<String, RequestParameter> parametersMap) {
        LOG.trace(String.format("Create new user : login - %s", parametersMap.get(Parameter.NEW_LOGIN).getValue()));
        User user = new User();
        bindUser(parametersMap, user);
        user = userDAO.persist(user);
        LOG.trace("User was created");
        return user;
    }

    public boolean blockUser(long id, boolean block) {
        LOG.trace("Try to change user. ID: " + id + " , block : " + block);
        User user = userDAO.getByPK(id);
        if (user == null) {
            LOG.debug("User not exists. ID: " + id);
            return false;
        }
        user.setBlocked(block);
        boolean result = userDAO.update(user);
        if (result) {
            LOG.trace("User was changed. Id: " + user.getId());
        } else {
            LOG.trace("Can not change user. Id: " + user.getId());
        }
        return result;
    }

    public List<User> get(Sorter sorter, Pagination pagination, EntityCondition... conditions) {
        EntityCondition notAdminCondition = EntityCondition.neq(UserMeta.ROLE, "ADMIN");
        List<EntityCondition> conditionList = new ArrayList<>();
        conditionList.add(notAdminCondition);
        if (conditions != null) {
            conditionList.addAll(Arrays.asList(conditions));
        }
        EntityCondition[] allConditions = conditionList.toArray(new EntityCondition[]{});
        pagination.setItemsCount(userDAO.count(allConditions));
        return userDAO.get(sorter, pagination, allConditions);
    }

    public User getByPk(long id) {
        return userDAO.getByPK(id);
    }

    public User getByEmail(String email) {
        List<User> users = userDAO.get(EntityCondition.eq(UserMeta.EMAIL, email));
        if (users.size() > 1) {
            LOG.error(Message.ERR_EMAIL_DUPLICATE + " " + email);
            throw new ServiceException(Message.ERR_EMAIL_DUPLICATE);
        }
        if (users.size() == 1) {
            return users.get(0);
        }
        return null;
    }

    public Token createToken(User user) {
        Token token = new Token(UUID.randomUUID().toString(), user.getId());
        TransactionManager manager = new JdbcTransactionManager();
        return manager.execute(() -> {
            GenericDAO<Token> tokenDao = factory.getTokenDao();
            tokenDao.delete(EntityCondition.eq(TokenMeta.USER_ID, user.getId()));
            return tokenDao.persist(token);
        });
    }

    public Token getToken(String token) {
        if (token == null) {
            return null;
        }
        GenericDAO<Token> dao = factory.getTokenDao();
        List<Token> tokens = dao.get(EntityCondition.eq(TokenMeta.TOKEN, token));
        if (tokens.size() == 1) {
            return tokens.get(0);
        }
        return null;
    }

    public boolean enableUser(Long id) {
        User user = userDAO.getByPK(id);
        if (user == null) {
            return false;
        }
        user.setEnabled(true);
        TransactionManager manager = new JdbcTransactionManager();
        LOG.info("Trying to enable user and delete all user tokens. User ID: " + user.getId());
        return manager.execute(() -> {
            boolean result = userDAO.update(user);
            LOG.info("User enabled: " + result);
            LOG.info("Tokens deleted: " +
                    factory.getTokenDao().delete(EntityCondition.eq(TokenMeta.USER_ID, user.getId())));
            return result;
        });
    }

    public boolean isTokenExpired(Token token) {
        Calendar cal = Calendar.getInstance();
        return (token.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0;
    }

    private void bindUser(HashMap<String, RequestParameter> parametersMap, User user) {
        user.setLogin(parametersMap.get(Parameter.NEW_LOGIN).getValue());
        user.setEmail(parametersMap.get(Parameter.EMAIL).getValue());
        user.setPassword(hashPassword(parametersMap.get(Parameter.NEW_PASSWORD).getValue()));
    }

    private String hashPassword(String password) {
        try {
            return Hasher.hash(password, HASH_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            LOG.error("No such hashing algorithm: " + HASH_ALGORITHM);
            throw new ServiceException(e);
        }
    }


    public void changePassword(User user, String password) {
        user.setPassword(hashPassword(password));
        TransactionManager manager = new JdbcTransactionManager();
        LOG.info("Trying to change user password and delete all user tokens. User ID: " + user.getId());
        manager.execute(new Operation<Void>() {
            @Override
            public Void execute() {
                LOG.info("Password changed: " +
                        userDAO.update(user));
                LOG.info("Tokens deleted: " +
                        factory.getTokenDao()
                                .delete(EntityCondition.eq(TokenMeta.USER_ID, user.getId())));
                return null;
            }
        });
    }
}
