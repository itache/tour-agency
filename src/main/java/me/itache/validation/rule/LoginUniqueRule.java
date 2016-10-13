package me.itache.validation.rule;

import me.itache.dao.DAOFactory;
import me.itache.dao.modifier.EntityCondition;
import me.itache.model.meta.UserMeta;

/**
 *
 */
public class LoginUniqueRule implements Rule {
    @Override
    public boolean validate(String data) {
        if(data == null) {
            return true;
        }
        return DAOFactory.instance().getUserDao().count(EntityCondition.eq(UserMeta.LOGIN, data)) == 0;
    }
}
