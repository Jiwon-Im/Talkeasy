# README


## Talkeasy

---

```jsx
AAC를 활용하여 뇌병변장애를 가진 피보호자와 보호자의 의사소통 채팅 어플
```

프로젝트 소개

---

### 개요

- 삼성청년소프트웨어아카데미(SSAFY) 2학기 자율프로젝트에서 만든 프로젝트
- 2023.04.10  ~ 2023.05.19 (6주)
- 총 6명 ( 모바일 2명, 백엔드 4명)으로 구성

### 주제

- AAC(보완 · 대체 의사소통)을 활용한 안드로이드 채팅 앱

### 아키텍처

![TalkEasy](https://github.com/skaguswl/algorithm/assets/105181946/389761db-d52e-44e3-82f8-eb2c4abbb900)


## 개발환경

### 형상 관리

---

- **GITLAB**

### 이슈 관리

---

- **JIRA**
    - 매주 목표량을 설정하여 스프린트 진행
    - In-Progress 및 Done으로 각자 지금 하고있는 업무와 완료된 업무를 공유

### 소통 관리

---

- **Mattermost**
    - 프로젝트 자료 공유
    - 의견 작성
    - 미완료 업무 공유
- **Notion**
    - 요구사항 명세서 작성 및 공유
    - API 명세서 작성 및 공유
    - 컨벤션 문서 관리
- **Slack**
    - 프로젝트 자료 공유
    - gitlab 연동
- **Range**
    - 하루 목표량을 설정하여 스크럼에 반영

### UI/UX

---

- **Figma**

### IDE

---

- **IntelliJ IDEA 2022.3.2**
- **PyCharm** **2022.1.4**
- **안드로이드 스튜디오 버전**

### DATABASE

---

- **MongoDB Community Server**
    - **windows 6.0.6**
- **Mongo db Compass 1.36.2**
- **PostgreSQL 15.2**
- **DBeaver 23.0.3**

### SERVER

---

- **AWS EC2**
    - **UBUNTU 20.04 LTS**
    - **MobaXterm_Personal_22.3.exe**
    - **DOCKER 20.10.23**
    - **NGINX 1.18.0**
    - **S3**

### 협업툴

---

- **SWAGGER 2.9.2**
- **POSTMAN for Windows Version 10.9.4**

### BACK-END

---

- **Java Open-JDK azul 11**
- **SpringBoot Gradle 2.7.7**
    - **Spring Data JPA**
    - **Lombok**
    - **Swagger 2.9.2**
- **Python 3.10.9**

### Android

---

- 

## 주요기능

### 로그인

- OAuth2.0을 활용한 카카오 로그인

### AAC

- 단어 선택을 통해 문장 완성하여 TTS를 통해 주변 사람과 소통할 수 있음
- GPT Open AI를 활용한 문장 완성

### 채팅

- 단어 선택을 통해 만든 문장을 보호자에게 보내 소통할 수 있음.
- RabbitMQ를 활용해 실시간 채팅 관리

### SOS 서비스

- Stomp를 활용하여 피보호자가 SOS 요청을 할 경우 보호자에게 알람이 전송됨.

  

### 위치 정보 분석 서비스

- kafka, postgis를 활용해 하루 / 일주일 단위로 방문했던 위치 관련 정보 제공
