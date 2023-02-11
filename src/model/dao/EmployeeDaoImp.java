package model.dao;

import model.Employee;
import model.Gender;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeDaoImp implements EmployeeDao{
    private final String fileName;
    private List<Employee> data;

    public EmployeeDaoImp(String fileName) {
        this.fileName = fileName;
        try(BufferedReader br = new BufferedReader(new FileReader(fileName)))
        {
            data = br.lines()
                    .skip(1)
                    .map(str-> str.split(","))
                    .map(arr -> new Employee(
                            arr[0],arr[1],arr[2],arr[3], Gender.valueOf(arr[4]),
                            arr[5],arr[6],arr[7],arr[8],
                            arr[9],
                            Double.parseDouble(arr[10].replace(",","."))))
                    .collect(Collectors.toList());
        }
        catch(IOException ex) {
            data = new ArrayList<>();
        }
    }

    @Override
    public void save() {
        try (FileOutputStream fos = new FileOutputStream(fileName);
            PrintWriter writer = new PrintWriter(fos)){
            writer.println("lastName,name,patronymic,birthday,gender,phone," +
                    "function,department,supervisor,employmentday,salary");
            for (Employee employee : data)
                writer.println(employee);
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }
    @Override
    public List<Employee> allEmployee() {
        return data;
    }
}
