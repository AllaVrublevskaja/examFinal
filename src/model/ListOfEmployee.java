package model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ListOfEmployee {
    private final List<Employee> data;
    private final Map <String,String> departmentMap;
    private final Map<String,String[]> professionMap;
    private final Scanner scan;
    private String department;
    private String supervisor;

    public ListOfEmployee(Scanner scan,List<Employee> data,
                          Map<String, String> departmentMap,
                          Map<String, String[]> professionMap) {
        this.scan = scan;
        this.data = data;
        this.departmentMap = departmentMap;
        this.professionMap = professionMap;
    }
    public void newEmployee(){
        if (data.size() == 0)
            System.out.println("Нет информации о сотрудниках. Заполните.");
        System.out.println("Фамилия: ");
        String surname = scan.nextLine();
        System.out.println("Имя: ");
        String name = scan.nextLine();
        System.out.println("Отчество: ");
        String patronymic = scan.nextLine();
        String out ="Дата рождения(ДД.ММ.ГГГГ): ";
        String birthday=getDate(out);
        Gender gender = getGender();
        String phone=getPhone();
        getDepartment();
        String function = getFunction(department);
        out ="Дата поступления (ДД.ММ.ГГГГ): ";
        String employmentDate = getDate(out);
        double salary = getSalary();
        data.add(new Employee(surname, name, patronymic, birthday, gender, phone,
                function, department, supervisor, employmentDate, salary));
    }

    public Gender getGender() {
        Gender gender;
        while (true) {
            System.out.println("Пол(ж,м): ");
            String gender1 = scan.nextLine();
            if (gender1.equals("м") || gender1.equals("ж")) {
                gender = gender1.equals("м") ? Gender.MALE : Gender.FEMALE;
                break;
            }
        }
        return gender;
    }
    public String getPhone(){
        String[] words;
        String phone1;
        int part1;
        int part2;
        while (true) {
            System.out.println("Телефон(+7(хxxх)хххххх): ");
            phone1 = scan.nextLine();
            if (phone1.length() != 14) continue;
            words = phone1.split("[()]");
            if (words.length!=3) continue;
            if (!words[0].equals("+7")) continue;
            if (words[1].length() == 3 && words[2].length() == 7 ||
                    words[1].length() == 4 && words[2].length() == 6){
                part1 = getNumeric(words[1]);
                part2 = getNumeric(words[2]);
                if(part1 <= 0 || part2 <= 0) continue;
                break;
            }
        }
        return phone1;
    }
    public void getDepartment() {
        boolean quit = false;
        String dep = "";
        List <String> strings = new ArrayList<>();
        while (!quit) {
            for (Map.Entry<String,String> entry : departmentMap.entrySet()) {
                System.out.println(entry.getKey() + " " + entry.getValue());
                strings.add(entry.getKey());
            }
            System.out.println("Отдел: ");
            dep = scan.nextLine();
            for (String string : strings)
                if (string.equals(dep)) {
                    quit = true;
                    break;
                }
        }
        department = dep;
        supervisor = departmentMap.get(dep);
    }
    public String getFunction(String department) {
        String func;
        Map <String,String[]> function = professionMap.entrySet().stream()
                .filter(e->e.getKey().equals(department))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        while (true) {
            for (Map.Entry<String,String[]> entry : function.entrySet())
                for (String strings : entry.getValue())
                    System.out.println(entry.getKey()+","+strings);

            System.out.println("Должность: ");
            func = scan.nextLine();
            for (Map.Entry<String,String[]> entry : function.entrySet())
                for (String strings : entry.getValue())
                    if (strings.equals(func))
                        return func;
        }
    }
    public String getDate(String out) {
        String inputDate, date, birthday;
        do {
            if (!out.equals(""))
                System.out.println(out);
            inputDate = scan.nextLine();
            date = inputDate.replace('.', '-');
        } while (!isDateValid(date));
        birthday = inputDate.replace('-', '.');
        return birthday;
    }
    public static boolean isDateValid(String date) {
        try {
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    public static int getNumeric(String string){
        int intValue=0;
        try {
            intValue = Integer.parseInt(string);
        } catch (NumberFormatException e) {
            System.out.println("Строка "+string+" не является целым числом.");
        }
        return intValue;
    }
    public double getSalary(){
        boolean quit = false;
        String salary = "";
        while(!quit){
            System.out.println("Зарплата: ");
            salary = scan.nextLine();
            quit = isValidDouble(salary);
        }
        return Double.parseDouble(salary);
    }
    public boolean isValidDouble(String string){
        boolean quit;
        try {
            Double.parseDouble(string);
            quit = true;
        } catch (NumberFormatException e) {
            quit = false;
            System.out.println("Строка не является числом типа double.");
        }
        return quit;
    }
    public void deleteEmployee(){
        EmployeePredicates predicate = new EmployeePredicates();
        System.out.println("Фамилия: ");
        String surname = scan.nextLine();
        System.out.println("Имя: ");
        String name = scan.nextLine();
        System.out.println("Отчество: ");
        String patronymic = scan.nextLine();
        int size = data.size();
        data.removeIf(predicate.fio(surname,name,patronymic));
        if (size == data.size())
            System.out.println(surname+" "+name+" "+patronymic+" отсутствует в списке сотрудников.");
    }
    public void changeEmployee() {
        EmployeePredicates predicate = new EmployeePredicates();
        Employee employee;
        System.out.println("Фамилия: ");
        String surname = scan.nextLine();
        System.out.println("Имя: ");
        String name = scan.nextLine();
        System.out.println("Отчество: ");
        String patronymic = scan.nextLine();

        employee = data.stream()
                .filter(predicate.fio(surname, name, patronymic))
                .findFirst()
                .orElse(null);
        if (employee == null)
            System.out.println("Нет такого сотрудника.");
        else {
            int index = data.indexOf(employee);
            String item, str;
            boolean quit = false;
            boolean change = false;
            while (!quit) {
                System.out.println(employee);
                System.out.println("Изменить данные :");

                System.out.println("""                
                        1.  Фамилия
                        2.  Имя
                        3.  Отчество
                        4.  Дата рождения
                        5.  Пол
                        6.  Телефон
                        7.  Отдел
                        8.  Профессия
                        9.  Дата поступления
                        10. Зарплата
                        11. Выход""");

                System.out.println("Введите пункт меню");

                item = scan.nextLine();
                switch (item) {
                    case "1" -> {
                        System.out.println("Фамилия: " +
                                employee.getSurname() + " изменить на :");
                        str = scan.nextLine();
                        if (str != null) {
                            employee.setSurname(str);
                            change = true;
                        }
                    }
                    case "2" -> {
                        System.out.println("Имя: " +
                                employee.getName() + " изменить на :");
                        str = scan.nextLine();
                        if (str != null) {
                            employee.setName(str);
                            change = true;
                        }
                    }
                    case "3" -> {
                        System.out.println("Отчество: " +
                                employee.getPatronymic() + " изменить на :");
                        str = scan.nextLine();
                        if (str != null) {
                            employee.setPatronymic(str);
                            change = true;
                        }
                    }
                    case "4" -> {
                        System.out.println("Дата рождения: " +
                                employee.getBirthday() + " изменить на :");
                        str = getDate("");
                        if (str != null) {
                            employee.setBirthday(str);
                            change = true;
                        }
                    }
                    case "5" -> {
                        System.out.println("Пол: " +
                                employee.getGender() + " изменить на :");
                        Gender gender = getGender();
                        if (gender != null) {
                            employee.setGender(gender);
                            change = true;
                        }
                    }
                    case "6" -> {
                        System.out.println("Телефон: " +
                                employee.getPhone() + " изменить на :");
                        str = getPhone();
                        if (str != null) {
                            employee.setPhone(str);
                            change = true;
                        }
                    }
                    case "7" -> {
                        System.out.println("Отдел: " +
                                employee.getDepartment() + " изменить на :");
                        getDepartment();
                        if (department != null) {
                            employee.setDepartment(department);
                            employee.setSupervisor(supervisor);
                            change = true;
                        }
                    }
                    case "8" -> {
                        System.out.println("Профессия: " +
                                employee.getFunction() + " изменить на :");
                        str = getFunction(department);
                        if (str != null) {
                            employee.setFunction(str);
                            change = true;
                        }
                    }
                    case "9" -> {
                        System.out.println("Дата поступления: " +
                                employee.getEmploymentDate() + " изменить на :");
                        str = getDate("");
                        if (str != null) {
                            employee.setEmploymentDate(str);
                            change = true;
                        }
                    }
                    case "10" -> {
                        System.out.println("Зарплата: " +
                                employee.getSalary() + " изменить на :");
                        double salary = getSalary();
                        if (salary > 0) {
                            employee.setSalary(salary);
                            change = true;
                        }
                    }
                    case "11" -> quit = true;
                }
            }
            if (change)
                data.set(index, employee);
        }
    }
}
