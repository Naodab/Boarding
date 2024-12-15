import {html} from "../common.js";

function renderAddBoardingFee() {
    return html`
        <form class="modal closure active update" id="add-fee"
              method="POST" action="boardingFees?mode=add">
            <h1 class="modal__title">Tạo đợt thu tiền</h1>
            <i class="fa-solid fa-xmark btn-icon btn-close" id="back"></i>
            <div class="error-message"></div>
            <div class="main-modal">
                <div class="main-header">
                    <div class="data-header">
                        <div class="admin-form-group">
                            <label for="name">Tháng:</label>
                            <input type="number" id="month" name="month" class="form-control">
                            <span class="form-message"></span>
                        </div>
                        <div class="admin-form-group">
                            <label for="dateOfBirth">Ngày bắt đầu:</label>
                            <input type="date" id="startDate" name="startDate" class="form-control"
                                   value="${new Date().toISOString().split('T')[0]}">
                            <span class="form-message"></span>
                        </div>
                        <div class="admin-form-group">
                            <label for="dateOfBirth">Ngày kết thúc:</label>
                            <input type="date" id="endDate" name="endDate" class="form-control"
                                   value="${new Date().toISOString().split('T')[0]}">
                            <span class="form-message"></span>
                        </div>
                        <div class="admin-form-group">
                            <label for="address">Giá buổi chính</label>
                            <input type="number" id="mainCosts" name="mainCosts" class="form-control">
                            <span class="form-message"></span>
                        </div>
                        <div class="admin-form-group">
                            <label for="email">Giá buổi phụ:</label>
                            <input type="number" id="subCosts" name="subCosts" class="form-control">
                            <span class="form-message"></span>
                        </div>
                    </div>
                </div>
                <div class="modal__function top--margin">
                    <input type="submit" class="btn btn--green modal__btn updatable" value="Xác nhận">
                    <div class="btn btn--pink modal__btn" id="cancel">Hủy</div>
                </div>
            </div>
        </form>
    `
}

function renderInvoiceByClass(boardingClasses) {
    return html`
        <div class="modal closure active" id="detail-students">
            <h1 class="modal__title">Danh sách theo lớp</h1>
            <i class="fa-solid fa-xmark btn-icon btn-close" id="back"></i>
            <div class="main-modal">
                <select class="selection" id="boardingClass">
                    ${boardingClasses.map(boardingClass => html`
                        <option value="${boardingClass.id}">${boardingClass.name}</option>
                    `)}
                </select>
                <table class="table" id="detail-students-table">
                    <tr>
                        <th class="th__first">Mã</th>
                        <th>Họ và tên</th>
                        <th>Chi phí dư</th>
                        <th>Buổi chính</th>
                        <th>Buổi phụ</th>
                        <th>Số ngày học</th>
                        <th class="th__last">Số tiền nộp</th>
                    </tr>
                </table>
                <div class="pages-container" id="student-detail-page-container"></div>
                <div class="modal__function top--margin">
                    <div class="btn btn--green modal__btn" id="cancel">Thoát</div>
                </div>
            </div>
        </div>
    `;
}

function renderChangeEatingDay({menus, isDeleted, menuId}) {
    return html`
        <div class="modal closure active" id="detail-students">
            <h1 class="modal__title">Thay đổi thực đơn</h1>
            <i class="fa-solid fa-xmark btn-icon btn-close" id="back"></i>
            <div class="main-modal">
                <div class="admin-form-group">
                    <label>Thực đơn</label>
                    <select class="selection" id="menu-selection">
                        ${menus.map(menu =>
                            html`<option value="${menu}" ${menuId === menu && "selected"}>${menu}</option>`
                        )}
                    </select>
                </div>
                <div class="modal__function top--margin">
                    <div class="btn--green btn modal__btn" id="change-btn">Chọn</div>
                    <div class="btn btn--pink modal__btn" id="delete-btn">${isDeleted && "Bỏ xóa" || "Xóa"}</div>
                </div>
            </div>
        </div>
    `;
}

export {
    renderInvoiceByClass,
    renderAddBoardingFee,
    renderChangeEatingDay
}