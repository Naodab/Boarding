<%@page import="java.util.List"%>
<%@page import="model.dto.BoardingFeeResponse"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>[Giáo viên] - Thu học phí học sinh</title>
	<link rel="stylesheet"
		href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
	<link rel="stylesheet" href="./css/base.css">
	<link rel="stylesheet" href="./css/admin/admin.css">
	<link rel="stylesheet" href="./css/teacher/boardingFee.css">
</head>
<body>
	<div id="overlay"></div>
	<div id="coating"></div>
	<div id="background"></div>
	<div id="container">
		<%@ include file="header.jsp"%>
		<div class="content closure">
            <div class="content-header">THU HỌC PHÍ</div>
            <div>
            	<% 
				    int boardingFeeId = -1;
            		if ((Integer) request.getAttribute("boardingFeeId") != null) 
						boardingFeeId = (Integer) request.getAttribute("boardingFeeId");
				    int numberOfItems = (Integer) request.getAttribute("numberOfItems");
				%>
				<select name="boardingFee" id="boardingFee" class="selection">
				    <option>--Chọn đợt thu tiền--</option>
				    <% 
				        for (int i = 1; i <= numberOfItems; i++) { 
				            String optionText;
				            if (i < 5) {
				                optionText = i + " - tháng " + (i + 8);
				            } else {
				                optionText = i + " - tháng " + (i - 4);
				            }
				    %>
				    	<% if (boardingFeeId != -1) {%>
				        	<option value="<%=i%>" <%= (i == boardingFeeId) ? "selected" : "" %>><%= optionText %></option>
				        <% } else { %>
				        	<option value="<%=i%>"><%= optionText %></option>
				        <% } %>
				    <% } %>
				</select>
            </div>
            <div class="content-body">
                <div class="table-container">
                    <table class="table student-table" id="table">
                        <tr>
                            <th class="th__first center">STT</th>
                            <th class="center">Mã học sinh</th>
                            <th>Họ và tên học sinh</th>
                            <th class="center">Mã hóa đơn</th>
                            <th class="th__last">Trạng thái</th>
                        </tr>
                        <% 	List<BoardingFeeResponse> listBoardingFees = (List)request.getAttribute("listBoardingFees");
                            for (int i = 0; i < listBoardingFees.size(); i++) {
                        %>
                        <tr>
                            <td class="center"><%= i + 1 %></td>
                            <td class="center"><%= listBoardingFees.get(i).getStudent_id() %></td>
                            <td><%= listBoardingFees.get(i).getStudentName() %></td>
                            <td class="invoiceId center"><%= listBoardingFees.get(i).getInvoiceId() %></td>
                            <td>
                            	<div>
								    <%
								        int status = listBoardingFees.get(i).getStatus();
								        int studentId = listBoardingFees.get(i).getStudent_id();
								    %>
								    <input type="radio" id="notSubmit" name="<%= studentId %>" value="notSubmit"
								           <%= (status == 1 || status == 0) ? "checked" : "disabled" %>>
								    <label for="notSubmit">Chưa nộp</label>
								
								    <input type="radio" id="submitted" name="<%= studentId %>" value="submitted"
								           <%= (status == 2) ? "checked" : (status == 1 || status == 0) ? "" : "disabled" %>>
								    <label for="submitted">Đã nộp</label>
								
								    <input type="radio" id="printed" name="<%= studentId %>" value="printed"
								           <%= (status == 3) ? "checked" : "disabled" %>>
								    <label for="printed">Đã in hóa đơn</label>
								</div>
                            </td>
                        </tr>
                        <% } %>
                    </table>
                </div>
            </div>
            <div>
            	<input type="button" class="save-button" value="Lưu thay đổi" id="saveChanges">
            </div>
        </div>
	</div>

	<script type="module" src="<%= request.getContextPath() %>/js/teacher/boardingFee.js"></script>
</body>
</html>