package com.talkeasy.server.dto.chat;

import com.querydsl.codegen.Serializer;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
public class MessageDto{
    private String msgId;
    private String roomId;
    private String toUserId;
    private String fromUserId;
    private String msg; // 메시지 내용
    private String created_dt; // 생성 시간?
}
