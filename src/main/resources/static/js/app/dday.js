var date = document.getElementById("originalDate").innerText;
var currentDate = new Date();
var originalDate = new Date(date);
var ddayValue = Math.floor((currentDate - originalDate) / (1000 * 60 * 60 * 24));
document.getElementById('origcinalDate').innerText = 'D-Day: ' + ddayValue + ' days';