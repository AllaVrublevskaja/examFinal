import model.dao.DepartmentDaoImp;
import model.dao.EmployeeDaoImp;
import model.dao.LoginDaoImp;
import model.dao.ProfessionDaoImp;


public class Main {
    public static void main(String[] args) {

        Application app = new Application(new LoginDaoImp("csv\\login.csv"),
                new DepartmentDaoImp("csv\\departmentMap.csv"),
                new ProfessionDaoImp("csv\\professionalMap.csv"),
                new EmployeeDaoImp("csv\\employees.csv")
        );
        app.run();
    }
}