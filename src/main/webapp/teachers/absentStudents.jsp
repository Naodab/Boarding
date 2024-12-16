<%@page import="model.dto.AbsenceResponse"%>
<%@page import="model.bean.Student"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>[Giáo viên] - Giám sát học sinh</title>
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
    <link rel="stylesheet" href="./css/base.css">
    <link rel="stylesheet" href="./css/admin/admin.css">
    <link rel="stylesheet" href="./css/teacher/absentStudents.css">
</head>
<body>
    <div id="overlay"></div>
    <div id="coating"></div>
    <div id="background"></div>
    <div id="container">
        <%@ include file="header.jsp"%>

        <div class="content closure">
            <div class="content-header">Điểm danh học sinh</div>
            <div class="content-body">
                <div class="table-container">
                    <table class="table student-table" id="table">
                        <tr>
                            <th class="th__first center">STT</th>
                            <th class="center">Mã học sinh</th>
                            <th>Họ và tên học sinh</th>
                            <th class="th__last center">Vắng?</th>
                        </tr>
                        <% 	List<AbsenceResponse> listAbsenceResponses = (List)request.getAttribute("listAbsences");
                        	if (listAbsenceResponses != null) {
                            for (int i = 0; i < listAbsenceResponses.size(); i++) {
                        %>
                        <tr>
                            <td class="center"><%= i + 1 %></td>
                            <td class="center"><%= listAbsenceResponses.get(i).getStudent_id() %></td>
                            <td><%= listAbsenceResponses.get(i).getName() %></td>
                            <% if (listAbsenceResponses.get(i).isAbsent()) {%>
                            	<td><input type="checkbox" class="absent-checkbox center-checkbox" data-student_id="<%= listAbsenceResponses.get(i).getStudent_id()%>" checked></td>
                        	<% } else {%>
                        		<td><input type="checkbox" class="absent-checkbox center-checkbox" data-student_id="<%= listAbsenceResponses.get(i).getStudent_id()%>"></td>
                        	<% } %>
                        </tr>
                        <% } } %>
                    </table>
                </div>
                <div class="button-container">
                	<input type="button" value="Cập nhật thể trạng" id="changeToPhysical" class="button">
                	<input type="button" value="Lưu thay đổi" id="saveChanges" class="button">
                </div>
            </div>
        </div>
    </div>
    <script type="module" src="<%= request.getContextPath() %>/js/teacher/absentStudents.js"></script>
</body>
</html>