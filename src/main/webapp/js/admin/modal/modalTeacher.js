import {html} from "../common.js";

function renderDetailTeacherModal(teacher) {
    return html`
		<form class="modal closure active update" id="detail-teacher" 
			  method="POST" action="teachers?mode=update">
			<h1 class="modal__title">Chi tiết giáo viên</h1>
			<i class="fa-solid fa-xmark btn-icon btn-close" id="back"></i>
			<div class="error-message"></div>
			<div class="main-modal">
				<div class="main-header">
					<div class="data-header">
						<div class="admin-form-group">
							<div class="admin-form-group first-column">
								<label for="id">Mã giáo viên:</label>
								<input type="text" id="id" name="teacher_id" value="${teacher.teacher_id}" readonly>
							</div>
							<div class="admin-form-group second-column">
								<label for="sex">Giới tính:</label>
								<div class="form-control radio-container">
									<input type="radio" id="sex-male" name="sex" class="form-control" 
										   value="Nam" ${teacher.sex ? 'checked' : ''} readonly>Nam
									<input type="radio" id="sex-female" name="sex" class="form-control" 
										   value="Nữ" ${!teacher.sex ? 'checked' : ''} readonly>Nữ
								</div>
							</div>
						</div>
						<div class="admin-form-group">
							<label for="name">Họ và tên:</label>
							<input type="text" id="name" name="name" class="form-control"
								   value="${teacher.name}" readonly>
						</div>
						<div class="admin-form-group">
							<label for="dateOfBirth">Ngày sinh:</label>
							<input type="date" id="dateOfBirth" name="dateOfBirth" class="form-control"
								   value="${teacher.dateOfBirth}" readonly>
						</div>
						<div class="admin-form-group">
							<label for="class">Lớp:</label>
							<span class="form-select">${teacher.boardingClass}</span>
						</div>
						<div class="admin-form-group">
							<label for="address">Địa chỉ</label>
							<input type="text" id="address" name="address" class="form-control"
								   value="${teacher.address}" readonly>
						</div>
						<div class="admin-form-group">
							<label for="email">Email:</label>
							<input type="email" id="email" name="email" class="form-control"
								   value="${teacher.email}" readonly>
						</div>
						<div class="admin-form-group">
							<label for="phone">Số điện thoại:</label>
							<input type="number" id="phone" name="phone" value="${teacher.phone}" readonly>
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

function renderAddTeacherModal({nextId, classes}) {
    return html`
		<form class="modal closure active update" id="update-student" 
			  method="POST" action="teachers?mode=add">
			<h1 class="modal__title">Thêm giáo viên</h1>
			<i class="fa-solid fa-xmark btn-icon btn-close" id="back"></i>
			<div class="error-message"></div>
			<div class="main-modal">
				<div class="main-header">
					<div class="data-header">
						<div class="admin-form-group">
							<div class="admin-form-group first-column">
								<label for="id">Mã giáo viên:</label>
								<input type="text" id="id" name="teacher_id" value="${nextId}" readonly>
                                <span class="form-message"></span>
                            </div>
							<div class="admin-form-group second-column">
								<label for="sex">Giới tính:</label>
								<div class="form-control radio-container">
									<input type="radio" id="sex-male" name="sex" 
										   class="form-control" value="Nam" checked>Nam
									<input type="radio" id="sex-female" name="sex" 
										   class="form-control" value="Nữ"}>Nữ
                                    <span class="form-message"></span>
                                </div>
							</div>
						</div>
						<div class="admin-form-group">
							<label for="name">Họ và tên:</label>
							<input type="text" id="name" name="name" class="form-control">
                            <span class="form-message"></span>
                        </div>
						<div class="admin-form-group">
							<label for="dateOfBirth">Ngày sinh:</label>
							<input type="date" id="dateOfBirth" name="dateOfBirth" class="form-control" 
								   value="${new Date().toISOString().split('T')[0]}">
                            <span class="form-message"></span>
                        </div>
						<div class="admin-form-group">
							<label for="class">Lớp:</label>
							<select class="updatable" name="boardingClass_id">
								${classes.map(classs =>
                                    html`<option value="${classs.id}">
										${classs.id + ". " + classs.name}
									</option>`
                                )}
							</select>
                            <span class="form-message"></span>
                        </div>
						<div class="admin-form-group">
							<label for="address">Địa chỉ</label>
							<input type="text" id="address" name="address" class="form-control">
                            <span class="form-message"></span>
                        </div>
						<div class="admin-form-group">
							<label for="email">Email:</label>
							<input type="email" id="email" name="email" class="form-control">
                            <span class="form-message"></span>
                        </div>
						<div class="admin-form-group">
							<label for="phone">Số điện thoại:</label>
							<input type="number" id="phone" name="phone" required>
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
	`;
}

function renderUpdateTeacherModal({teacher, classes}) {
    return html`
		<form class="modal closure active update" id="update-teacher" 
			  method="POST" action="teachers?mode=update">
			<h1 class="modal__title">Chi tiết giáo viên</h1>
			<i class="fa-solid fa-xmark btn-icon btn-close" id="back"></i>
			<div class="error-message"></div>
			<div class="main-modal">
				<div class="main-header">
					<div class="data-header">
						<div class="admin-form-group">
							<div class="admin-form-group first-column">
								<label for="id">Mã giáo viên:</label>
								<input type="text" id="id" name="teacher_id" value="${teacher.teacher_id}" readonly>
                                <span class="form-message"></span>
							</div>
							<div class="admin-form-group second-column">
								<label for="sex">Giới tính:</label>
								<div class="form-control radio-container">
									<input type="radio" id="sex-male" name="sex" class="form-control" 
										   value="Nam" ${teacher.sex ? 'checked' : ''}>Nam
									<input type="radio" id="sex-female" name="sex" class="form-control" 
										   value="Nữ" ${!teacher.sex ? 'checked' : ''}>Nữ
								</div>
                                <span class="form-message"></span>
                            </div>
						</div>
						<div class="admin-form-group">
							<label for="name">Họ và tên:</label>
							<input type="text" id="name" name="name" class="form-control"
								   value="${teacher.name}">
                            <span class="form-message"></span>
                        </div>
						<div class="admin-form-group">
							<label for="dateOfBirth">Ngày sinh:</label>
							<input type="date" id="dateOfBirth" name="dateOfBirth" class="form-control"
								   value="${teacher.dateOfBirth}">
                            <span class="form-message"></span>
                        </div>
						<div class="admin-form-group">
							<label for="class">Lớp:</label>
                            <select class="updatable" name="boardingClass_id">
                                ${classes.map(classs =>
                                        html`<option value="${classs.id}" ${classs.name === teacher.boardingClass && "selected"}>
										${classs.id + ". " + classs.name}
									</option>`
                                )}
                            </select>
                            <span class="form-message"></span>
                        </div>
						<div class="admin-form-group">
							<label for="address">Địa chỉ</label>
							<input type="text" id="address" name="address" class="form-control"
								   value="${teacher.address}">
                            <span class="form-message"></span>
                        </div>
						<div class="admin-form-group">
							<label for="email">Email:</label>
							<input type="email" id="email" name="email" class="form-control"
								   value="${teacher.email}">
                            <span class="form-message"></span>
                        </div>
						<div class="admin-form-group">
							<label for="phone">Số điện thoại:</label>
							<input type="number" id="phone" name="phone" value="${teacher.phone}">
                            <span class="form-message"></span>
                        </div>
					</div>
				</div>
				<div class="modal__function top--margin">
                    <input type="submit" class="btn btn--green modal__btn updatable" value="Xác nhận">
					<div class="btn btn--pink modal__btn" id="cancel">Hủy</div>
				</div>
			</div>
		</form>>
	`;
}

export {
    renderAddTeacherModal,
    renderUpdateTeacherModal,
    renderDetailTeacherModal
}