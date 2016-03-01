package sirun.tannt275.funie3s.model;

import com.google.gson.Gson;

/**
 * Created by tannt_000 on 11/30/2015.
 */
public class BaseModel {

    public String convertToString(){
        return new Gson().toJson(this);
    }
}

