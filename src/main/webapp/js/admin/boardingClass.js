import {
    turnOnModal,
    turnOffModal,
    renderAlertModal,
    renderConfirmModal
} from "../modal.js";

import Validator from "../validator.js";
import {
    renderAddBoardingClass,
    renderDetailBoardingClass,
    renderStudentsDetailModal,
    renderUpdateBoardingClass
} from "./modal/modalBoardingClass.js";

const $ = document.querySelector.bind(document);
const $$ = document.querySelectorAll.bind(document);

$("#classes").classList.add("active");

let activePage = 1;
let size = 1;
let itemsPerPage = 12;
let sortType = "";
let lookupValue = "";
let activeBoardingClassId;
let activePageDetail = 1;

window.onload = () => displayPage(1);

function checkRoomExist(room) {
    return fetch(`./boardingClasses?mode=checkRoom&room=${room}`, {
        method: 'POST',
        headers: {
            "Content-Type": "application/json"
        }
    }).then(res => res.json()).then(data => data.result);
}

function addBoardingClass(boardingClass) {
    const tr = document.createElement("tr");
    tr.innerHTML = `
        <td>${boardingClass.boardingClassId}</td>
        <td>${boardingClass.name}</td>
        <td>${boardingClass.teacherName}</td>
        <td>${boardingClass.room}</td>
        <td>${boardingClass.numberStudent}</td>
        <td>${boardingClass.numberOfBed}</td>
    `;

    tr.onclick = () => {
        activeBoardingClassId = boardingClass.boardingClassId;
        turnOnModal(renderDetailBoardingClass, boardingClass);

        $("#student-detail").onclick = () => {
            turnOffModal();
            activePageDetail = 1;
            turnOnModal(renderStudentsDetailModal);
            displayPageDetail(1);
        }

        $("#update-btn").onclick = () => {
            turnOffModal();
            fetch("./boardingClasses?mode=preUpdate", {
                method: 'POST',
                headers: {
                    "Content-Type": "application/json"
                }
            }).then(resp => resp.json()).then(teachers => {
                turnOnModal(renderUpdateBoardingClass, {boardingClass, teachers});

                Validator({
                    form: '#update-boardingClass',
                    formGroupSelector: ".admin-form-group",
                    errorSelector: '.form-message',
                    rules: [
                        Validator.isRequired('#name'),
                        Validator.isRequired("#room"),
                        Validator.isRequired("#numberOfBed")
                    ],
                    onSubmit: data => {
                        if (data.room === boardingClass.room) {
                            $("#update-boardingClass").submit();
                            return;
                        }
                        checkRoomExist(data.room).then(result => {
                            if (result) {
                                $(".error-message").innerText = "Phòng hiện tại đã có lớp";
                            } else {
                                $("#update-boardingClass").submit();
                            }
                        })
                    }
                })
            });
        }

        $("#delete-btn").onclick = () => {
            turnOffModal();
            if (boardingClass.numberStudent > 0) {
                turnOnModal(renderAlertModal, "Hiện có học sinh trong lớp, không thể xóa");
                return;
            }
            turnOnModal(renderConfirmModal, `Bạn chắc chắn muốn xóa lớp ${boardingClass.name}`);

            $("#yes").onclick = () => {
                turnOffModal();
                fetch(`./boardingClasses?mode=delete&boardingClassId=${boardingClass.boardingClassId}`, {
                    method: 'POST',
                    headers: {
                        "Content-Type": "application/json"
                    }
                }).then(() => {
                    deleteBoardingClass(boardingClass);
                    turnOnModal(renderAlertModal, "Xóa thành công!");
                });
            }
        }
    }

    $(".class-table tbody").appendChild(tr);
}

function deleteBoardingClass({boardingClass_id}) {
    const trs = $$("table tbody tr");
    for (let i = 1; i < trs.length; i++) {
        const tr = trs[i];
        const td = tr.querySelectorAll("td")[0];
        if (td.innerText === String(boardingClass_id)) {
            tr.remove();
            return;
        }
    }
}

function deleteBoardingClasses() {
    const table = $(".class-table tbody");
    const trs = $$(".class-table tr");
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

    fetch(`./boardingClasses?mode=see&page=${page - 1}${querySort}${querySearch}`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json"
        }
    }).then(resp => {
        if (resp.ok) {
            return resp.json();
        }
    }).then(data => {
        deleteBoardingClasses();
        size = data.size;
        data.items.forEach(boardingClass => addBoardingClass(boardingClass));
        updatePageNumbers();
    });
}

function updatePageDetailNumbers(size, itemsPerPage) {
    const totalPages = Math.ceil(size / itemsPerPage);
    const pageNumbersDiv = $("#student-detail-page-container");

    const visibleRange = 2;
    const pages = [];
    const start = Math.max(2, activePageDetail - visibleRange);
    const end = Math.min(totalPages - 1, activePageDetail + visibleRange);

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
            return `<div ${page === activePageDetail ? 'class="active page page-detail"' : "class='page page-detail'"}>
					${page}
				</div>`;
        })
        .join("");

    const pageDivs = $$(".page-detail");
    pageDivs.forEach(pageDiv => {
        if (!pageDiv.className.includes('non'))
            pageDiv.onclick = () => gotToPageDetail(Number(pageDiv.innerText), size, itemsPerPage);
    })
}

function gotToPageDetail(page, size, itemsPerPage) {
    const totalPages = Math.ceil(size / itemsPerPage);
    console.log(totalPages)
    if (page < 1 || page > totalPages || page === activePageDetail) return;
    activePageDetail = page;
    displayPageDetail(activePageDetail);
}

function addStudent(student) {
    const tr = document.createElement("tr");
    tr.innerHTML = `
        <td>${student.id}</td>
        <td>${student.name}</td>
    `;
    $("#detail-students-table tbody").appendChild(tr);
}

function deleteStudents() {
    const table = $("#detail-students-table tbody");
    const trs = $$("#detail-students-table tr");
    trs.forEach((tr, index) => {
        if (index > 0)
            table.removeChild(tr);
    });
}

function displayPageDetail(page) {
    fetch(`./boardingClasses?mode=student&page=${page - 1}&boardingClassId=${activeBoardingClassId}`, {
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
        data.items.forEach(student => addStudent(student));
        updatePageDetailNumbers(data.size, 8);
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
    fetch(`./boardingClasses?mode=preAdd`, {
        method: 'POST',
        headers: {
            "Content-Type": "application/json"
        }
    }).then(resp => resp.json()).then(data => {
        turnOnModal(renderAddBoardingClass, data);

        Validator({
            form: '#add-boardingClass',
            formGroupSelector: ".admin-form-group",
            errorSelector: '.form-message',
            rules: [
                Validator.isRequired('#name'),
                Validator.isRequired("#room"),
                Validator.isRequired("#numberOfBed")
            ],
            onSubmit: data => {
                checkRoomExist(data.room).then(result => {
                    if (result) {
                        $(".error-message").innerText = "Phòng hiện tại đã có lớp";
                    } else {
                        $("#add-boardingClass").submit();
                    }
                })
            }
        })
    })
}