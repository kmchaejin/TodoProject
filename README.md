# TodoProject 
RESTful API를 구현한 Spring 프로젝트입니다.

<br>

## 구현된 기능
### 할 일 생성 <br>
할 일의 내용, 작성자 이름, 비밀번호를 서버에 전달 <br>
비밀번호는 필수값으로, 입력하지 않으면 에러 메시지를 응답 <br>

### 할 일 전체 조회
option 1. 특정 날짜에 수정된 할 일 전체 조회 <br>
option 2. 특정 이름의 사용자가 작성한 할 일 전체 조회 <br>
option 3. 특정 날짜에 특정 이름의 사용자가 작성한 할 일 전체 조회 <br>
option 4. 모든 할 일 전체 조회 <br>

### 할 일 단건 조회
할 일의 id를 서버에 전달 <br>
존재하지 않는 id인 경우 에러 메시지를 응답 <br>

### 할 일 수정
할 일의 id와 수정 내용, 비밀번호를 서버에 전달 <br>
작성자 이름과 할 일 내용만 수정 가능 <br>
존재하지 않는 id인 경우 에러 메시지를 응답 <br>
해당 할 일을 생성할 때 입력한 비밀번호와 일치하지 않으면 에러 메시지를 응답 <br>

### 할 일 삭제
할 일의 id와 비밀번호를 서버에 전달 <br>
존재하지 않는 id인 경우 에러 메시지를 응답 <br>
해당 할 일을 생성할 때 입력한 비밀번호와 일치하지 않으면 에러 메시지를 응답 <br>

<br>

## API 명세서
https://documenter.getpostman.com/view/43160193/2sAYkKJxoc
<br><br>
![image](https://github.com/user-attachments/assets/cb329b66-2ffe-4c7f-baa4-872b8131bb47)

<br>

## ERD
![image](https://github.com/user-attachments/assets/fbbf9f89-d6ef-4e9a-9154-51087c9bd743)

<br>

## dependencies
implementation 'mysql:mysql-connector-java:8.0.28' <br>
implementation 'org.springframework.boot:spring-boot-starter-data-jdbc' <br>
implementation 'org.springframework.boot:spring-boot-starter-validation:3.3.5' <br>
implementation 'org.springframework.boot:spring-boot-starter-thymeleaf' <br>
implementation 'org.springframework.boot:spring-boot-starter-web' <br>
compileOnly 'org.projectlombok:lombok' <br>
annotationProcessor 'org.projectlombok:lombok' <br>
testImplementation 'org.springframework.boot:spring-boot-starter-test' <br>
testRuntimeOnly 'org.junit.platform:junit-platform-launcher' <br>
