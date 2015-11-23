/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

    function downloadImageWithPost(){
        var imgID = document.getElementById("imgID").value;
            $.ajax({ 
                type:"post",
                url:"images/get/" + imgID + ".jpg",
                accept: "image/jpg",
                success : function(data) {
                    document.getElementById("ItemPreview").src = "images/get/" + imgID + ".jpg";
                    document.getElementById("ItemPreview").alt = "title"; 
                    
                },
                error : function(data) {
                    document.getElementById("ItemPreview").src = null;
                    document.getElementById("ItemPreview").alt = "error";
                }
            });
        }
        