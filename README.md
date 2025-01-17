<h1 align="center">가나다<br><strong>가나다라</strong></h1>
<h3 align="center">가나다라</h3>

<p align="center">
<img src="https://github.com/user-attachments/assets/47d7b689-2a63-44c4-9185-42a33bfe6667"  width="850" height="480"  />
</p>


---
## 1. 프로젝트 개요

**📊 시장 분석**
- ㅇ
- ㅇ
- ㅇ


**💡 필요성**  
- ㅇ
- ㅇ
- ㅇ


**🚀 기대 효과**  
- ㅇ
- ㅇ 

---


## 2. 개발 기간
<h3 align="center">24/03/01 ~ 24/10/31</h3>

<p align="center">
<img src="https://github.com/user-attachments/assets/c8772bd4-6ffc-4704-b734-dd6d7b03eba9"  width="800" height="480"  />
</p>




## 3. 팀원 소개 및 역할
- 공통
DB구조 설계, 디자인 설계, API 설계


- **ㅇ**: 전체적인 시스템 구성 및 API 설계, 결제 시스템 주도


  기능 : 결제, 장바구니, 게시판(이벤트 ,공지사항, QnA), 점주 페이지(주문내역, 휴무일, 메뉴 관리, 가게 승인 페이지, 가게등록 페이지
  
- **ㅇ**:


  기능 : AI, 마이페이지(주소지, 즐겨찾기, 장바구니, 결제내역), 점주 페이지(리뷰 관리,가게 정보), 스토어 검색 기능, 즐겨찾기, 카카오 로그인, 댓글 기능, 

  
- **ㅇㅇ**: 사용자 인터페이스 개발 및 디자인 최적화, UI/UX 개선 주도


  기능 :  가게 승인 페이지, 가게등록 페이지
---
## 4. 개발 환경

- **백엔드**: ![springboot](https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)<img src="https://img.shields.io/badge/3.1.4-515151?style=for-the-badge"> ![MySQL](https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white)<img src="https://img.shields.io/badge/8.0.33-515151?style=for-the-badge"> ![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ%20IDEA-EE4C2C?style=for-the-badge&logo=IntelliJ%20IDEA&logoColor=white) <img src="https://img.shields.io/badge/java-%23ED8B00?style=for-the-badge&logo=openjdk&logoColor=white"><img src="https://img.shields.io/badge/17-515151?style=for-the-badge">

- **프론트엔드**: ![Next.js](https://img.shields.io/badge/Next.js-000000?style=for-the-badge&logo=next.js&logoColor=white)<img src="https://img.shields.io/badge/14.2.11-515151?style=for-the-badge"> ![React](https://img.shields.io/badge/React-61DAFB?style=for-the-badge&logo=react&logoColor=black)<img src="https://img.shields.io/badge/18.3.1-515151?style=for-the-badge"> ![Node.js](https://img.shields.io/badge/Node.js-339933?style=for-the-badge&logo=node.js&logoColor=white)

- **협업 툴**: ![GitHub](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white) ![Notion](https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=notion&logoColor=white) <a href="https://www.erdcloud.com/d/8Lf2f63JR7jpDJMqQ" target="_blank">
    <img src="https://img.shields.io/badge/ERDCloud-0072FF?style=for-the-badge&logo=erdcloud&logoColor=white" alt="ERDCloud" />
</a> 클릭!


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


  
