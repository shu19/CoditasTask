package com.bean;

import java.io.File;
import java.io.IOException;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class EmpServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		EmpServlet myClass = new EmpServlet();

		if (request.getParameter("readExcel") != null) {
			try {
				myClass.readExcelFolder();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}else if (request.getParameter("readExcel") != null) {
			try {
				myClass.createDatabase();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		} 
		response.setContentType("text/html");
		request.setAttribute("arraylist1", fetchEmployee(request, response));
		RequestDispatcher rd = request.getRequestDispatcher("success.jsp");
		rd.forward(request, response);

	}

	private ArrayList<Employee> fetchEmployee(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// PrintWriter out = response.getWriter();
		int month = 0;

		if (request.getParameter("month") != null) {
			month = Integer.parseInt(request.getParameter("month"));
		}

		ArrayList<Employee> arraylist = new ArrayList<Employee>();
		try {
			String qry = "";
			Connection con = null;
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "root");
			if (month != 0) {
				if(month!=12)
				{
					qry = "select DISTINCT EMPID,EMPNAME,MANAGER from CODITASEMP where EMPDATE>=date '2018-" + month
				+ "-01' and EMPDATE<date '2018-" + (month + 1) + "-01'" + " order by EMPID";
				
				}else {
					
					qry = "select DISTINCT EMPID,EMPNAME,MANAGER from CODITASEMP where EMPDATE>=date '2018-" + month
							+ "-01' and EMPDATE<date '2018-" + (month) + "-31'" + " order by EMPID";
				}
				
			} else {
				qry = "select DISTINCT EMPID,EMPNAME,MANAGER from CODITASEMP  order by EMPID";
			}

			PreparedStatement ps = con.prepareStatement(qry);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getString("EMPNAME"));
				Employee e = new Employee();

				e.setEmpId(rs.getString("EMPID"));
				e.setName(rs.getString("EMPNAME"));
				e.setManager(rs.getString("MANAGER"));
				arraylist.add(e);

			}
			con.close();
			return arraylist;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		fetchEmployee(request, response);
		String id = request.getParameter("empId");

		// int month = Integer.parseInt(request.getParameter("month"));

		response.setContentType("text/html");
		// PrintWriter out = response.getWriter();

		ArrayList<Employee> arraylist = new ArrayList<Employee>();
		try {

			Connection con = null;
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "root");

			String query = "SELECT *  FROM   coditasemp WHERE EMPID =" + id;
			System.out.println(query);
			
			PreparedStatement ps = con.prepareStatement(query);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getString("EMPNAME"));
				Employee e = new Employee();
				e.setEmpId(rs.getString("EMPID"));
				e.setName(rs.getString("EMPNAME"));
				e.setManager(rs.getString("MANAGER"));
				e.setDate(rs.getString("EMPDATE"));
				e.setInTime(rs.getString("EMPINTIME"));
				e.setOutTime(rs.getString("EMPOUTTIME"));
				e.setTotalTime(rs.getString("EMPTOTAL"));
				e.setStatus(rs.getString("EMPSTATUS"));
				arraylist.add(e);
			}

			request.setAttribute("arraylist2", arraylist);
			request.setAttribute("arraylist1", fetchEmployee(request, response));
			RequestDispatcher rd = request.getRequestDispatcher("success.jsp");
			rd.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static Connection con = null;

	public void createDatabase() throws IOException, SQLException, ClassNotFoundException 
	{
		String qry = "Create TABLE CODITASEMP (EMPID varchar(20),EMPNAME varchar(20),MANAGER varchar(20),EMPDATE varchar(20),EMPINTIME varchar(20),EMPOUTTIME varchar(20),EMPTOTAL varchar(20),EMPSTATUS varchar(20))";
		Class.forName("oracle.jdbc.driver.OracleDriver");
		con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "root");
		PreparedStatement ps = con.prepareStatement(qry);

		ps.executeQuery();

		con.close();
		
	}
	
	public void readExcelFolder() throws IOException, SQLException, ClassNotFoundException {

		Class.forName("oracle.jdbc.driver.OracleDriver");
		con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "root");

		
		setInputFile("C:\\Users\\intel\\Desktop\\CoditasEmployee\\");
		readDirectory();
		con.close();

	}

	private static String inputFile;

	public static void setInputFile(String InputFile) {
		inputFile = InputFile;
	}

	public static void readDirectory() throws IOException, SQLException {

		File dir = new File(inputFile);
		File[] directoryListing = dir.listFiles();
		if (directoryListing != null) {
			for (File child : directoryListing) {

				read(child.getPath());

			}
		}
	}

	public static void read(String filePath) throws IOException, SQLException {

		File f = new File(filePath);
		Workbook w;
		try {
			w = Workbook.getWorkbook(f);

			String query = " insert into CODITASEMP values (?, ?, ?, ?, ?,?,?,?)";
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
							System.out.println("else :" + cell.getContents() + "\t CellType" + type);
						}
					} else if (!(type == CellType.EMPTY)) {
						preparedStmt.setString(j + 1, cell.getContents());
					}

				}
				preparedStmt.execute();
			}

		} catch (BiffException e) {
			e.printStackTrace();
		} catch (SQLException s) {
			s.printStackTrace();
		}
	}

}
