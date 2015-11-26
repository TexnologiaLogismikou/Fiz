var stompClient = null;

function connect(){
    var socket = new SockJS('/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/chat', function(chat){
            showGreeting(JSON.parse(chat.body).message, JSON.parse(chat.body).user);
        });
    });
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendMessage(userid) {
    var message = document.getElementById('message').value;
    stompClient.send("/app/chat", {}, JSON.stringify(
        {
            'message': message ,
            'user': userid
        }));
    document.getElementById('message').value = " ";
    var textarea = document.getElementById('text-area');
    textarea.scrollTop = textarea.scrollHeight;
}

function showGreeting(message, user) {

    message = message.trim();
    document.getElementById('text-area').value += user + ": " + message + "\n";
}

function enterFunction(e) {
    if (e.keyCode == 13) {
        sendMessage();
    }
}