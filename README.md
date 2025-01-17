<h1 align="center">KNU Remodel<br><strong>강남대학교 홈페이지 재구성</strong></h1>
<h3 align="center">강민주, 강명균, 서동현, 서은빈, 유우열, 이인재</h3>

<p align="center">
<img src="https://github.com/user-attachments/assets/285a7b09-ef4d-4578-b048-573d132d33b3"  width="850" height="480"  />
</p>


---
## 1. 프로젝트 개요

**목적**
- 학생 중심의 **접근성** 향상
- 참여율 증대 및 효율적 **개인화** 기능 제공
- **가시성** 확보를 위한 UI/UI 개선

**목표**
- 프로그램 **참여율** 증가
- 충분한 **경험과 교류**의 기회 제공
- 개인적인 성장 촉진 및 **교내 활동 활성화**

---


## 2. 개발 기간
<h3 align="center">24/03/01 ~ 24/10/31</h3>

<p align="center">
<img src="https://github.com/user-attachments/assets/c8772bd4-6ffc-4704-b734-dd6d7b03eba9"  width="800" height="480"  />
</p>




## 3. 팀원 소개 및 역할
- 공통
DB구조 설계, 디자인 설계, API 설계

- **강민주**


  기능 : 디자인, 화면 구현 & 스타일링

- **강명균**


  기능 : 화면 구현, 페이징 설정

- **서동현**


  기능 : 로그인, 데이터베이스 구축(크롤링, 랭킹, 식단표, 학사일정)
데이터베이스, 서버랑 연결 코딩(식단표, 크롤링, 랭킹, 학사일정)
  
- **서은빈**


  기능 : 데이터 관리(백업포함) 자동화 코딩(식단표, 크롤링, 랭킹, 학사일정)
데이터 전처리(수집된 데이터를 정제하고 필요한 형식으로 변환) 구축(식단표, 크롤링, 학사일정)

  
- **유우열**


  기능 : 분야별 크롤링, 검색창, DB실시간 모니터링 코딩(식단표, 크롤링, 랭킹, 학사일정)
크롤링 엔진 개발

- **이인재**


  기능 : 전반적인 코드 관리, 단과대 크롤링

---
## 4. 개발 환경

- **백엔드**: ![springboot](https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)<img src="https://img.shields.io/badge/3.1.4-515151?style=for-the-badge"> ![MySQL](https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white)<img src="https://img.shields.io/badge/8.0.33-515151?style=for-the-badge"> ![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ%20IDEA-EE4C2C?style=for-the-badge&logo=IntelliJ%20IDEA&logoColor=white) <img src="https://img.shields.io/badge/java-%23ED8B00?style=for-the-badge&logo=openjdk&logoColor=white"><img src="https://img.shields.io/badge/17-515151?style=for-the-badge">

- **프론트엔드**: ![React](https://img.shields.io/badge/React-61DAFB?style=for-the-badge&logo=react&logoColor=black)<img src="https://img.shields.io/badge/18.3.1-515151?style=for-the-badge"> ![Node.js](https://img.shields.io/badge/Node.js-339933?style=for-the-badge&logo=node.js&logoColor=white)

- **협업 툴**: ![GitHub](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white) ![Notion](https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=notion&logoColor=white) <a href="https://www.erdcloud.com/d/8Lf2f63JR7jpDJMqQ" target="_blank">
    <img src="https://img.shields.io/badge/ERDCloud-0072FF?style=for-the-badge&logo=erdcloud&logoColor=white" alt="ERDCloud" />
</a> 클릭!<a href="https://gitmind.com/app/docs/md3av2n5" target="_blank">
    <img src="https://img.shields.io/badge/GitMind-FF7F50?style=for-the-badge&logo=git&logoColor=white" alt="GitMind" />
</a> 클릭!


---
## 5. 프로젝트 설계

**프론트엔드**

![Essay Structure](https://github.com/user-attachments/assets/55038de5-e68c-4e69-a4b6-0daa091de3a4)

**백엔드**
![image01](https://github.com/user-attachments/assets/c1f26116-29be-40f9-ab52-ec86ba10aea4)
![image02](https://github.com/user-attachments/assets/4ec10e29-5c80-4a3c-a7b3-0ad8709a7fa6)

---
## 5. 차별점 
- **ㅇㅇ** : 원하는 날짜, 시간대에 주문. 예약 가능


<p align="center">
<img src="https://github.com/user-attachments/assets/9c9b6215-4126-45da-9a26-87bf9c99273c"  width="850" height="430"  />
</p>



- **ㅇ** : 여러 업체의 메뉴 담기 가능

<p align="center">
<img src="https://github.com/user-attachments/assets/fa14e3d7-9942-47e4-9a2d-8708bac6525f"  width="400" height="480"  />
</p>




- **ㅇㅇ** : 리뷰 리스트 기반 AI 선택지 반환 

<p align="center">
<img src="https://github.com/user-attachments/assets/a6ee9b51-c6fc-4750-9ffa-f79ee00b2dbe"  width="400" height="480"  />
</p>

---  

## 6. 설치 방법


- **프론트엔드**
```sh
npx create-next-app@latest //next.js 모듈 다운
npm install
npm run dev

```

- **로그인**
```sh
spring.security.oauth2.client.provider.kakao.authorization-uri=https://kauth.kakao.com/oauth/authorize
spring.security.oauth2.client.provider.kakao.token-uri=https://kauth.kakao.com/oauth/token
spring.security.oauth2.client.provider.kakao.user-info-uri=https://kapi.kakao.com/v2/user/me
spring.security.oauth2.client.provider.kakao.user-name-attribute=id
spring.security.oauth2.client.registration.kakao.client-id='your id'
spring.security.oauth2.client.registration.kakao.client-secret='your secretcode'
spring.security.oauth2.client.registration.kakao.client-authentication-method=client_secret_post
spring.security.oauth2.client.registration.kakao.redirect-uri='your uri'
spring.security.oauth2.client.registration.kakao.authorization-grant-type=authorization_code
spring.security.oauth2.client.registration.kakao.client-name=kakao
spring.security.oauth2.client.registration.kakao.scope=profile_nickname
```
- **AI**
```sh
chatgpt.api-key='your key'
openai.model=gpt-3.5-turbo
```

- **DB**
```sh
wspring.application.name=modoProject
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.datasource.url= 'your url'
useSSL=false&useUnicode=true&serverTimezone=Asia/Seoul&allowPublicKeyRetrieval=true
spring.datasource.username= 'your username'
spring.datasource.password= 'your password'

spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.properties.hibernate.dialect.storage_engine=innodb
spring.jpa.open-in-view=false
server.servlet.encoding.force-response=true

```
```sh
spring.servlet.multipart.max-file-size=100MB
spring.servlet.multipart.max-request-size=100MB
```



## 7. 프로젝트 후기

- **ㅇ**  
"ㅇㅇㅇ."  

- **ㅇ**  
"ㅇㅇㅇ"

- **ㅇ**  
"ㅇㅇㅇ"

---


  
