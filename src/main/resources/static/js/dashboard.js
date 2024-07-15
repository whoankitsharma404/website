
let text = localStorage.getItem("user");
let obj = JSON.parse(text);
console.log(obj.name);
document.getElementById("user").innerHTML = obj.name;