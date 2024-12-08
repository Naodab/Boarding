<%@page import="model.bean.Student"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>[Giáo viên] - Giám sát học sinh</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
<link rel="stylesheet" href="./css/base.css">
<!-- <link rel="stylesheet" href="./css/teacher/teacher.css">  -->
<link rel="stylesheet" href="./css/teacher/teacher2.css">
</head>
<body>
	<div id="overlay"></div>
	<div id="coating"></div>
	<div id="background"></div>
	<div id="container">
		<%@ include file="header.jsp"%>
		
		<div class="content closure">
			<div class="content-header">Thông tin học sinh</div>
			<div class="content-body">
				<div class="table-container">
					<table class="table student-table" id="table">
						<tr>
							<th class="th__first">STT</th>
							<th>Mã học sinh</th>
							<th>Họ và tên học sinh</th>
							<th>Chiều cao</th>
							<th class="th__last">Cân nặng</th>
						</tr>
						<% 	List<Student> listStudent = (List)request.getAttribute("listStudents");
							for (int i = 0; i < listStudent.size(); i++) {
						%>
						<tr>
							<td><%= i + 1 %></td>
							<td><%= listStudent.get(i).getStudent_id() %></td>
							<td><%= listStudent.get(i).getName() %></td>
							<td><%= listStudent.get(i).getHeight() %></td>
							<td><%= listStudent.get(i).getWeight() %></td>
						</tr>
						<% } %>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	
</script>
</html>