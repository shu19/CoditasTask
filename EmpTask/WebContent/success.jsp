<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ page import="com.bean.*"%>
<%@ page import="java.util.ArrayList"%>
<%
String path = request.getContextPath();
String month = request.getParameter("month");

String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>Employee</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</head>

<body>




	<% ArrayList<Employee> aa = (ArrayList<Employee>)request.getAttribute("arraylist1"); %>
	<% ArrayList<Employee> bb = null;
 if(request.getAttribute("arraylist2") != null){ bb = (ArrayList<Employee>)request.getAttribute("arraylist2"); } %>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12">
				<div class="col-md-1">
					<a href="AddServlet" class="btn btn-info btn-lg"> <span
						class="glyphicon glyphicon-home"></span> Home
					</a>
				</div>



				<div class="dropdown col-md-1">
					<button class="btn btn-primary dropdown-toggle btn-lg"
						type="button" data-toggle="dropdown">
						Month <span class="caret"></span>
					</button>

					<ul class="dropdown-menu">
						<li><a href="AddServlet?month=01">January</a></li>
						<li><a href="AddServlet?month=02">February</a></li>
						<li><a href="AddServlet?month=03">March</a></li>
						<li><a href="AddServlet?month=04">April</a></li>
						<li><a href="AddServlet?month=05">May</a></li>
						<li><a href="AddServlet?month=06">Jun</a></li>
						<li><a href="AddServlet?month=07">July</a></li>
						<li><a href="AddServlet?month=08">August</a></li>
						<li><a href="AddServlet?month=09">September</a></li>
						<li><a href="AddServlet?month=10">October</a></li>
						<li><a href="AddServlet?month=11">November</a></li>
						<li><a href="AddServlet?month=12">December</a></li>
					</ul>
				</div>
			</div>
			<div class="col-md-12">



				<div class="col-md-4">

					<table class="table table-striped">
						<tr>
							<td>EmpID</td>
							<td>Name</td>
							<td>Manager</td>
							<td><span class="glyphicon glyphicon-eye-open"></span></td>
						</tr>

						<% for(Employee al : aa){ %>
						<tr>
							<td><%=al.getEmpId() %></td>
							<td><%=al.getName() %></td>
							<td><%=al.getManager() %></td>


							<td><a
								href="AddServlet?month=<%=month%>&empId=<%=al.getEmpId() %>">View
									Details</a></td>
						</tr>
						<%} %>
					</table>



				</div>

				<div class="col-md-8">


					<%  if(bb!=null){ %>
					<table class="table table-bordered">
						<tr>
							<td>EmpID</td>
							<td>Name</td>
							<td>Manager</td>
							<td>Date</td>
							<td>InTime</td>
							<td>OutTime</td>
							<td>TotalTime</td>
							<td>Status</td>
						</tr>

						<% for(Employee al : bb){ %>
						<tr>
							<td><%=al.getEmpId() %></td>
							<td><%=al.getName() %></td>
							<td><%=al.getManager() %></td>
							<td><%=al.getDate() %></td>
							<td><%=al.getInTime() %></td>
							<td><%=al.getOutTime() %></td>
							<td><%=al.getTotalTime() %></td>
							<td><%=al.getStatus()%></td>
						</tr>
						<% } %>
					</table>

					<% } %>

				</div>
			</div>
		</div>
	</div>

</body>
</html>
