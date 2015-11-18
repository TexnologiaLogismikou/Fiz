/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//function uploadImage(){
//   var images = document.getElementById('images').value;
//     
//    $.ajax({
//        type : "post", 
//        url  : "/upload/a", 
//        //accept: "application/json",
//        //contentType: "application/json",
//        data : {"file":images},
//                
//         success : function() { 
//            //document.getElementById("images").value = null;//clears the input field "images"
//            window.location.href = "/lcomplete.html";//moves to the next page
//        },
//        
//         error : function(response,e,data) { //if the program responds with fail-error this part of the code will run
//            switch(data.toString()){
//                case "Not Found" ://posts a specified alert on the problem
//                    alert("images where wrong");
//                    break;
//                default://posts a general alert 
//                    alert("Unexpected Error" + data.toString());
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


//    function b64toBlob(b64Data, contentType, sliceSize) {
//        contentType = contentType || '';
//        sliceSize = sliceSize || 1024;
//       var byteCharacters = atob(b64Data);
//        var byteArrays = [];
//
//        for (var offset = 0; offset < byteCharacters.length; offset += sliceSize) {
//            var slice = byteCharacters.slice(offset, offset + sliceSize);
//            var byteNumbers = Array.prototype.map.call(slice, charCodeFromCharacter);
//            var byteArray = new Uint8Array(byteNumbers);
//            byteArrays.push(byteArray);
//        }
//        for (var i = 0; i < byteArray.length; i++) {
//            alert(byteArray[i]);
//        }
//
//    }
//     function uploadFile() {
//        var input = document.getElementById('images');
//        alert("input" + input.toString());
//        // var file = $("#objFile")[0].files[0];
//        var file = input.files[0];
//        alert("file" + file.toString());
//        fr = new FileReader();
//        fr.onload = receivedText;
//        //fr.readAsText(file);
//        fr.readAsDataURL(file);
//    }