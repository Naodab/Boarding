import {html} from "../common.js";

function renderDetailFood(food) {
    return html`
        <form class="modal closure active update" id="add-food"
              method="POST" action="foods?mode=add">
            <h1 class="modal__title">Chi tiết món ăn</h1>
            <i class="fa-solid fa-xmark btn-icon btn-close" id="back"></i>
            <div class="error-message"></div>
            <div class="main-modal">
                <div class="main-header">
                    <div class="data-header">
                        <div class="admin-form-group">
                            <div class="admin-form-group first-column">
                                <label for="id">Mã món ăn:</label>
                                <input type="text" id="id" name="foodId" value="${food.food_id}" readonly>
                            </div>
                        </div>
                        <div class="admin-form-group">
                            <label for="name">Tên món ăn:</label>
                            <input type="text" id="name" name="name" class="form-control" value="${food.name}" readonly>
                            <span class="form-message"></span>
                        </div>
                        <div class="admin-form-group">
                            <label for="sex">Phân loại:</label>
                            <div class="form-control radio-container">
                                <input type="radio" id="sex-male" name="category"
                                       class="form-control" value="true" ${food.category ? "checked" : ""}>Món chính
                                <input type="radio" id="sex-female" name="category"
                                       class="form-control" value="false" ${!food.category ? "checked" : ""}>Món phụ
                                <span class="form-message"></span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal__function top--margin">
                    <div class="btn btn--green modal__btn" id="update-btn">Cập nhật</div>
                    ${food.canDelete && html`
                        <div class="btn btn--pink modal__btn" id="delete-btn">Xóa</div>
                    `}
                </div>
            </div>
        </form>
    `
}

function renderUpdateFood(food) {
    return html`
        <form class="modal closure active update" id="update-food"
              method="POST" action="foods?mode=update">
            <h1 class="modal__title">Cập nhật món ăn</h1>
            <i class="fa-solid fa-xmark btn-icon btn-close" id="back"></i>
            <div class="error-message"></div>
            <div class="main-modal">
                <div class="main-header">
                    <div class="data-header">
                        <div class="admin-form-group">
                            <div class="admin-form-group first-column">
                                <label for="id">Mã món ăn:</label>
                                <input type="text" id="id" name="foodId" value="${food.food_id}" readonly>
                            </div>
                        </div>
                        <div class="admin-form-group">
                            <label for="name">Tên món ăn:</label>
                            <input type="text" id="name" name="name" class="form-control" value="${food.name}">
                            <span class="form-message"></span>
                        </div>
                        <div class="admin-form-group">
                            <label for="sex">Phân loại:</label>
                            <div class="form-control radio-container">
                                <input type="radio" id="sex-male" name="category"
                                       class="form-control" value="true" ${food.category ? "checked" : ""}>Món chính
                                <input type="radio" id="sex-female" name="category"
                                       class="form-control" value="false" ${food.category ? "" : "checked"}}>Món phụ
                                <span class="form-message"></span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal__function top--margin">
                    <input type="submit" class="btn btn--green modal__btn updatable" value="Cập nhật">
                </div>
            </div>
        </form>
    `
}

function renderAddFood(nextId) {
    return html`
        <form class="modal closure active update" id="add-food"
              method="POST" action="foods?mode=add">
            <h1 class="modal__title">Thêm món ăn</h1>
            <i class="fa-solid fa-xmark btn-icon btn-close" id="back"></i>
            <div class="error-message"></div>
            <div class="main-modal">
                <div class="main-header">
                    <div class="data-header">
                        <div class="admin-form-group">
                            <div class="admin-form-group first-column">
                                <label for="id">Mã món ăn:</label>
                                <input type="text" id="id" name="foodId" value="${nextId}" readonly>
                            </div>
                        </div>
                        <div class="admin-form-group">
                            <label for="name">Tên món ăn:</label>
                            <input type="text" id="name" name="name" class="form-control">
                            <span class="form-message"></span>
                        </div>
                        <div class="admin-form-group">
                            <label for="sex">Phân loại:</label>
                            <div class="form-control radio-container">
                                <input type="radio" id="sex-male" name="category"
                                       class="form-control" value="true" checked>Món chính
                                <input type="radio" id="sex-female" name="category"
                                       class="form-control" value="false"}>Món phụ
                                <span class="form-message"></span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal__function top--margin">
                    <input type="submit" class="btn btn--green modal__btn updatable" value="Thêm">
                </div>
            </div>
        </form>
    `;
}

export {
    renderDetailFood,
    renderAddFood,
    renderUpdateFood
}