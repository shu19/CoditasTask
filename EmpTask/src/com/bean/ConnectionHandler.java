package com.bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHandler {

	/**
	 * This method creates database connectioon
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Connection createConnection() throws ClassNotFoundException, SQLException {
		Connection con = null;
		Class.forName("oracle.jdbc.driver.OracleDriver");
		con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "root");
		return con;
	}

	/**
	 * This method closes the database connection
	 * 
	 * @param con
	 * 
	 * @throws SQLException
	 */
	public void closeConnection(Connection con) throws SQLException {
		if(!con.isClosed())
			con.close();
	}
	
	
}
