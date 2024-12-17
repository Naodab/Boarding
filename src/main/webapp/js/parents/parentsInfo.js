import {renderStudentModal, turnOnModal} from "../modal.js";

document.getElementById("studentDetail").addEventListener("click", async function () {
	const selectBox = document.getElementById('studentName');
    const studentId = selectBox.value;
    const tableData = {studentId};
    try {
    	const response = await fetch('students?mode=studentInfo&stid=' + studentId, {
        	method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(tableData)
        });
        if (response.ok) {
            const data = await response.json();
            data.notAdmin = true;
            console.log(data);
            turnOnModal(renderStudentModal, data);
        }
    } catch (err) {console.error("Lỗi khi gửi dữ liệu:", err);}
});

document.getElementById("teacherDetail").addEventListener("click", async function () {
	const selectBox = document.getElementById('teacherName');
    const teacherId = selectBox.value;
    const tableData = {teacherId};
    try {
    	const response = await fetch('teachers?mode=teacherInfo&tcid=' + teacherId, {
        	method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(tableData)
        });
        if (response.ok) {
            const data = await response.json();
            data.notAdmin = true;
            console.log(data);
            //turnOnModal for teacher;
        }
    } catch (err) {console.error("Lỗi khi gửi dữ liệu:", err);}
});