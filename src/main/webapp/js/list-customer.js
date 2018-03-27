window.onload =() => {
    
    
    //ListCustomer Event Listener

    document.getElementById("getCustomers").addEventListener('click',getAllCustomers);
       
    //Get all customers  as soon as it loads
    //  getAllCustomers();

}

function getAllCustomers(){
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

        xhr.open("GET",`getAll.do?fetch=LIST`);
   
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
      //Get customer list node
      let customerList = document.getElementById("customerList");

      //Clean customer list
      customerList.innerHTML ="";

      data.forEach((customer) => {
          //Creating node of <li>
          let customerNode =document.createElement("li");
          
          //Add class for styling <li class="something">
          //You can access any HTML field  do customerNoder.id
          customerNode.className ="list-group-item";
          
          //Creating innerHTML object, adding customer name to it
          //<li class ="something">[creating this object] </li>

        let customerNodeText = document.createTextNode(`${customer.lastName},${customer.firstName}`);


        customerNode.appendChild(customerNodeText);

        //Finally. we append the new customerNode to the CustomerList
        //<ul id ="customerList">
        //<li class = "something"> Perez,julio</li>
        //</ul>
        customerList.appendChild(customerNode);
      });


    }

}