function html([first, ...strings], ...values) {
	return values.reduce(
		(acc, cur) => acc.concat(cur, strings.shift()),
		[first]
	)
		.filter(x => x && x !== true || x === 0)
		.join('');
}

const $ = document.querySelector.bind(document);
const $$ = document.querySelectorAll.bind(document);

function addEventForEye() {
	const iconEyes = $$(".password-container i");
	iconEyes.forEach(icon => {
		icon.onclick = () => {
			const pwContainer = icon.closest(".password-container");
			if (pwContainer && pwContainer.classList.contains("active")) {
				pwContainer.classList.remove("active");
				pwContainer.querySelector("input").type = "password";
			} else if (pwContainer) {
				pwContainer.classList.add("active");
				pwContainer.querySelector("input").type = "text";
			}
		}
	});
}

function turnOnModal(renderFunction, attr) {
	const overlay = $("#overlay");
	overlay.style.zIndex = "100";
	overlay.innerHTML = renderFunction(attr);
	const backBtn = $("#back");
	if (backBtn) {
		backBtn.onclick = function() {
			turnOffModal();
		};
	}

	const cancel = $("#cancel");
	if (cancel) {
		cancel.onclick = function() {
			turnOffModal();
		}
	}

	const ok = $("#ok");
	if (ok) {
		ok.onclick = function() {
			turnOffModal();
		}
	}
}

function turnOffModal() {
	const overlay = $("#overlay");
	overlay.innerHTML = "";
	overlay.style.zIndex = "-10";
}

function renderLogin(message) {
	return html`
		<form class="modal closure active" id="login" method="POST" action="auth?mode=login">
			<h1 class="modal__title">Đăng nhập</h1>
			<i class="fa-solid fa-xmark btn-icon btn-close" id="back"></i>
			<div class="error-message">${message && message}</div>
			<div class="main-modal">
				<div class="password-item form-group">
					<label for="oldPassword" class="modal__label">Tài khoản</label>
					<div class="password-container">
						<input type="text" id="username" name="username" class="password__input" placeholder="Tài khoản">
					</div>
					<div class="form-message"></div>
				</div>
				<div class="password-item form-group">
					<label for="oldPassword" class="modal__label">Mật khẩu</label>
		
					<div class="password-container">
						<input type="password" id="password" name="password" class="password__input" placeholder="Mật khẩu">
						<i class="fa-solid fa-eye on"></i>
						<i class="fa-solid fa-eye-slash off"></i>
					</div>
					<div class="form-message"></div>
				</div>
				<div class="modal__function top--margin">
					<input type="submit" class="btn btn--green modal__btn" id="confirm-login" value="Đăng nhập">
				</div>
			</div>
		</form>
    `;
}

function renderStudentModal(student) {
	return html`
		<form class="modal closure active" id="update-student" method="POST" action="students?mode=update">
			<h1 class="modal__title">Thông tin chi tiết</h1>
			<i class="fa-solid fa-xmark btn-icon btn-close" id="back"></i>
			<div class="error-message"></div>
			<div class="main-modal">
				<div class="main-header">
					<div class="data-header">
						<div class="admin-form-group">
							<div class="admin-form-group first-column">
								<label for="id">Mã học sinh:</label>
								<input type="text" id="id" name="student_id" value="${student.student_id}" readonly>
							</div>
							<div class="admin-form-group second-column">
								<label for="sex">Giới tính:</label>
								<div class="form-control radio-container">
									<input type="radio" id="sex-male" name="sex" class="form-control" value="Nam"
										${student.sex==="Nam" && "checked" } readonly>Nam
									<input type="radio" id="sex-female" name="sex" class="form-control" value="Nữ"
										${student.sex==="Nữ" && "checked"} readonly>Nữ
								</div>
							</div>
						</div>
						<div class="admin-form-group">
							<label for="name">Họ và tên:</label>
							<input type="text" id="name" name="name" class="form-control" value="${student.name}"  readonly>
						</div>
						<div class="admin-form-group">
							<label for="dateOfBirth">Ngày sinh:</label>
							<input type="date" id="dateOfBirth" name="dateOfBirth" class="form-control" value="${student.dateOfBirth}" readonly>
						</div>
						<div class="admin-form-group">
							<div class="admin-form-group">
								<label for="sex">Ăn phụ:</label>
								<div class="form-control radio-container">
									<input type="radio" id="sex-male" name="subMeal" class="form-control" value="true"
										${student.subMeal && "checked"} readonly>Có
									<input type="radio" id="sex-female" name="subMeal" class="form-control" value="false"
										${!student.subMeal && "checked"} readonly>Không
								</div>
							</div>
							<div class="admin-form-group second-column">
								<label for="class">Lớp:</label>
								<span class="form-select hide">${student.boardingClassName}</span>
							</div>
						</div>
						<div class="admin-form-group">
							<label for="address">Địa chỉ</label>
							<input type="text" id="address" name="address" class="form-control" value="${student.address}"
								 readonly>
						</div>
						<div class="admin-form-group">
							<label for="class">Phụ huynh:</label>
							<span class="form-select hide">${student.parentName}</span>
						</div>
					</div>
				</div>
				<div class="modal__function top--margin">
					<div class="btn btn--green modal__btn hide" id="update-btn">Cập nhật</div>
					<div class="btn btn--pink modal__btn hide" id="delete-btn">Xóa</div>
				</div>
			</div>
		</form>>
	`;
}

function renderStudentUpdateModal(student, parents, classes) {
	return html`
		<form class="modal closure active update" id="update-student" method="POST" action="students?mode=update">
			<h1 class="modal__title">Thông tin chi tiết</h1>
			<i class="fa-solid fa-xmark btn-icon btn-close" id="back"></i>
			<div class="error-message"></div>
			<div class="main-modal">
				<div class="main-header">
					<div class="data-header">
						<div class="admin-form-group">
							<div class="admin-form-group first-column">
								<label for="id">Mã học sinh:</label>
								<input type="text" id="id" name="student_id" value="${student.student_id}" readonly>
							</div>
							<div class="admin-form-group second-column">
								<label for="sex">Giới tính:</label>
								<div class="form-control radio-container">
									<input type="radio" id="sex-male" name="sex" class="form-control" value="Nam"
										${student.sex==="Nam" && "checked" }>Nam
									<input type="radio" id="sex-female" name="sex" class="form-control" value="Nữ"
										${student.sex==="Nữ" && "checked" }>Nữ
								</div>
							</div>
						</div>
						<div class="admin-form-group">
							<label for="name">Họ và tên:</label>
							<input type="text" id="name" name="name" class="form-control" value="${student.name}" >
						</div>
						<div class="admin-form-group">
							<label for="dateOfBirth">Ngày sinh:</label>
							<input type="date" id="dateOfBirth" name="dateOfBirth" class="form-control" value="${student.dateOfBirth}" >
						</div>
						<div class="admin-form-group">
							<div class="admin-form-group">
								<label for="sex">Ăn phụ:</label>
								<div class="form-control radio-container">
									<input type="radio" id="sex-male" name="subMeal" class="form-control" value="true"
										${student.subMeal && "checked"}>Có
									<input type="radio" id="sex-female" name="subMeal" class="form-control" value="false"
										${!student.subMeal && "checked"}>Không
								</div>
							</div>
							<div class="admin-form-group second-column">
								<label for="class">Lớp:</label>
								<span class="form-select hide">${student.boardingClassName}</span>
								<select class="updatable" name="boardingClass_id">
									${classes.map(classs => 
										html`<option value="${classs.id}" ${student.boardingClass_id == classs.id && 'selected'}>
											${classs.id + ". " + classs.name}
										</option>`
									)}
								</select>
							</div>
						</div>
						<div class="admin-form-group">
							<label for="address">Địa chỉ</label>
							<input type="text" id="address" name="address" class="form-control" value="${student.address}">
						</div>
						<div class="admin-form-group">
							<label for="class">Phụ huynh:</label>
							<span class="form-select hide">${student.parentName}</span>
							<select name="parents_id" class="updatable">
								${parents.map(parent => 
									html`<option value="${parent.id}" ${student.parents_id == parent.id && 'selected'}>
										${parent.id + ". " + parent.name}
									</option>`
								)}
							</select>
						</div>
					</div>
				</div>
				<div class="modal__function top--margin">
					<input type="submit" class="btn btn--green modal__btn updatable" value="Xác nhận">
				</div>
			</div>
		</form>>
	`;
}

function renderStudentAddModal({nextId, parents, classes}) {
	return html`
		<form class="modal closure active update" id="update-student" method="POST" action="students?mode=add">
			<h1 class="modal__title">Thông tin chi tiết</h1>
			<i class="fa-solid fa-xmark btn-icon btn-close" id="back"></i>
			<div class="error-message"></div>
			<div class="main-modal">
				<div class="main-header">
					<div class="data-header">
						<div class="admin-form-group">
							<div class="admin-form-group first-column">
								<label for="id">Mã học sinh:</label>
								<input type="text" id="id" name="student_id" value="${nextId}" readonly>
							</div>
							<div class="admin-form-group second-column">
								<label for="sex">Giới tính:</label>
								<div class="form-control radio-container">
									<input type="radio" id="sex-male" name="sex" class="form-control" value="Nam" checked>Nam
									<input type="radio" id="sex-female" name="sex" class="form-control" value="Nữ"}>Nữ
								</div>
							</div>
						</div>
						<div class="admin-form-group">
							<label for="name">Họ và tên:</label>
							<input type="text" id="name" name="name" class="form-control">
						</div>
						<div class="admin-form-group">
							<label for="dateOfBirth">Ngày sinh:</label>
							<input type="date" id="dateOfBirth" name="dateOfBirth" class="form-control" value="${new Date().toISOString().split('T')[0]}">
						</div>
						<div class="admin-form-group">
							<div class="admin-form-group">
								<label for="sex">Ăn phụ:</label>
								<div class="form-control radio-container">
									<input type="radio" id="sex-male" name="subMeal" class="form-control" value="true" checked>Có
									<input type="radio" id="sex-female" name="subMeal" class="form-control" value="false">Không
								</div>
							</div>
							<div class="admin-form-group second-column">
								<label for="class">Lớp:</label>
								<select class="updatable" name="boardingClass_id">
									${classes.map(classs => 
										html`<option value="${classs.id}">
											${classs.id + ". " + classs.name}
										</option>`
									)}
								</select>
							</div>
						</div>
						<div class="admin-form-group">
							<label for="address">Địa chỉ</label>
							<input type="text" id="address" name="address" class="form-control">
						</div>
						<div class="admin-form-group">
							<label for="class">Phụ huynh:</label>
							<select name="parents_id" class="updatable">
								${parents.map(parent => 
									html`<option value="${parent.id}" >
										${parent.id + ". " + parent.name}
									</option>`
								)}
							</select>
						</div>
					</div>
				</div>
				<div class="modal__function top--margin">
					<div class="btn btn--green modal__btn hide" id="update-btn">Cập nhật</div>
					<input type="submit" class="btn btn--green modal__btn updatable" value="Xác nhận">
				</div>
			</div>
		</form>>
	`;
}

function turnOnUpdateStudent(student, parents, classes) {
	const overlay = $("#overlay");
	overlay.style.zIndex = "100";
	overlay.innerHTML = renderStudentUpdateModal(student, parents, classes);

	const backBtn = $("#back");
	if (backBtn) {
		backBtn.onclick = function() {
			turnOffModal();
		};
	}
}

function renderParentsModal(parents) {
	return html`
		<form class="modal closure active" id="update-student" method="POST" action="parents?mode=update">
			<h1 class="modal__title">Thông tin chi tiết</h1>
			<i class="fa-solid fa-xmark btn-icon btn-close" id="back"></i>
			<div class="error-message"></div>
			<div class="main-modal">
				<div class="main-header">
					<div class="data-header">
						<div class="admin-form-group">
							<div class="admin-form-group first-column">
								<label for="id">Mã phụ huynh:</label>
								<input type="text" id="id" name="student_id" value="${parents.parents_id}" readonly>
							</div>
							<div class="admin-form-group second-column">
								<label for="sex">Giới tính:</label>
								<div class="form-control radio-container">
									<input type="radio" id="sex-male" name="sex" class="form-control" value="Nam"
										${parents.sex && "checked" } readonly>Nam
									<input type="radio" id="sex-female" name="sex" class="form-control" value="Nữ"
										${!parents.sex && "checked"} readonly>Nữ
								</div>
							</div>
						</div>
						<div class="admin-form-group">
							<label for="name">Họ và tên:</label>
							<input type="text" id="name" name="name" class="form-control" value="${parents.name}"  readonly>
						</div>
						<div class="admin-form-group">
							<label for="dateOfBirth">Ngày sinh:</label>
							<input type="date" id="dateOfBirth" name="dateOfBirth" class="form-control" value="${parents.dateOfBirth}" readonly>
						</div>
						<div class="admin-form-group">
							<label for="address">Địa chỉ:</label>
							<input type="text" id="address" name="address" class="form-control" value="${parents.address}"
								 readonly>
						</div>
						<div class="admin-form-group">
							<label for="email">Email:</label>
							<input type="email" id="email" name="email" class="form-control" value="${parents.email}"
								   readonly>
						</div>
						<div class="admin-form-group">
							<label for="phone">Số điện thoại:</label>
							<input type="number" id="phone" name="phone" value="${parents.phone}" readonly>
						</div>
						<div class="admin-form-group">
							<div class="admin-form-group">
								<label for="">Các con:</label>
								<span class="form-select hide">${parents.numberChildren}</span>
							</div>
							<span class="second-column detail" id="children-detail">Xem chi tiết</span>
						</div>
					</div>
				</div>
				<div class="modal__function top--margin">
					<div class="btn btn--green modal__btn hide" id="update-btn">Cập nhật</div>
					<div class="btn btn--pink modal__btn hide" id="delete-btn">Xóa</div>
				</div>
			</div>
		</form>>
	`;
}

function renderUpdateParentsModal(parents) {
	return html`
		<form class="modal closure active" id="update-parents" method="POST" action="parents?mode=update">
			<h1 class="modal__title">Cập nhật</h1>
			<i class="fa-solid fa-xmark btn-icon btn-close" id="back"></i>
			<div class="error-message"></div>
			<div class="main-modal">
				<div class="main-header">
					<div class="data-header">
						<div class="admin-form-group">
							<div class="admin-form-group first-column">
								<label for="id">Mã phụ huynh:</label>
								<input type="text" id="id" name="parents_id" value="${parents.parents_id}" readonly>
							</div>
							<div class="admin-form-group second-column">
								<label for="sex">Giới tính:</label>
								<div class="form-control radio-container">
									<input type="radio" id="sex-male" name="sex" class="form-control" value="Nam"
										${parents.sex && "checked" }>Nam
									<input type="radio" id="sex-female" name="sex" class="form-control" value="Nữ"
										${!parents.sex && "checked"}>Nữ
								</div>
							</div>
						</div>
						<div class="admin-form-group">
							<label for="name">Họ và tên:</label>
							<input type="text" id="name" name="name" class="form-control" value="${parents.name}" required>
						</div>
						<div class="admin-form-group">
							<label for="dateOfBirth">Ngày sinh:</label>
							<input type="date" id="dateOfBirth" name="dateOfBirth" class="form-control" value="${parents.dateOfBirth}" required>
						</div>
						<div class="admin-form-group">
							<label for="address">Địa chỉ:</label>
							<input type="text" id="address" name="address" class="form-control" value="${parents.address}" required>
						</div>
						<div class="admin-form-group">
							<label for="email">Email:</label>
							<input type="email" id="email" name="email" class="form-control" value="${parents.email}">
						</div>
						<div class="admin-form-group">
							<label for="phone">Số điện thoại:</label>
							<input type="number" id="phone" name="phone" value="${parents.phone}" required>
						</div>
					</div>
				</div>
				<div class="modal__function top--margin">
					<input type="submit" class="btn btn--green modal__btn" value="Xác nhận">
					<div class="btn btn--pink modal__btn" id="cancel">Hủy</div>
				</div>
			</div>
		</form>
	`;
}

function renderAddParentsModal(id) {
	return html`
		<form class="modal closure active" id="add-parents" method="POST" action="parents?mode=add">
			<h1 class="modal__title">Thêm phụ huynh</h1>
			<i class="fa-solid fa-xmark btn-icon btn-close" id="back"></i>
			<div class="error-message"></div>
			<div class="main-modal">
				<div class="main-header">
					<div class="data-header">
						<div class="admin-form-group">
							<div class="admin-form-group first-column">
								<label for="id">Mã phụ huynh:</label>
								<input type="text" id="id" name="parents_id" value="${id}" readonly>
							</div>
							<div class="admin-form-group second-column">
								<label for="sex">Giới tính:</label>
								<div class="form-control radio-container">
									<input type="radio" id="sex-male" name="sex" class="form-control" value="Nam">Nam
									<input type="radio" id="sex-female" name="sex" class="form-control" value="Nữ">Nữ
								</div>
							</div>
						</div>
						<div class="admin-form-group">
							<label for="name">Họ và tên:</label>
							<input type="text" id="name" name="name" class="form-control" required>
						</div>
						<div class="admin-form-group">
							<label for="dateOfBirth">Ngày sinh:</label>
							<input type="date" id="dateOfBirth" name="dateOfBirth" class="form-control" required>
						</div>
						<div class="admin-form-group">
							<label for="address">Địa chỉ:</label>
							<input type="text" id="address" name="address" class="form-control" required>
						</div>
						<div class="admin-form-group">
							<label for="email">Email:</label>
							<input type="email" id="email" name="email" class="form-control">
						</div>
						<div class="admin-form-group">
							<label for="phone">Số điện thoại:</label>
							<input type="number" id="phone" name="phone" required>
						</div>
					</div>
				</div>
				<div class="modal__function top--margin">
					<input type="submit" name="add" class="btn btn--green modal__btn" id="confirm-btn" value="Xác nhận">
					<div class="btn btn--pink modal__btn" id="cancel">Hủy</div>
				</div>
			</div>
		</form>
	`;
}

function renderChildrenTableModal(data) {
	return html`
		<div class="modal closure active" id="update-avatar">
			<h1 class="modal__title">Chi tiết</h1>
			<i class="fa-solid fa-xmark btn-icon btn-close" id="back"></i>
			<div class="main-modal">
				<table class="table detail-children">
					<tr>
						${data.column && data.column.map((column, index) => 
							html`
								<th class="${index === 0 && 'th__first'} ${index === data.column.length - 1 && 'th__last'}">
									${column}
								</th>
							`
						)}
					</tr>
					${
						data.items.map((item, index) =>
							html`
								<tr>
									<td>${item.id}</td>
									<td>${item.name}</td>
								</tr>
							`
						)
					}
				</table>
				<div class="modal__function top--margin">
					<div class="btn btn--green modal__btn" id="cancel">Thoát</div>
				</div>
			</div>
		</div>
	`;
}

function renderConfirmModal(message) {
    return html`
        <div class="modal closure active" id="update-avatar">
            <h1 class="modal__title">Thông báo</h1>
            <i class="fa-solid fa-xmark btn-icon btn-close" id="back"></i>
            <div class="main-modal">
                <h3 class="modal__elo modal-item">${message}</h3>
                <div class="modal__function top--margin">
                    <div class="btn btn--green modal__btn" id="yes">Có</div>
                    <div class="btn btn--pink modal__btn" id="no">Không</div>
                </div>
            </div>
        </div>
    `;
}

function renderAlertModal(message) {
	return html`
        <div class="modal closure active" id="update-avatar">
            <h1 class="modal__title">Thông báo</h1>
            <i class="fa-solid fa-xmark btn-icon btn-close" id="back"></i>
            <div class="main-modal">
                <h3 class="modal__elo modal-item">${message}</h3>
                <div class="modal__function top--margin">
                    <div class="btn btn--green modal__btn" id="ok">Ok</div>
                </div>
            </div>
        </div>
    `;
}

let confirmResolve;
function confirm(message) {
    turnOnModal(renderConfirmModal, message);

    $("#yes").addEventListener("click", function () {
        resolveConfirm(true);
    });

    $("#no").addEventListener("click", function () {
        resolveConfirm(false);
    });

    $("#back").onclick = function () {
        resolveConfirm(false);
    };

    return new Promise(resolve => {
        confirmResolve = resolve;
    });
}

function resolveConfirm(isConfirmed) {
    turnOffModal();
    confirmResolve(isConfirmed);
}


export {
	turnOnModal,
	turnOffModal,
	addEventForEye,
	renderLogin,
	renderAlertModal,
	renderStudentModal,
	turnOnUpdateStudent,
	renderStudentAddModal,
	renderChildrenTableModal,
	renderUpdateParentsModal,
	renderAddParentsModal,
	confirm,
	renderConfirmModal,
	renderParentsModal
}