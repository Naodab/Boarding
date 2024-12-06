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
        backBtn.onclick =  function () {
            turnOffModal();
        };
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
						<input type="password" id="username" name="username" class="password__input" placeholder="Tài khoản">
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
		</form>>
    `;
}

export {
	turnOnModal,
	turnOffModal,
	addEventForEye,
	renderLogin
}