<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Quản lý bán trú</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
<link rel="stylesheet" href="../css/base.css">
<link rel="stylesheet" href="../css/admin/admin.css">
</head>
<body>
	<div id="overlay"></div>
	<div id="coating"></div>
	<div id="background"></div>
	<div id="container">
		<%@ include file="header.jsp"%>
		
		<div class="content closure">
			<div class="content-header">Quản lý học sinh</div>
			<div class="content-body">
				<div class="content-function">
					<div class="sort-function function-item"></div>
					<div class="lookup-function function-item"></div>
				</div>
				<table class="table student-table">
					<tr>
						<th class="th__first">Mã</th>
						<th>Họ và tên</th>
						<th>Giới tính</th>
						<th>Ngày sinh</th>
						<th>Địa chỉ</th>
						<th>Phụ huynh</th>
						<th>Lớp</th>
						<th class="th__last">Ăn phụ</th>
					</tr>
					<%
						
					%>
				</table>
			</div>
		</div>
	</div>
</body>
</html>