window.onload = () =>{
//share data between sevlet
    document.getElementById("username").innerHTML = sessionStorage.getItem("employeeUsername");

}