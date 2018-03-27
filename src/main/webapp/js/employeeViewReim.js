window.onload =() => {
    
    
    //ListCustomer Event Listener

    document.getElementById("getAllEmployee").addEventListener('click',getAllReimbursement);
       

}

function getAllReimbursement(){

    let e = document.getElementById("status");
    let status=e.options[e.selectedIndex].value;
    let id =sessionStorage.getItem("employeeId");

    let xhr = new XMLHttpRequest();
    // If the request is DONE (4 ) , AND EVERYTHING IS OK
            xhr.onreadystatechange = () =>{
                if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200){
                    //Getting JSON FROM response body
                    let data = JSON.parse(xhr.responseText);
                    console.log(data);
    
                    //Present the data to the user
                    presentReimbursement(data);
                }
    
            };


              
        //Doing a Http to a specific endpoint

        xhr.open("POST",`mutiplerequest.do?fetch=LIST&status=${status}&id=${id}`);
   
        //Sending our request
        xhr.send();


}

function presentReimbursement(data){

    //If Message is a member of the JSON, something went wrong
    if(data.message){

        document.getElementById("listMessage").innerHTML ='<span class="label label-danger label-center">Something Went Wrong</span>';
    }
    else if (data.length ===0){
        let employeeList = document.getElementById("profile");
      employeeList.innerHTML ="";
        document.getElementById("listMessage").innerHTML ='<span class="label label-danger label-center">No record Found</span>';

    }
    
    else{
   
      let employeeList = document.getElementById("profile");
      employeeList.innerHTML ="";

      data.forEach((employee) => {
        // let dates = new Date(employee.requested);
          let employeeNode =document.createElement("li");
          employeeNode.className ="list-group-item";
          //If pending  resolved is null 
        //   if(employee.resolved===null){
        let employeeNodeText = document.createTextNode(`Amount : ${employee.amount} Description:  ${employee.description} 
       Status:  ${employee.status.status}  Type: ${employee.type.type}`);
    // }

    //else retrieve resolved date
        employeeNode.appendChild(employeeNodeText);

        employeeList.appendChild(employeeNode);
      });


    }

}