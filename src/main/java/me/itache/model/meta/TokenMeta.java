package me.itache.model.meta;

import me.itache.model.entity.Token;

/**
 * Represents Token entity object data.
 */
public class TokenMeta implements Meta<Token> {
    private static final String TABLE = "token";
    public static final Meta<Token> instance = new TokenMeta();
    public static final EntityField<Token> ID = new EntityField<>("id", instance);
    public static final EntityField<Token> TOKEN = new EntityField<>("token", instance);
    public static final EntityField<Token> USER_ID = new EntityField<>("userId", instance);
    public static final EntityField<Token> EXPIRY_DATE = new EntityField<>("expiryDate", instance);

    private TokenMeta() {
    }

    @Override
    public EntityField<Token>[] getFields() {
        return new EntityField[]{
              TOKEN, USER_ID, EXPIRY_DATE
        };
    }

    @Override
    public EntityField<Token> getIdField() {
        return ID;
    }

    @Override
    public String getTableName() {
        return TABLE;
    }
}
