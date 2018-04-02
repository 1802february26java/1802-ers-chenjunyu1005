window.onload =() => {

    //Register  Event Listener





    document.getElementById("submit").addEventListener('click',()=>{

        

       

        let amount = document.getElementById("amount").value;

        let description = document.getElementById("description").value;

        let e = document.getElementById("type");

        let type = e.options[e.selectedIndex].value;

        let selectfile  = document.getElementById("file").files[0];
        console.log(selectfile);
        // var formData = new FormData();
        

        // formData.append('amount',amount);
        // formData.append('description',description);
        // formData.append('type',type);
        // formData.append('selectfile',selectfile);


        


        let reader = new FileReader();
        
        reader.onload= (event)=>{
            sessionStorage.setItem("imageresult",event.target.result);
            // console.log(reader.result);
        }
      
        let img= reader.readAsDataURL(selectfile);

        let upload=sessionStorage.getItem("imageresult");

        // var block = upload.split(";");

        // var contentType = block[0].split(":")[1];
        // var realData = block[1].split(",")[1];
        // var blob = b64toBlob(realData, contentType);
        // body.post(URL, {image: upload})


   

        // console.log(reader.readAsDataURL(selectfile));

        // var formdata = new FormData();

        // formdata.append( "sampleFile", sampleFile);





        //AJAX Logic 



        let xhr = new XMLHttpRequest();

// If the request is DONE (4 ) , AND EVERYTHING IS OK

        xhr.onreadystatechange = () =>{

            if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200){

                //Getting JSON FROM response body

                let data = JSON.parse(xhr.responseText);

                console.log(data);

                submitReiem(data);

            }



        };

        

        //Doing a Http to a specific endpoint

        // body.(.post(URL, {image: stringValue})

    xhr.open("POST",`summitrequest.do?amount=${amount}&description=${description}&type=${type}`);   

        //Sending our request

        xhr.send(upload);
    //    xhr.send();
    

    })



}

// function b64toBlob(b64Data, contentType, sliceSize) {
//     contentType = contentType || '';
//     sliceSize = sliceSize || 512;

//     var byteCharacters = atob(b64Data);
//     var byteArrays = [];

//     for (var offset = 0; offset < byteCharacters.length; offset += sliceSize) {
//         var slice = byteCharacters.slice(offset, offset + sliceSize);

//         var byteNumbers = new Array(slice.length);
//         for (var i = 0; i < slice.length; i++) {
//             byteNumbers[i] = slice.charCodeAt(i);
//         }

//         var byteArray = new Uint8Array(byteNumbers);

//         byteArrays.push(byteArray);
//     }

//   var blob = new Blob(byteArrays, {type: contentType});
//   return blob;
// }


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

        document.getElementById("registrationMessage").innerHTML ='<span class="label label-success label-center">Upload Successful</span>';

        setTimeout(()=>{ window.location.replace("home.do") }, 3000);

    }

    else{

    

    document.getElementById("registrationMessage").innerHTML ='<span class="label label-danger label-center">Something Went Wrong</span>';



    }



}