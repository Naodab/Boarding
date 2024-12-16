<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Quản lý bán trú</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
<link rel="stylesheet" href="css/base.css">
<link rel="stylesheet" href="css/admin/admin.css">
</head>
<body>
	<%
	Object message = request.getAttribute("message");
	if (message != null) {
		out.println("<div style='display: none;' class='error-loggin'>" + (String) message + "</div>");
	}
	%>
	<div id="overlay"></div>
	<div id="coating"></div>
	<div id="background"></div>
	<div id="container">
		<div class="sidebar closure">
			<div class="sidebar-header">
				<img alt="Chess.com logo" src="image/primary_icon.jpg" width="150" height="100" />
				<a href="" class="active"><i class="fa-solid fa-house"></i>Trang chủ</a>
				<a href="" id="login-btn"><i class="fa-solid fa-right-to-bracket"></i>Đăng nhập</a>
			</div>
			<a class="settings-link" id="about-us-btn">Thông tin</a>
		</div>
		<div class="content closure">
			<div class="content--homepage scrollable-element">
				<div class="content-header">TRƯỜNG TIỂU HỌC SỐ I HÒA PHƯỚC</div>
				<div class="function__header">Chào mừng bạn đến với website quản lý bán trú của chúng tôi.</div>
				<div class="content-image">
					<img alt="Chess.com logo" src="image/background.jpg" />
				</div>
				<div class="content-title function__header"><i class="fa-solid fa-circle-info"></i>Giới thiệu:</div>
				<div class="content-description">
					Website quản lý bán trú được thiết kế với giao diện hiện đại và thân thiện,
					nhằm hỗ trợ tối đa việc kết nối giữa nhà trường và phụ huynh, đồng thời nâng cao hiệu quả quản lý các hoạt động bán trú.
					Hệ thống được chia thành 3 vai trò chính với các chức năng cụ thể như sau:
				</div>
				<div>
					<strong>1. Admin</strong> <br>
					<div class="text--description">
						- Quản lý thông tin học sinh, phụ huynh và giáo viên. <br>
						- Quản lý các lớp học và thực đơn cho từng ngày ăn. <br>
						- Tạo tài khoản và phân quyền sử dụng hệ thống. <br>
						- Tạo các đợt thu học phí và quản lý hóa đơn thanh toán.
					</div>
					<strong>2. Giáo viên</strong> <br>
					<div class="text--description">
						- Điểm danh học sinh hàng ngày. <br>
						- Cập nhật thông tin về cân nặng và chiều cao của học sinh. <br>
						- Theo dõi và cập nhật lịch ăn hàng ngày cho từng lớp. <br>
						- Thu học phí từ phụ huynh và xác nhận giao dịch.
					</div>
					<strong>3. Phụ huynh</strong> <br>
					<div class="text--description">
						- Tra cứu thông tin bữa ăn hàng ngày của con em.. <br>
						- Xem chi tiết về học phí và lịch sử thanh toán. <br>
						- Theo dõi sự phát triển của con qua các chỉ số cân nặng và chiều cao được cập nhật định kỳ. <br>
						- Thu học phí từ phụ huynh và xác nhận giao dịch.
					</div>
				</div>
				<div class="content-description">
					Với hệ thống này, nhà trường có thể quản lý toàn diện các hoạt động
					liên quan đến bán trú, giáo viên dễ dàng theo dõi tình hình học sinh, và phụ huynh được cung cấp đầy
					đủ thông tin để an tâm về sự phát triển của con em mình.
				</div>
			</div>
		</div>
	</div>
	<script type="module" src="js/index.js"></script>
</body>
</html>