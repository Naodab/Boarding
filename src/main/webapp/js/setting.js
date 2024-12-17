import {addEventForEye, renderAlertModal, renderChangePassword, renderSettingModal, turnOnModal} from "./modal.js";
import Validator from "./validator.js";

const settingBtn = document.querySelector("#setting-btn");
if (settingBtn) {
    settingBtn.addEventListener("click", function() {
        turnOnModal(renderSettingModal);

        document.querySelector("#change-password-btn").onclick = () => {
            turnOnModal(renderChangePassword);
            addEventForEye();
            Validator({
                form: "#change-password",
                formGroupSelector: ".form-group",
                errorSelector: ".form-message",
                rules: [
                    Validator.isRequired('#oldPassword'),
                    Validator.isRequired("#password"),
                    Validator.isRequired("#prePassword"),
                ],
                onSubmit: data => {
                    if (data.password !== data.prePassword) {
                        document.querySelector(".error-message")
                            .innerText = "Mật khẩu nhập lại không chính xác.";
                        return;
                    }
                    fetch('./auth?mode=changePassword', {
                        method: "POST",
                        headers: {
                            "Content-Type": "application/json",
                        },
                        body: JSON.stringify(data),
                    }).then(resp => resp.json()).then(data => {
                        if (data.result) {
                            turnOnModal(renderAlertModal, "Đổi mật khẩu thành công.");
                        } else {
                            document.querySelector(".error-message").innerText = "Mật khẩu cũ không chính xác";
                        }
                    });
                }
            })
        }
    })
}