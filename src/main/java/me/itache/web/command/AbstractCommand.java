package me.itache.web.command;

import me.itache.model.entity.User;

import java.util.Arrays;
import java.util.List;

/**
 *
 */
public abstract class AbstractCommand implements Command {
    protected List<User.Role> permissions;

    public AbstractCommand(User.Role... permissions) {
         this.permissions = Arrays.asList(permissions);
    }

    @Override
    public boolean checkPermission(User user) {
        if(user == null) {
            return false;
        }
        return permissions.contains(user.getRole());
    }
}
