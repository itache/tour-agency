package me.itache.model.meta;

import me.itache.model.entity.User;

/**
 * Represents User entity object data.
 */
public class UserMeta implements Meta<User> {
    private static final String TABLE = "user";
    public static final Meta<User> instance = new UserMeta();
    public static final EntityField<User> ID = new EntityField<>("id", instance);
    public static final EntityField<User> LOGIN = new EntityField<>("login", instance);
    public static final EntityField<User> PASSWORD = new EntityField<>("password", instance);
    public static final EntityField<User> ROLE = new EntityField<>("role", instance);
    public static final EntityField<User> EMAIL = new EntityField<>("email", instance);
    public static final EntityField<User> ENABLED = new EntityField<>("enabled", instance);
    public static final EntityField<User> BLOCKED = new EntityField<>("blocked", instance);

    private UserMeta() {
    }

    public String getTableName() {
        return TABLE;
    }

    public EntityField<User>[] getFields() {
        return new EntityField[]{
                LOGIN, PASSWORD, ROLE, EMAIL, ENABLED, BLOCKED
        };
    }

    @Override
    public EntityField<User> getIdField() {
        return ID;
    }
}
