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
</body>
<script type="text/javascript">
	document.getElementById("absentStudent").addEventListener("click", function () {
		window.location.href = "teachers?mode=changeToAbsent";
	});
	document.getElementById("saveChanges").addEventListener("click", async function () {
		const heights = Array.from(document.querySelectorAll(".height-input"));
		const weights = Array.from(document.querySelectorAll(".weight-input"));
		const updates = [];
		heights.forEach(heightInput => {
			let student_id = heightInput.getAttribute("data-student_id");
			let height = heightInput.value || heightInput.getAttribute("data-oldVal");
			let oldHeight = heightInput.getAttribute("data-oldVal");
			let weight, oldWeight;
			weights.forEach(weightInput => {
				if (weightInput.getAttribute("data-student_id") === student_id) {
					weight = weightInput.value || weightInput.getAttribute("data-oldVal");
					oldWeight = weightInput.getAttribute("data-oldVal");
				}
			});
			if (oldWeight !== weight || oldHeight !== height) {
				updates.push({student_id, height, weight});
			}
		});
		if (updates.length > 0) {
			const response = await fetch("./students?mode=updatePhysical", {
				method: "POST",
				headers: {"Content-type": "application/json"},
				body: JSON.stringify(updates)
			});
			if (response.ok) {
				alert("Cập nhập thành công!");
				location.reload();
			} else {alert("Không thể cập nhật thông tin!");}
		}
	});
</script>
</html>