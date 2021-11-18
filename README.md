# 🧩blockAi🧩

## 👀 프로젝트 소개
공인인증서를 사용한 본인인증이 번거롭진 않으신가요?

간편 본인인증을 하며 개인정보 유출에 대한 걱정 하신 적 있으신가요?

걱정마세요. 

블록체인과 AI를 활용한 본인인증 솔루션, `blockAi` 가 도와드릴게요!


## ✨ 주요 기능
- DID(디지털 신분증) 발급
  - 가입시 문자인증으로 1차 본인인증
  - 사용자의 안면과 음성 데이터를 입력 
  - RSA 알고리즘으로 생체 데이터를 암호화
  - 암호화된 정보를 블록체인에 저장
  
- DID로 신분 인증
  - 전화번호로 1차 인증하여 DID를 조회
  - 인증 자의 안면 및 음성 데이터를 입력받음
  - 조회한 DID의 생체 정보와 대조하여 유사도 판별

## 🔍 페이지 상세


## 🖥️ 기술스택
<div>
<img src="https://img.shields.io/badge/Java-ED8B00?style=flat-square&logo=Java&logoColor=white"/> 
<img src="https://img.shields.io/badge/Spring-6ec45c?style=flat-square&logo=Spring&logoColor=white"/>
<img src="https://img.shields.io/badge/JPA-6DB33F?style=flat-square&logo=SpringBoot&logoColor=white"/>
<img src="https://img.shields.io/badge/MySQL-485899?style=flat-square&logo=MySQL&logoColor=white"/>
<img src="https://img.shields.io/badge/JavaScript-gray?style=flat-square&logo=JavaScript&logoColor=yellow"/> 
<img src="https://img.shields.io/badge/Vue-35495e?style=flat-square&logo=Vue.js&logoColor=4FC08D"/>
<img src="https://img.shields.io/badge/Solidity-C0C0C0?style=flat-square&logo=Solidity&logoColor=363636"/>
<img src="https://img.shields.io/badge/Ethereum-white?style=flat-square&logo=Ethereum&logoColor=3c3c3d"/>
<img src="https://img.shields.io/badge/Jenkins-D24939?style=flat-square&logo=Jenkins&logoColor=white"/>
<img src="https://img.shields.io/badge/AWS EC2-232F3E?style=flat-square&logo=AmazonAWS&logoColor=FF9900"/>
<img src="https://img.shields.io/badge/AWS S3-232F3E?style=flat-square&logo=AmazonAWS&logoColor=D24939"/>
<img src="https://img.shields.io/badge/NGINX-CFE1D0?style=flat-square&logo=NGINX&logoColor=009639"/>
<img src="https://img.shields.io/badge/Docker-DAE4EB?style=flat-square&logo=Docker&logoColor=2496ED"/>
</div>
<br>

**Backend** `Java` `SpringBoot` `JPA`

**Frontend**  `Javascript` `Vue.js`

**Blockchain** `Solidity` `Remix`

**Infra** `Jenkins` `AWS EC2` `AWS S3` `NGINX` `Docker`


## 프로젝트 아키텍처
![아키텍쳐](https://user-images.githubusercontent.com/43156636/142373145-c76bc37f-c8d4-49c3-86b9-435e8c4aca4c.PNG)

## 💞DID you know 팀원을 소개합니다!
---

|**권기웅**|**김예슬**|**박세령**|**이지수**|**이한울**|**임영찬**|
| :--: | :--: | :--: | :--: | :--: | :--: |
| <img src="https://user-images.githubusercontent.com/31243566/140014940-24863fd4-b715-43c8-ba5a-334493d6f532.png" width="100px;"> | <img src="https://user-images.githubusercontent.com/31243566/140015068-f8f4017b-2f63-4e99-890f-ee808f1d1762.jpg" width="100px;"> | <img src="https://user-images.githubusercontent.com/31243566/140033345-5f960686-0ed5-4e77-9eed-42a8ef5a7647.png" width="100px;"> | <img src="https://user-images.githubusercontent.com/31243566/140014690-e3cab2ab-3594-41ed-a272-938003d20b16.jpg" width="100px;"> | <img src="https://user-images.githubusercontent.com/31243566/140014989-9c450e1e-fc8e-42b7-8238-e2dcff2ac602.jpg" width="100px;"> | <img src="https://user-images.githubusercontent.com/43156636/140012993-ad47fc3a-20b3-4615-9cd4-74018f4b80b2.jpg" width="100px;"> |
|FE|BE & Devops|BE & AI|BE & AI|BE & Blockchain|BE & Blockchain|
|[@kiung22](https://github.com/kiung22)| [@qlcid](https://github.com/qlcid) | [@ParkSeRyeong](https://github.com/ParkSeRyeong) | [@zsoozsoo](https://github.com/zsoozsoo) | [@hanull](https://github.com/hanull) | [@lim8662](https://github.com/lim8662) |

## ⛓ 포트 메뉴얼
| **PORT** |                        **이름**                         |
| :------: | :-----------------------------------------------------: |
|   443    |                          HTTPS                          |
|    80    |       HTTP - HTTPS로 리다이렉트(프론트로 리다이렉트)     |
|   3306   |                          MySQL                          |
|   8081   |                         Jenkins                         |
|   8080   |              Spring boot Docker Container               |
|   3000   |               Vue, NGINX Docker Container               |

## 🙆‍♀️ Git 컨벤션
```
feat:     새로운 기능을 추가할 경우
fix:      버그를 고친 경우
style:    코드 포맷 변경, 간단한 수정, 코드 변경이 없는 경우
refactor: 프로덕션 코드 리팩토링
docs:     문서를 수정한 경우(ex> Swagger)
test:     테스트 코드
rename:   파일 혹은 폴더명 수정 및 이동
remove:   파일 삭제
chore:    빌드 업무 수정(ex> dependency 추가)
```
```
ex) feat: 회원가입 API 구현
```

## 🙆‍♀️ Git Flow 브랜치 전략
```
master: 제품으로 출시될 수 있는 브랜치
develop: 다음 출시 버전을 개발하는 브랜치
feature: 기능 개발하는 브랜치
release: 이번 출시 버전을 준비하는 브랜치
hotfix: 출시 버전에서 발생한 버그를 수정하는 브랜치
```
```
feature/{keyword} 

ex) feature-fe/loginRestApi
```

## 추가 정보
[👉팀 노션👈](https://www.notion.so/86346ce94a8e4074a34f901ed97f9c0d)

