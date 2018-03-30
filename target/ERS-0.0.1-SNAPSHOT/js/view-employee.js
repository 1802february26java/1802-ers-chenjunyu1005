window.onload =() => {
    
    
    //ListCustomer Event Listener

    // document.getElementById("getCustomers").addEventListener('click',getProfile);
       
    //Get all customers  as soon as it loads
    //  getAllCustomers();

    getProfile();

}

function getProfile(){
    let xhr = new XMLHttpRequest();
    // If the request is DONE (4 ) , AND EVERYTHING IS OK
            xhr.onreadystatechange = () =>{
                if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200){
                    //Getting JSON FROM response body
                    let data = JSON.parse(xhr.responseText);
                    console.log(data);
    
                    //Present the data to the user
                    presentCustomers(data);
                }
    
            };


              
        //Doing a Http to a specific endpoint

        xhr.open("GET",`viewemployee.do?fetch=LIST`);
   
        //Sending our request
        xhr.send();


}

function presentCustomers(data){

    //If Message is a member of the JSON, something went wrong
    if(data.message){

        document.getElementById("listMessage").innerHTML ='<span class="label label-danger label-center">Something Went Wrong</span>';
    }
    //We  present all the customers to the user
    else{
        var txt="";
        txt += "<table class='table table-dark'>"; 
        txt +="<thead>"
         txt +="<tr>"
         txt +="<th scope='col'>First</th>"
         txt +="<th scope='col'>Last</th>"
         txt +="<th scope='col'>Username</th>"
         txt +="<th scope='col'>Email</th>"
         txt +="</tr>"
         txt +="</thead>"
       txt +="<tbody>"
            txt += "<tr>"
            txt += "<td>" + data.firstName + "</td>";
            txt += "<td>" + data.lastName + "</td>";
            txt += "<td>" + data.username + "</td>";
            txt += "<td>" + data.email + "</td>";
           txt += "</tr>"
       
         txt += "</tbody>"
           txt += "</table>" 
           document.getElementById("profile").innerHTML = txt;
      
    //   //Get customer list node
    //   let customerList = document.getElementById("profile");

    // //   //Clean customer list
    // //   customerList.innerHTML ="";

      
    //       //Creating node of <li>
    //       let customerNode =document.createElement("li");
          
    //       //Add class for styling <li class="something">
    //       //You can access any HTML field  do customerNoder.id
    //       customerNode.className ="list-group-item";
          
    //       //Creating innerHTML object, adding customer name to it
    //       //<li class ="something">[creating this object] </li>

    //     let customerNodeText = document.createTextNode(`${data.firstName}  ${data.lastName}  ${data.username} 
    //     ${data.email}`);


    //     customerNode.appendChild(customerNodeText);

    //     //Finally. we append the new customerNode to the CustomerList
    //     //<ul id ="customerList">
    //     //<li class = "something"> Perez,julio</li>
    //     //</ul>
    //     customerList.appendChild(customerNode);
    


    }

}