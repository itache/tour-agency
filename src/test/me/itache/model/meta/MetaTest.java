package me.itache.model.meta;

import me.itache.model.entity.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

public class MetaTest {
    @Test
    public void shouldProduceObjectMap(){
        User user = new User();
        user.setRole(User.Role.ADMIN);
        user.setLogin("test");
        HashMap<String, Object> expectedMap = new HashMap<>();
        expectedMap.put("login", user.getLogin());
        expectedMap.put("password", user.getPassword());
        expectedMap.put("email", user.getEmail());
        expectedMap.put("role", user.getRole());
        expectedMap.put("blocked", user.isBlocked());
        expectedMap.put("enabled", false);
        HashMap<String, Object> actualMap = UserMeta.instance.getObjectMap(user);
        Assert.assertEquals(actualMap.toString(), expectedMap.toString());
    }
}
