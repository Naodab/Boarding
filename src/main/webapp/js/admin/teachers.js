import {turnOnModal, turnOffModal, renderStudentModal, turnOnUpdateStudent} from "/boarding/js/modal.js";

document.querySelector("#teachers").classList.add("active");

let activePage = 1;
let size = 1;
let itemsPerPage = 12;
let sortType = "";
let lookupValue = "";

window.onload = () => displayPage(1);

function addParents(teacher) {
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
	document.querySelector(".teacher-table tbody").appendChild(tr);
}

function deleteTeachers() {
	const table = document.querySelector(".teacher-table tbody");
	const trs = document.querySelectorAll(".teacher-table tr");
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

	fetch(`/boarding/teachers?mode=see&page=${page - 1}${querySort}${querySearch}`, {
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
