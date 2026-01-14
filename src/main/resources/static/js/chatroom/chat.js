const $chatroomId = document.getElementById("chatroomId");
const $chatLog = document.querySelector('.chat-log-box');
const $messageInput = document.getElementById("messageInput");
const $sendBtn = document.querySelector('.submit-btn');

document.addEventListener("DOMContentLoaded", function () {
    connect();
});

let client = null;

// WebSocket 연결
const connect = function () {
    const ws = new SockJS('/chat/stomp');
    client = Stomp.over(ws);

    client.connect({}, function () {
        console.log('websocket connect!')

        // 채팅방 구독
        client.subscribe(`/sub/chatrooms/${$chatroomId}`, function (message) {
            const data = JSON.parse(message.body);
        });
    });
}

// 메시지 전송
const sendMessage = () => {
    const message = $messageInput.value.trim();
    if(!message) return;

    client.send(
        `/pub/chatrooms/${$chatroomId.value}/chat`,
        {},
        JSON.stringify({ message })
    );

    $messageInput.value = "";
};

// 메시지 보내기
$sendBtn.addEventListener('click', sendMessage);
$messageInput.addEventListener('keydown', e => {
    if (e.key === "Enter") {
        sendMessage();
    }
});