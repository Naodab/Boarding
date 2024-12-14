import {html} from "../common.js";

function renderDetailBoardingClass(boardingClass) {
    return html`
		<form class="modal closure active update" id="detail-teacher" 
			  method="POST" action="boardingClasses?mode=update">
			<h1 class="modal__title">Chi tiết lớp học</h1>
			<i class="fa-solid fa-xmark btn-icon btn-close" id="back"></i>
			<div class="error-message"></div>
			<div class="main-modal">
				<div class="main-header">
					<div class="data-header">
						<div class="admin-form-group">
							<div class="admin-form-group first-column">
								<label for="id">Mã lớp học:</label>
								<input type="text" id="id" name="boardingClass_id" value="${boardingClass.boardingClassId}" readonly>
							</div>
						</div>
						<div class="admin-form-group">
							<label for="name">Tên lớp:</label>
							<input type="text" id="name" name="name" class="form-control"
								   value="${boardingClass.name}" readonly>
                            <span class="form-message"></span>
                        </div>
                        <div class="admin-form-group">
                            <label for="name">Phòng:</label>
                            <input type="text" id="room" name="room" class="form-control"
                                   value="${boardingClass.room}" readonly>
                            <span class="form-message"></span>
                        </div>
                        <div class="admin-form-group">
							<label for="class">Giáo viên:</label>
							<span class="form-select">${boardingClass.teacherName}</span>
                            <span class="form-message"></span>
                        </div>
						<div class="admin-form-group">
							<label for="address">Số chố ngủ</label>
							<input type="number" id="numberOfBed" name="numberOfBed" class="form-control"
								   value="${boardingClass.numberOfBed}" readonly>
                            <span class="form-message"></span>
                        </div>
                        <div class="admin-form-group">
                            <div class="admin-form-group">
                                <label for="">Số học sinh:</label>
                                <span class="form-select">${boardingClass.numberStudent}</span>
                            </div>
                            <span class="second-column detail" id="student-detail">Xem chi tiết</span>
                        </div>
					</div>
				</div>
				<div class="modal__function top--margin">
					<div class="btn btn--green modal__btn" id="update-btn">Cập nhật</div>
					<div class="btn btn--pink modal__btn" id="delete-btn">Xóa</div>
				</div>
			</div>
		</form>>
	`;
}

function renderUpdateBoardingClass({boardingClass, teachers}) {
    return html`
		<form class="modal closure active update" id="update-boardingClass" 
			  method="POST" action="boardingClasses?mode=update">
			<h1 class="modal__title">Cập nhật lớp học</h1>
			<i class="fa-solid fa-xmark btn-icon btn-close" id="back"></i>
			<div class="error-message"></div>
			<div class="main-modal">
				<div class="main-header">
					<div class="data-header">
						<div class="admin-form-group">
							<div class="admin-form-group first-column">
								<label for="id">Mã lớp học:</label>
								<input type="text" id="id" name="boardingClassId" value="${boardingClass.boardingClassId}" readonly>
							</div>
						</div>
						<div class="admin-form-group">
							<label for="name">Tên lớp:</label>
							<input type="text" id="name" name="name" class="form-control"
								   value="${boardingClass.name}">
                            <span class="form-message"></span>
                        </div>
                        <div class="admin-form-group">
                            <label for="name">Phòng:</label>
                            <input type="text" id="room" name="room" class="form-control"
                                   value="${boardingClass.room}">
                            <span class="form-message"></span>
                        </div>
                        <div class="admin-form-group">
							<label for="class">Giáo viên:</label>
                            <select class="selection" name="teacherId">
                                ${teachers.map(teacher => html`
                                    <option value="${teacher.id}" 
                                            ${boardingClass.teacherId === teacher.id && 'selected'}>
                                        ${teacher.id + ". " +teacher.name}
                                    </option>
                                `)}
                            </select>
                            <span class="form-message"></span>
                        </div>
						<div class="admin-form-group">
							<label for="address">Số chố ngủ</label>
							<input type="number" id="numberOfBed" name="numberOfBed" class="form-control"
								   value="${boardingClass.numberOfBed}">
                            <span class="form-message"></span>
                        </div>
					</div>
				</div>
				<div class="modal__function top--margin">
                    <input type="submit" class="btn btn--green modal__btn updatable" value="Xác nhận">
					<div class="btn btn--pink modal__btn" id="delete-btn">Xóa</div>
				</div>
			</div>
		</form>>
	`;
}


function renderAddBoardingClass({nextId, teachers}) {
    return html`
		<form class="modal closure active update" id="add-boardingClass" 
			  method="POST" action="boardingClasses?mode=add">
			<h1 class="modal__title">Thêm lớp học</h1>
			<i class="fa-solid fa-xmark btn-icon btn-close" id="back"></i>
			<div class="error-message"></div>
			<div class="main-modal">
				<div class="main-header">
					<div class="data-header">
						<div class="admin-form-group">
							<div class="admin-form-group first-column">
								<label for="id">Mã lớp học:</label>
								<input type="text" id="id" name="boardingClassId" value="${nextId}" readonly>
							</div>
						</div>
						<div class="admin-form-group">
							<label for="name">Tên lớp:</label>
							<input type="text" id="name" name="name" class="form-control">
                            <span class="form-message"></span>
                        </div>
                        <div class="admin-form-group">
                            <label for="name">Phòng:</label>
                            <input type="text" id="room" name="room" class="form-control">
                            <span class="form-message"></span>
                        </div>
                        <div class="admin-form-group">
							<label for="class">Giáo viên:</label>
                            <select class="selection" name="teacherId">
                                ${teachers.map(teacher => html`
                                    <option value="${teacher.id}">
                                        ${teacher.id + ". " +teacher.name}
                                    </option>
                                `)}
                            </select>
                            <span class="form-message"></span>
                        </div>
						<div class="admin-form-group">
							<label for="address">Số chố ngủ</label>
							<input type="number" id="numberOfBed" name="numberOfBed" class="form-control">
                            <span class="form-message"></span>
                        </div>
					</div>
				</div>
				<div class="modal__function top--margin">
                    <input type="submit" class="btn btn--green modal__btn updatable" value="Thêm">
					<div class="btn btn--pink modal__btn" id="delete-btn">Xóa</div>
				</div>
			</div>
		</form>>
	`;
}

function renderStudentsDetailModal() {
    return html`
		<div class="modal closure active" id="detail-students">
			<h1 class="modal__title">Chi tiết</h1>
			<i class="fa-solid fa-xmark btn-icon btn-close" id="back"></i>
			<div class="main-modal">
				<table class="table" id="detail-students-table">
					<tr>
                        <th class="th__first">Mã</th>
                        <th class="th__last">Học sinh</th>
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

export {
    renderAddBoardingClass,
    renderDetailBoardingClass,
    renderStudentsDetailModal,
    renderUpdateBoardingClass
}