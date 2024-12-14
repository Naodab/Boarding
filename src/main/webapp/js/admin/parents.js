import {
	turnOnModal,
	turnOffModal,
	renderAlertModal,
	renderParentsModal,
	renderConfirmModal,
	renderAddParentsModal,
	renderChildrenTableModal,
	renderUpdateParentsModal,
} from "../modal.js";

import Validator from "../validator.js";
import {checkExistUsername} from "./common.js";

const $ = document.querySelector.bind(document);
const $$ = document.querySelectorAll.bind(document);

$("#parents").classList.add("active");

let activePage = 1;
let size = 1;
let itemsPerPage = 12;
let sortType = "";
let lookupValue = "";

window.onload = () => displayPage(1);

function addParents(parents) {
	const tr = document.createElement("tr");
	tr.innerHTML = `
		<td>${parents.parents_id}</td>
		<td>${parents.name}</td>
		<td>${parents.sex ? "Nam" : "Nữ"}</td>
		<td>${parents.dateOfBirth}</td>
		<td>${parents.address}</td>
		<td>${parents.email}</td>
		<td>${parents.phone}</td>
	`
	tr.onclick = () => {
		turnOnModal(renderParentsModal, parents);
		$("#update-btn").onclick = () => {
			turnOnModal(renderUpdateParentsModal, parents);
			$(".error-message").innerText = "";
			Validator({
				form: '#update-parents',
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
						$("#update-parents").submit();
						return;
					}
					checkExistUsername(data.phone).then(result => {
						if (result) {
							$(".error-message").innerText = "Số điện thoại không hợp lệ";
						} else {
							$("#update-parents").submit();
						}
					})
				}
			});
		}

		$("#delete-btn").onclick = () => {
			turnOffModal();
			turnOnModal(renderConfirmModal, `Bạn chắc chắn muốn xóa ${parents.name}?`);
			$("#yes").onclick = () => {
				if (parents.numberChildren > 0) {
					turnOnModal(renderAlertModal, `Hiện phụ huynh này có ${parents.numberChildren} nên không thể xóa.`);
				} else {
					fetch(`./parents?mode=delete&parents_id=${parents.parents_id}&username=${parents.phone}`, {
						method: 'GET',
						headers: {
							"Content-Type": "application/json"
						}
					}).then(() => {
						turnOffModal();
						deleteParent(parents);
						turnOnModal(renderAlertModal, "Xóa thành công");
					})
				}
			}

			$("#no").onclick = () => {
				turnOffModal();
			}
		}

		$("#children-detail").onclick = () => {
			turnOffModal();
			fetch(`./parents?mode=children&parents_id=${parents.parents_id}`, {
				method: "GET",
				headers: {
					"Content-Type": "application/json"
				}
			}).then(resp => resp.json()).then(data => {
				const items = {column: ["Mã", "Họ và tên"], items: data};
				turnOnModal(renderChildrenTableModal, items);
				$("#cancel").onclick = () => {
					turnOffModal();
				}
			})
		}
	}

	$(".parents-table tbody").appendChild(tr);
}

function deleteParent({parents_id}) {
	const trs = $$("table tbody tr");
	for (let i = 1; i < trs.length; i++) {
		const tr = trs[i];
		const td = tr.querySelectorAll("td")[0];
		if (td.innerText === String(parents_id)) {
			tr.remove();
			return;
		}
	}
}

function deleteParents() {
	const table = $(".parents-table tbody");
	const trs = $$(".parents-table tr");
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

	fetch(`/boarding/parents?mode=see&page=${page - 1}${querySort}${querySearch}`, {
		method: "GET",
		headers: {
			"Content-Type": "application/json"
		}
	}).then(resp => {
		if (resp.ok) {
			return resp.json();
		}
	}).then(data => {
		deleteParents();
		size = data.size;
		data.items.forEach(parents => addParents(parents));
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
	fetch("./parents?mode=preAdd", {
		method: 'GET',
		headers: {
			"Content-Type": "application/json"
		}
	}).then(resp => resp.json()).then(data => {
		turnOnModal(renderAddParentsModal, data.nextId);

		$(".error-message").innerText = "";
		Validator({
			form: '#add-parents',
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
						$("#add-parents").submit();
					}
				})
			}
		});
	});
}