package com.talkeasy.server.domain.chat;

import com.talkeasy.server.dto.chat.MessageDto;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import static java.time.LocalTime.now;

@Document("chat_room_detail")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomDetail {

    @Id
    private String id;
    private String roomId;
    private String msg; // 메시지 내용
    private String created_dt; // 생성 시간?
    private Integer readCnt; // 읽음 수정
    private String toUserId;
    private String fromUserId;
    private String imageUrl;
    private int type; // 0(msg) :: 1(location) :: 2(sos)
    private int status; //0(REQUEST) :: 1(RESULT) :: 2(REJECT)


    public ChatRoomDetail(MessageDto messageDto) {
        this.toUserId = messageDto.getToUserId();
        this.fromUserId = messageDto.getFromUserId();
        this.roomId = messageDto.getRoomId();
        this.msg = messageDto.getMsg();
        this.created_dt = messageDto.getCreated_dt();
        this.readCnt = 1;

        this.type = messageDto.getType();

//        this.imageUrl = MessageDto.

    }
}