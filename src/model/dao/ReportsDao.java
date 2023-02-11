package model.dao;

import model.Employee;

import java.util.function.Predicate;

public interface ReportsDao {
   void reportStructure();
   void reportAverage();
   void reportAverageDepartment();
   void reportTopTenSalary();
   void reportTopTenYear();
   void findEmployee(Predicate<Employee> predicate,String str,
                     String punktName,String fileReport);
}
