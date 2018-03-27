window.onload =() => {
    
    
    //ListCustomer Event Listener

    document.getElementById("getAllEmployee").addEventListener('click',getAllEmployees);
       

}

function getAllEmployees(){
    let xhr = new XMLHttpRequest();
    // If the request is DONE (4 ) , AND EVERYTHING IS OK
            xhr.onreadystatechange = () =>{
                if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200){
                    //Getting JSON FROM response body
                    let data = JSON.parse(xhr.responseText);
                    console.log(data);
    
                    //Present the data to the user
                    presentEmployees(data);
                }
    
            };


              
        //Doing a Http to a specific endpoint

        xhr.open("GET",`viewall.do?fetch=LIST`);
   
        //Sending our request
        xhr.send();


}

function presentEmployees(data){

    //If Message is a member of the JSON, something went wrong
    if(data.message){

        document.getElementById("listMessage").innerHTML ='<span class="label label-danger label-center">Something Went Wrong</span>';
    }
    
    else{
   
      let employeeList = document.getElementById("profile");
      employeeList.innerHTML ="";

      data.forEach((employee) => {
          let employeeNode =document.createElement("li");
          employeeNode.className ="list-group-item";
        let employeeNodeText = document.createTextNode(`${employee.lastName},${employee.firstName}, ${employee.username} 
        ${employee.email}`);

        employeeNode.appendChild(employeeNodeText);

        employeeList.appendChild(employeeNode);
      });
       
    // let employeeList = document.getElementById("profile");
    //  data.forEach((em) => {
    //     let trNode = document.createElement("tr");
    //     em.forEach((td) => {
    //         let tdNode =document.createElement("td");
    //         tdnodeText = document.createTextNode(`${td.lastName}`);
    //         tdsecondTest = document.createTextNode(`${td.firstName}`);
    //         tdNode.appendChild(tdnodeText);
    //         tdNode.appendChild(tdsecondTest);
    //     });
    //     trNode.appendChild(tdNode);
    //  });
    }

}