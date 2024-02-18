

# Dev wiki api 프로젝트 설명 문서 .

- 각 도메인 별 메모사항, 리서치 자료는 projectDocs 내부에 있습니다.


# 목차 

- [서비스에 대해](#서비스에-대해서)
- [인증](#인증)
   - [추가 정리 내용](#추가-정리-내용)
- [회원 가입 및 로그인 플로우](#회원-가입-및-로그인-플로우)
   - [회원 가입 플로우](#회원-가입-플로우)
   - [로그인 플로우](#로그인-플로우)
- [CSRF 방어](#CSRF-방어)
- [공동 편집](#공동-편집)
- [검색](#검색)


# 서비스에 대해서 

devwiki는 백엔드 2명 , 디자이너 1명으로 이루어진 팀으로 진행하는 서비스 입니다 .


dev wiki 는 개발자들을 위한 정보검색, 커뮤니티 사이트 입니다. 주요 컨텐츠로는 원문 문서(공식문서) 에 대한 공동 번역과 질문 게시판이 있습니다.

그리고 모든 리소스는 ES full text search 를 통한 검색이 가능하도록 기획 중에 있습니다 .

운영 계획은 3월 말까지 지인들 위주로 작게 오픈을 시작할 예정입니다. 
(지금은 제작중인 서비스입니다 .)

---

대략적인 기술적 고려 사항은 아래와 같습니다.

- 검색에서 인기도, 유저 별 categorization 반영하기.
- 구글, 네이버 검색 노출을 위한 NEXT Js 채용.
- 공동 수정 리소스에 대한 따닥 방지 
- 게시판 운영을 위한 보안 정책 설정 ( session based login , csrf)
- 검색, 인증에 대한 서버 분리. 
- port and adapter 패턴 적용과 adapter 위주의 slicing test .
- k8s native 한 운영환경 구축. 
- DB( mysql,es, redis ) k8s 에서 운영 . 


---

### 서비스 컨셉 이미지 

![img](/projectDocs/images/컨셉이미지.png)

----


# 인증

### 추가 정리 내용 

1. 이 서비스에서 JWT 쓰면 안되는 이유 : [이 서비스에서 JWT를 사용하지 않는 이유](/projectDocs/user_and_auth/whyDontUseJWT.md)
2. csrf 정리 및  방어 대책. [csrf공격과방어](/projectDocs/user_and_auth/CSRF공격과대응.md)


## 서비스의 특징 고려하기 (인증)

프로젝트의 특징을 고려한 후 로그인 및 인증 방식을 결정하기 전에 다음과 같은 서비스의 특징을 고려해봤습니다.

1. **이 서비스는 개발자 커뮤니티 사이트입니다.**
    - 대상자가 개발자이므로 괜히 때려보는 ? 사람이 있을 수 있습니다.  잘 알려진 공격 방법 (CSRF, XSS) 방어가 필요합니다.

2. **주요 기능은 "공동 번역"입니다.**
    - 다른 사용자가 작성한 리소스를 제3자가 수정할 수 있는 구조이므로 JWT 처럼 기간을 주고 무조건 적으로 접근을 허용하면 안 됩니다.
    - 서버 측에서 특정 사용자의 접근을 막아야 할 가능성을 고려해야 합니다.
    

3. **개발자가 사용하는 서비스이므로 로그인은 GitHub 를 통한 Oauth** 기반의 회원가입, 로그인을 제공합니다.

## 결정 사항

위와 같은 이유로 다음 결정 사항을 채택했습니다.

1. 로그인의 유지: 세션 기반 로그인
2. 세션 스토리지: Redis
3. 계정 획득 및 관리: GitHub OAuth 코드 및 고유 ID를 기반으로 사용자를 분류


# 회원 가입 및 로그인 플로우

아래는 회원 가입 및 로그인 프로세스에 대한 설명입니다.
## 회원 가입 플로우

![img](/projectDocs/images/회원가입.png)



1. 사용자가 `devwiki.com/join`에서 GitHub OAuth 가입 버튼을 클릭합니다.
2. GitHub OAuth 로그인 UI에서 GitHub ID와 암호를 입력합니다.
3. 인증이 성공하면 코드(code) 값과 함께 `devwiki.com/callbackJoin`으로 리디렉션됩니다.
4. `devwiki.com/callbackJoin`에서 HTTP POST를 사용하여 `devwiki.api/preCheckJoin`으로 GitHub 코드를 전송합니다.
5. `devwiki.api`에서 GitHub 코드를 기반으로 회원 가입 가능 여부를 검증합니다.
6. 가입 가능하면 `devwiki.com/callbackJoin`으로 `success: true`를 반환합니다.
7. 사용자는 `devwiki.com/join/info`에서 닉네임 및 필요한 사용자 정보를 입력하고 HTTP POST로 `devwiki.api/join`으로 필요한 정보를 전송합니다.

## 로그인 플로우

회원 가입과 마찬가지로, 깃허브 oauth 인증에 성공하면, 해당 유저가 로그인이 가능한지 검증 한 후, 로그인 세션과 CSRF 토큰을 생성합니다. 유저에게는 JSESSIONID 는 쿠키로, CSRF token 은 바디에 넣어서 보내줍니다.

![img](/projectDocs/images/로그인.png)


# 로그인 회원가입 플로우 정리.


## 가입 가능 여부 확인

1. oauth Code 값으로 access token 확득
2. access Token 으로 유저 정보 획득
3. 해당 정보의 유저가 DB 에 있는지 확인.




![img](/projectDocs/images/GithubUserJoinPreCheckService.png)


### 회원 가입

1. access token 으로 유저정보 획득
2. githubUser 테이블에 유저정보 등록 가능한지 확인
3. usermeta table 에 유저 정보 등록 가능한지 확인 .
4. 저장 .

![img](/projectDocs/images/GithubUserJoinService.png)

### 로그인
1. oauth Code 값으로 access token 확득
2. access Token 으로 유저 정보 획득
3. 해당 정보의 유저가 DB 에 있는지 확인.
4. (2) 의 정보로 유저 메타 정보 쿼리
5. 유저가 로그인 가능한 유저인지 확인.
6. 세션 & csrf 토큰 생성.

![img](/projectDocs/images/GithubLoginService.png)


# CSRF 방어

security config 와 CSRF 대응에 대한 커스텀 필터, 정책은 아래 링크에 있습니다. 

csrf 정리 및  방어 대책: [devwiki에서 csrf 막기](/projectDocs/user_and_auth/devwiki에서CSRF막기.md)

# 공동 편집

### 공동 편집 플로우 .
![img](/projectDocs/images/번역문서_수정_새버전_생성.png)


### redis 문서 id채번 과 동시성 테스트 
[Id 채번에 대한 따닥 막기와 테스트](/projectDocs/concurrency/공동수정_따닥_막기.md)

# 댓글과 대댓글

![img](/projectDocs/images/대댓글_완성_샘플.png)

## 삽질과 테스트 
[대댓글 순서유지 알고리즘 정리와 테스트 ](/projectDocs/comment/대댓글_구현.md)

# 검색

### 검색에 인기도 반영하기 

