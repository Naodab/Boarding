<%@page import="model.dto.BoardingFeeResponse"%>
<%@page import="java.util.List"%>
<%@page import="java.text.DecimalFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>[Phụ huynh] - Lịch sử đóng học phí</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
<link rel="stylesheet" href="./css/base.css">
<link rel="stylesheet" href="./css/admin/admin.css">
<link rel="stylesheet" href="./css/parent/historyPayment.css">
</head>
<body>
	<div id="overlay"></div>
	<div id="coating"></div>
	<div id="background"></div>
	<div id="container">
		<%@ include file="header.jsp"%>
		
		<div class="content closure">
			<div class="content-header">Lịch sử đóng học phí</div>
			<div class="content-body">
				<div class="table-container">
					<table class="table student-table" id="table">
						<tr>
							<th class="th__first center">STT</th>
							<th class="center">Mã hóa đơn</th>
							<th>Họ và tên học sinh</th>
							<th class="center">Đợt thu tiền</th>
							<th class="center">Trạng thái</th>
							<th class="th__last">Chi phí nộp</th>
						</tr>
						<% 	List<BoardingFeeResponse> listBoardingFeeResponses = (List)request.getAttribute("listBoardingFeeResponses");
							for (int i = 0; i < listBoardingFeeResponses.size(); i++) {
						%>
						<tr>
							<td class="center"><%= i + 1 %></td>
							<td class="center"><%= listBoardingFeeResponses.get(i).getInvoiceId() %></td>
							<td><%= listBoardingFeeResponses.get(i).getStudentName() %></td>
							<td class="center"><%= listBoardingFeeResponses.get(i).getTimeToSubmit() %></td>
							<% 	String status = null;
								int statusInt = listBoardingFeeResponses.get(i).getStatus();
								if (statusInt == 0 || statusInt == 1) status = "Chưa nộp";
								else if (statusInt == 2) status = "Đã nộp";
								else status = "Đã in hóa đơn";
							%>
							<td class="center"><%= status %></td>
							<td class="center"><%= listBoardingFeeResponses.get(i).getMoneyToSubmit() %></td>
						</tr>
						<% } %>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="module" src="<%=request.getContextPath()%>/js/setting.js"></script>
</html>