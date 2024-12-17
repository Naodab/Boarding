<%@page import="model.bean.Student"%>
<%@page import="java.util.List"%>
<%@page import="java.text.DecimalFormat"%>
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
<link rel="stylesheet" href="./css/admin/admin.css">
<link rel="stylesheet" href="./css/teacher/physicalStudents.css">
</head>
<body>
	<div id="overlay"></div>
	<div id="coating"></div>
	<div id="background"></div>
	<div id="container">
		<%@ include file="header.jsp"%>
		
		<div class="content closure">
			<div class="content-header">Cập nhật thông tin thể trạng học sinh</div>
			<div class="content-body">
				<div class="table-container">
					<table class="table student-table" id="table">
						<tr>
							<th class="th__first center">STT</th>
							<th class="center">Mã học sinh</th>
							<th>Họ và tên học sinh</th>
							<th class="center">Chiều cao</th>
							<th class="center">Cân nặng</th>
							<th>Cập nhật chiều cao (m)</th>
							<th class="th__last">Cập nhật cân nặng (kg)</th>
						</tr>
						<% 	List<Student> listStudent = (List)request.getAttribute("listStudents");
							for (int i = 0; i < listStudent.size(); i++) {
						%>
						<tr>
							<td class="center"><%= i + 1 %></td>
							<td class="center"><%= listStudent.get(i).getStudent_id() %></td>
							<td><%= listStudent.get(i).getName() %></td>
							<%
							    double number = listStudent.get(i).getWeight();
							    DecimalFormat df = new DecimalFormat("#.##");
							    String formatNumber = df.format(number);
							%>
							<td class="center"><%= listStudent.get(i).getHeight() %></td>
							<td class="center"><%= formatNumber %></td>
							<td><input type="number" step="0.01" class="height-input" data-student_id="<%= listStudent.get(i).getStudent_id()%>" data-oldVal="<%= listStudent.get(i).getHeight() %>"></td>
							<td><input type="number" step="0.01" class="weight-input" data-student_id="<%= listStudent.get(i).getStudent_id()%>" data-oldVal="<%= listStudent.get(i).getWeight() %>"></td>
						</tr>
						<% } %>
					</table>
				</div>
				<div class="button-container">
					<input type="button" value="Điểm danh" id="absentStudent" class="button">
					<input type="button" value="Lưu thay đổi" id="saveChanges" class="button">
				</div>
			</div>
		</div>
	</div>
	<script type="module" src="<%= request.getContextPath() %>/js/teacher/physicalStudents.js"></script>
</body>
</html>