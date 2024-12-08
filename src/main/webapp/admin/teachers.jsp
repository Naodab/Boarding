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
<link rel="stylesheet" href="/boarding/css/base.css">
<link rel="stylesheet" href="/boarding/css/admin/admin.css">
</head>
<body>
	<div id="overlay"></div>
	<div id="coating"></div>
	<div id="background"></div>
	<div id="container">
		<%@ include file="header.jsp"%>

		<div class="content closure">
			<div class="content-header">Quản lý giáo viên</div>
			<div class="content-body">
				<div class="content-function">
					<div class="sort function__sort function-item">
						<div class="function__header">Sắp xếp</div>
						<select name="sort__field" class="selection">
							<option value="name">Họ và tên</option>
							<option value="dateOfBirth">Ngày sinh</option>
						</select>
						<div class="sort__type-container">
							<div class="sort-group">
								<input type="radio" id="sort--up" name="sort__type"
									class="sort__type-item" value="ASC"> <label
									for="sort--up"> <i class="fa-solid fa-up-long"></i>
								</label>
							</div>
							<div class="sort-group">
								<input type="radio" id="sort--down" name="sort__type"
									class="sort__type-item" value="DESC"> <label
									for="sort--down"> <i class="fa-solid fa-down-long"></i>
								</label>
							</div>
							<div class="sort-group">
								<input type="radio" id="sort--none" name="sort__type"
									class="sort__type-item" value="" checked> <label
									for="sort--none">Không</label>
							</div>
						</div>
					</div>
					<div class="function__lookup function-item">
						<div class="function__header">Tìm kiếm</div>
						<div class="function__lookup">
							<div class="lookup__search-bar">
								<input type="text" id="search" name="search"
									placeholder="Nhập id hoặc họ và tên"> <span
									class="lookup__icon"> <i
									class="fa-solid fa-magnifying-glass"></i>
								</span>
							</div>
						</div>

					</div>
				</div>
				<div class="content-data scrollable-element">
					<table class="table teacher-table">
						<tr>
							<th class="th__first">Mã</th>
							<th>Họ và tên</th>
							<th>Giới tính</th>
							<th>Ngày sinh</th>
							<th>Địa chỉ</th>
							<th>Lớp</th>
							<th class="th__last">Số điện thoại</th>
						</tr>
					</table>
				</div>
				<div class="pages-container"></div>
			</div>
			<div class="content-footer">
				<div class="btn btn--green footer-item">Thêm giáo viên</div>
			</div>
		</div>
	</div>
	<script type="module" src="/boarding/js/admin/teachers.js"></script>
</body>
</html>