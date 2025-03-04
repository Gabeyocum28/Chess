package dataaccess;

import model.AuthData;

import java.util.HashMap;

public class MemoryAuthDAO implements AuthDAO{
    public final static HashMap<String, AuthData> AUTH_TOKENS = new HashMap<>();

    public void createAuth(AuthData authData){
        AUTH_TOKENS.put(authData.authToken(), authData);
    }
    public AuthData getAuth(String authtoken){
        return AUTH_TOKENS.get(authtoken);
    }
    public void deleteAuth(String authtoken){
        AUTH_TOKENS.remove(authtoken);
    }
    public void clear(){
        AUTH_TOKENS.clear();
    }
}
