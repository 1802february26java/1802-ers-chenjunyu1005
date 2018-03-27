

document.getElementById("updateStatus").addEventListener('click', updateStatus);



function updateStatus() {
    let e = document.getElementById("status");
    let reimbursementstatus = e.options[e.selectedIndex].value;
    let reimbursementId = sessionStorage.getItem("reimbursementId");

    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = () => {
        if (xhr.readyState === 4 && xhr.status === 200) {
            let data = JSON.parse(xhr.responseText);
            console.log(data);

            update(data);
        }
    };

    xhr.open("POST", `finalizerequest.do?&reimbursementId=${reimbursementId}&reimbursementstatus=${reimbursementstatus}`)
    xhr.send();
}


function update(data) {
    if (data.message === "Update Reimbursement Successfully") {
        document.getElementById("listMessage").innerHTML = '<span class="label label-success label-center">Update Status Successfully</span>';
        setTimeout(() => { window.location.replace("mutiplerequest.do") }, 3000);

    }
    else {

        document.getElementById("listMessage").innerHTML = '<span class="label label-danger label-center">Something Went Wrong</span>';

    }

}