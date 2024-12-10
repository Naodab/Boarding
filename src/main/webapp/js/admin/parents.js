import {
	turnOnModal,
	turnOffModal,
	renderParentsModal,
	renderConfirmModal,
	renderChildrenTableModal, renderAlertModal, renderUpdateParentsModal, renderAddParentsModal
} from "../modal.js";

document.querySelector("#parents").classList.add("active");

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
		document.querySelector("#update-btn").onclick = () => {
			turnOnModal(renderUpdateParentsModal, parents);
			document.querySelector("#confirm-btn").onclick = () => {
				turnOnModal(renderAlertModal, "Cập nhật thành công.");
			}
		}

		document.querySelector("#delete-btn").onclick = () => {
			turnOffModal();
			turnOnModal(renderConfirmModal, `Bạn chắc chắn muốn xóa ${parents.name}?`);
			document.querySelector("#yes").onclick = () => {
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

			document.querySelector("#no").onclick = () => {
				turnOffModal();
			}
		}

		document.querySelector("#children-detail").onclick = () => {
			turnOffModal();
			fetch(`./parents?mode=children&parents_id=${parents.parents_id}`, {
				method: "GET",
				headers: {
					"Content-Type": "application/json"
				}
			}).then(resp => resp.json()).then(data => {
				const items = {column: ["Mã", "Họ và tên"], items: data};
				turnOnModal(renderChildrenTableModal, items);
				document.querySelector("#cancel").onclick = () => {
					turnOffModal();
				}
			})
		}
	}

	document.querySelector(".parents-table tbody").appendChild(tr);
}

function deleteParent({parents_id}) {
	const table = document.querySelector(".parents-table tbody");
	const trs = table.querySelectorAll("tr");
	trs.forEach((tr, index) => {
		const td = tr.querySelector("td");
		console.log(td);
		if (td.innerText === parents_id) {
			table.removeChild(tr);
		}
	})
}

function deleteParents() {
	const table = document.querySelector(".parents-table tbody");
	const trs = document.querySelectorAll(".parents-table tr");
	trs.forEach((tr, index) => {
		if (index > 0)
			table.removeChild(tr);
	});
}

function updatePageNumbers() {
	const totalPages = Math.ceil(size / itemsPerPage);
	const pageNumbersDiv = document.querySelector(".pages-container");

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
	
	const pageDivs = document.querySelectorAll(".page");
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
		const sortField = document.querySelector(".selection").value;
		querySort += `&sortField=${sortField}`;
	}
	let querySearch = lookupValue ? `&search=${lookupValue}` : "";
	if (querySearch) {
		const searchField = document.querySelector(".search-field").value;
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

const sortItems = document.querySelectorAll('input[name="sort__type"]');
sortItems.forEach(item => {
	item.addEventListener('change', () => {
		sortType = item.value;
		activePage = 1;
		displayPage(1);
	});
});

const sortFields = document.querySelectorAll(".selection");
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

const searchInput = document.querySelector("#search");
searchInput.onkeyup = () => {
	debounceSearch(searchInput.value)
}

document.querySelector("#add-btn").onclick = () => {
	fetch("./parents?mode=preAdd", {
		method: 'GET',
		headers: {
			"Content-Type": "application/json"
		}
	}).then(resp => resp.json()).then(data => {
		turnOnModal(renderAddParentsModal, data.nextId);
		document.querySelector("#confirm-btn").onclick = () => {
			turnOffModal();
		}
	});
}