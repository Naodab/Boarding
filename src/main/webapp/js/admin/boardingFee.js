import {
    turnOnModal,
    turnOffModal,
    renderAlertModal,
    renderConfirmModal,
    renderLoading
} from "../modal.js";

import Validator from "../validator.js";
import {renderAddBoardingFee, renderInvoiceByClass} from "./modal/modalBoardingFee.js";

const $ = document.querySelector.bind(document);
const $$ = document.querySelectorAll.bind(document);

$("#fees").classList.add("active");

let activePage = 1;
let size = 1;
let itemsPerPage = 8;
let lookupValue = "";
let boardingFee;
let activePageDetail = 1;
let activeClass = 1;

window.onload = () => {
    fetch("./eatingHistories?mode=months", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        }
    }).then(resp => resp.json()).then(data => {
        const monthsSelect = $("#fees-selection");
        data.forEach(item => {
            const option = document.createElement("option");
            option.value = item;
            option.innerText = "Tháng " + item;
            monthsSelect.appendChild(option);
        });

        if (data.length > 0)
            displayData(data[0]);
    });
}

$("#fees-selection").onchange = () => {
    displayData($("#fees-selection").value);
}

function displayData(value) {
    console.log(value)
    fetch(`./boardingFees?mode=see&boardingFeeId=${value}`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        }
    }).then(resp => resp.json()).then(data => {
        boardingFee = data;
        $("#boardingFeeId").innerText = data.boardingFeeId;
        $("#money").innerText = data.mainCosts + data.subCosts;
        $("#from").innerText = data.startDate;
        $("#to").innerText = data.endDate;
        $("#payedMoney").innerText = (data.payedMoneys / 1000) + "/" + (data.totalMoney / 1000);
        $("#payedStudent").innerText = data.payedStudents;
        $("#nonPrinted").innerText = data.nonPrintedInvoices;

        displayPage(1);
    })
}

function addInvoice(invoice) {
    const tr = document.createElement("tr");
    tr.innerHTML = `
        <td>${invoice.invoiceId}</td>
        <td>${invoice.studentId}</td>
        <td>${invoice.studentName}</td>
        <td>${invoice.studentClass}</td>
        <td>${invoice.paymentDate}</td>
        <td>${invoice.money}</td>
        <td>${invoice.status}</td>
    `;

    tr.onclick = () => {

    }
    $(".invoice-table tbody").appendChild(tr);
}

function deleteInvoices() {
    const table = $(".invoice-table tbody");
    const trs = $$(".invoice-table tr");
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
    let querySearch = lookupValue ? `&search=${lookupValue}` : "";
    if (querySearch) {
        const searchField = $(".search-field").value;
        querySearch += `&searchField=${searchField}`;
    }

    fetch(`./invoices?mode=see&page=${page - 1}${querySearch}&boardingFeeId=${boardingFee.boardingFeeId}`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json"
        }
    }).then(resp => {
        if (resp.ok) {
            return resp.json();
        }
    }).then(data => {
        deleteInvoices();
        size = data.size;
        data.items.forEach(invoice => addInvoice(invoice));
        updatePageNumbers();
    });
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
    if (page < 1 || page > totalPages || page === activePageDetail) return;
    activePageDetail = page;
    displayPageDetail(activePageDetail);
}

function addDetailInvoice(invoice) {
    const tr = document.createElement("tr");
    tr.innerHTML = `
        <td>${invoice.studentId}</td>
        <td>${invoice.studentName}</td>
        <td>${invoice.returnMoney}</td>
        <td>${invoice.mainCosts}</td>
        <td>${invoice.subCosts}</td>
        <td>${boardingFee.numberDays}</td>
        <td>${invoice.money}</td>
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
    fetch(`./invoices?mode=class&page=${page - 1}&boardingFeeId=${boardingFee.boardingFeeId}&boardingClassId=${activeClass}`, {
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
        data.items.forEach(invoice => addDetailInvoice(invoice));
        updatePageDetailNumbers(data.size, 10);
    });
}

$("#print-btn").onclick = () => {
    if (boardingFee.nonPrintedInvoices === 0) {
        turnOnModal(renderAlertModal, "Hiện không có hóa đơn nào để in.");
        return;
    }
    turnOnModal(renderLoading);

    fetch(`./invoices?mode=print&boardingFeeId=${boardingFee.boardingFeeId}`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        }
    }).then(resp => resp.ok).then(() => {
        turnOnModal(renderAlertModal, "In thành công");
    });
}

$("#list-btn").onclick = () => {
    turnOnModal(renderLoading);

    fetch(`./boardingFees?mode=list`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        }
    }).then(resp => resp.json()).then(data => {
        activeClass = data[0].id;
        turnOnModal(renderInvoiceByClass, data);
        $("#boardingClass").onchange = () => {
            activeClass = $("#boardingClass").value;
            activePageDetail = 1;
            displayPageDetail(1);
        }
        displayPageDetail(1);
    })
}

function preventChange(input) {
    let previousMonth = 0;
    input.onfocus = () => {
        previousMonth = new Date(input.value).getMonth();
    }

    input.onchange = () => {
        const newDate = new Date(input.value);
        if (newDate.getMonth() !== previousMonth) {
            newDate.setMonth(previousMonth);
            input.value = newDate.toISOString().split("T")[0];
        }
    }
}

$("#add-btn").onclick = () => {
    turnOnModal(renderAddBoardingFee);

    const startDate = $("#startDate");
    const endDate = $("#endDate");
    preventChange(startDate);
    preventChange(endDate);
    $("#month").onkeyup = () => {
        const month = new Date($("#month").value).getMonth();
        if (month > 0 && month < 13) {
            const start = new Date(startDate.value);
            const end = new Date(endDate.value);

            start.setMonth(month);
            end.setMonth(month);

            startDate.value = start.toISOString().split("T")[0];
            endDate.value = end.toISOString().split("T")[0];
        }
    }

    Validator({
        form: '#update-teacher',
        formGroupSelector: ".admin-form-group",
        errorSelector: '.form-message',
        rules: [
            Validator.isRequired('#month'),
            Validator.isRequired("#mainCosts"),
            Validator.isRequired("#subCosts"),
        ],
        onSubmit: data => {

        }
    });
}