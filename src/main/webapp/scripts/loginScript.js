/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function initializeLogin() {
    var username = document.getElementById("username").value; //takes the value from the username field
    var password = document.getElementById("password").value; //takes the value from the password field
    
    $.ajax({//jQuery with Ajax template
        type : "post", //send type is "POST"
        url  : "/log-in", //the url the data will be posted
        data : 
            { "username": username, //the parameters the values will be stored
              "password": password
            },
              
        success : function() { //if the program responde with success this part of the code will run
            document.getElementById("username").value = null;//clears the input field "username"
            document.getElementById("password").value = null;//clears the input field "password"
            window.location.href = "/lcomplete.html";//moves to the next page
        },
        error : function(response,e,data) { //if the program responde with fail-error this part of the code will run
            switch(data.toString()){
                case "Not Found" ://posts a specified alert on the problem
                    alert("Username/password where wrong");
                    break;
                default://posts a general alert 
                    alert("Unexpected Error" + data.toString());
                    break;
            }
        }
               
    });
    
}