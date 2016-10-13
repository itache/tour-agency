package me.itache.dao.jdbc;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import me.itache.TestDataSourceInitializer;
import me.itache.dao.GenericDAO;
import me.itache.dao.modifier.EntityCondition;
import me.itache.model.entity.User;
import me.itache.model.meta.UserMeta;
import me.itache.utils.db.JdbcTransactionManager;
import me.itache.utils.db.Operation;
import me.itache.utils.db.TransactionManager;
import me.itache.utils.db.pool.DataSourceConnectionPool;

import javax.sql.DataSource;

public class UserJdbcDaoTest {
    private static GenericDAO<User> dao;

    @BeforeClass
    public static void init() {
        DataSource dataSource = TestDataSourceInitializer.getInstance().getDatasource();
        if(!DataSourceConnectionPool.isInit()){
            DataSourceConnectionPool.init(dataSource);
        }
        dao = new UserJdbcDao(DataSourceConnectionPool.getInstance());
    }

    @Test
    public void shouldGetObjectByPK(){
        User expected = new User();
        expected.setId(1L);
        expected.setLogin("admin");
        expected.setPassword("21232f297a57a5a743894a0e4a801fc3");
        expected.setRole(User.Role.ADMIN);
        expected.setEmail("");
        Assert.assertEquals(expected, dao.getByPK(1L));
    }

    @Test
    public void shouldPersistAndDeleteObject() {
        User expected = new User();
        expected.setLogin("new_one");
        expected.setPassword("21232f297a57a5a743894a0e4a801fc3");
        expected.setRole(User.Role.ADMIN);
        expected.setEmail("admin@mail.ru");

        TransactionManager manager = new JdbcTransactionManager();
        manager.execute(new Operation<Void>() {
            @Override
            public Void execute() {
                expected.setId(dao.persist(expected).getId());
                Assert.assertEquals(expected, dao.getByPK(expected.getId()));
                Assert.assertTrue(dao.delete(expected.getId()));
                Assert.assertNull(dao.getByPK(expected.getId()));
                return null;
            }
        });
    }

    @Test
    public void shouldUpdateObject() {
        TransactionManager manager = new JdbcTransactionManager();
        manager.execute(new Operation<Void>() {
            @Override
            public Void execute() {
                User user = dao.getByPK(2);
                String previousEmail = user.getEmail();
                String newEmail = "new@email.org";
                user.setEmail(newEmail);
                Assert.assertTrue(dao.update(user));
                Assert.assertEquals(newEmail, dao.getByPK(2).getEmail());
                user.setEmail(previousEmail);
                Assert.assertTrue(dao.update(user));
                Assert.assertEquals(previousEmail, dao.getByPK(2).getEmail());
                return null;
            }
        });
        System.out.println(dao.count(EntityCondition.eq(UserMeta.ID, 100)));
    }
}
