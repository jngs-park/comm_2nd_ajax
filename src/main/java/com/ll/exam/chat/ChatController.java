package com.ll.exam.chat;
import com.ll.exam.Rq;
import com.ll.exam.article.dto.ArticletDto;
import com.ll.exam.chat.dto.ChatRoomDto;
import java.util.List;
public class ChatController {
    private ChatService chatService;
    public ChatController() {
        chatService = new ChatService();
    }
    public void showCreateRoom(Rq rq) {
        rq.view("usr/chat/createRoom");
    }
    public void doCreateRoom(Rq rq) {
        String title = rq.getParam("title", "");
        String body = rq.getParam("body", "");
        if (title.length() == 0) {
            rq.historyBack("제목을 입력해주세요.");
            return;
        }
        if (body.length() == 0) {
            rq.historyBack("내용을 입력해주세요.");
            return;
        }
        long id = chatService.createRoom(title, body);
        rq.replace("/usr/chat/room/%d".formatted(id), "%d번 채팅방이 생성 되었습니다.".formatted(id));
    }
    public void showRoomList(Rq rq) {
        List<ChatRoomDto> chatRoomDtos = chatService.findAllRooms();
        rq.setAttr("rooms", chatRoomDtos);
        rq.view("usr/chat/roomList");
    }
    public void showModifyRoom(Rq rq) {
        long id = rq.getLongPathValueByIndex(0, -1);
        if ( id == -1 ) {
            rq.historyBack("번호를 입력해주세요.");
            return;
        }
        ChatRoomDto chatRoom = chatService.findRoomById(id);
        if ( chatRoom == null ) {
            rq.historyBack("존재하지 않는 채팅방 입니다.");
            return;
        }
        rq.setAttr("room", chatRoom);
        rq.view("usr/chat/modifyRoom");
    }
    public void doModifyRoom(Rq rq) {
        long id = rq.getLongPathValueByIndex(0, -1);
        if ( id == -1 ) {
            rq.historyBack("번호를 입력해주세요.");
            return;
        }
        String title = rq.getParam("title", "");
        if ( title.length() == 0 ) {
            rq.historyBack("제목을 입력해주세요.");
            return;
        }
        String body = rq.getParam("body", "");
        if ( body.length() == 0 ) {
            rq.historyBack("내용을 입력해주세요.");
            return;
        }
        ChatRoomDto chatRoom = chatService.findRoomById(id);
        if ( chatRoom == null ) {
            rq.historyBack("존재하지 않는 채팅방 입니다.");
            return;
        }
        chatService.modifyRoom(id, title, body);
        rq.replace("/usr/chat/room/%d".formatted(id), "%d번 채팅방이 수정되었습니다.".formatted(id));
    }
    public void deleteRoom(Rq rq) {
        long id = rq.getLongPathValueByIndex(0, 0);
        if (id == 0) {
            rq.historyBack("번호를 입력해주세요.");
            return;
        }
        ChatRoomDto chatRoomDto = chatService.findRoomById(id);
        if (chatRoomDto == null) {
            rq.historyBack("해당 글이 존재하지 않습니다.");
            return;
        }
        chatService.deleteRoom(id);

        rq.replace("/usr/chat/roomList", "%d번 채팅방이 삭제되었습니다.".formatted(id));
    }

    public void showRoom(Rq rq) {
        long id = rq.getLongPathValueByIndex(0, -1);

        if ( id == -1 ) {
            rq.historyBack("번호를 입력해주세요.");
            return;
        }

        ChatRoomDto chatRoom = chatService.findRoomById(id);

        if ( chatRoom == null ) {
            rq.historyBack("존재하지 않는 채팅방 입니다.");
            return;
        }

        rq.setAttr("room", chatRoom);
        rq.view("usr/chat/room");
    }

    public void doWriteMessage(Rq rq) {
        long roomId = rq.getLongPathValueByIndex(0, -1);
        String body = rq.getParam("body", "");

        rq.println("채팅방 번호 : %d<br />".formatted(roomId));
        rq.println("채팅메세지 : %s<br />".formatted(body));
    }
}