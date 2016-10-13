package me.itache.model.meta;

import me.itache.model.entity.Tour;
import org.junit.Assert;
import org.junit.Test;
import me.itache.TestEntity;
import me.itache.TestMeta;

import java.util.Date;

/**
 *
 */
public class EntityFieldTest {
    @Test
    public void shouldFillObjectWithPrimitives() {
        Tour tour = new Tour();
        EntityField<Tour> field = new EntityField<>("id", TourMeta.instance);
        field.fillObject(tour, 12L);
        Assert.assertEquals(new Long(12), tour.getId());
    }

    @Test
    public void shouldFillObjectWithEnum() {
        Tour tour = new Tour();
        EntityField<Tour> field = new EntityField<>("type", TourMeta.instance);
        field.fillObject(tour, Tour.Type.EXCURSION);
        Assert.assertEquals(Tour.Type.EXCURSION, tour.getType());
    }

    @Test
    public void shouldFillObjectWithEnumRaw() {
        Tour tour = new Tour();
        EntityField field = new EntityField<>("type", TourMeta.instance);
        field.fillObject(tour, "excursion");
        Assert.assertEquals(Tour.Type.EXCURSION, tour.getType());
    }

    @Test
    public void shouldFillObjectWithDate() {
        TestEntity testEntity = new TestEntity();
        EntityField field = new EntityField<>("date", new TestMeta());
        Date date = new Date();
        field.fillObject(testEntity, date);
        Assert.assertEquals(date, testEntity.getDate());
    }

    @Test
    public void shouldExtractFieldValue() {
        TestEntity testEntity = new TestEntity();
        EntityField field = new EntityField<>("date", new TestMeta());
        Date date = new Date();
        testEntity.setDate(date);
        Assert.assertEquals(date, field.extractValue(testEntity));
    }
}
