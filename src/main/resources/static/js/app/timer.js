// totalTime 값을 가져오기
var total = document.getElementById('totalTime');
var totalTime = total.textContent || total.innerText;

// totalTime을 시간으로 변환하여 총 시간을 계산
var totalHours = parseInt(totalTime.split(':')[0]);
// 페이지 로딩 시 이미지 변경 함수 호출
window.onload = changeImage;

// 이미지 변경을 위한 함수
function changeImage() {
    var imgElement = document.getElementById('timeImage');


    if (totalHours >= 1) {
        imgElement.src = "/img/img_1.png";
    }
    else if (totalHours >= 2) {
        imgElement.src = "/img/img_2.png";
    }
    else if (totalHours >= 3) {
        imgElement.src = "/img/img_3.png";
    }
}



var timers = {};
timers.isRunning = false;

function startStop(id) {
    var button = document.querySelector(`#controls button[data-id="${id}"]`);
    if (!timers[id] || !timers[id].isRunning) {
        start(id);
        button.textContent = "︎︎◼︎";
        button.style.backgroundColor = "#FF6757";
    } else {
        stop(id);
        button.textContent = "▶︎";
        button.style.backgroundColor = "#5A5A5A";
        sendData(id);
    }
}

function start(id) {
    //id값이 timer_id에 있는 String 값을 가져와서 밀리초 단위로 변환해서 DB에 있는 시간 정보 이어서 감
    var timerElement = document.getElementById('timer_' + id);
    var saveTime = timerElement.textContent || timerElement.innerText;
    var milliseconds = convertToMilliseconds(saveTime);
    timers[id] = timers[id] || {};
    timers[id].elapsedTime = milliseconds;

    timers[id].isRunning = true;
    timers[id].startTime = Date.now() - (timers[id].elapsedTime || 0);
    // if (!timers[id].elapsedTime) {
    //     timers[id].startTime = Date.now() - (timers[id].elapsedTime || 0);
    // }
    update(id);
}

function stop(id) {
    timers[id] = timers[id] || {};
    timers[id].isRunning = false;
    timers[id].elapsedTime = Date.now() - timers[id].startTime;
}

function update(id) {
    if (timers[id] && timers[id].isRunning) {
        requestAnimationFrame(function () {
            update(id);
        });
    }
    var currentTime = timers[id] && timers[id].isRunning ?
        Date.now() - timers[id].startTime :
        (timers[id] && timers[id].elapsedTime) || 0;
    var formattedTime = formatTime(currentTime);
    document.getElementById('studyTime_'+id).value = formattedTime;
    document.getElementById('timer_' + id).innerHTML = formattedTime;
}


function formatTime(milliseconds) {
    var totalSeconds = Math.floor(milliseconds / 1000);
    var hours = Math.floor(totalSeconds / 3600);
    var minutes = Math.floor((totalSeconds % 3600) / 60);
    var seconds = totalSeconds % 60;

    return pad(hours) + ':' + pad(minutes) + ':' + pad(seconds);
}


function convertToMilliseconds(formattedTime) {
    var timeArray = formattedTime.split(':');
    var hours = parseInt(timeArray[0], 10);
    var minutes = parseInt(timeArray[1], 10);
    var seconds = parseInt(timeArray[2], 10);

    var totalMilliseconds = (hours * 3600 + minutes * 60 + seconds) * 1000;

    return totalMilliseconds;
}

function pad(number) {
    return (number < 10 ? '0' : '') + number;
}

function sendData(id) {
    $.ajax({
        url: '/studytime/upload/' + id,
        data: $("#form"+id).serialize(),
        type: 'POST',
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

function toggleStartStop(id) {
    var button = document.querySelector(`#controls button[data-id="${id}"]`);

    if (button.textContent === "▶︎") {
        button.textContent = "︎︎◼︎";
        startStop(id);
    } else {
        button.textContent = "▶︎";
    }
}
