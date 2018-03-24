package DB;

import java.sql.*;
public class DBController {

	public boolean createDatabase(Connection conn) {
		try {
			Statement st = conn.createStatement();
			
			
		} catch (SQLException e) {
			return false;
		}

		return true;
	}
	
}
