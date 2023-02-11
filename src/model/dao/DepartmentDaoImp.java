package model.dao;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class DepartmentDaoImp implements DepartmentDao{
    private final String fileName;
    private Map<String,String> department;

    public DepartmentDaoImp(String fileName) {
        this.fileName = fileName;
        try(BufferedReader br = new BufferedReader(new FileReader(fileName)))
        {
            department = br.lines()
                    .skip(1)
                    .map(str-> str.split(","))
                    .collect(Collectors.toMap(arr-> arr[0],
                            arr->arr[1]));
        }
        catch(IOException ex) {
            department = new HashMap<>();
        }
    }

    @Override
    public void save() {
        try(FileOutputStream outputStream = new FileOutputStream(fileName);
            PrintWriter writer = new PrintWriter(outputStream)
        ){
            writer.println("department,supervisor");
            for(Map.Entry<String,String> entry : department.entrySet())
                writer.println(entry.getKey()+","+entry.getValue());
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public Map<String, String> allDepartment() {
        return department;
    }
}
