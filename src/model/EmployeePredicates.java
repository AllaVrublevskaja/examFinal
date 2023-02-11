package model;

import java.util.function.Predicate;

public class EmployeePredicates {
    public Predicate<Employee> surname(String str){
        return arr->arr.getSurname().equals(str);
    }
    public Predicate<Employee> function(String str){
        return arr->arr.getFunction().equals(str);
    }
    public Predicate<Employee> department(String str){
        return arr->arr.getDepartment().equals(str);
    }
    public Predicate<Employee> supervisor(String str){
        return arr->arr.getSupervisor().equals(str);
    }
    public Predicate<Employee> fio (String surname,String name,String patronymic){
        return arr->arr.getSurname().equals(surname) &&
                arr.getName().equals(name) && arr.getPatronymic().equals(patronymic);
    }
}
