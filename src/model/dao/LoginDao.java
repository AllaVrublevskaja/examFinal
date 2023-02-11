package model.dao;

import java.util.Map;

public interface LoginDao {
    void save ();
    Map<String,String> allLogin();
}
