window.onload =() => {
    //Register  Event Listener

      
    document.getElementById("checkusername").addEventListener('click',()=>{
        
      
        let username = document.getElementById("username").value;
     


        //AJAX Logic 

        let xhr = new XMLHttpRequest();
// If the request is DONE (4 ) , AND EVERYTHING IS OK
        xhr.onreadystatechange = () =>{
            if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200){
                //Getting JSON FROM response body
                let data = JSON.parse(xhr.responseText);
                console.log(data);

                //Call registration response processing

                checkName(data);
            }

        };
        
        //Doing a Http to a specific endpoint

        xhr.open("POST",`checkusername.do?username=${username}`);   
        //Sending our request
        xhr.send();
    
    })

}



function checkName(data){

    //If Message is a member of the JSON,something went wrong 
    //check  username taken or went wrong
    if(data.message==="Username existed already"){
        //Confirm registration and redirect to login
        // document.getElementById("usernameMessage").innerHTML="";
        document.getElementById("usernameMessage").innerHTML ='<span class="label label-danger label-center">Username already exist try again</span>';
       
        

       


    }
    else{
    //     //Using sessionStorage of JavaScript
    // sessionStorage.setItem("customerId",data.id);
    // sessionStorage.setItem("customerUsername",data.username);
    // document.getElementById("usernameMessage").innerHTML="";
    document.getElementById("usernameMessage").innerHTML ='<span class="label label-success label-center">userName is valid</span>';

   

    }

}