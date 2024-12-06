import { addEventForEye, renderLogin, turnOnModal } from "./modal.js";
import Validator from "./validator.js";

const $ = document.querySelector.bind(document);

const erroMessage = $(".error-loggin");
if (erroMessage) {
	handleLogin(erroMessage.innerText);
}

$("#login-btn").onclick = event => {
	event.preventDefault();
	handleLogin();
}

function handleLogin(message) {
	turnOnModal(renderLogin, message);
	addEventForEye();
	Validator({
		form: '#login',
		formGroupSelector: ".form-group",
		errorSelector: '.form-message',
		rules: [
			Validator.isRequired('#username'),
			Validator.isRequired("#password")
		],
		onSubmit: data => $("#login").submit()
	});
}