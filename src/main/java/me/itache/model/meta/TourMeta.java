package me.itache.model.meta;

import me.itache.model.entity.Tour;

/**
 * Represents Tour entity object data.
 */
public class TourMeta implements Meta<Tour> {
    public static final String TABLE = "tour";
    public static final TourMeta instance = new TourMeta();
    public static final EntityField<Tour> PRICE = new EntityField<>("price", instance);
    public static final EntityField<Tour> ID = new EntityField<>("id", instance);
    public static final EntityField<Tour> IS_HOT = new EntityField<>("isHot", instance);
    public static final EntityField<Tour> TYPE = new EntityField<>("type", instance);
    public static final EntityField<Tour> HOTEL_LEVEL = new EntityField<>("hotelLevel", instance);
    public static final EntityField<Tour> PERSONS = new EntityField<>("persons", instance);
    public static final EntityField<Tour> START_DATE = new EntityField<>("start", instance);
    public static final EntityField<Tour> DURATION = new EntityField<>("duration", instance);
    public static final EntityField<Tour> COUNTRY_CODE = new EntityField<>("countryCode", instance);
    public static final EntityField<Tour> DISCOUNT_STEP = new EntityField<>("discountStep", instance);
    public static final EntityField<Tour> MAX_DISCOUNT = new EntityField<>("maxDiscount", instance);

    private TourMeta() {
    }

    public String getTableName() {
        return TABLE;
    }

    public EntityField[] getFields() {
        return new EntityField[]{
                PRICE, IS_HOT, TYPE, HOTEL_LEVEL, PERSONS,
                START_DATE, DURATION, COUNTRY_CODE, DISCOUNT_STEP, MAX_DISCOUNT
        };
    }

    @Override
    public EntityField<Tour> getIdField() {
        return ID;
    }
}
