const $ = document.querySelector.bind(document);
const $$ = document.querySelectorAll.bind(document);

$("#eatingHistories").classList.add("active");

let activePage = 1;
let size = 1;
let itemsPerPage = 12;
let lookupValue = "";

function addEatingHistory(eatingHistory) {
    const tr = document.createElement("tr");
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
        console.log(data)
        const monthsSelect = $("#months-selection");
        data.forEach(item => {
            const option = document.createElement("option");
            option.value = item;
            option.innerText = "Tháng " + item;
            monthsSelect.appendChild(option);
        });

        monthsSelect.onchange = () => {
            displayPage(1);
        }

        displayPage(1);
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
        console.log(data);
        deleteEatingHistories();
        size = data.size;
        data.items.forEach(eatingHistory => addEatingHistory(eatingHistory));
        updatePageNumbers();
    });
}

