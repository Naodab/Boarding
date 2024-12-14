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
            <div class="content-header">Điểm danh học sinh</div>
            <div class="content-body">
                <div class="table-container">
                    <table class="table student-table" id="table">
                        <tr>
                            <th class="th__first">STT</th>
                            <th>Mã học sinh</th>
                            <th>Họ và tên học sinh</th>
                            <th class="th__last">Vắng?</th>
                        </tr>
                        <% 	List<AbsenceResponse> listAbsenceResponses = (List)request.getAttribute("listAbsences");
                            for (int i = 0; i < listAbsenceResponses.size(); i++) {
                        %>
                        <tr>
                            <td><%= i + 1 %></td>
                            <td><%= listAbsenceResponses.get(i).getStudent_id() %></td>
                            <td><%= listAbsenceResponses.get(i).getName() %></td>
                            <% if (listAbsenceResponses.get(i).isAbsent()) {%>
                            	<td><input type="checkbox" class="absent-checkbox" data-student_id="<%= listAbsenceResponses.get(i).getStudent_id()%>" checked></td>
                        	<% } else {%>
                        		<td><input type="checkbox" class="absent-checkbox" data-student_id="<%= listAbsenceResponses.get(i).getStudent_id()%>"></td>
                        	<% } %>
                        </tr>
                        <% } %>
                    </table>
                </div>
                <input type="button" value="Lưu thay đổi" id="saveChanges">
                <input type="button" value="Cập nhật thể trạng" id="changeToPhysical">
            </div>
        </div>
    </div>
</body>
<script type="text/javascript">
    document.getElementById("changeToPhysical").addEventListener("click", async function () {
        window.location.href = "teachers?mode=changeToPhysical";
    });
    document.getElementById("saveChanges").addEventListener("click", async function () {
        const selectedCheckboxes = document.querySelectorAll(".absent-checkbox:checked");
        const studentIds = Array.from(selectedCheckboxes).map(checkbox => checkbox.getAttribute("data-student_id"));
        console.log(studentIds)
        if (studentIds.length > 0) {
            const update = {studentIds};
            const response = await fetch("./absences?mode=updateAbsent", {
                method: "POST",
                headers: {"Content-type": "application/json"},
                body: JSON.stringify(update)
            });
            if (response.ok) {
                alert("Cập nhập thành công!");
                location.reload();
            } else {alert("Không thể cập nhật thông tin!");}
        }
    });
</script>
</html>