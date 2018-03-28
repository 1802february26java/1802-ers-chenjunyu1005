window.onload =() => {
    
    
    //ListCustomer Event Listener

    //  document.getElementById("singleid").addEventListener('click',getSingleRequst());
       
    
 //Get all customers  as soon as it loads

     getSingleRequst();

}

function getSingleRequst(){

   let reimbursementId= sessionStorage.getItem("reimbursementId");
// document.getElementById("id")
   console.log(reimbursementId);
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
    else{
      
      let customerList = document.getElementById("profile");

      
          let customerNode =document.createElement("li");
         
          customerNode.className ="list-group-item";
          
        let customerNodeText = document.createTextNode(`Amount : ${data.amount}  Description :${data.description} 
        Requested Date: ${data.requested.dayOfWeek}-${data.requested.month}-${data.requested.dayOfMonth}-${data.requested.year}
       Status: ${data.status.status} Type:${data.type.type}`);


        customerNode.appendChild(customerNodeText);

      
        customerList.appendChild(customerNode);
    


    }

}