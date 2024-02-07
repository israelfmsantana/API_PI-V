const tasksEndpoint = "http://localhost:8080/task/user";

function hideLoader() {
  document.getElementById("loading").style.display = "none";
}

function show(tasks) {
  let tab = `<thead>
            <th scope="col">#</th>
            <th scope="col">Description</th>
        </thead>`;

  for (let task of tasks) {
    tab += `
            <tr>
                <td scope="row">${task.id}</td>
                <td>${task.description}</td>
            </tr>
        `;
  }

  document.getElementById("tasks").innerHTML = tab;
}

async function getTasks() {
  let key = "Authorization";
  const response = await fetch(tasksEndpoint, {
    method: "GET",
    headers: new Headers({
      Authorization: localStorage.getItem(key),
    }),
  });

  var data = await response.json();
  console.log(data);
  if (response) hideLoader();

  if (data.User && data.User.Profiles === ProfileEnum.ADMIN)
    getTasksAllUser();
  else
    show(data);

  
  
}

document.addEventListener("DOMContentLoaded", function (event) {
  if (!localStorage.getItem("Authorization"))
    window.location = "/view/login.html";
});

getTasks();



const tasksAllUserEndpoint = "http://localhost:8080/task/user_admin";

async function getTasksAllUser() {
  let key = "Authorization";
  const response = await fetch(tasksAllUserEndpoint, {
    method: "GET",
    headers: new Headers({
      Authorization: localStorage.getItem(key),
    }),
  });

  var dataAllUser = await response.json();
  console.log(dataAllUser);
  if (response) hideLoader();
  showAllUser(dataAllUser);
}


function showAllUser(tasksAllUser) {
  let tab = `<thead>
            <th scope="col">#</th>
            <th scope="col">Description</th>
            <th scope="col">Username</th>
        </thead>`;

  for (let task of tasksAllUser) {
    tab += `
            <tr>
                <td scope="row">${task.id}</td>
                <td>${task.description}</td>
                <td>${task.user,username}</td>
            </tr>
        `;
  }

  document.getElementById("tasks").innerHTML = tab;
}