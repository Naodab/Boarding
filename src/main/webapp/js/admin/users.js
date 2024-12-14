import {
    turnOnModal,
    turnOffModal,
    renderAlertModal,
    renderConfirmModal,
    addEventForEye
} from "../modal.js";

import Validator from "../validator.js";
import {renderChangeDefaultModal} from "./modal/modalUser.js";

const $ = document.querySelector.bind(document);
const $$ = document.querySelectorAll.bind(document);

$("#users").classList.add("active");

let activePage = 1;
let size = 1;
let itemsPerPage = 12;
let sortType = "";
let lookupValue = "";

window.onload = () => displayPage(1);

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

    fetch(`./users?mode=see&page=${page - 1}${querySort}${querySearch}`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json"
        }
    }).then(resp => {
        if (resp.ok) {
            return resp.json();
        }
    }).then(data => {
        deleteUsers();
        size = data.size;
        data.items.forEach(user => addUser(user));
        $$(".reset-password").forEach(btn => {
            btn.onclick = () => {
                const username = btn.getAttribute("data-id");
                turnOnModal(renderConfirmModal, `Bạn chắc chắn muốn cái lại mật khẩu của ${username}?`);
                $("#yes").onclick = () => {
                    fetch(`./users?mode=reset&username=${username}`, {
                        method: "POST",
                        headers: {
                            "Content-Type": "application/json"
                        }
                    }).then(() => {
                        turnOffModal();
                        turnOnModal(renderAlertModal, "Cài lại thành công");
                    });
                }
            }
        })
        updatePageNumbers();
    });
}

function addUser(user) {
    const tr = document.createElement("tr");
    tr.innerHTML = `
        <td>${user.username}</td>
        <td>${user.position}</td>
        <td>${user.name}</td>
        <td>${user.userId}</td>
        <td>${user.lastLogin}</td>
        <td><div class="reset-password" data-id="${user.username}">Cài lại mật khẩu</div></td>
    `;

    $(".user-table tbody").appendChild(tr);
}

function deleteUsers() {
    const table = $(".user-table tbody");
    const trs = $$(".user-table tr");
    trs.forEach((tr, index) => {
        if (index > 0)
            table.removeChild(tr);
    });
}

$("#change-default-btn").onclick = () => {
    fetch("./users?mode=getDefault", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        }
    }).then(resp => resp.json()).then(data => {
        console.log(data);
        turnOnModal(renderChangeDefaultModal, data.result);
        addEventForEye();
        Validator({
            form: '#change-default',
            formGroupSelector: ".form-group",
            errorSelector: '.form-message',
            rules: [],
            onSubmit: data => {
                console.log(data);
                if ($("#password").value !== $("#prePassword").value) {
                    $(".error-message").innerText = "Mật khẩu nhập lại không chính xác";
                    return;
                }
                $("#change-default").submit();
            }
        });
    })
}