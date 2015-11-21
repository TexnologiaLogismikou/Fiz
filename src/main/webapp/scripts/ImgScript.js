/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//
//
//
//function uploadImage(){
//    document.getElementById("images").disabled = true;
//   var images = document.getElementById("images");
//   var imName = document.getElementById("filename").value;
//     
//    $.ajax({
//        type : "post", 
//        url  : "/upload/a", 
//        //accept: "application/json",
//        //contentType: "application/json",
//        data : {"name":imName
//               ,"file":images.src},
//                
//         success : function() { 
//            //document.getElementById("images").value = null;//clears the input field "images"
//            document.getElementById("images").value = "";
//            document.getElementById("filename").value = "";
//            alert("success");
//        },
//        
//         error : function(response,e,data) { //if the program responds with fail-error this part of the code will run
//            switch(data.toString()){
//                case "Not Found" ://posts a specified alert on the problem
//                    alert("images where wrong");
//                    break;
//                default://posts a general alert 
//                    alert("Unexpected Error \n" + data.toString());
//                    break;
//            }
//        }
//    });
//}

    function downloadImageWithPost(){
        var imgID = document.getElementById("imgID").value;
            document.getElementById("ItemPreview").src= null;
            document.getElementById("ItemPreview").alt = null;
    $.ajax({
        type : "post",
        url  : "/upload",
        accept: "image/jpg",
        data: {"id" : imgID},
        success : function(data) {
            document.getElementById("ItemPreview").src = "/upload/b?id=" + imgID;
            document.getElementById("ItemPreview").alt = "NO image";
        },
        error : function () {
            alert("all bad");
        }
    });    
}