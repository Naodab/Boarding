import {renderAlertModal, renderConfirmModal, turnOffModal, turnOnModal} from "../modal.js";
import {renderAddMenu, renderDetailMenu} from "./modal/modalMenu.js";
import {html} from "./common.js";


const $ = document.querySelector.bind(document);
const $$ = document.querySelectorAll.bind(document);
$("#menus").classList.add("active");

const colorClosure = ["closure--pink", "closure--yellow", "closure--green", "closure--blue"];

let activePage = 1;
let size = 1;
let itemsPerPage = 10;
let lookupValue = "";

window.onload = () => displayPage(1);

function addMenu(menu) {
    const div = document.createElement('div');
    div.classList.add('data-item__column');
    div.classList.add('closure');
    div.classList.add(colorClosure[Math.floor(Math.random() * colorClosure.length)]);
    div.innerHTML = `
        <div class="data-item__column__title">Mã: ${menu.menuId}</div>
        <div class="data-item__column__label">Bữa trưa:</div>
        ${html`
            ${menu.mainFoods.map(item => html`
                <div class="data-item__column__content">${item}</div>
            `)}
        `}
        <div class="data-item__column__label top--margin">Bữa phụ:</div>
        ${html`
            <div class="data-item__column__content">${menu.subFood}</div>
        `}
    `;
    div.onclick = () => {
        turnOnModal(renderDetailMenu, menu);

        $("#update-btn").onclick = () => {
            fetch(`./menus?mode=changeActive&menuId=${menu.menuId}`, {
                method: "GET",
                headers: {
                    "Content-Type": "application/json"
                }
            }).then(resp => resp.ok).then(() => {
                turnOnModal(renderAlertModal, "Cập nhật thành công");
                displayPage(activePage);
            })
        }
    }
    $("#foods-container").appendChild(div);
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
    let querySearch = lookupValue ? `&search=${lookupValue}` : "";
    if (querySearch) {
        const searchField = $(".search-field").value;
        querySearch += `&searchField=${searchField}`;
    }

    fetch(`./menus?mode=see&page=${page - 1}${querySearch}`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json"
        }
    }).then(resp => {
        if (resp.ok) {
            return resp.json();
        }
    }).then(data => {
        deleteMenus();
        size = data.size;
        data.items.forEach(food => addMenu(food));
        updatePageNumbers();
    });
}

function deleteMenus() {
    $("#foods-container").innerHTML = "";
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
    fetch("./menus?mode=preAdd", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        }
    }).then(resp => resp.json()).then(data => {
        turnOnModal(renderAddMenu, data);
    })
}