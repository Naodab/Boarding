import {renderAlertModal, renderConfirmModal, turnOffModal, turnOnModal} from "../modal.js";
import {renderAddFood, renderDetailFood, renderUpdateFood} from "./modal/modalFood.js";

import Validator from "../validator.js";

const $ = document.querySelector.bind(document);
const $$ = document.querySelectorAll.bind(document);
$("#foods").classList.add("active");

const colorClosure = ["closure--pink", "closure--yellow", "closure--green", "closure--blue"];

let activePage = 1;
let size = 1;
let itemsPerPage = 16;
let lookupValue = "";

window.onload = () => displayPage(1);

function checkBeforeSave(name) {
    return fetch(`./foods?mode=checkExist&name=${name}`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        }
    }).then(resp => resp.json()).then(data => data.result);
}

function addFood(food) {
    console.log(food);
    const div = document.createElement('div');
    div.classList.add('data-item');
    div.classList.add('closure');
    div.classList.add(colorClosure[Math.floor(Math.random() * colorClosure.length)]);
    div.innerHTML = `
        <div class="data-item__content">
            <div class="data-item__number">${ food.food_id + ". " + food.name }</div>
            <div class="data-item__description">
                ${ food.category ? "Món chính" : "Món phụ" }
            </div>
        </div>
        <div class="data-item__icon">
            <i class="fa-solid fa-utensils"></i>
        </div>
    `;
    div.onclick = () => {
        turnOnModal(renderDetailFood, food);

        $("#update-btn").onclick = () => {
            turnOnModal(renderUpdateFood, food);

        }

        const deleteBtn = $("#delete-btn");
        if (deleteBtn) {
            deleteBtn.onclick = () => {
                turnOnModal(renderConfirmModal, `Bạn chắc chắn muốn xóa ${food.name}?`);
                $("#yes").onclick = () => {
                    fetch(`./foods?mode=delete&name=${name}`, {
                        method: "POST",
                        headers: {
                            "Content-Type": "application/json"
                        }
                    }).then(resp => resp.ok).then(result => {
                        if (result) {
                            turnOnModal(renderAlertModal, "Xóa thành công");
                        } else {
                            turnOnModal(renderAlertModal, "Xảy ra lỗi");
                        }
                    })
                }
            }
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

    fetch(`./foods?mode=see&page=${page - 1}${querySearch}`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json"
        }
    }).then(resp => {
        if (resp.ok) {
            return resp.json();
        }
    }).then(data => {
        deleteFoods();
        size = data.size;
        data.items.forEach(food => addFood(food));
        updatePageNumbers();
    });
}

function deleteFoods() {
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
    fetch("./foods?mode=preAdd", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        }
    }).then(resp => resp.json()).then(data => {
        turnOnModal(renderAddFood, data.nextId);

        Validator({
            form: '#add-food',
            formGroupSelector: ".admin-form-group",
            errorSelector: '.form-message',
            rules: [
                Validator.isRequired('#name')
            ],
            onSubmit: data => {
                const errorMessage = $(".error-message");
                errorMessage.innerText = "";
                checkBeforeSave(data.name).then(result => {
                    if (result) {
                        errorMessage.innerText = "Món ăn đã tồn tại";
                    } else {
                        $("#add-food").submit();
                    }
                })
            }
        })
    })
}