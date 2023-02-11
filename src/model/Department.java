package model;

import java.util.HashMap;

public class Department {
    private String department;
    private String supervisor;
    HashMap<String,String > departmentMap;

    public Department(String department, String supervisor) {
        this.department = department;
        this.supervisor = supervisor;
    }

    public String getDepartment() {
        return department;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }
}
