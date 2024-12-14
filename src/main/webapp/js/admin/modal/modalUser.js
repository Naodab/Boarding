import {html} from "../common.js";

function renderChangeDefaultModal(oldPassword) {
    return html`
        <form class="modal closure active" id="change-default" method="POST" action="users?mode=changeDefault">
            <h1 class="modal__title">Thay đổi mật khẩu mặc định</h1>
            <i class="fa-solid fa-xmark btn-icon btn-close" id="back"></i>
            <div class="error-message"></div>
            <div class="main-modal">
                <div class="password-item form-group">
                    <label for="oldPassword" class="modal__label">Mật khẩu cũ</label>

                    <div class="password-container">
                        <input type="password" id="oldPassword" name="oldPassword" class="password__input" 
                               value="${oldPassword}" readonly>
                        <i class="fa-solid fa-eye on"></i>
                        <i class="fa-solid fa-eye-slash off"></i>
                    </div>
                    <div class="form-message"></div>
                </div>

                <div class="password-item form-group">
                    <label for="oldPassword" class="modal__label">Mật khẩu mới</label>

                    <div class="password-container">
                        <input type="password" id="password" name="password" class="password__input">
                        <i class="fa-solid fa-eye on"></i>
                        <i class="fa-solid fa-eye-slash off"></i>
                    </div>
                    <div class="form-message"></div>
                </div>
                <div class="password-item form-group">
                    <label for="oldPassword" class="modal__label">Nhập lại mật khẩu</label>

                    <div class="password-container">
                        <input type="password" id="prePassword" name="prePassword" class="password__input">
                        <i class="fa-solid fa-eye on"></i>
                        <i class="fa-solid fa-eye-slash off"></i>
                    </div>
                    <div class="form-message"></div>
                </div>
                <div class="modal__function top--margin">
                    <input type="submit" class="btn btn--green modal__btn" id="confirm-login" value="Thay đổi">
                </div>
            </div>
        </form>
    `;
}

export {renderChangeDefaultModal}