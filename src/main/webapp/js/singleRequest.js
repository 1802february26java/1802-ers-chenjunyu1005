window.onload =() => {
    
    
    //ListCustomer Event Listener

    //  document.getElementById("singleid").addEventListener('click',getSingleRequst);
       
    
 //Get all customers  as soon as it loads

     getSingleRequst();

}

function getSingleRequst(){

   let reimbursementId= sessionStorage.getItem("reimbursementId");
   let status = sessionStorage.getItem("status");
   
    let xhr = new XMLHttpRequest();
    // If the request is DONE (4 ) , AND EVERYTHING IS OK
            xhr.onreadystatechange = () =>{
                if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200){
                    //Getting JSON FROM response body
                    let data = JSON.parse(xhr.responseText);
                    console.log(data);
    
                    //Present the data to the user
                    presentEmployee(data);
                }
    
            };


              
        //Doing a Http to a specific endpoint

        xhr.open("POST",`singlerequest.do?fetch=LIST&reimbursementId=${reimbursementId}&status=${status}`);
   
        //Sending our request
        xhr.send();


}

function presentEmployee(data){

    //If Message is a member of the JSON, something went wrong
    if(data.message){

        document.getElementById("listMessage").innerHTML ='<span class="label label-danger label-center">Something Went Wrong</span>';
    }
    //We  present all the customers to the user
    else{
      //Get customer list node
      let customerList = document.getElementById("profile");

    //   //Clean customer list
    //   customerList.innerHTML ="";

      
          //Creating node of <li>
          let customerNode =document.createElement("li");
          
          //Add class for styling <li class="something">
          //You can access any HTML field  do customerNoder.id
          customerNode.className ="list-group-item";
          
          //Creating innerHTML object, adding customer name to it
          //<li class ="something">[creating this object] </li>

        let customerNodeText = document.createTextNode(`Amount : ${data.amount}  Description :${data.description} 
        Requested Date: ${data.requested.dayOfWeek}-${data.requested.month}-${data.requested.dayOfMonth}-${data.requested.year}
       Status: ${data.status.status} Type:${data.type.type}`);


        customerNode.appendChild(customerNodeText);

      
        customerList.appendChild(customerNode);
    


    }

}