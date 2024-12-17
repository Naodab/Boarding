<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="model.dto.ParentResponse"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>[Phụ huynh] - Thông tin cá nhân</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
<link rel="stylesheet" href="./css/base.css">
<link rel="stylesheet" href="./css/parent/parentsInfor.css">
</head>
<body>
	<div id="overlay"></div>
	<div id="coating"></div>
	<div id="background"></div>
	<div id="container">
		<%@ include file="header.jsp"%>
		<div class="content closure">
			<div class="content-header">Thông tin cá nhân</div>
			<div class="content-body">
				<% 	ParentResponse parent  = (ParentResponse)request.getAttribute("parentInfor"); %>
				<div class="content-avatar">
					<img alt="Chess.com logo" src="./image/primary_icon.jpg" width="150" height="100" />
				</div>
				<div class="content-right">
					<div class="column left">
						<div class="info-box name">
							<span class="label">Họ và tên:</span> <span class="user-info"><%= parent.getParentName() %></span>
						</div>
						<div class="info-box dob">
							<% 	Date date = parent.getDateOfBirth();
								Calendar calendar = Calendar.getInstance();
								calendar.setTime(date);
								int year  = calendar.get(Calendar.YEAR);
								int month = calendar.get(Calendar.MONTH) + 1;
								int day   = calendar.get(Calendar.DAY_OF_MONTH);
								String dob = (day < 10 ? ("0" + day) : day) + "/" + (month < 10 ? ("0" + month) : month) + "/" + year; 
							%>
							<span class="label">Ngày sinh:</span> <span class="user-info"><%= dob %></span>
						</div>
						<div class="info-box gender">
							<% 	String sex = null;
								if (parent.isSex() == true) sex = "Nam";
								else sex = "Nữ";
							%>
							<span class="label">Giới tính:</span> <span class="user-info"><%= sex %></span>
						</div>
					</div>
					<div class="column right">
						<div class="info-box id">
							<span class="label">Mã phụ huynh:</span> <span class="user-info"><%= parent.getParentId() %></span>
						</div>
					</div>
				</div>
				<div class="content-center">
				    <form action="">
				    	<div class="info-box address">
					        <span class="label">Địa chỉ:</span> 
					        <input type="text" class="user-info" id="address" value="<%= parent.getAddress() %>" readonly/>
					    </div>
					    <div class="info-box phoneNumber">
					        <span class="label">Số điện thoại:</span>
					        <input type="tel" class="user-info" id="phoneNumber" value="<%= parent.getPhoneNumber() %>" readonly/>
					    </div>
					    <div class="info-box email">
					        <span class="label">Email:</span>
					        <input type="email" class="user-info" id="email" value="<%= parent.getEmail() %>" readonly/>
					    </div>
					    <div>
					        <span class="label">Thông tin học sinh:</span>
					        <select name="studentName" id="studentName">
							    <% 
							        for (int i = 0; i < parent.getStudentNameList().size(); i++) {
							        	String contentText = parent.getStudentNameList().get(i);
							    %>
							    	<option value="<%=parent.getStudentIdList().get(i)%>"><%= contentText %></option>
							    <% } %>
							</select>
							<input type="button" value="Xem chi tiết" id="studentDetail">
					    </div>
					    <div>
					        <span class="label">Thông tin giáo viên:</span>
					        <select name="teacherName" id="teacherName">
							    <% 
							        for (int i = 0; i < parent.getTeacherNameList().size(); i++) {
							        	String contentText = parent.getTeacherNameList().get(i);
							    %>
							    	<option value="<%=parent.getTeacherIdList().get(i)%>"><%= contentText %></option>
							    <% } %>
							</select>
							<input type="button" value="Xem chi tiết" id="teacherDetail">
					    </div>
				    </form>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="module" src="<%=request.getContextPath()%>/js/setting.js"></script>
</html>