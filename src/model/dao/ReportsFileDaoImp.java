package model.dao;

import model.Employee;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static java.util.stream.Collectors.averagingDouble;
import static java.util.stream.Collectors.groupingBy;

public class ReportsFileDaoImp implements ReportsDao{
    private final Map<String,String> departmentMap;
    private final List<Employee> data;

    public ReportsFileDaoImp(  DepartmentDao departmentDao,
                               EmployeeDao employeeDao) {
        this.departmentMap = departmentDao.allDepartment();
        this.data = employeeDao.allEmployee();
    }

    @Override
    public void reportStructure() {
        String punktName = "Структура организации";
        try(FileOutputStream outputStream = new FileOutputStream("reports\\reportStructure.txt");
            PrintWriter writer = new PrintWriter(outputStream)
        ){
            writer.println(punktName+":");
            writer.println();
            departmentMap.keySet()
                    .forEach(writer::println);
            writer.println();
        }
        catch(IOException ex) {
            System.out.println("По пункту "+punktName+" нет информации");
        }
    }

    @Override
    public void reportAverage() {
        String punktName = "Средняя зарплата по организации";
        try(FileOutputStream outputStream = new FileOutputStream("reports\\reportAverage.txt");
            PrintWriter writer = new PrintWriter(outputStream)
        ){
            writer.println();
            double average = data.stream()
                    .mapToDouble(Employee::getSalary)
                    .reduce(0, Double::sum) / data.size();
            String str = String.format("%.2f",average);
            writer.println(punktName+": "+str);
            writer.println();
        }
        catch(IOException ex) {
            System.out.println("По пункту "+punktName+" нет информации");
        }
    }

    @Override
    public void reportAverageDepartment() {
        String punktName = "Средняя зарплата по отделам";
        try(FileOutputStream outputStream = new FileOutputStream("reports\\reportAverageDepartment.txt");
            PrintWriter writer = new PrintWriter(outputStream)
        ){
            String str;
            Map <String,Double> averageDepartment = data.stream()
                    .collect(groupingBy(Employee::getDepartment,
                            averagingDouble(Employee::getSalary)));
            writer.println(punktName);
            writer.println();
            for (Map.Entry<String,Double> entry : averageDepartment.entrySet()) {
                str = String.format("%-23s  %.2f",entry.getKey() , entry.getValue());
                writer.println(str);
            }
            writer.println();
        }
        catch(IOException ex) {
            System.out.println("По пункту "+punktName+" нет информации");
        }
    }

    @Override
    public void reportTopTenSalary() {
        String punktName = "ТОП-10 самых дорогих сотрудников по зарплате";
        try(FileOutputStream outputStream = new FileOutputStream("reports\\reportTopTenSalary.txt");
            PrintWriter writer = new PrintWriter(outputStream)
        ){
            writer.println(punktName);
            writer.println();
            data.stream()
                    .sorted(Comparator.comparingDouble(Employee::getSalary).reversed())
                    .limit(10)
                    .forEach(writer::println);
            writer.println();
        }
        catch(IOException ex) {
            System.out.println("По пункту "+punktName+" нет информации");
        }
    }

    @Override
    public void reportTopTenYear() {
        String punktName = "ТОП-10 самых преданных сотрудников по количеству лет работы в организации.";
        try(FileOutputStream outputStream = new FileOutputStream("reports\\reportTopTenYear.txt");
            PrintWriter writer = new PrintWriter(outputStream)
        ){
            writer.println(punktName);
            writer.println();
            data.stream()
                    .sorted(Comparator.comparingInt(Employee::getYear))
                    .limit(10)
                    .forEach(writer::println);
            writer.println();
        }
        catch(IOException ex) {
            System.out.println("По пункту "+punktName+" нет информации");
        }
    }

    @Override
    public void findEmployee(Predicate<Employee> predicate,String str,
                             String punktName,String fileReport) {
        try(FileOutputStream fos = new FileOutputStream(fileReport);
            PrintWriter writer = new PrintWriter(fos)) {

            List<Employee> findEmployee = data.stream()
                    .filter(predicate).toList();
            if (findEmployee.size() > 0) {
                writer.println("Список сотрудников " + punktName + ": " + str);
                writer.println();
                for (Employee employee : findEmployee)
                    writer.println(employee);
                writer.println();
            } else System.out.println("Нет информации "+punktName + " " + str);
        }
        catch(IOException ex){
            System.out.println("Нет сотрудников "+punktName+": "+str);
        }
    }
}
