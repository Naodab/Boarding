import {renderAlertModal, renderConfirmModal, renderLoading, turnOffModal, turnOnModal} from "../modal.js";
import {renderChangeEatingDay} from "./modal/modalBoardingFee.js";

const $ = document.querySelector.bind(document);
const $$ = document.querySelectorAll.bind(document);

$("#eatingHistories").classList.add("active");

let activePage = 1;
let size = 1;
let itemsPerPage = 12;
let lookupValue = "";
let menus;

function addEatingHistory(eatingHistory) {
    const tr = document.createElement("tr");
    tr.setAttribute("data-id", eatingHistory.menuId);
    tr.innerHTML = `
        <td>${eatingHistory.eatingHistoryId}</td>
        <td>${eatingHistory.menuId}</td>
        <td>${eatingHistory.mainFoods}</td>
        <td>${eatingHistory.subFood}</td>
        <td>${eatingHistory.eatingDay}</td>
    `;
    $(".eatingHistory-table tbody").appendChild(tr);
}

function deleteEatingHistories() {
    const table = $(".eatingHistory-table tbody");
    const trs = $$(".eatingHistory-table tr");
    trs.forEach((tr, index) => {
        if (index > 0)
            table.removeChild(tr);
    });
}

window.onload = () => {
    fetch(`./eatingHistories?mode=months`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        }
    }).then(resp => resp.json()).then(data => {
        const monthsSelect = $("#months-selection");
        data.forEach(item => addMonth(item));

        monthsSelect.onchange = () => {
            displayPage(1);
        }

        const message = $("#message");
        if (!message) {
            displayPage(1);
            return;
        }
        turnOnModal(renderLoading);
        const month = message.value;
        addMonth(month);
        monthsSelect.value = month;
        fetch(`./eatingHistories?mode=createFee`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            }
        }).then(resp => resp.json()).then(data => {
            turnOffModal();
            data.items.forEach(item => addEatingHistory(item));

            const trs = $$(".eatingHistory-table tbody tr");
            trs.forEach((tr, index) => {
                if (index === 0) return;
                tr.onclick = () => {
                    if (menus) {
                        turnOnModal(renderChangeEatingDay, {
                            menus,
                            isDeleted: tr.classList.contains("hidden"),
                            menuId: tr.getAttribute("data-id")
                        });
                        handleChangeModal(tr);
                        return;
                    }
                    fetch(`./eatingHistories?mode=menu`, {
                        method: "POST",
                        headers: {
                            "Content-Type": "application/json"
                        }
                    }).then(resp => resp.json()).then(data => {
                        menus = data;
                        turnOnModal(renderChangeEatingDay, {
                            menus,
                            isDeleted: tr.classList.contains("hidden"),
                            menuId: tr.getAttribute("data-id")
                        });
                        handleChangeModal(tr);
                    });
                }
            })
        });

        $("#add-btn").onclick = () => {
            turnOnModal(renderConfirmModal, 'Bạn chắc chắn muốn tạo đợt thu tiền này?');

            $("#yes").onclick = () => {
                turnOnModal(renderLoading);
                fetch("./eatingHistories?mode=add", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify(getEatingHistories())
                }).then(resp => resp.ok).then(() => {
                    turnOnModal(renderAlertModal, "Thêm thành công");
                    $("#ok").addEventListener('click', () => {
                        window.location.href = "./boardingFees";
                    })
                });
            }
        }
    });
}

function getEatingHistories() {
    const dates = [];
    const trs = $$(".eatingHistory-table tbody tr");
    trs.forEach((tr, index) => {
        if (index === 0) return;
        const date = {};
        const tds = tr.querySelectorAll('td');
        date.menu_id = tr.getAttribute("data-id");
        date.eating_day = tds[4].innerText;
        dates.push(date);
    });
    return dates;
}

function handleChangeModal(tr) {
    $("#change-btn").onclick = () => {
        const menuId = $("#menu-selection").value;
        fetch(`./eatingHistories?mode=detailMenu&menuId=${menuId}`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            }
        }).then(resp => resp.json()).then(menu => {
            turnOffModal();
            const tds = tr.querySelectorAll('td');
            tds[1].innerText = menu.menuId;
            tds[2].innerText = menu.mainFoods.join(', ');
            tds[3].innerText = menu.subFood;
            tr.setAttribute("data-id", menu.menuId);
        });
    }

    $("#delete-btn").onclick = () => {
        turnOffModal();
        if (tr.classList.contains("hidden")) {
            tr.classList.remove("hidden");
        } else {
            tr.classList.add("hidden");
        }
    }
}

function addMonth(month) {
    const option = document.createElement("option");
    option.value = month;
    option.innerText = "Tháng " + month;
    $("#months-selection").appendChild(option);
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
    fetch(`./eatingHistories?mode=see&page=${page - 1}${querySearch}&month=${$("#months-selection").value}`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json"
        }
    }).then(resp => {
        if (resp.ok) {
            return resp.json();
        }
    }).then(data => {
        deleteEatingHistories();
        size = data.size;
        data.items.forEach(eatingHistory => addEatingHistory(eatingHistory));
        updatePageNumbers();
    });
}