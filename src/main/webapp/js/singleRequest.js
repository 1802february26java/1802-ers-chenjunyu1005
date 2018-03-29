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

    //     var txt="";
    //   txt += "<table class='table table-dark'>"; 
    //   txt +="<thead>"
    //    txt +="<tr>"
    //    txt +="<th scope='col'>Amount</th>"
    //    txt +="<th scope='col'>Description</th>"
    //    txt +="<th scope='col'>RequstedDate</th>"
    //    txt +="<th scope='col'>Status</th>"
    //    txt +="<th scope='col'>Type</th>"
    //    txt +="</tr>"
    //    txt +="</thead>"
    //  txt +="<tbody>"
    //   for(y in data){
    //      txt += "<tr>"
    //      txt += "<td>" + data[y].amount + "</td>";
    //      txt += "<td>" + data[y].description + "</td>";
    //     //  txt += "<td>" + data[y].requested.dayOfWeek+ "-"+data[y].requested.monthValue+"-"+ data[y].requested.dayOfMonth+"-"+data[y].requested.year+"</td>";
    //      txt += "<td>" + data[y].status.status + "</td>";
    //      txt += "<td>" + data[y].type.type + "</td>";

    //     txt += "</tr>"
    // }
    //   txt += "</tbody>"
    //     txt += "</table>" 
    //     document.getElementById("profile").innerHTML = txt;
      
      let customerList = document.getElementById("profile");

      
          let customerNode =document.createElement("li");
         
          customerNode.className ="list-group-item";
          
        let customerNodeText = document.createTextNode(`Amount : ${data.amount}  Description :${data.description} 
        Requested Date: ${data.requested.dayOfWeek}-${data.requested.month}-${data.requested.dayOfMonth}-${data.requested.year}
       Status: ${data.status.status} Type:${data.type.type}`);


        customerNode.appendChild(customerNodeText);

      
        customerList.appendChild(customerNode);

        // document.getElementById("ItemPreview").src = "data:image/png;base64," + data.receipt;
    


    }

}