import {turnOnModal, turnOffModal, renderStudentModal, turnOnUpdateStudent, renderStudentAddModal, renderConfirmModal } from "/boarding/js/modal.js";

document.querySelector("#students").classList.add("active");

let activePage = 1;
let size = 1;
let itemsPerPage = 12;
let sortType = "";
let lookupValue = "";

window.onload = () => {
	displayPage(1);
}

function addStudent(student) {
	const tr = document.createElement("tr");
	tr.innerHTML = `
		<td>${student.student_id}</td>
		<td>${student.name}</td>
		<td>${student.dateOfBirth}</td>
		<td>${student.address}</td>
		<td>${student.sex}</td>
		<td>${student.parentName}</td>
		<td>${student.boardingClassName}</td>
		<td>${student.subMeal ? 'Có' : 'Không'}</td>
	`
	tr.onclick = () => {
		turnOnModal(renderStudentModal, student);
		document.querySelector("#update-btn").onclick = () => {
			turnOffModal();
			student.isUpdate = true;
			fetch(`/boarding/students?mode=preUpdate`, {
				method: 'GET',
				headers: {
					"Content-Type": "application/json"
				}
			}).then(resp => resp.json()).then(data => {
				turnOnUpdateStudent(student, data.parents, data.classes);
			})
		}
		
		document.querySelector("#delete-btn").onclick = () => {
			turnOffModal();
			turnOnModal(renderConfirmModal, `Bạn chắc chắn muốn xóa ${student.name}?`);
			document.querySelector("#yes").onclick = () => {
				fetch(`/boarding/students?mode=delete&student_id=${student.student_id}`, {
					method: 'POST',
					headers: {
						"Content-Type": "application/json"
					}
				}).then(() => {
					turnOffModal();
				});
			}
			document.querySelector("#no").onclick = () => {
				turnOffModal();
			}
		}
	}
	document.querySelector(".student-table tbody").appendChild(tr);
}

function deleteStudents() {
	const table = document.querySelector(".student-table tbody");
	const trs = document.querySelectorAll(".student-table tr");
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
	const querySearch = lookupValue ? `&search=${lookupValue}` : "";
	
	fetch(`/boarding/students?mode=see&page=${page - 1}${querySort}${querySearch}`, {
		method: "GET",
		headers: {
			"Content-Type": "application/json"
		}
	}).then(resp => {
		if (resp.ok) {
			return resp.json();
		}
	}).then(data => {
		deleteStudents();
		size = data.size;
		data.students.forEach(student => addStudent(student));
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

const sortFields = document.querySelector(".selection");
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
	console.log(1);
	fetch("/boarding/students?mode=preAdd", {
		method: 'GET',
		headers: {
			"Content-Type": "application/json"
		}
	}).then(resp => resp.json()).then(data => {
		turnOnModal(renderStudentAddModal, data);
	})
}