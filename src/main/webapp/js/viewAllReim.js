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
//         var txt='';
       
    
//         for(y in data){

        
//         txt += '<a><tr class="clickable-row">'
//         txt += '<td>' + data[y].amount + '</td>';
//         txt += '<td>' + data[y].description + '</td>';
//         txt += '<td>' + data[y].requester.firstName + '</td>';
//         txt += '<td>' + data[y].requester.lastName + '</td>';
//         txt += '<td>' + data[y].requester.email + '</td>';
//         txt += '<td>' + data[y].status.status + '</td>';
//         txt += '<td>' + data[y].type.type + '</td>';
//         txt += '<td><a id="singlerequst">' + 'singleRequsting' + '</a></td>';

//        txt += '</tr></a>'
    
//    } 
//    document.getElementById("tableData").innerHTML = txt;
      let employeeList = document.getElementById("profile");
      employeeList.innerHTML ="";

      data.forEach((employee) => {
          let employeeNode =document.createElement("a");
        //   employeeNode.href="singlerequest.do";
          employeeNode.className ="list-group-item";
          employeeNode.id="singleid";
        let employeeNodeText = document.createTextNode(`Amount:${employee.amount} Description:${employee.description} FirstName:${ employee.requester.firstName} LastName: ${employee.requester.lastName} Email:  ${employee.requester.email}
        Status: ${employee.status.status} Type : ${employee.type.type}`);
    //else retrieve resolved date
        employeeNode.appendChild(employeeNodeText);

        employeeList.appendChild(employeeNode);

        employeeNode.addEventListener('click',(rid)=>{
            sessionStorage.setItem("reimbursementId",employee.id);
             window.location.replace("singlerequest.do");
          });
    
      });
      
    

    }

}