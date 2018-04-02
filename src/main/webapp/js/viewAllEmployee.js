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
   
    //   let employeeList = document.getElementById("profile");
    //   employeeList.innerHTML ="";

    //   data.forEach((employee) => {
    //       let employeeNode =document.createElement("li");
    //       employeeNode.className ="list-group-item";
    //     let employeeNodeText = document.createTextNode(`${employee.lastName},${employee.firstName}, ${employee.username} 
    //     ${employee.email}`);

    //     employeeNode.appendChild(employeeNodeText);

    //     employeeList.appendChild(employeeNode);
    //   });
      var txt="";
      txt += "<table class='table table-dark'>"; 
      txt +="<thead>"
       txt +="<tr>"
       txt +="<th scope='col'>First</th>"
       txt +="<th scope='col'>Last</th>"
       txt +="<th scope='col'>Username</th>"
       txt +="<th scope='col'>Email</th>"
       txt +="<th scope='col'>Role</th>"
       txt +="</tr>"
       txt +="</thead>"
     txt +="<tbody>"
      for(y in data){
         txt += "<tr>"
         txt += "<td>" + data[y].firstName + "</td>";
         txt += "<td>" + data[y].lastName + "</td>";
         txt += "<td>" + data[y].username + "</td>";
         txt += "<td>" + data[y].email + "</td>";
         txt += "<td>" + data[y].employeeRole.type + "</td>";

        txt += "</tr>"
    }
      txt += "</tbody>"
        txt += "</table>" 
        document.getElementById("profile").innerHTML = txt;

        
   

    }

}



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

    