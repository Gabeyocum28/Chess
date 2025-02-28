package dataaccess;

import model.AuthData;
import model.UserData;

import java.util.HashMap;

public class MemoryAuthDAO implements AuthDAO{
    public final static HashMap<String, AuthData> authtokens = new HashMap<>();

    public void createAuth(AuthData authData){
        authtokens.put(authData.authToken(), authData);
    }
    public AuthData getAuth(String authtoken){
        return authtokens.get(authtoken);
    }
    public void clear(){
        authtokens.clear();
    }
}
