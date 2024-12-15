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
</head>
<body>
	<div id="overlay"></div>
	<div id="coating"></div>
	<div id="background"></div>
	<div id="container">
		<%@ include file="header.jsp"%>

		<div class="content closure">
			<div class="content-header">Quản lý ngày ăn</div>
			<div class="content-body">
				<div class="content-function">
					<div class="sort function__sort function-item">
						<div class="function__header">Tháng</div>
						<select name="month__select" class="selection sort-field" id="months-selection"></select>
					</div>
					<div class="function__lookup function-item">
						<div class="function__header">Tìm kiếm</div>
						<div class="function__lookup">
							<div class="lookup__search-bar">
								<input type="text" id="search" name="search"
									placeholder="Nhập id thông tin"> <span
									class="lookup__icon"> <i
									class="fa-solid fa-magnifying-glass"></i>
								</span>
							</div>
						</div>
						<select name="sort__field" class="selection search-field">
							<option value="eatingHistory_id">Mã ngày ăn</option>
						</select>
					</div>

				</div>
				<div class="content-data scrollable-element">
					<table class="table eatingHistory-table">
						<tr>
							<th class="th__first">Mã</th>
							<th>Mã thực đơn</th>
							<th>Bữa trưa</th>
							<th>Bữa phụ</th>
							<th class="th__last">Ngày ăn</th>
						</tr>
					</table>
				</div>
				<div class="pages-container"></div>
			</div>
			<div class="btn btn--green footer-item special-btn" id="add-btn" hidden="hidden">Phân ngày ăn</div>
		</div>
	</div>
	<script type="module" src="<%= request.getContextPath() %>/js/admin/eatingHistory.js"></script>
</body>
</html>