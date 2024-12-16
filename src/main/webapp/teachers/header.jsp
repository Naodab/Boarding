<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="sidebar closure">
	<div class="sidebar-header">
		<img alt="Chess.com logo" src="./image/primary_icon.jpg" width="150" height="100" />
		<a href="<%= request.getContextPath() %>/teachers?mode=teacherInfor"><i class="fa-solid fa-person"></i>Thông tin cá nhân</a>
		<a href="<%= request.getContextPath() %>/teachers?mode=boardingFee"><i class="fa-solid fa-coins"></i>Thu học phí</a>
		<a href="<%= request.getContextPath() %>/teachers?mode=studentInfor"><i class="fa-solid fa-children"></i>Thông tin học sinh</a>
		<a href="<%= request.getContextPath() %>/eatingHistories?mode=eatingDay"><i class="fa-solid fa-utensils"></i>Theo dõi ngày ăn</a>
		<a href="<%= request.getContextPath() %>/teachers?mode=changeToPhysical"><i class="fa-solid fa-people-roof"></i>Giám sát học sinh</a>
	</div>
	<a class="settings-link" id="setting-btn">Cài đặt</a>
</div>