import java.util.*;

public class getEmployees {
	public getEmployees() {
		List<List<String>> employeeList = dbConnection.getTable("employees");
		for (List<String> employeeLoop : employeeList) {
			System.out.println(employeeLoop);
		}
	}

}


