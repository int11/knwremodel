<p align="right">
  <a href="https://github.com/mimo626/knuKproject">
    <img src="https://img.shields.io/badge/프론트%20페이지%20바로가기-000000?style=for-the-badge&logo=github&logoColor=white" alt="프론트 페이지 바로가기">
  </a>
</p>


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
- **가시성** 확보를 위한 UI/UX 개선

**목표**
- 프로그램 **참여율** 증가
- 충분한 **경험과 교류**의 기회 제공
- 개인적인 성장 촉진 및 **교내 활동 활성화**

---


## 2. 개발 기간
<h3 align="center">23/09/01 ~ 23/12/3</h3>

<p align="center">
<img src="https://github.com/user-attachments/assets/d19cdb4d-44f2-4264-afbc-e660ab1e277d"  width="1000" height="600"  />
</p>






## 3. 팀원 소개 및 역할
프론트 
  - **강민주**


    디자인, 화면 구현 & 스타일링

  - **강명균**


    화면 구현, 페이징 설정

백엔드
  - **공통**


    RBAC, API 연동


  - **서동현**


    소셜 로그인 개발,  개발자 공지사항 개발, 학생인증 기능 개발, 

  
  - **서은빈**


    데이터 관리(백업포함) 랭킹 시스템(HOT PROGRAM) 구축, 학사일정 크롤링 개발, 좋아요 기능 개발, 마이페이지 개발(좋아요 내역, 댓글 내역)


  
  - **유우열**


    분야별 크롤링, 검색 기능(실시간 검색어 순위, 최근 검색어), 댓글 기능,  게시글 조회수 기능, 엔티티 연관관계 매핑


  - **이인재**


    전반적인 코드 관리, 단과대 크롤링

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

<details>
  <summary>구조 확인</summary>
  
  ![Essay Structure](https://github.com/user-attachments/assets/55038de5-e68c-4e69-a4b6-0daa091de3a4)
  
</details>

**백엔드**

<details>
  <summary>구조 확인</summary>
  
  ![image01](https://github.com/user-attachments/assets/c1f26116-29be-40f9-ab52-ec86ba10aea4)
  
  ![image02](https://github.com/user-attachments/assets/4ec10e29-5c80-4a3c-a7b3-0ad8709a7fa6)
  
</details>

---

## 6. 기존 사이트와의 차별점 

  <summary>가시성 높은 디자인</summary>
  
  
  Styled Components와 @Media Query를사용하여 반응형 디자인 구현. 다양한 화면 크기에서 자동으로 레이아웃이 조정되도록 설계
  


  <summary>학생들의 관심을 유도할 수 있는 정보 제공</summary>
  
사용자의 전공, 학년, 게시물의 인기도 데이터를 기반으로 맞춤형 게시물을 추천


<div style="display: flex; justify-content: space-between;">
<img src="https://github.com/user-attachments/assets/c394ec5e-719d-445d-ba4d-6c51ad808571" width="350" />
<img src="https://github.com/user-attachments/assets/10ee6129-7df6-4125-bed3-6a4ff65a3094" width="350" />
</div>



  <summary>프로그램 동기 부여</summary>
  
- Scheduling을 통한 데이터 크롤링과 재분류를 통해 사용자에게 가공된 데이터 제공. 프로그램에 대한 접근성 강화


<div style="display: flex; justify-content: space-between;">
  <img src="https://github.com/user-attachments/assets/e93422a5-dd66-4b79-b3f7-b7b4fcd8ef8f"  width="350"   />
  <img src="https://github.com/user-attachments/assets/68921a7e-30d7-488d-a361-1256c9f16c09"  width="350" />
</div>






  <summary>개인화 홈페이지 제작</summary>
  
- Instargram 기능 중 일부를 벤치마킹하여 기존 학교 웹서비스와 통합. 자신이 쓴 댓글과 좋아요를 누른 게시물을 한 눈에 모아 조회 가능

  
<p align="center">  
  <img src="https://github.com/user-attachments/assets/9d208a20-b57f-4ad4-b78f-a5f5a1f67e0d"  width="350"   />

</p>




---  


## 7. 설치 방법


- **프론트엔드**
```sh
npx create-next-app@latest //next.js 모듈 다운
npm install
npm run dev

```

- **로그인**
```sh
spring.security.oauth2.client.registration.google.client-id='your key'
spring.security.oauth2.client.registration.google.client-secret='your key'
spring.security.oauth2.client.registration.google.scope=profile,email
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

- **SMTP**
```sh
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username='your email'
spring.mail.password='your secret'
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtp.timeout=5000
spring.mail.properties.mail.smtp.auth=true
```


---  

## 8. 프로젝트 후기

- **강민주**  
"팀장으로서의 역할이 어떤 것인지 깨달을 수 있었습니다. 그동안의 팀플과는 달리 처음으로 해본 프로젝트는 계획과 규칙을 정하는 것이 더 효율적이란 걸 몸소 느꼈고 그것을 잘 지키도록 이끄는 것이 팀장의 역할이었습니다. 처음엔 이를 잘 몰랐지만 점점 알게 되어 책임감있게 임하려고 노력했습니다. 왠만한 일이 아니면 최대한 매일 회의를 빠지지 않으려 노력했으며 화면 구현과 디자인에 많은 시간을 할애했습니다. 하지만 처음 써보는 문법를 공부하면서 구현을 하니 확실히 오랜 시간이 걸렸고 계획보다 낮은 진전의 결과가 나와 아쉽습니다. 이 경험을 첫 걸음으로 생각하고 남은 시간동안 발전하는 모습으로 성장하고 싶다고 느꼈습니다."  

- **서은빈**  
"프로젝트를 계획할 때 10주라는 기간이 충분할 것으로 예상했지만, 진행하다 보니 생각보다 많은 시간이 필요했습니다. 이 과정에서 여러 번의 수정과 오류를 경험하게 되었고, 기획한 내용보다는 조금 덜 진행되어 아쉬움이 남습니다. 그러나 이번 학기 동안 지속적인 협업을 통해 팀원들과 함께 작업한 것에 보람을 느낍니다. 문제에 직면했을 때 어려움도 있었지만, 함께 문제를 해결하고 소통하는 과정 자체가 즐거웠습니다"

- **서동현**  
"프로젝트로 진행하는 웹개발은 처음이라 많이 서툴렀지만 많은 것을 배웠습니다. 커뮤니케이션이 같이 프로젝트를 진행함에 있어서 가장 중요하다고 느꼈고, 배움은 끝이 없다고 느꼈습니다. 저의 부족한 부분을 이번 프로젝트를 통해 성장할 수 있었고 앞으로 어떻게 나아가야 할지 확신이 생기는 프로젝트였습니다"

- **우우열**  
"처음 도전한 웹 개발 프로젝트는 많은 배움을 주었습니다. 프로젝트를 진행하며 예상하지 못한 어려움들을 마주했지만, 팀원들과의 협력을 통해 하나씩 해결해 나갔습니다. 이러한 과정에서 개발뿐만 아니라 협업의 중요성과 의사소통 능력의 필요성을 깨달았습니다. 부족한 점을 돌아보며 앞으로 더 나은 모습으로 발전하고 싶습니다."

- **이인재**  
"이번 프로젝트는 저에게 웹 개발에 대한 기초를 다질 수 있는 좋은 기회였습니다. 처음 시도해보는 프로젝트였기에 서툰 부분도 많았지만, 팀원들과의 소통을 통해 문제를 해결해나갈 수 있었습니다. 특히, 협업 과정에서 서로의 아이디어를 공유하고 조율하는 것이 프로젝트의 성공에 얼마나 중요한지 느꼈습니다. 이 경험을 바탕으로 앞으로 더욱 성장할 수 있도록 노력할 것입니다."

- **강명균**  
"처음이라 낯설고 어려운 부분도 많았지만, 팀원들과 함께 노력하며 많은 것을 배울 수 있었습니다. 특히, 문제 해결 과정에서 얻은 경험은 앞으로의 개발 과정에서 큰 자산이 될 것입니다. 이 프로젝트를 통해 제 자신의 가능성을 다시 한번 확인하며 성장할 수 있었습니다."

---

  
