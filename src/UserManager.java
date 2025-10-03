import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class UserManager {

    private final Map<String, User> users = new LinkedHashMap<>();


    // adding a new user
    public boolean addUser(User user) {
        String key = user.getFullName().toLowerCase();
        if (users.containsKey(key)) {
            Logger.error("Пользователь с таким ФИО уже существует: " + user.getFullName());
            return false;
        }
        users.put(user.getFullName(), user);
        return true;
    }

    //finding user by full name(ignore case)
    public User findUser(String fullName) {
        return users.get(fullName.toLowerCase());
    }

    // deleting user by full name
    public boolean removeUser(String fullName) {
        String key= fullName.toLowerCase();
        if(!users.containsKey(key)) {
            Logger.error("Пользователь не найден: " + fullName);
            return false;
        }
        users.remove(key);
        return true;
    }

    // ger all users
    public Collection<User> getAllUsers() {
        return users.values();
    }

    // set new user data
    public void setUsers(Collection<User> userList) {
        users.clear();
        for (User user : userList) {
            users.put(user.getFullName().toLowerCase(), user);
        }
    }

}
