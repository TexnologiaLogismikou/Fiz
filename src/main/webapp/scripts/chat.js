//window.onload = function() {
//    $.ajax({
//        type: "post",
//        url: "/chat",
//        data: {
//            "username": "null",
//            "message": "null"
//        },
//
//        success: function () {
//
//        },
//        error: function () {
//            alert("something went wrong");
//        }
//    });
//};

function chat() {
    var username = "andrew";
    var message = document.getElementById("message").value;
    document.getElementById("header").innerHTML = "Welcome " + username;
    $.ajax({
        type: "post",
        url: "/chat",
        data: {
            "username": username,
            "message": message
        },

        success: function (response, e, data) {

            document.getElementById("message").value = null;
            document.getElementById("textare").value = response.toString();
            document.getElementById("header").innerHTML =
                "Response = " + response +
                "\nValue = " + response.value +
                "\nToString = " + response.toString() +
                "\nLocaleString = " + response.toLocaleString() +
                "\nresponseText = " + response.responseText;
        },
        error: function () {
            alert("something went wrong");
        }
    });
}
