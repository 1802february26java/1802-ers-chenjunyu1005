window.onload =() => {
    //Register  Event Listener


    document.getElementById("submit").addEventListener('click',()=>{
        
       
        let amount = document.getElementById("amount").value;
        let description = document.getElementById("description").value;
        let e = document.getElementById("type");
        let type=e.options[e.selectedIndex].value;


        //AJAX Logic 

        let xhr = new XMLHttpRequest();
// If the request is DONE (4 ) , AND EVERYTHING IS OK
        xhr.onreadystatechange = () =>{
            if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200){
                //Getting JSON FROM response body
                let data = JSON.parse(xhr.responseText);
                console.log(data);

                //Call registration response processing

                submitReiem(data);
            }

        };
        
        //Doing a Http to a specific endpoint

        xhr.open("POST",`summitrequest.do?amount=${amount}&description=${description}&type=${type}`);   
        //Sending our request
        xhr.send();
    
    })

}

function disableAllComponents(){
    document.getElementById("amount").setAttribute("disabled","disabled");
    document.getElementById("description").setAttribute("disabled","disabled");
    document.getElementById("type").setAttribute("disabled","disabled");
    document.getElementById("submit").setAttribute("disabled","disabled");

}

function submitReiem(data){

    //If Message is a member of the JSON,something went wrong 
    //check  username taken or went wrong
    if(data.message==="Submit Success"){
        // sessionStorage.setItem("reimbursementId",data.id);
        

        disableAllComponents();
        document.getElementById("registrationMessage").innerHTML ='<span class="label label-success label-center">Update Successful</span>';
        setTimeout(()=>{ window.location.replace("home.do") }, 3000);

       


    }
    else{
    
    document.getElementById("registrationMessage").innerHTML ='<span class="label label-danger label-center">Something Went Wrong</span>';

    }

}