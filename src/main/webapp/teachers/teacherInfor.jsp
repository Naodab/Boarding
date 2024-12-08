<%@page import="model.bean.Teacher"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>[Giáo viên] - Thông tin cá nhân</title>
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
			<div class="content-header">Thông tin cá nhân giáo viên</div>
			<div class="content-body">
				<% 	Teacher teacher  = (Teacher)request.getAttribute("teacherInfor"); %>
				<div class="content-avatar">
					<img alt="Chess.com logo" src="./image/primary_icon.jpg" width="150" height="100" />
				</div>
				<div class="content-right">
					<div class="column left">
						<div class="info-box name">
							<span class="label">Họ và tên:</span> <span class="user-info"><%= teacher.getName() %></span>
						</div>
						<div class="info-box dob">
							<span class="label">Ngày sinh:</span> <span class="user-info"><%= teacher.getDateOfBirth() %></span>
						</div>
						<div class="info-box gender">
							<span class="label">Giới tính:</span> <span class="user-info"><%= teacher.getSex()%></span>
						</div>
					</div>
					<div class="column right">
						<div class="info-box class">
							<span class="label">Lớp:</span> <span class="user-info"><%= request.getAttribute("teachClass") %></span>
						</div>
						<div class="info-box id">
							<span class="label">Mã giáo viên:</span> <span class="user-info"><%= teacher.getTeacher_id() %></span>
						</div>
					</div>
				</div>
				<div class="content-center">
				    <form action="">
				    	<div class="info-box address">
					        <span class="label">Địa chỉ:</span> 
					        <input type="text" class="user-info" id="address" value="<%= teacher.getAddress() %>" />
					    </div>
					    <div class="info-box phoneNumber">
					        <span class="label">Số điện thoại:</span>
					        <input type="tel" class="user-info" id="phoneNumber" value="<%= teacher.getPhoneNumber() %>" />
					    </div>
					    <div class="info-box email">
					        <span class="label">Email:</span>
					        <input type="email" class="user-info" id="email" value="<%= teacher.getEmail() %>" />
					    </div>
				    </form>
				</div>
				<div>
					<input type="button" class="button" id="btn-save-edit-infor" value="Lưu thông tin">				
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	document.getElementById("btn-save-edit-infor").addEventListener("click", async function () {
		const address = document.getElementById("address").value.trim();
		const email = document.getElementById("email").value.trim();
		const phoneNumber = document.getElementById("phoneNumber").value.trim();
	    const formData = { address, email, phoneNumber };
	
	    try {
	        const response = await fetch('teachers?mode=updateTeacherInfor', {
	            method: 'POST',
	            headers: {
	                'Content-Type': 'application/json'
	            },
	            body: JSON.stringify(formData)
	        });
	    } catch (err) {
	        console.error("Lỗi khi gửi dữ liệu:", err);
	    }
	    
	    document.getElementById("address").value = address;
	    document.getElementById("email").value = email;
	    document.getElementById("phoneNumber").value = phoneNumber;
	});
</script>
</html>