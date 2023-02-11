package model.dao;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class LoginDaoImp implements LoginDao {
    private final String fileName;
    private Map<String,String> login;

    public LoginDaoImp(String fileName) {
        this.fileName = fileName;
        try(BufferedReader br = new BufferedReader(new FileReader(fileName)))
        {
            login = br.lines()
                    .skip(1)
                    .map(str-> str.split(","))
                    .collect(Collectors.toMap(arr-> arr[0],
                            arr->arr[1]));
        }
        catch(IOException ex) {
            login = new HashMap<>();
        }
    }

    @Override
    public void save() {
        try(FileOutputStream outputStream = new FileOutputStream(fileName);
            PrintWriter writer = new PrintWriter(outputStream)
        ){
            writer.println("login,password");
            for(Map.Entry<String,String> entry : login.entrySet())
                writer.println(entry.getKey()+","+entry.getValue());
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Map<String, String> allLogin() {
        return login;
    }
}
