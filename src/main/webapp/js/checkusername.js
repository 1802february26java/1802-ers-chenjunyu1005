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

                register(data);
            }

        };
        
        //Doing a Http to a specific endpoint

        xhr.open("POST",`register.do?username=${username}`);   
        //Sending our request
        xhr.send();
    
    })

}



function register(data){

    //If Message is a member of the JSON,something went wrong 
    //check  username taken or went wrong
    if(data.message==="No Such Username"){
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