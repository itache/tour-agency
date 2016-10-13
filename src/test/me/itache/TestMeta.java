package me.itache;

import me.itache.model.meta.EntityField;
import me.itache.model.meta.Meta;

/**
 *
 */
public class TestMeta implements Meta<TestEntity> {

    @Override
    public EntityField[] getFields() {
        return new EntityField[0];
    }

    @Override
    public EntityField<TestEntity> getIdField() {
        return null;
    }

    @Override
    public String getTableName() {
        return "test";
    }
}
