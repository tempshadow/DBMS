import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;
public class test {
	public static void main(String[] args) throws SQLException {
		// Connect to database
		final String hostName = "mans8927-sql-server.database.windows.net";
		final String dbName = "cs-dsa-4513-sql-db";
		//NOTICE SHOBEY
		final String user = "delete this and Put User name of azure account in here instead";
		final String password = "delete this and put password of azure account in here instead";
		final String url = String.format("jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;hostNameInCertificate=*.database.windows.net;loginTimeout=30;",
				hostName, dbName, user, password);
		try (final Connection connection = DriverManager.getConnection(url)) {
			final String schema = connection.getSchema();
			System.out.print("Enter 1, 2, 3, or 4.\n");
			Scanner input = new Scanner(System.in);
			String temp = input.nextLine();
			while(Integer.parseInt(temp) != 4) {
				switch (Integer.parseInt(temp)) {
				case 1: System.out.print("Enter pid: ");
				int pid = input.nextInt();
				System.out.print("Enter pname: ");
				String pname = input.next();
				System.out.print("Enter age: ");
				int age = input.nextInt();
				String sqlExect1 = "EXEC Option1_Add_Performer ?, ?, ?";
				PreparedStatement Option1_Add_Performer = connection.prepareCall(sqlExect1);
				Option1_Add_Performer.setInt(1, pid);
				Option1_Add_Performer.setString(2, pname);
				Option1_Add_Performer.setInt(3, age);
				Option1_Add_Performer.execute();
				
				System.out.print("Enter 1, 2, 3, or 4.\n");
				break;
				
				//Shobey, Here is part 2 of question 2
				case 2: System.out.print("Enter pid: ");
				pid = input.nextInt();
				System.out.print("Enter pname: ");
				pname = input.next();
				System.out.print("Enter age: ");
				age = input.nextInt();
				System.out.print("Enter did: ");
				String did = input.next();
				int years_of_experience = 0;
				final String selectSql2 = "Select AVG(years_of_experience) "
						+ "FROM Performer "
						+ "JOIN Acted ON (Performer.pid = Acted.pid) "
						+ "JOIN Movie ON (Acted.mname = Movie.mname) "
						+ "WHERE did = " + did;
				try (final Statement statement = connection.createStatement();
						final ResultSet resultSet = statement.executeQuery(selectSql2)) {
					while(resultSet.next()) {
						years_of_experience = resultSet.getInt(1);
					}
					if (years_of_experience == 0) {
						years_of_experience = age - 18;
						if (years_of_experience < 0) {
							years_of_experience = 0;
						}
						else if (years_of_experience > age) {
							years_of_experience = age;
						}
					}
				}
				System.out.print("Enter 1, 2, 3, or 4.\n");
				break;
				
				case 3: System.out.print("three\n");
				System.out.print("Enter 1, 2, 3, or 4.\n");
				temp = input.nextLine();
				break;
				}
				temp = input.next();
			}
			return;
		}
	}
}