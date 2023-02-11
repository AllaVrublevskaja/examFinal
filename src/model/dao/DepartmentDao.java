package model.dao;

import java.util.Map;

public interface DepartmentDao {
    void save ();
    Map<String,String> allDepartment();
}
