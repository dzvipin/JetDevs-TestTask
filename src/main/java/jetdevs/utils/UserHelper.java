package jetdevs.utils;

import com.google.common.collect.Lists;
import jetdevs.constants.ErrorCodes;
import jetdevs.model.User;

import java.util.List;

public class UserHelper {

    public static List<User> getUsers() {
        return Lists.newArrayList(
                new User(1, ErrorCodes.DEFAULT_USER_NAME, ErrorCodes.DEFAULT_USER_PASSWORD, ErrorCodes.USER_ROLE),
                new User(2, ErrorCodes.DEFAULT_ADMIN_NAME, ErrorCodes.DEFAULT_ADMIN_PASSWORD, ErrorCodes.ADMIN_ROLE));
    }

    public static User getUser(String userName) {
        return getUsers()
                .stream()
                .filter(user -> user
                        .getUserName()
                        .equals(userName))
                .findFirst()
                .orElse(null);
    }
    public static User getUserById(int id) {
        return getUsers()
                .stream()
                .filter(user -> user
                        .getId()==id)
                .findFirst()
                .orElse(null);
    }

}
