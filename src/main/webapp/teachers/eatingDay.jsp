<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@page import="model.dto.EatingDayResponse"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>[Giáo viên] - Theo dõi ngày ăn</title>
	<link rel="stylesheet"
		href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
	<link rel="stylesheet" href="./css/base.css">
	<link rel="stylesheet" href="./css/admin/admin.css">
	<link rel="stylesheet" href="./css/teacher/eatingDay.css">
</head>
<body>
	<div id="overlay"></div>
	<div id="coating"></div>
	<div id="background"></div>
	<div id="container">
		<%@ include file="header.jsp"%>
		<div class="content closure">
            <div class="content-header">Theo dõi lịch sử ngày ăn</div>
            <div>
            	<% 
				    int boardingFeeId = -1;
            		if ((Integer) request.getAttribute("boardingFeeId") != null) 
						boardingFeeId = (Integer) request.getAttribute("boardingFeeId");
				    int numberOfItems = (Integer) request.getAttribute("numberOfItems");
				%>
				<select name="boardingFee" id="boardingFee">
				    <option>--Chọn thực đơn cho đợt thu học phí--</option>
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
                            <th class="center">Ngày ăn</th>
                            <th>Món chính</th>
                            <th class="th__last">Món phụ</th>
                        </tr>
                        <% 	List<EatingDayResponse> listEatingDayResponse = (List)request.getAttribute("listEatingDayResponse");
                            for (int i = 0; i < listEatingDayResponse.size(); i++) {
                        %>
                        <tr>
                            <td class="center"><%= i + 1 %></td>
                            <% 	Date date = listEatingDayResponse.get(i).getEatingDay();
								Calendar calendar = Calendar.getInstance();
								calendar.setTime(date);
								int year  = calendar.get(Calendar.YEAR);
								int month = calendar.get(Calendar.MONTH) + 1;
								int day   = calendar.get(Calendar.DAY_OF_MONTH);
								String dob = ((day < 10) ? ("0" + day) : (day)) + "/" + ((month < 10) ? ("0" + month) : (month)) + "/" + year; 
							%>
                            <td class="center"><%= dob %></td>
                            <td><%= listEatingDayResponse.get(i).getMainMeals().toString().substring(1, listEatingDayResponse.get(i).getMainMeals().toString().length() - 1) %></td>
                            <td><%= listEatingDayResponse.get(i).getSubMeals().toString().substring(1, listEatingDayResponse.get(i).getSubMeals().toString().length() - 1) %></td>
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
	    if (boardingFeeId) {
	    	window.location.href = "eatingHistories?mode=eatingDay&boardingFeeId=" + boardingFeeId;
	    }
	});
</script>
</html>