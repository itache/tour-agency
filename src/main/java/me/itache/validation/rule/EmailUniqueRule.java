package me.itache.validation.rule;

import me.itache.dao.DAOFactory;
import me.itache.dao.modifier.EntityCondition;
import me.itache.model.meta.UserMeta;

/**
 * Checks that user with given email not exists in DB
 */
public class EmailUniqueRule implements Rule {
    @Override
    public boolean validate(String data) {
        if(data == null) {
            return true;
        }
        return DAOFactory.instance().getUserDao().count(EntityCondition.eq(UserMeta.EMAIL, data)) == 0;
    }
}
