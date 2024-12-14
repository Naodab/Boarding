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
	<!-- <link rel="stylesheet" href="./css/teacher/teacher.css">  -->
	<link rel="stylesheet" href="./css/teacher/teacher2.css">
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
            	<select name="boardingFeeId" id="boardingFee">
            		<option>--Chọn đợt thu tiền--</option>
            		<% 	int numberOfItems = (Integer)request.getAttribute("numberOfItems");
            			for (int i = 1; i <= numberOfItems; i++) {
            				if (i < 5) {
            		%>
            				<option value="<%=i%>"><%= i + " - tháng " + (i + 8) %></option>
            				<% } else { %>
            				<option value="<%=i%>"><%= i + " - tháng " + (i - 4) %></option>
            		<% } } %>
            	</select>
            </div>
            <div class="content-body">
                <div class="table-container">
                    <table class="table student-table" id="table">
                        <tr>
                            <th class="th__first">STT</th>
                            <th>Mã học sinh</th>
                            <th>Họ và tên học sinh</th>
                            <th>Mã hóa đơn</th>
                            <th class="th__last">Trạng thái</th>
                        </tr>
                        <% 	List<BoardingFeeResponse> listBoardingFees = (List)request.getAttribute("listBoardingFees");
                            for (int i = 0; i < listBoardingFees.size(); i++) {
                        %>
                        <tr>
                            <td><%= i + 1 %></td>
                            <td><%= listBoardingFees.get(i).getStudent_id() %></td>
                            <td><%= listBoardingFees.get(i).getStudentName() %></td>
                            <td><%= listBoardingFees.get(i).getInvoiceId() %></td>
                            <td><%= listBoardingFees.get(i).getStatus() %></td>
                        </tr>
                        <% } %>
                    </table>
                </div>
            </div>
        </div>
	</div>
</body>
<script type="text/javascript">
	document.getElementById("boardingFee").addEventListener("change", function () {
		const boardingFeeId = this.value;
	    if (selectedValue) {
	    	window.location.href = "teachers?mode=boardingFee&boardingFeeId=" + boardingFeeId;
	    }
	});
</script>
</html>