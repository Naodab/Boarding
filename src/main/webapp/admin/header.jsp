<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="sidebar closure">
	<div class="sidebar-header">
		<img alt="Chess.com logo" src="<%= request.getContextPath() %>/image/primary_icon.jpg" width="150" height="100" />
		<a href="<%= request.getContextPath() %>/students" id="students"><i class="fa-solid fa-graduation-cap"></i>Quản lý học sinh</a>
		<a href="<%= request.getContextPath() %>/parents" id="parents"><i class="fa-solid fa-person-breastfeeding"></i>Quản lý phụ huynh</a>
		<a href="<%= request.getContextPath() %>/teachers" id="teachers"><i class="fa-solid fa-chalkboard-user"></i>Quản lý giáo viên</a>
		<a href="<%= request.getContextPath() %>/boardingClasses" id="classes"><i class="fa-solid fa-school"></i>Quản lý lớp học</a>
		<a href="<%= request.getContextPath() %>/eatingHistories" id="eatingHistories"><i class="fa-regular fa-calendar-days"></i>Quản lý ngày ăn</a>
		<a href="<%= request.getContextPath() %>/boardingFees" id="fees"><i class="fa-solid fa-money-check-dollar"></i>Quản lý thu tiền</a>
		<a href="<%= request.getContextPath() %>/menus" id="menus"><i class="fa-solid fa-bars"></i>Quản lý thực đơn</a>
		<a href="<%= request.getContextPath() %>/foods" id="foods"><i class="fa-solid fa-bowl-food"></i>Quản lý thức ăn</a>
		<a href="<%= request.getContextPath() %>/users" id="users"><i class="fa-solid fa-user"></i>Quản lý tài khoản</a>
	</div>
	<a class="settings-link" id="setting-btn">Cài đặt</a>
</div>