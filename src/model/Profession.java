package model;

public class Profession {
    private final String department;
    private final String profession;

    public Profession(String department, String profession) {
        this.department = department;
        this.profession = profession;
    }

    public String getDepartment() {
        return department;
    }

    public String getProfession() {
        return profession;
    }
}
