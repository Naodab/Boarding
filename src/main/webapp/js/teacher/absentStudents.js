import {renderAlertModal, turnOnModal} from "../modal.js";

document.getElementById("changeToPhysical").addEventListener("click", async function () {
    window.location.href = "teachers?mode=changeToPhysical";
});

document.getElementById("saveChanges").addEventListener("click", async function () {
    const selectedCheckboxes = document.querySelectorAll(".absent-checkbox:checked");
    const studentIds = Array.from(selectedCheckboxes).map(checkbox => checkbox.getAttribute("data-student_id"));
    if (studentIds.length > 0) {
        const update = {studentIds};
        console.log(update);
        const response = await fetch("./absences?mode=updateAbsent", {
            method: "POST",
            headers: {"Content-type": "application/json"},
            body: JSON.stringify(update)
        });
        if (response.ok) {
            turnOnModal(renderAlertModal, "Cập nhật thành công")
            document.getElementById("ok").addEventListener("click", function () {
                window.location.reload();
            })
        } else {
            turnOnModal(renderAlertModal, "Không thể cập nhật.");
        }
    }
});