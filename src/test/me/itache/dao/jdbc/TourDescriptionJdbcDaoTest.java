package me.itache.dao.jdbc;

import me.itache.dao.modifier.EntityCondition;
import me.itache.dao.modifier.Sorter;
import me.itache.utils.db.pool.DataSourceConnectionPool;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import me.itache.TestDataSourceInitializer;
import me.itache.dao.GenericDAO;
import me.itache.exception.DAOException;
import me.itache.model.entity.TourDescription;
import me.itache.model.meta.TourDescriptionMeta;

import javax.sql.DataSource;
import java.util.List;
import java.util.Locale;

public class TourDescriptionJdbcDaoTest {
    private static GenericDAO<TourDescription> daoEN;
    private static GenericDAO<TourDescription> daoRu;

    @BeforeClass
    public static void init() {
        DataSource dataSource = TestDataSourceInitializer.getInstance().getDatasource();
        if(!DataSourceConnectionPool.isInit()){
            DataSourceConnectionPool.init(dataSource);
        }
        daoEN = new TourDescriptionJdbcDao(DataSourceConnectionPool.getInstance(), Locale.ENGLISH);
        daoRu = new TourDescriptionJdbcDao(DataSourceConnectionPool.getInstance(), Locale.forLanguageTag("ru"));
    }

    @Test
    public void shouldProperlySortObjectList() {
        Sorter sorter = new Sorter("title", Sorter.Order.DESC);
        List<TourDescription> descriptions = daoEN.get(sorter);
        for (int i = 0; i < descriptions.size(); i++) {
            if (i != 0) {
                String currentTitle = descriptions.get(i).getTitle();
                String prevTitle = descriptions.get(i - 1).getTitle();
                Assert.assertTrue(currentTitle.compareTo(prevTitle) <= 0);
            }
        }
    }

    @Test
    public void shouldReturnObjectListByConditions() {
        List<TourDescription> descriptions = daoEN.get(
                EntityCondition.lessThan(TourDescriptionMeta.ID, 10),
                EntityCondition.greaterThan(TourDescriptionMeta.ID, 5)
        );
        for (int i = 0; i < descriptions.size(); i++) {
            if (i != 0) {
                Long currentID = descriptions.get(i).getId();
                Assert.assertTrue(currentID >= 5 && currentID <= 10);
            }
        }
    }

    @Test
    public void shouldIgnoreControversialConditions() {
        List<TourDescription> descriptions = daoRu.get(
                EntityCondition.lessThan(TourDescriptionMeta.ID, 5),
                EntityCondition.lessThan(TourDescriptionMeta.ID, 10)
        );
        for (int i = 0; i < descriptions.size(); i++) {
            if (i != 0) {
                Long currentID = descriptions.get(i).getId();
                Assert.assertTrue(currentID <= 5);
            }
        }
    }

    @Test(expected = DAOException.class)
    public void shouldThrowExceptionOnUnsupportedLocale() {
        new TourDescriptionJdbcDao(DataSourceConnectionPool.getInstance(), Locale.CHINA);
    }
}
