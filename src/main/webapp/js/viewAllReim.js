window.onload =() => {
    
    
    //ListCustomer Event Listener

    document.getElementById("getAllEmployee").addEventListener('click',getAllReimbursement);
       

}

function getAllReimbursement(){

    let e = document.getElementById("status");
    let status=e.options[e.selectedIndex].value;
    sessionStorage.setItem("status",status);
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

        xhr.open("POST",`mutiplerequest.do?fetch=LIST&status=${status}`);
   
        //Sending our request
        xhr.send();


}

function presentReimbursement(data){

    //If Message is a member of the JSON, something went wrong
    if(data.message){

        document.getElementById("listMessage").innerHTML ='<span class="label label-danger label-center">Something Went Wrong</span>';
    }
    
    else{
      let employeeList = document.getElementById("profile");
      employeeList.innerHTML ="";

      data.forEach((employee) => {
        // let dates = new Date(employee.requested);
        sessionStorage.setItem("reimbursementId",employee.id);

          let employeeNode =document.createElement("a");
          employeeNode.href="singlerequest.do";
          employeeNode.className ="list-group-item";
          employeeNode.id="singleid";
          
          //If pending  resolved is null 
        //   if(employee.resolved===null){
    
        let employeeNodeText = document.createTextNode(`Amount:${employee.amount} FirstName:${ employee.requester.firstName} LastName: ${employee.requester.lastName} Email:  ${employee.requester.email}
        Status: ${employee.status.status} Type : ${employee.type.type}`);
     

    //else retrieve resolved date
        employeeNode.appendChild(employeeNodeText);

        employeeList.appendChild(employeeNode);
      });


    }

}