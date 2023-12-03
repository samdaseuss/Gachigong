const currentDate = document.querySelector(".current-date");
const daysTag = document.querySelector(".days");
const prevNextIcon = document.querySelectorAll(".icons span");
const bringDateDiv = document.querySelector(".bring-date");

let date = new Date();
let currYear = date.getFullYear();
let currMonth = date.getMonth();

const months = [
    "January", "February", "March", "April", "May", "June", "July", "August",
    "September", "October", "November", "December"
];

const renderCalendar = () => {
    let firstDayofMonth = new Date(currYear, currMonth, 1).getDay();
    let lastDateofMonth = new Date(currYear, currMonth + 1, 0).getDate();
    let lastDayofMonth = new Date(currYear, currMonth, lastDateofMonth).getDay();

    let liTag = "";
    let prevMonthLastDate = new Date(currYear, currMonth, 0).getDate();

    for (let i = firstDayofMonth; i > 0; i--) {
        liTag += `<li class="inactive">${prevMonthLastDate - i + 1}</li>`;
    }

    for (let i = 1; i <= lastDateofMonth; i++) {
        let day = i < 10 ? `0${i}` : `${i}`;
        let month = currMonth + 1 < 10 ? `0${currMonth + 1}` : `${currMonth + 1}`;
        let formattedDate = `${currYear}-${month}-${day}`;

        let isToday =
            i === date.getDate() &&
            currMonth === date.getMonth() &&
            currYear === date.getFullYear()
                ? "active"
                : "";
        liTag += `<li class="${isToday}" data-date="${formattedDate}">${i}</li>`;
    }

    for (let i = lastDayofMonth + 1; i < 7; i++) {
        liTag += `<li class="inactive">${i - lastDayofMonth}</li>`;
    }

    currentDate.innerText = `${months[currMonth]} ${currYear}`;
    daysTag.innerHTML = liTag;

    bringDateDiv.textContent = `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()}`;
};

renderCalendar();

prevNextIcon.forEach((icon) => {
    icon.addEventListener("click", () => {
        currMonth = icon.id === "prev" ? currMonth - 1 : currMonth + 1;
        if (currMonth === -1) {
            currMonth = 11;
            currYear--;
        } else if (currMonth === 12) {
            currMonth = 0;
            currYear++;
        }
        renderCalendar();
    });
});


// URL에서 날짜 파라미터를 추출하는 함수
function getDateFromURL() {
    const url = window.location.href;
    const urlParts = url.split('/');
    const lastPart = urlParts[urlParts.length - 1];
    const datePattern = /\d{4}-\d{2}-\d{2}/;
    const dateMatch = lastPart.match(datePattern);

    if (dateMatch) {
        return dateMatch[0];
    } else {
        return null;
    }
}

// 날짜를 받아서 캘린더를 다시 그리는 함수
function renderCalendarFromDate(clickedDate) {
    // 날짜를 파싱하여 캘린더를 다시 그리는 로직 추가
    // 여기서 clickedDate를 이용하여 캘린더를 그리는 로직을 작성하면 됩니다.
}

document.addEventListener('DOMContentLoaded', () => {
    const clickedDate = getDateFromURL();

    if (clickedDate) {
        const bringDateDiv = document.querySelector(".bring-date");
        bringDateDiv.textContent = clickedDate;
        renderCalendarFromDate(clickedDate);
    } else {
        // URL에서 날짜가 없을 때의 기본 동작
        renderCalendarFromDate(); // 기본적으로 오늘 날짜를 기준으로 캘린더를 그림
    }
});

daysTag.addEventListener("click", (event) => {
    const clickedDate = event.target.dataset.date;
    sendDate(clickedDate);
    if (clickedDate) {
        const bringDateDiv = document.querySelector(".bring-date");
        bringDateDiv.textContent = clickedDate;

        const newURL = `/calendar/${clickedDate}`; // 새로운 URL 생성
        history.pushState({}, '', newURL); // URL 변경
        window.location.reload();
        renderCalendarFromDate(clickedDate);
    }
});


function sendDate(date) {
    $.ajax({
        url: '/calendar/' + date,
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            location.reload();
        },
        error: function(data){
            console.log(data);
            if(data.responseJSON.code == "studyTime") {
                document.getElementById('studyTime').classList.add("fieldError");
                document.getElementById("nameErr").innerHTML = data.responseJSON.message;
            } else {
                document.getElementById("globalErr").innerHTML = data.responseJSON.message;
            }
        }
    });
}