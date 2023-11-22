var timerElement = document.getElementById('timer');
var isRunning = false;
var startTime;
var elapsedTime = 0;
var timers = {};

function startStop(id) {
    if (!timers[id] || !timers[id].isRunning) {
        start(id);
    } else {
        stop(id);
    }
}

function start(id) {
    timers[id] = timers[id] || {};
    timers[id].isRunning = true;
    timers[id].startTime = Date.now() - (timers[id].elapsedTime || 0);
    update(id);
}

function stop(id) {
    timers[id] = timers[id] || {};
    timers[id].isRunning = false;
    timers[id].elapsedTime = Date.now() - timers[id].startTime;

    var formattedTime = formatTime(timers[id].elapsedTime);
    // formattedTime을 'hh:mm:ss' 형식에서 Duration 객체로 변환
    var durationArray = formattedTime.split(':').map(Number);
    var durationInSeconds = durationArray[0] * 3600 + durationArray[1] * 60 + durationArray[2];
    var studyTime = java.time.Duration.ofSeconds(durationInSeconds);
    uploadTime(id, formattedTime);
}

// function reset(id) {
//     if (!timers[id] || !timers[id].isRunning) {
//         timers[id] = timers[id] || {};
//         timers[id].elapsedTime = 0;
//         update(id);
//     }
// }

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
    document.getElementById('timer_' + id).innerHTML = formattedTime;
}

function uploadTime(id, formattedTime) {
    // Send data to server using AJAX
    var xhr = new XMLHttpRequest();
    var url = '/studytime/upload/' + id;
    xhr.open('POST', url, true);
    xhr.setRequestHeader('Content-Type', 'application/json');

    // Convert data into JSON format and send
    var data = JSON.stringify({ formattedTime: formattedTime });

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            // Process response from server (if necessary)
            console.log('Data uploaded successfully!');
        }
    };

    xhr.send(data);
}


function formatTime(milliseconds) {
    var totalSeconds = Math.floor(milliseconds / 1000);
    var hours = Math.floor(totalSeconds / 3600);
    var minutes = Math.floor((totalSeconds % 3600) / 60);
    var seconds = totalSeconds % 60;

    return pad(hours) + ':' + pad(minutes) + ':' + pad(seconds);
}

function pad(number) {
    return (number < 10 ? '0' : '') + number;
}