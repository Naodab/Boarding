<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="sidebar closure">
	<div class="sidebar-header">
		<img alt="Chess.com logo" src="./image/primary_icon.jpg" width="150" height="100" />
		<a href="<%= request.getContextPath() %>/parents?mode=parentInfor"><i class="fa-solid fa-person"></i>Thông tin cá nhân</a>
		<a href="<%= request.getContextPath() %>/parents?mode=seeBoardingFees"><i class="fa-solid fa-coins"></i>Lịch sử nộp tiền</a>
		<a href="<%= request.getContextPath() %>/parents?mode=seeEatingHistory"><i class="fa-solid fa-utensils"></i>Lịch sử ngày ăn</a>
	</div>
	<a class="settings-link" id="setting-btn">Cài đặt</a>
</div>