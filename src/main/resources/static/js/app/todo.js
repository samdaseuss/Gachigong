const inputField = document.querySelector(".input-field input");
const todoLists = document.querySelector(".todoLists");
const clearButton = document.querySelector(".clear");

function allTasks() { let tasks = document.querySelectorAll(".pending"); }

inputField.addEventListener("keyup", (e) => {
    let inputVal = inputField.value.trim();
    if(e.key === "Enter" && inputVal.length > 0) {
        let list = `<div class="input-group mb-3">
                            <span class="input-group-text"><input class="form-check-input mt-0" type="checkbox" value="" aria-label=""></span>
                            <span class="form-control">${inputVal}</span>
                            <span class="input-group-text btn-danger" onclick="deleteTask(e)">X</span>
                    </div>`
        todoLists.insertAdjacentHTML("beforeend", list);
        inputField.value = "";
        allTasks();
    }
})

function handleStatus(e) {
    const checkbox = e.querySelector("input");
    checkbox.checked = checkbox.checked ? false : true;
    e.classList.toggle("pending");
}

deleteTask(e) => e.parentElement.remove;