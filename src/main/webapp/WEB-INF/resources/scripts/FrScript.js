function createFriendship(username,friendname){

    alert("ahahaa");
     $.ajax({
        type : "post",
        url  : "/friendlist/addfriend",
        data : {"username":username,"friendname":friendname},

        success : function(){
            document.getElementById("username").value = null;
            document.getElementById("friendname").value = null;
            window.location.href = "/lcomplete.html";
        },
        error : function(response,e,data){
            switch(data.toString()){
                case "Not Found" :
                    alert("Username/Friend does not exist");
                    break;               
                case "Found" :
                    alert("friendship already exist");
                    break;
                default:
                    alert("Unexpected Error" + data.toString());
                    break;
            }
        }
    });
}
