package me.itache.model.meta;

import me.itache.helpers.LocaleManager;
import me.itache.model.entity.TourDescription;

import java.util.Locale;

/**
 * Represents TourDescription entity object data.
 */
public class TourDescriptionMeta implements LocalizedMeta<TourDescription> {
    public static final String TABLE = "tour_description";
    public static final LocalizedMeta<TourDescription> instance = new TourDescriptionMeta();
    public static final EntityField<TourDescription> ID = new EntityField<>("id", instance);
    public static final EntityField<TourDescription> TOUR_ID = new EntityField<>("tourId", instance);
    public static final EntityField<TourDescription> TITLE = new EntityField<>("title", instance);
    public static final EntityField<TourDescription> DESCRIPTION = new EntityField<>("description", instance);

    private TourDescriptionMeta() {
    }

    @Override
    public String getTableName() {
        return TABLE + "_" + LocaleManager.instance().getCurrentLocale();
    }

    @Override
    public String getTableName(Locale locale) {
        return TABLE + "_" + locale;
    }

    @Override
    public EntityField[] getFields() {
        return new EntityField[]{
                TOUR_ID, TITLE, DESCRIPTION
        };
    }

    @Override
    public EntityField<TourDescription> getIdField() {
        return ID;
    }
}
