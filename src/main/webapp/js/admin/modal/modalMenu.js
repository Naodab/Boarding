import {html} from "../common.js";

function renderAddMenu({nextId, mainFoods, subFoods}) {
    return html`
        <form class="modal closure active update" id="add-food"
              method="POST" action="menus?mode=update">
            <h1 class="modal__title">Thêm thực đơn</h1>
            <i class="fa-solid fa-xmark btn-icon btn-close" id="back"></i>
            <div class="error-message"></div>
            <div class="main-modal">
                <div class="main-header">
                    <div class="data-header">
                        <div class="admin-form-group">
                            <div class="admin-form-group first-column">
                                <label for="id">Mã thực đơn:</label>
                                <input type="text" id="id" name="foodId" value="${nextId}" readonly>
                            </div>
                        </div>
                        <div class="admin-form-group">
                            <label for="name">Tên món chính</label>
                        </div>
                        <div class="admin-form-group">
                            <label for="name"></label>  
                            <select name="mainFoodId0" class="selection">
                                ${mainFoods.map(food => html`<option value="${food.id}">${food.name}</option>`)}
                            </select>
                            <span class="form-message"></span>
                        </div>
                        <div class="admin-form-group">
                            <label for="name"></label>
                            <select name="mainFood1Id" class="selection">
                                ${mainFoods.map(food => html`<option value="${food.id}">${food.name}</option>`)}
                            </select>
                            <span class="form-message"></span>
                        </div>
                        <div class="admin-form-group">
                            <label for="name"></label>
                            <select name="mainFoodId2" class="selection">
                                ${mainFoods.map(food => html`<option value="${food.id}">${food.name}</option>`)}
                            </select>
                            <span class="form-message"></span>
                        </div>
                        <div class="admin-form-group">
                            <label for="name"></label>
                            <select name="mainFoodId3" class="selection">
                                ${mainFoods.map(food => html`<option value="${food.id}">${food.name}</option>`)}
                            </select>
                            <span class="form-message"></span>
                        </div>
                        <div class="admin-form-group">
                            <label for="name"></label>
                            <select name="mainFoodId4" class="selection">
                                ${mainFoods.map(food => html`<option value="${food.id}">${food.name}</option>`)}
                            </select>
                            <span class="form-message"></span>
                        </div>
                        <div class="admin-form-group">
                            <label for="name">Tên món phụ</label>
                        </div>
                        <div class="admin-form-group">
                            <label for="name"></label>
                            <select name="subFoodId" class="selection">
                                ${subFoods.map(food => html`<option value="${food.id}">${food.name}</option>`)}
                            </select>
                            <span class="form-message"></span>
                        </div>
                    </div>
                </div>
                <div class="modal__function top--margin">
                    <input type="submit" class="btn btn--green modal__btn updatable" value="Thêm">
                </div>
            </div>
        </form>
    `
}

function renderDetailMenu(menu) {
    console.log(menu)
    return html`
        <form class="modal closure active update" id="add-food"
              method="POST" action="menus?mode=update">
            <h1 class="modal__title">Chi tiết thực đơn</h1>
            <i class="fa-solid fa-xmark btn-icon btn-close" id="back"></i>
            <div class="error-message"></div>
            <div class="main-modal">
                <div class="main-header">
                    <div class="data-header">
                        <div class="admin-form-group">
                            <div class="admin-form-group first-column">
                                <label for="id">Mã thực đơn:</label>
                                <input type="text" id="id" name="foodId" value="${menu.menuId}" readonly>
                            </div>
                        </div>
                        <div class="admin-form-group">
                            <label for="name">Tên món chính</label>
                        </div>
                        ${menu.mainFoods.map(food => html`
                            <div class="admin-form-group">
                                <label for="name"></label>
                                <span class="form-select">${food}</span>
                                <span class="form-message"></span>
                            </div>
                        `)}
                        <div class="admin-form-group">
                            <label for="name">Tên món phụ</label>
                        </div>
                        <div class="admin-form-group">
                            <label for="name"></label>
                            <span class="form-select ">${menu.subFood}</span>
                            <span class="form-message"></span>
                        </div>
                    </div>
                </div>
                <div class="modal__function top--margin">
                    ${menu.active && '<div class="btn btn--pink modal__btn" id="update-btn">Tắt hoạt động</div>'}
                    ${!menu.active && '<div class="btn btn--blue modal__btn" id="update-btn">Bật hoạt động</div>'}
                </div>
            </div>
        </form>
    `
}

export {
    renderDetailMenu,
    renderAddMenu
}