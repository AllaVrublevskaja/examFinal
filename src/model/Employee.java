package model;

public class Employee {
    private String surname;         // фамилия
    private String name;            // имя
    private String patronymic;      // отчество
    private String birthday;        // дата рождения
    private Gender gender;          // пол
    private String phone;           // телефон
    private String function;        // должность
    private String department;  // отдел
    private String supervisor;      // непосредственный начальник
    private String employmentDate;  // дата приема на работу
    private double salary;          // зарплата

    public Employee(){}
    public Employee(String surname, String name, String patronymic,
                    String birthday, Gender gender, String phone,
                    String function, String department, String supervisor,
                    String employmentDate, double salary) {

        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.birthday = birthday;
        this.gender = gender;
        this.phone = phone;
        this.function = function;
        this.department = department;
        this.supervisor = supervisor;
        this.employmentDate = employmentDate;
        this.salary = salary;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public String getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(String employmentDate) {
        this.employmentDate = employmentDate;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
    public String toString() {
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%.2f",
                this.surname,
                this.name,
                this.patronymic,
                this.birthday,
                this.gender,
                this.phone,
                this.function,
                this.department,
                this.supervisor,
                this.employmentDate,
                this.salary);
    }
    public String getFio(){
        return surname+" "+name+" "+patronymic;
//        return String.format("%s %s %s",surname,name,patronymic);
    }
    public int getYear() {
        return Integer.parseInt(employmentDate.substring(6));
    }
}
