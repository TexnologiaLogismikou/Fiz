function testAlrt(){
    alert("AHAHAAHAHHA");
}

function registerUser() {
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;
    var user = JSON.stringify({username: username, password: password})

    $.ajax({
        type : "post",
        url  : "/register",
        accept: "application/json",
        contentType: "application/json",
        data : user,

        success : function(response) {
            document.getElementById("username").value=null;
            document.getElementById("password").value=null;
            window.location.href = "/home";
        },
        error : function(respone,e,data) {
            switch(data.toString()){
                case "Found" :
                    alert("The username already exists");
                    break;
                default:
                    alert("Unexpected Error: " + data.toString());
                    break;
            }
        }
    });
}