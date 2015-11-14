/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function initializeRegister() {
    var username = document.getElementById("username").value; //takes the value from the username field
    var password = document.getElementById("password").value; //takes the value from the password field
    var user = JSON.stringify({username: username, password: password})
    
    $.ajax({//jQuery with Ajax template
        type : "post", //send type is "POST"
        url  : "/register",//the url the data will be posted
        contentType: "application/json",
        data : user,
        success : function(response) {  //if the program responds with success this part of the code will run
           document.getElementById("username").value=null; //clears the input field "username"
           document.getElementById("password").value=null; //clears the input field "password"
           window.location.href = "/complete.html"; //moves to the next page
        },  
        error : function(respone,e,data) { //if the program responds with fail-error this part of the code will run
            if(data.toString() === "Found") {
                alert("The username already exists"); //posts a specified alert on the problem
            } else {
                alert("Unsupported exception"); //posts a general alert that a problem occurred
            }
       }
    });
}

