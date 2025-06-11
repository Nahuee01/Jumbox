import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class conexion {
	private static String URL = "jdbc:mysql://localhost:3306/jumbox";
	private static String USER = "root";
	private static String PASSWORD = "";

	private static Connection conect;
	private static conexion instance;

	private conexion() {
		try {
			conect = (Connection) DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("Se conectó");
		} catch (SQLException e) {
			System.out.println("No se conectó");

		}
	}

	public static conexion getInstance() {
		if (instance == null) {
			instance = new conexion();
		}
		return instance;
	}

	public Connection getConection() {
		return conect;
	}

}
