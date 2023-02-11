package model;

import model.dao.DepartmentDao;
import model.dao.LoginDao;
import model.dao.ProfessionDao;

import java.util.Map;
import java.util.Scanner;

public class Reference {
    private final Map <String,String> login;
    private final Map <String,String> departmentMap;
    private final Map <String,String[]> professionMap;
    public Scanner scan;
    private String currentUser;
    boolean quit = false;
    public Reference(LoginDao loginDao, DepartmentDao departmentDao,
                     ProfessionDao professionDao) {
        this.login = loginDao.allLogin();
        this.departmentMap = departmentDao.allDepartment();
        this.professionMap = professionDao.allProfession();
        this.currentUser = null;
        this.scan = new Scanner(System.in);
    }
    public void loginList() {
        while (!quit) {
            System.out.println("Список пользователей");
            System.out.println("""
                    1. Новый пользователь
                    2. Удалить пользователя
                    3. Изменить информацию о пользователе
                    4. Посмотреть список пользователей
                    5. Авторизоваться
                    6. Выход""");
            System.out.println("Введите пункт меню:");
            String item = scan.nextLine();

            String password;
            String nickName;
            switch (item) {
                case "1" -> currentUser = registerHandler();
                case "2" -> {
                    System.out.println("Введите Login:");
                    nickName = scan.nextLine();
                    System.out.println("Введите Password:");
                    password = scan.nextLine();
                    if (login.size() > 0 && login.containsKey(nickName) && login.get(nickName).equals(password)) {
                        login.remove(nickName);
                    }
                }
                case "3" -> {
                    System.out.println("Введите Login:");
                    nickName = scan.nextLine();
                    if(!login.containsKey(nickName))
                        System.out.println("Нет такого пользователя");
                    else {
                        password = login.get(nickName);
                        System.out.println("Изменить "+nickName+" "+password+" на: ");
                        System.out.println("Введите новый пароль:");
                        String pasNew = scan.nextLine();
                        if (!pasNew.equals("")) {
                            login.replace(nickName, password, pasNew);
                        }
                    }
                }
                case "4" ->  {
                    for (Map.Entry <String,String> map: login.entrySet())
                        System.out.println( map.getKey()+"  "+map.getValue());
                }
                case "5" -> currentUser=authHandler();
                case "6" -> quit = true;
            }
        }
    }
    public void departmentList() {
            String item;
            boolean quit = false;
            if (departmentMap.size() == 0)
                System.out.println("Нет информации о структуре организации. Заполните.");
            while(!quit){
                System.out.println("Структура организации");

                System.out.println("""
                       1. Новый отдел
                       2. Удалить отдел
                       3. Изменить информацию об отделе
                       4. Посмотреть структуру организации
                       5. Выход""");

                System.out.println("Введите пункт меню:");
                item = scan.nextLine();

                String sup;
                switch (item) {
                    case "1"-> {
                        System.out.println("Введите название отдела:");
                        String dep = scan.nextLine();
                        System.out.println("Введите фамилию начальника отдела:");
                        sup = scan.nextLine();
                        departmentMap.put(dep,sup);
                    }
                    case "2" -> {
                        System.out.println("Введите название отдела:");
                        String dep = scan.nextLine();
                        departmentMap.remove(dep);}
                    case "3"-> {
                        System.out.println("Введите название отдела:");
                        String dep = scan.nextLine();
                        if(!departmentMap.containsKey(dep))
                            System.out.println("Нет такого отдела");
                        else {
                            sup = departmentMap.get(dep);
                            System.out.println("Изменить "+dep+" "+sup+" на: ");
                            System.out.println("Фамилия начальника отдела:");
                            String supNew = scan.nextLine();
                            if (!supNew.equals("")) {
                                departmentMap.replace(dep, sup, supNew);
                            }
                        }
                    }
                    case "4"-> {
                        for (Map.Entry <String,String> map: departmentMap.entrySet())
                            System.out.println( map.getKey()+"  "+map.getValue());
                    }
                    case "5" -> quit = true;
                }
            }
    }
    public void professionList() {
            String item;
            boolean quit = false;
            if (professionMap.size() == 0)
                System.out.println("Нет информации о профессиях по отделам. Заполните.");
            while(!quit){
                System.out.println("Профессии по отделам");

                System.out.println("""
                       1. Новыe профессии
                       2. Удалить профессию
                       3. Изменить информацию о профессии
                       4. Посмотреть профессии по отделам
                       5. Выход""");

                System.out.println("Введите пункт меню:");
                item = scan.nextLine();

                String[] prof;
                switch (item) {
                    case "1"-> {
                        System.out.println("Введите название отдела:");
                        String dep = scan.nextLine();
                        if(!departmentMap.containsKey(dep))
                            System.out.println("Нет такого отдела");
                        else {
                            System.out.println("Введите профессии через пробел:");
                            prof = scan.nextLine().split(" ");
                            professionMap.put(dep, prof);
                        }
                    }
                    case "2"-> {
                        System.out.println("Введите название отдела:");
                        String dep = scan.nextLine();
                        if(!departmentMap.containsKey(dep))
                            System.out.println("Нет такого отдела");
                        else {
                            System.out.println("Введите профессию:");
                            String profN = scan.nextLine();
                            boolean isProfValid = false;
                            prof = professionMap.get(dep);
                            for(String s : prof)
                                if (s.equals(profN)) {
                                    isProfValid = true;
                                    break;
                                }
                            if (isProfValid) {
                                String[] profNew = new String[prof.length - 1];
                                int j = 0;
                                for (String s : prof) {
                                    if (!s.equals(profN)) {
                                        profNew[j] = s;
                                        j++;
                                    }
                                }
                                professionMap.remove(dep, prof);
                                professionMap.put(dep, profNew);
                            }
                            else System.out.println("Нет такой профессии");
                        }
                    }
                    case "3"-> {
                        System.out.println("Введите название отдела:");
                        String dep = scan.nextLine();
                        if(!departmentMap.containsKey(dep))
                            System.out.println("Нет такого отдела");
                        else {
                            System.out.println("Введите старую профессию:");
                            String profO = scan.nextLine();
                            System.out.println("Введите новую профессию:");
                            String profN = scan.nextLine();
                            String[] profOld = professionMap.get(dep);
                            if (profOld.length > 0) {
                                String[] profNew = new String[profOld.length];
                                int j = -1;
                                for (String s : profOld) {
                                    j++;
                                    if (s.equals(profO)) profNew[j] = profN;
                                    else profNew[j] = s;
                                }
                                professionMap.replace(dep,profOld,profNew);
                            }
                            else System.out.println("Нет профессий в отделе");
                        }
                    }
                    case "4"-> {
                        for (Map.Entry <String,String[]> map: professionMap.entrySet())
                            for (String strings : map.getValue())
                                System.out.println(map.getKey()+","+strings);
                    }
                    case "5" -> quit = true;
                }
            }
    }
    public String registerHandler() {
        if (login.size() == 0)
            System.out.println("Нет информации о пользователях. Зарегистрируйтесь.");
        System.out.println("Введите Login:");
        String nickName = scan.nextLine();
        System.out.println("Введите Password:");
        String password = scan.nextLine();
        if (login.size()>0 && login.containsKey(nickName) &&
                login.get(nickName).equals(password)){
            System.out.println("Такой пользователь уже существует.");
        }
        else {
            currentUser = nickName;
            login.put(nickName,password);
            System.out.println("Вы успешно зарегистрированы!");
        }
        return currentUser;
    }
    public String authHandler() {
        System.out.println("Введите Login:");
        String nickName = scan.nextLine();
        System.out.println("Введите Password:");
        String password = scan.nextLine();
        if (login.size()>0 && login.containsKey(nickName) &&
                login.get(nickName).equals(password)){
                currentUser = nickName;
            System.out.println("Вы успешно авторизованы...");
        }
        else {
            System.out.println("Неверный логин или пароль!");
        }
        return currentUser;
    }
}
