//var timerElement = document.getElementById('timer');
//var isRunning = false;
//var startTime;
//var elapsedTime = 0;
var timers = {};
timers.isRunning = false;

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
