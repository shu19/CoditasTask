package com.bean;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;



/**
 * This class reads excel file (.xls file only ) and then store the data in oracle 10g Database.
 * @author Shubham Raut
 *
 */
public class ReadExcel {

	private static String inputFile;

	/**
	 * This method is used for creating table in database
	 * 
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void createDatabase() throws IOException, SQLException, ClassNotFoundException {
		ConnectionHandler connection=new ConnectionHandler();
		Connection con = connection.createConnection();

		String qry = "Create TABLE CODITASEMP (EMPID varchar(20),EMPNAME varchar(20),MANAGER varchar(20),EMPDATE varchar(20),EMPINTIME varchar(20),EMPOUTTIME varchar(20),EMPTOTAL varchar(20),EMPSTATUS varchar(20))";
		PreparedStatement ps = con.prepareStatement(qry);

		ps.executeQuery();

		connection.closeConnection(con);

	}

	/**
	 * In this method folder path is provided
	 * 
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void readExcelFolder() throws IOException, SQLException, ClassNotFoundException {

		setInputFile("C:\\Users\\intel\\Desktop\\CoditasEmployee\\");
		readDirectory();

	}

	/**
	 * @param InputFile :
	 *            is folder path
	 */
	public static void setInputFile(String InputFile) {
		inputFile = InputFile;
	}

	/**
	 * This method reads every file in the folder
	 * 
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static void readDirectory() throws IOException, SQLException, ClassNotFoundException {

		File dir = new File(inputFile);
		File[] directoryListing = dir.listFiles();
		if (directoryListing != null) {
			for (File child : directoryListing) {

				read(child.getPath());

			}
		}
	}

	/**
	 * @param filePath
	 *            is the Excel file Path which is provided from readDirectory() method
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */

	public static void read(String filePath) throws IOException, SQLException, ClassNotFoundException {

		File f = new File(filePath);
		Workbook w;
		try {
			w = Workbook.getWorkbook(f);

			String query = " insert into CODITASEMP values (?, ?, ?, ?, ?,?,?,?)";

			ConnectionHandler connection = new ConnectionHandler();
			Connection con = connection.createConnection();

			PreparedStatement preparedStmt = con.prepareStatement(query);

			Sheet s = w.getSheet(0);
			for (int i = 1; i < s.getRows(); i++) {
				for (int j = 0; j < s.getColumns(); j++) {
					Cell cell = s.getCell(j, i);
					CellType type = cell.getType();

					if ((type == CellType.DATE)) {
						if (j + 1 == 4) {
							preparedStmt.setDate(j + 1, new java.sql.Date(Date.parse(cell.getContents())));
						} else {
							preparedStmt.setString(j + 1, cell.getContents());
						}
					} else if (!(type == CellType.EMPTY)) {
						preparedStmt.setString(j + 1, cell.getContents());
					}

				}
				preparedStmt.execute();
				connection.closeConnection(con);
			}

		} catch (BiffException e) {
			e.printStackTrace();
		} catch (SQLException s) {
			s.printStackTrace();
		}
	}
}
