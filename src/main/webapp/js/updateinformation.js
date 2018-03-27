window.onload =() => {
    //Register  Event Listener


    document.getElementById("submit").addEventListener('click',()=>{
        
        //check userName
        // let username = document.getElementById("username").value;
        // if(username==repeatpassword){
        //     document.getElementById("registrationMessage").innerHTML ='<span class="label label-danger label-center">password does not match</span>';
        //     return;
        // }

        let firstName = document.getElementById("firstName").value;
        let lastName = document.getElementById("lastName").value;
        let username = document.getElementById("username").value;
        let email = document.getElementById("email").value;


        //AJAX Logic 

        let xhr = new XMLHttpRequest();
// If the request is DONE (4 ) , AND EVERYTHING IS OK
        xhr.onreadystatechange = () =>{
            if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200){
                //Getting JSON FROM response body
                let data = JSON.parse(xhr.responseText);
                console.log(data);

                //Call registration response processing

                updateinformation(data);
            }

        };
        
        //Doing a Http to a specific endpoint

        xhr.open("POST",`update.do?firstName=${firstName}&lastName=${lastName}&username=${username}&email=${email}`);   
        //Sending our request
        xhr.send();
    
    })

}

function disableAllComponents(){
    document.getElementById("firstName").setAttribute("disabled","disabled");
    document.getElementById("lastName").setAttribute("disabled","disabled");
    document.getElementById("username").setAttribute("disabled","disabled");
    document.getElementById("email").setAttribute("disabled","disabled");
    document.getElementById("submit").setAttribute("disabled","disabled");

}

function updateinformation(data){

    //If Message is a member of the JSON,something went wrong 
    //check  username taken or went wrong
    if(data.message==="Succefully Updated"){
        //Confirm registration and redirect to login
        disableAllComponents();
        document.getElementById("registrationMessage").innerHTML ='<span class="label label-success label-center">Update Successful</span>';
        setTimeout(()=>{ window.location.replace("home.do") }, 3000);

       


    }
    else{
    
    document.getElementById("registrationMessage").innerHTML ='<span class="label label-danger label-center">Something Went Wrong</span>';

    }

}