package Connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {

	public static Connection con = null;
	public static Connect instance = new Connect();

	public void connect() throws SQLException {
		String url = "jdbc:sqlserver://localhost:1433;databaseName=hsk";
		String user = "sa";
		String password = "khongcopass@1";
		con = DriverManager.getConnection(url, user, password);
	}

	public void disconnect() {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static Connection getCon() {
		return con;
	}

	public static Connect getInstance() {
		return instance;
	}

}
