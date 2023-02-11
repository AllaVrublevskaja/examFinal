package model.dao;

import model.Employee;
import model.EmployeePredicates;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static java.util.stream.Collectors.averagingDouble;
import static java.util.stream.Collectors.groupingBy;

public class ReportsConsoleDaoImp implements ReportsDao {
    private final Map<String,String> departmentMap;
    private final List<Employee> data;

    public ReportsConsoleDaoImp(Map<String, String> departmentMap,
                                List<Employee> data) {
        this.departmentMap = departmentMap;
        this.data = data;
    }
    @Override
    public void reportStructure() {
            String punktName = "Структура организации";
            System.out.println(punktName+":");
            System.out.println();
            departmentMap.keySet()
                    .forEach(System.out::println);
            System.out.println();
    }

    @Override
    public void reportAverage() {
        String punktName = "Средняя зарплата по организации";
            System.out.println();
            double average = data.stream()
                    .mapToDouble(Employee::getSalary)
                    .reduce(0, Double::sum) / data.size();
            String str = String.format("%.2f",average);
            System.out.println(punktName+": "+str);
            System.out.println();
    }

    @Override
    public void reportAverageDepartment() {
            String str;
            String punktName = "Средняя зарплата по отделам";
            Map <String,Double> averageDepartment = data.stream()
                    .collect(groupingBy(Employee::getDepartment,
                            averagingDouble(Employee::getSalary)));
            System.out.println(punktName);
            System.out.println();
            for (Map.Entry<String,Double> entry : averageDepartment.entrySet()) {
                str = String.format("%-23s  %.2f",entry.getKey() , entry.getValue());
                System.out.println(str);
            }
            System.out.println();
    }

    @Override
    public void reportTopTenSalary() {
            String punktName = "ТОП-10 самых дорогих сотрудников по зарплате";
            System.out.println(punktName);
            System.out.println();
            data.stream()
                    .sorted(Comparator.comparingDouble(Employee::getSalary).reversed())
                    .limit(10)
                    .forEach(System.out::println);
            System.out.println();
    }

    @Override
    public void reportTopTenYear() {
        String punktName = "ТОП-10 самых преданных сотрудников по количеству лет работы в организации.";
        System.out.println(punktName);
        System.out.println();
        data.stream()
                .sorted(Comparator.comparingInt(Employee::getYear))
                .limit(10)
                .forEach(System.out::println);
        System.out.println();
    }

    @Override
    public void findEmployee(Predicate<Employee> predicate,String str,
                             String punktName,String fileReport) {

        List<Employee> findEmployee = data.stream()
                    .filter(predicate).toList();
        if(findEmployee.size() > 0) {
            System.out.println("Список сотрудников " + punktName + ": " + str);
            System.out.println();
            for (Employee employee : findEmployee)
                System.out.println(employee);
            System.out.println();
        } else System.out.println("Нет информации "+punktName + " " + str);
    }
}
