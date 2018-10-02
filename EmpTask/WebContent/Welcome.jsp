<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'Login.jsp' starting page</title>

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


<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

</head>

<body>
	<h2>
		<center>
			<%
				if (request.getAttribute("errormessage") != null) {
			%>
			<%=request.getAttribute("errormessage")%>
			<%
				}
			%>
		</center>
	</h2>

	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12">

			<div class="col-md-6">
				<form action="${pageContext.request.contextPath}/EmpServlet"
					method="post">

					<div class="btn btn-danger btn-lg">
					<span class="glyphicon glyphicon-folder-open"></span>
						<input type="submit" name="readExcel" value=" Read Excel"
							class="btn btn-danger btn-lg"
							 />
	
	
					</div>
	
						<input type="submit" name="createDatabase" value="Create Database"
							class="btn btn-danger btn-lg"
							 />
									
					
				</form>

			</div>
			
			<div class="col-md-6">
				<form action="EmpServlet" method="post">
					<center>
						
						<input
							type=submit value="Go to Employee Attendance Page" class="btn btn-success btn-lg">

					</center>
				</form>
			</div>
			</div>
		</div>
	</div>
</body>
</html>
