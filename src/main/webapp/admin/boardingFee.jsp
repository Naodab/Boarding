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
			<div class="content-header">Quản lý thu tiền</div>

			<div id="data-container">
				<div class="data-detail-item closure closure--pink">
					<div class="data-detail-title">Thông tin</div>
					<div class="data-detail-body">
						<div class="data-detail-item">
							<div class="data-detail-body-content">
								<div class="body-content-label">Mã ĐTT:</div>
								<span class="body-content-span" id="boardingFeeId"></span>
							</div>
							<div class="data-detail-body-content">
								<div class="body-content-label">Từ:</div>
								<span class="body-content-span" id="from"></span>
							</div>
						</div>
						<div class="data-detail-item">
							<div class="data-detail-body-content">
								<div class="body-content-label">Số tiền:</div>
								<span class="body-content-span" id="money"></span>
							</div>
							<div class="data-detail-body-content">
								<div class="body-content-label">Đến:</div>
								<span class="body-content-span" id="to"></span>
							</div>
						</div>
					</div>
				</div>
				<div class="data-detail-item closure closure--blue">
					<div class="data-detail-title">Tình hình</div>
					<div class="data-detail-body data-detail-body--column">
						<div class="data-detail-item data-detail-item--row">
							<div class="data-detail-body-content">
								<div class="body-content-label">Số tiền đã thu:</div>
								<span class="body-content-span" id="payedMoney"></span>
							</div>
							<div class="data-detail-body-content">
								<div class="body-content-label">Số hóa đơn chưa in:</div>
								<span class="body-content-span" id="nonPrinted"></span>
							</div>
						</div>
						<div class="data-detail-body-content">
							<div class="body-content-label">Số học sinh đã đóng tiền:</div>
							<span class="body-content-span" id="payedStudent"></span>
						</div>
					</div>
				</div>
			</div>
			<div class="content-body-invoice">
				<div class="content-function">
					<div class="sort function__sort function-item">
						<div class="function__header">Đợt thu tiền</div>
						<select name="month__select" class="selection sort-field" id="fees-selection"></select>
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
							<option value="invoice_id">Mã hóa đơn</option>
							<option value="student_id">Mã học sinh</option>
						</select>
					</div>

				</div>
				<div class="content-data-invoice scrollable-element">
					<table class="table invoice-table">
						<tr>
							<th class="th__first">Mã hóa đơn</th>
							<th>Mã học sinh</th>
							<th>Họ và tên</th>
							<th>Lớp</th>
							<th>Ngày nộp</th>
							<th>Số tiền nộp</th>
							<th class="th__last">Trạng thái</th>
						</tr>
					</table>
				</div>
				<div class="pages-container"></div>
			</div>
			<div class="content-footer">
				<div class="btn btn--green footer-item" id="add-btn">Tạo đợt thu tiền</div>
				<div class="btn btn--blue footer-item" id="print-btn">In hóa đơn</div>
				<div class="btn btn--pink footer-item" id="list-btn">Danh sách theo lớp</div>
			</div>
		</div>
	</div>
	<script type="module" src="<%= request.getContextPath() %>/js/admin/boardingFee.js"></script>
</body>
</html>