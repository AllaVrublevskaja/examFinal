package model.dao;

import java.util.Map;

public interface ProfessionDao {
    void save ();
    Map<String,String[]> allProfession();
}
