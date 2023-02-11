package model.dao;

import model.Employee;

import java.util.List;


public interface EmployeeDao {
    void save ();
    List<Employee> allEmployee();

}
