package dataaccess;

import model.UserData;

import java.util.HashMap;

public class MemoryUserDAO implements UserDAO{
    public final static HashMap<String, UserData> USERS = new HashMap<>();

    public void createUser(UserData userData){
        USERS.put(userData.username(), userData);
    }

    public UserData getUser(String username){
        return USERS.get(username);
    }
    public void clear(){
        USERS.clear();
    }

}
