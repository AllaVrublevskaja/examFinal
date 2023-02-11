import model.Employee;
import model.EmployeePredicates;
import model.ListOfEmployee;
import model.Reference;
import model.dao.*;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Application {
    private final LoginDao loginDao;
    private final Map<String,String> login;
    private final DepartmentDao departmentDao;
    private final Map <String,String> departmentMap;
    private final ProfessionDao professionDao;
    private final Map <String,String[]> professionMap;
    private final EmployeeDao employeeDao;
    private final List<Employee> data;
    private Reference reference;
    private final Scanner scan;
    private boolean running;
    private String currentUser;
    boolean isFile;
    public Application(LoginDao loginDao, DepartmentDao departmentDao,
                       ProfessionDao professionDao, EmployeeDao employeeDao) {
        this.loginDao = loginDao;
        this.departmentDao = departmentDao;
        this.professionDao = professionDao;
        this.employeeDao = employeeDao;
        this.scan = new Scanner(System.in);
        this.running = true;
        this.currentUser = null;
        login = loginDao.allLogin();
        departmentMap = departmentDao.allDepartment();
        professionMap = professionDao.allProfession();
        data = employeeDao.allEmployee();
    }
    public void run() {
        reference = new Reference(scan, login,departmentMap,
                professionMap);
        while (running) {
            //Если пользователь авторизован
            if (currentUser != null) {
                if (departmentMap.size() == 0) reference.departmentList();
                if (professionMap.size() == 0) reference.professionList();
                if (data.size() == 0) {
                    ListOfEmployee list = new ListOfEmployee(scan,data,
                            departmentMap,professionMap);
                    list.newEmployee();}
                selectionMenu();
            } else {
                if (login.size() == 0)
                    currentUser = reference.registerHandler();
                else
                    mainMenu();
            }
        }
        loginDao.save();
        departmentDao.save();
        professionDao.save();
        employeeDao.save();
    }
private void mainMenu() {
        System.out.println("------------ГЛАВНОЕ МЕНЮ------------");
        System.out.println("""
                1 - Авторизация
                2 - Регистрация
                3 - Выход""");
        String action = scan.nextLine();
        switch (action) {
            case "1" -> currentUser = reference.authHandler();
            case "2" -> currentUser = reference.registerHandler();
            case "3" -> this.running = false;
            default -> System.out.println("Неизвестная команда...");
        }
    }
    private void selectionMenu() {
        System.out.println("---------Выберите действие---------");
        System.out.println("""
                1 - Список сотрудников
                2 - Найти сотрудника
                3 - Отчеты
                4 - Справочная информация
                5 - Выход""");
        String action = scan.nextLine();
        switch (action) {
            case "1" -> menuItem1();
            case "2" -> menuItem2();
            case "3" -> menuItem3();
            case "4" -> menuItem4();
            case "5" -> this.running = false;
            default -> System.out.println("Неизвестная команда...");
        }
    }
    public void menuItem1(){
        String item;
        boolean quit = false;
        ListOfEmployee list = new ListOfEmployee(scan,data,departmentMap,professionMap);
        while(!quit){
            System.out.println("Список сотрудников");

            System.out.println("""
            1. Прием на работу новых сотрудников
            2. Увольнение сотрудников
            3. Изменение информации о сотрудниках
            4. Выход""");

            System.out.println("Введите пункт меню:");
            item = scan.nextLine();

            switch (item) {
                case "1" -> list.newEmployee();
                case "2" -> list.deleteEmployee();
                case "3" -> list.changeEmployee();
                case "4"-> quit = true;
            }
        }
    }
    public void menuItem2(){
        String item;
        boolean quit = false;
        EmployeePredicates predicates = new EmployeePredicates();
        ReportsFileDaoImp file = new ReportsFileDaoImp(departmentMap,data);
        ReportsConsoleDaoImp console = new ReportsConsoleDaoImp(departmentMap,data);
        while(!quit){
            System.out.println("Найти сотрудника");

            System.out.println("""
            1. по фамилии
            2. по должности
            3. по названию отдела
            4. по фамилии начальника
            5. Выход""");

            System.out.println("Введите пункт меню:");
            item = scan.nextLine();
            if (!item.equals("5"))
                isFile = isFileOut();
            String str;
            String fileReport;
            String punktName;
            switch (item) {
                case "1"->
                {   punktName = "по фамилии";
                    System.out.println("Введите Фамилию");
                    str = scan.nextLine();
                    fileReport = "reports\\findEmployeeSurname.txt";
                    if (isFile)
                        file. findEmployee(predicates.surname(str),
                            str, punktName, fileReport);
                    else console.findEmployee(predicates.surname(str),
                            str, punktName, fileReport);
                }
                case "2"->
                {   punktName = "по должности";
                    System.out.println("Введите должность");
                    str = scan.nextLine();
                    fileReport = "reports\\findEmployeeFunction.txt";
                    if (isFile)
                        file.findEmployee(predicates.function(str),
                                str, punktName, fileReport);
                    else console.findEmployee(predicates.function(str),
                            str, punktName, fileReport);
                }
                case "3"->
                {   punktName = "по названию отдела";
                    System.out.println("Введите название отдела");
                    str = scan.nextLine();
                    fileReport = "reports\\findEmployeeDepartment.txt";
                    if (isFile)
                        file.findEmployee(predicates.department(str),
                                str, punktName, fileReport);
                    else console.findEmployee(predicates.department(str),
                            str, punktName, fileReport);
                }
                case "4"->
                {   punktName = "по фамилии начальника";
                    System.out.println("Введите фамилию начальника");
                    str = scan.nextLine();
                    fileReport = "reports\\findEmployeeSupervisor.txt";
                    if (isFile)
                        file.findEmployee(predicates.supervisor(str),
                                str, punktName, fileReport);
                    else console.findEmployee(predicates.supervisor(str),
                            str, punktName, fileReport);
                }
                case "5"-> quit = true;
            }
        }
    }
    public void menuItem3(){
        String item;
        boolean quit = false;
        ReportsFileDaoImp file = new ReportsFileDaoImp(departmentMap,data);
        ReportsConsoleDaoImp console = new ReportsConsoleDaoImp(departmentMap,data);
        while(!quit){
            System.out.println("Отчеты");

            System.out.println("""
            1. Структура организации
            2. Средняя зарплата по организации
            3. Средняя зарплата по отделам
            4. ТОП-10 самых дорогих сотрудников по зарплате
            5. ТОП-10 самых преданных сотрудников по количеству лет работы в организации
            6. Выход""");

            System.out.println("Введите пункт меню:");
            item = scan.nextLine();
            if (!item.equals("6"))
                isFile = isFileOut();

            switch (item){
                case "1"-> {
                    if (isFile) file.reportStructure();
                    else console.reportStructure();
                }
                case "2"-> {
                    if (isFile) file.reportAverage();
                    else console.reportAverage();
                }
                case "3"-> {
                    if (isFile) file.reportAverageDepartment();
                    else console.reportAverageDepartment();
                }
                case "4"-> {
                    if (isFile) file.reportTopTenSalary();
                    else console.reportTopTenSalary();
                }
                case "5" -> {
                    if (isFile) file.reportTopTenYear();
                    else console.reportTopTenYear();
                }

                case "6"-> quit=true;
            }
        }
    }
    public void menuItem4(){
        String item;
        boolean quit = false;
        Reference reference = new Reference(scan, login, departmentMap,
                professionMap);
        while(!quit){
            System.out.println("Справочная информация");

            System.out.println("""
            1. Список пользователей
            2. Список отделов
            3. Профессии по отделам
            4. Выход""");

            System.out.println("Введите пункт меню:");
            item = scan.nextLine();

            switch (item) {
                case "1" -> reference.loginList();
                case "2" -> reference.departmentList();
                case "3" -> reference.professionList();
                case "4"-> quit = true;
            }
        }
    }
    public boolean isFileOut(){
        isFile = false;
        System.out.println("""
               1. Вывод на консоль
               2. Вывод в файл""");
        System.out.println("Выберите поток вывода :");
        String item1 = scan.nextLine();
        if (item1.equals("2")) isFile = true;
        return isFile;
    }
}
