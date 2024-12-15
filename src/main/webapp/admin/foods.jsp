<%@page import="model.dto.StudentResponse"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta charset="UTF-8">
<title>Quản lý bán trú</title>
	<link rel="stylesheet"
		href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/base.css">
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/admin/admin.css">
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/admin/food.css">
</head>
<body>
	<div id="overlay"></div>
	<div id="coating"></div>
	<div id="background"></div>
	<div id="container">
		<%@ include file="header.jsp"%>

		<div class="content closure">
			<div class="content-header">Quản lý món ăn</div>
			<div class="content-body">
				<div class="content-function">
					<div class="sort function__sort function-item">
						<div class="function__header">Danh sách món ăn</div>
					</div>
					<div class="function__lookup function-item">
						<div class="function__header">Tìm kiếm</div>
						<div class="function__lookup">
							<div class="lookup__search-bar">
								<input type="text" id="search" name="search"
									placeholder="Nhập thông tin"> <span
									class="lookup__icon"> <i
									class="fa-solid fa-magnifying-glass"></i>
								</span>
							</div>
						</div>
						<select name="sort__field" class="selection search-field">
							<option value="food_id">Mã món ăn</option>
							<option value="name">Tên món ăn</option>
						</select>
					</div>

				</div>
				<div class="content-data scrollable-element">
					<div class="table" id="foods-container"></div>
				</div>
				<div class="pages-container"></div>
			</div>
			<div class="btn btn--green footer-item special-btn" id="add-btn">Thêm món ăn</div>
		</div>
	</div>
	<script type="module" src="<%= request.getContextPath() %>/js/admin/food.js"></script>
</body>
</html>