package com.talkeasy.server.service.chat;

import com.google.gson.Gson;
import com.talkeasy.server.domain.chat.ChatRoomDetail;
import com.talkeasy.server.dto.chat.ChatReadDto;
import com.talkeasy.server.dto.chat.ChatReadResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.QueueInformation;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatReadService {

    private final MongoTemplate mongoTemplate;
    private final RabbitTemplate rabbitTemplate;
    private final Gson gson;
    private final RabbitAdmin rabbitAdmin;

    public ChatReadDto convertChat(Message message) {
        String str = new String(message.getBody());
        ChatReadDto chat = gson.fromJson(str, ChatReadDto.class);
        chat.setReadTime(LocalDateTime.now().toString());
        return chat;
    }

    public void readMessage(ChatReadDto chatReadDto) {

        List<ChatRoomDetail> chatList = mongoTemplate.find(Query.query(Criteria.where("created_dt").lte(chatReadDto.getReadTime()).and("readCnt").is(1)
                .and("roomId").is(chatReadDto.getRoomId())), ChatRoomDetail.class);

        for (ChatRoomDetail chat : chatList) {
            if (!chatReadDto.getReadUserId().equals(chat.getFromUserId())) {
                /* 상대방이 수신 시 */
                if (chat.getReadCnt() > 0) {
                    chat.setReadCnt(0);
                    mongoTemplate.save(chat);

                    log.info("read user {}", chatReadDto.getReadUserId());

                    log.info("roomId {}", chat.getRoomId());
                    log.info("userId {}", chat.getToUserId());
                    log.info("msg {}", chat.getMsg());

                    StringBuilder sb = new StringBuilder()
                            .append("room.")
                            .append(chat.getRoomId())
                            .append(".")
                            .append(chat.getFromUserId()); // 상대방에게 보내기
//                            .append(chat.getToUserId()); // 상대방에게 보내기

                    log.info("sb {}", sb);

                    ChatReadResponseDto chatReadResponseDto = ChatReadResponseDto.builder().msgId(chat.getId()).roomId(chat.getRoomId())
                            .readCnt(chat.getReadCnt()).msg(chat.getMsg()).build();

                    Message msg = MessageBuilder.withBody(gson.toJson(chatReadResponseDto).getBytes()).build();

                    if (getReadQueueInfo(chat.getRoomId(), chat.getFromUserId()) != null) {
                        QueueInformation queueInformation = getReadQueueInfo(chat.getRoomId(), chat.getFromUserId());
                        queueInformation.getName();
//                        rabbitTemplate.convertAndSend("read.exchange", sb.toString(), msg);
                        rabbitTemplate.send("read.exchange", sb.toString(), msg);
                    }
                }
            }
        }
    }

    public QueueInformation getReadQueueInfo(String roomId, String userId) {
        String queueName = String.format("read.queue.%s.%s", roomId, userId);
        return rabbitAdmin.getQueueInfo(queueName);
    }


}
