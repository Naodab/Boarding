import {renderParentsModal, renderStudentModal, turnOnModal} from "../modal.js";

document.querySelectorAll(".student-info").forEach(button => {
    button.addEventListener("click", async function () {
        const studentId = this.getAttribute("data");
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
                turnOnModal(renderStudentModal, data);
            }
        } catch (err) {console.error("Lỗi khi gửi dữ liệu:", err);}
    });
});

document.querySelectorAll(".parent-info").forEach(button => {
    button.addEventListener("click", async function () {
        const parentId = this.getAttribute("data");
        const tableData = {parentId};
        try {
            const response = await fetch('parents?mode=detailParents&prid=' + parentId, {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify(tableData)
            });
            if (response.ok) {
                const data = await response.json();
                data.notAdmin = true;
                console.log(data);
                turnOnModal(renderParentsModal, data);
            }
        } catch (err) {console.error("Lỗi khi gửi dữ liệu:", err);}
    });
});