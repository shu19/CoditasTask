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

/**
 * This class is used for displaying information of Employee.
 * 
 * @author Shubham Raut
 *
 */
public class EmpServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String TABLE_NAME="CODITASEMP";

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		ReadExcel readExcel = new ReadExcel();

		if (request.getParameter("readExcel") != null) {
			try {
				readExcel.readExcelFolder();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}else if (request.getParameter("createDatabase") != null) {
			try {
				readExcel.createDatabase();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		} 
		response.setContentType("text/html");
		request.setAttribute("arraylist1", fetchEmployee(request, response));
		RequestDispatcher rd = request.getRequestDispatcher("success.jsp");
		rd.forward(request, response);

	}

	/**
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	private ArrayList<Employee> fetchEmployee(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		int month = 0;

		if (request.getParameter("month") != null) {
			month = Integer.parseInt(request.getParameter("month"));
		}

		ArrayList<Employee> arraylist = new ArrayList<Employee>();
		try {
			String qry = new String();
			ConnectionHandler connection=new ConnectionHandler();
			
			Connection con = connection.createConnection();
			if (month != 0 ) {
				if(month!=12)
				{
					qry = "select DISTINCT EMPID,EMPNAME,MANAGER from "+TABLE_NAME+" where EMPDATE>=date '2018-" + month
				+ "-01' and EMPDATE<date '2018-" + (month + 1) + "-01'" + " order by EMPID";
				
				}else {
					
					qry = "select DISTINCT EMPID,EMPNAME,MANAGER from "+TABLE_NAME+" where EMPDATE>=date '2018-" + month
							+ "-01' and EMPDATE<date '2018-" + (month) + "-31'" + " order by EMPID";
				}
				
			} else {
				qry = "select DISTINCT EMPID,EMPNAME,MANAGER from "+TABLE_NAME+"  order by EMPID";
			}

			PreparedStatement ps = con.prepareStatement(qry);

			ResultSet rs = ps.executeQuery();
			
			
			while (rs.next()) {

				Employee e = new Employee();

				e.setEmpId(rs.getString("EMPID"));
				e.setName(rs.getString("EMPNAME"));
				e.setManager(rs.getString("MANAGER"));
				arraylist.add(e);

			}
			connection.closeConnection(con);
			
			return arraylist;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}



	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		fetchEmployee(request, response);
		String id = request.getParameter("empId");
		response.setContentType("text/html");

		ArrayList<Employee> arraylist = new ArrayList<Employee>();
		try {


			/**
			 * create Database Connection
			 */
			ConnectionHandler connection=new ConnectionHandler();
			Connection con = connection.createConnection();

			String query = "SELECT *  FROM   "+TABLE_NAME+" WHERE EMPID =" + id;

			PreparedStatement ps = con.prepareStatement(query);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getString("EMPNAME"));
				Employee e = new Employee();
				e.setEmpId(rs.getString("EMPID"));
				e.setName(rs.getString("EMPNAME"));
				e.setManager(rs.getString("MANAGER"));

				SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");
				java.util.Date date       = format.parse ( rs.getString("EMPDATE") );   
				String dateString = format.format( date   );
				e.setDate(dateString);


				e.setInTime(rs.getString("EMPINTIME"));
				e.setOutTime(rs.getString("EMPOUTTIME"));
				e.setTotalTime(rs.getString("EMPTOTAL"));
				e.setStatus(rs.getString("EMPSTATUS"));
				arraylist.add(e);
			}

			/**
			 * closing Database Connection
			 */
			connection.closeConnection(con);
			
			request.setAttribute("arraylist2", arraylist);
			request.setAttribute("arraylist1", fetchEmployee(request, response));
			RequestDispatcher rd = request.getRequestDispatcher("success.jsp");
			rd.forward(request, response);
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


		
	
}
