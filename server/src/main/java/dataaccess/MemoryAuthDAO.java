package dataaccess;

import model.AuthData;

import java.util.HashMap;

public class MemoryAuthDAO implements AuthDAO{
    public final static HashMap<String, AuthData> AuthTokens = new HashMap<>();

    public void createAuth(AuthData authData){
        AuthTokens.put(authData.authToken(), authData);
    }
    public AuthData getAuth(String authtoken){
        return AuthTokens.get(authtoken);
    }
    public void deleteAuth(String authtoken){
        AuthTokens.remove(authtoken);
    }
    public void clear(){
        AuthTokens.clear();
    }
}
