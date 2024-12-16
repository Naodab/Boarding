import {renderAlertModal, turnOnModal} from "../modal.js";

document.getElementById("absentStudent").addEventListener("click", function () {
    window.location.href = "teachers?mode=changeToAbsent";
});

document.getElementById("saveChanges").addEventListener("click", async function () {
    const heights = Array.from(document.querySelectorAll(".height-input"));
    const weights = Array.from(document.querySelectorAll(".weight-input"));
    const updates = [];
    heights.forEach(heightInput => {
        let student_id = heightInput.getAttribute("data-student_id");
        let height = heightInput.value || heightInput.getAttribute("data-oldVal");
        let oldHeight = heightInput.getAttribute("data-oldVal");
        let weight, oldWeight;
        weights.forEach(weightInput => {
            if (weightInput.getAttribute("data-student_id") === student_id) {
                weight = weightInput.value || weightInput.getAttribute("data-oldVal");
                oldWeight = weightInput.getAttribute("data-oldVal");
            }
        });
        if (oldWeight !== weight || oldHeight !== height) {
            updates.push({student_id, height, weight});
        }
    });
    if (updates.length > 0) {
        const response = await fetch("./students?mode=updatePhysical", {
            method: "POST",
            headers: {"Content-type": "application/json"},
            body: JSON.stringify(updates)
        });
        if (response.ok) {
            turnOnModal(renderAlertModal, "Cập nhật thành công")
            document.getElementById("ok").addEventListener("click", function () {
                window.location.reload();
            })
        } else {turnOnModal(renderAlertModal, "Không thể cập nhật.")}
    }
});