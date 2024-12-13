import {
	turnOnModal,
	turnOffModal,
	renderAlertModal,
	renderConfirmModal
} from "../modal.js";

import {
	renderAddTeacherModal,
	renderDetailTeacherModal,
	renderUpdateTeacherModal
} from "./modal/modalTeacher.js";

import Validator from "../validator.js";
import {checkExistUsername} from "./common.js";

const $ = document.querySelector.bind(document);
const $$ = document.querySelectorAll.bind(document);

$("#teachers").classList.add("active");

let activePage = 1;
let size = 1;
let itemsPerPage = 12;
let sortType = "";
let lookupValue = "";

window.onload = () => displayPage(1);

function addTeacher(teacher) {
	const tr = document.createElement("tr");
	tr.innerHTML = `
		<td>${teacher.teacher_id}</td>
		<td>${teacher.name}</td>
		<td>${teacher.dateOfBirth}</td>
		<td>${teacher.address}</td>
		<td>${teacher.sex ? "Nam" : "Nữ"}</td>
		<td>${teacher.phone}</td>
		<td>${teacher.email}</td>
		<td>${teacher.boardingClass}</td>
	`
	tr.onclick = () => {
		turnOnModal(renderDetailTeacherModal, teacher);

		$("#update-btn").onclick = () => {
			turnOffModal();
			fetch("./teachers?mode=preUpdate", {
				method: 'POST',
				headers: {
					"Content-Type": "application/json"
				}
			}).then(resp => resp.json()).then(classes => {
				turnOnModal(renderUpdateTeacherModal, {teacher, classes});

				$(".error-message").innerText = "";
				Validator({
					form: '#update-teacher',
					formGroupSelector: ".admin-form-group",
					errorSelector: '.form-message',
					rules: [
						Validator.isRequired('#name'),
						Validator.isRequired("#address"),
						Validator.isEmail("#email"),
						Validator.isRequired("#email"),
						Validator.isRequired("#phone"),
						Validator.isRequired("#dateOfBirth"),
					],
					onSubmit: data => {
						if (data.phone === parents.phone) {
							$("#update-teacher").submit();
							return;
						}
						checkExistUsername(data.phone).then(result => {
							if (result) {
								$(".error-message").innerText = "Số điện thoại không hợp lệ";
							} else {
								$("#update-teacher").submit();
							}
						})
					}
				});
			})
		}

		$("#delete-btn").onclick = () => {
			turnOffModal();
			if (teacher.boardingClass !== "Không có") {
				turnOffModal();
				turnOnModal(renderAlertModal, "Hiện giáo viên này đang quản lý lớp học, hãy chuyển công tác cho giáo viên khác trước khi xóa.");
				return;
			}
			turnOnModal(renderConfirmModal, "Bạn chắc chắn muốn xóa giáo viên " + teacher.name + "?");
			$("#yes").onclick = () => {
				fetch(`./teachers?mode=delete&teacher_id=${teacher.teacher_id}`, {
					method: 'POST',
					headers: {
						"Content-Type": "application/json"
					}
				}).then(() => {
					turnOffModal();
					deleteTeacher(teacher);
				}).catch(() => {
					turnOffModal();
					turnOnModal(renderAlertModal, "Xảy ra lỗi.");
				});
			}
			$("#no").onclick = () => turnOffModal();
		}
	}
	$(".teacher-table tbody").appendChild(tr);
}

function deleteTeacher({teacher_id}) {
	const trs = $$("table tbody tr");
	for (let i = 1; i < trs.length; i++) {
		const tr = trs[i];
		const td = tr.querySelectorAll("td")[0];
		if (td.innerText === String(teacher_id)) {
			tr.remove();
			return;
		}
	}
}

function deleteTeachers() {
	const table = $(".teacher-table tbody");
	const trs = $$(".teacher-table tr");
	trs.forEach((tr, index) => {
		if (index > 0)
			table.removeChild(tr);
	});
}

function updatePageNumbers() {
	const totalPages = Math.ceil(size / itemsPerPage);
	const pageNumbersDiv = $(".pages-container");

	const visibleRange = 2;
	const pages = [];
	const start = Math.max(2, activePage - visibleRange);
	const end = Math.min(totalPages - 1, activePage + visibleRange);

	pages.push(1);
	if (start > 2) {
		pages.push("...");
	}
	for (let i = start; i <= end; i++) {
		pages.push(i);
	}
	if (end < totalPages - 1) {
		pages.push("...");
	}
	if (totalPages > 1) {
		pages.push(totalPages);
	}

	pageNumbersDiv.innerHTML = pages
		.map(page => {
			if (page === "...") {
				return `<div class="page non">...</div>`;
			}
			return `<div ${page === activePage ? 'class="active page"' : "class='page'"}>
					${page}
				</div>`;
		})
		.join("");
	
	const pageDivs = $$(".page");
	pageDivs.forEach(pageDiv => {
		if (!pageDiv.className.includes('non'))
			pageDiv.onclick = () => gotToPage(Number(pageDiv.innerText));
	})
}

function gotToPage(page) {
	const totalPages = Math.ceil(size / itemsPerPage);
	if (page < 1 || page > totalPages || page === activePage) return;
	activePage = page;
	displayPage(activePage);
}

function displayPage(page) {
	let querySort = sortType ? `&sort=${sortType}` : "";
	if(querySort) {
		const sortField = $(".selection").value;
		querySort += `&sortField=${sortField}`;
	}
	let querySearch = lookupValue ? `&search=${lookupValue}` : "";
	if (querySearch) {
		const searchField = $(".search-field").value;
		querySearch += `&searchField=${searchField}`;
	}

	fetch(`./teachers?mode=see&page=${page - 1}${querySort}${querySearch}`, {
		method: "GET",
		headers: {
			"Content-Type": "application/json"
		}
	}).then(resp => {
		if (resp.ok) {
			return resp.json();
		}
	}).then(data => {
		deleteTeachers();
		size = data.size;
		data.items.forEach(teacher => addTeacher(teacher));
		updatePageNumbers();
	});
}

const sortItems = $$('input[name="sort__type"]');
sortItems.forEach(item => {
	item.addEventListener('change', () => {
		sortType = item.value;
		activePage = 1;
		displayPage(1);
	});
});

const sortFields = $$(".selection");
sortFields.onchange = () => {
	if (sortType) {
		activePage = 1;
		displayPage(1);
	}
}

function debounce(func, delay) {
	let timeout;
	return function (...args) {
		clearTimeout(timeout);
		timeout = setTimeout(() => func.apply(this, args), delay);
	};
}

const debounceSearch = debounce((search) => {
	lookupValue =  search;
	activePage = 1;
	displayPage(1);
}, 500);

const searchInput = $("#search");
searchInput.onkeyup = () => {
	debounceSearch(searchInput.value)
}

$("#add-btn").onclick = () => {
	fetch("./teachers?mode=preAdd", {
		method: 'GET',
		headers: {
			"Content-Type": "application/json"
		}
	}).then(resp => resp.json()).then(data => {
		turnOnModal(renderAddTeacherModal, data);

		$(".error-message").innerText = "";
		Validator({
			form: '#add-teacher',
			formGroupSelector: ".admin-form-group",
			errorSelector: '.form-message',
			rules: [
				Validator.isRequired('#name'),
				Validator.isRequired("#address"),
				Validator.isEmail("#email"),
				Validator.isRequired("#email"),
				Validator.isRequired("#phone"),
				Validator.isRequired("#dateOfBirth"),
			],
			onSubmit: data => {
				checkExistUsername(data.phone).then(result => {
					if (result) {
						$(".error-message").innerText = "Số điện thoại không hợp lệ";
					} else {
						$("#add-teacher").submit();
					}
				})
			}
		});
	})
}