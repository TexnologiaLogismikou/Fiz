/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function createFriendship()
{
    var username = document.getElementById("username").value;
    var friendname = document.getElementById("friendname").value; 
    
     $.ajax({//jQuery with Ajax template
        type : "post", //send type is "POST"
        url  : "/friendlist/addfriend", //the url the data will be posted
    
        data : {"username":username,"friendname":friendname},
              

        success : function() 
        { //if the program responds with success this part of the code will run
            document.getElementById("username").value = null;//clears the input field "username"
            document.getElementById("friendname").value = null;//clears the input field "friendname"
            window.location.href = "/lcomplete.html";//moves to the next page
        },
        error : function(response,e,data)
        { //if the program responds with fail-error this part of the code will run
            switch(data.toString())
            {
                case "Not Found" ://posts a specified alert on the problem
                    alert("Username/Friend does not exist");
                    break;               
                case "Found" ://posts a specified alert on the problem
                    alert("friendship already exist");
                    break;
                default://posts a general alert 
                    alert("Unexpected Error" + data.toString());
                    break;
            }
        }
    });
}
