package com.talkeasy.server.domain.aac;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("aac_category")
@Data
@Builder
public class AACCategory {
    @Id
    private String id;
    private String title; // 카테고리 이름
}
