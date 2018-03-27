window.onload =() => {

    //Redirect user to the right URL if they comes from somewhere else
    // if(window.location.pathname!=='/ERS/login.do'){
    //      window.location.replace('login.do');
    //  }


    //Login Event Listener


    document.getElementById("login").addEventListener('click',()=>{
        let username = document.getElementById("username").value;
        let password = document.getElementById("password").value;

        //AJAX Logic 

        let xhr = new XMLHttpRequest();
// If the request is DONE (4 ) , AND EVERYTHING IS OK
        xhr.onreadystatechange = () =>{
            if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200){
                //Getting JSON FROM response body
                let data = JSON.parse(xhr.responseText);
                console.log(data);

                //Call login response processing

                login(data);
            }

        };
        
        //Doing a Http to a specific endpoint

        xhr.open("POST",`login.do?username=${username}&password=${password}`);
   
        //Sending our request
        xhr.send();
    
    })

}

function login(data){

    //If Message is a member of the JSON, it was Authentication fails
    if(data.message==="AUTHENTICATION FAILED"){

        document.getElementById("loginMessage").innerHTML ='<span class="label label-danger label-center">Wrong credentials.</span>';
    }else if (data.message=="No Such Username"){
        document.getElementById("loginMessage").innerHTML ='<span class="label label-danger label-center">Username does Not exist</span>';

    }
    else{
        //Using sessionStorage of JavaScript
    sessionStorage.setItem("employeeId",data.id);
    sessionStorage.setItem("employeeUsername",data.username);

    //Redirect to Home Page
    window.location.replace("home.do");

    }

}