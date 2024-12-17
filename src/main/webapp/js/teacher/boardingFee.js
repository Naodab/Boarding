import {renderAlertModal, turnOnModal} from "../modal.js";

document.getElementById("boardingFee").addEventListener("change", function () {
    const boardingFeeId = this.value;
    if (boardingFeeId) {
        window.location.href = "teachers?mode=boardingFee&boardingFeeId=" + boardingFeeId;
    }
});

document.getElementById("saveChanges").addEventListener("click", async function () {
    const rows = document.querySelectorAll("#table tr");
    const data = [];
    rows.forEach((row, index) => {
        if (index === 0) return;
        const studentId = row.querySelector("input[type='radio']").name;
        const selectedStatus = row.querySelector("input[type='radio']:checked").value;
        const invoiceId = row.querySelector(".invoiceId").textContent;
        data.push({
            boardingFeeId: document.getElementById("boardingFee").value,
            invoiceId: invoiceId,
            studentId: studentId,
            status: selectedStatus
        });
    });
    if (data.length > 0) {
        const response = await fetch("./boardingFees?mode=updateBoardingFee", {
            method: "POST",
            headers: {"Content-type": "application/json"},
            body: JSON.stringify(data)
        });
        if (response.ok) {
            turnOnModal(renderAlertModal, "Cập nhật thành công")
            document.getElementById("ok").addEventListener("click", function () {
                window.location.reload();
            })
        } else {turnOnModal(renderAlertModal, "Vui lòng chọn đợt thu tiền.")}
    }
});