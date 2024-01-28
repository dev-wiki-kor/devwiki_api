

# CSRF 공격

웹 보안에 있어 CSRF(Cross-Site Request Forgery)는 중요한 공격 유형 중 하나입니다. 이해하기 위해서는 먼저 브라우저의 보안적 특징들에 대한 배경 지식이 필요합니다.


### 배경 지식: 브라우저의 보안적 특징

####  Cookie 는 Tab Thing 이 아닌 Browser Thing

브라우저는 쿠키를 탭 단위가 아닌, 전체 브라우저 차원에서 관리합니다.
이런 특징은 아래와 같은 편의성을 제공합니다 .

1. 크롬 브라우저로 1번 탭에서 A.com 에 접속한 유저.
2. A.com 에 로그인을 해서 JSESSIONID 에 세션 키값을 받았다 !
3. 별도의 2번 탭을 하나 더 연다음에, A.com 에 접속을 해도 JSESSION ID 가 전송이 되고, 로그인은 유지가 된다

이런 특성이 유저 입장에서 편리해지는 것은 맞지만, CSRF 공격의 원인이 되는 요소가 됩니다.
이런 보안상 취약점 때문에 쿠키에 **SAME-SITE, HTTPONLY** 같은 옵션을 줄 수 있습니다.

참고한 링크를 올립니다
https://security.stackexchange.com/questions/161111/why-dont-browsers-restrict-cookies-per-tab-to-combat-csrf-attacks

<br></br>


#### HTTPS

HTTPS 통신은 헤더와 바디 데이터를 모두 암호화합니다. 라우팅 과정에서는 4계층의 IP 주소를 기반으로 이루어지기 때문에, 암호화된 상태에서도 데이터는 올바른 목적지로 전송됩니다. HTTPS 통신 과정은 대략 다음과 같습니다

	1) DNS 조회를 통해 호스트의 IP 주소를 확보합니다.
	2) SSL/TLS 핸드셰이크를 통해 서버와 클라이언트 간의 암호화된 연결을 설정합니다.
	3) 브라우저가 송신 전 본문 ( header +  body ) 암호화
	4) os 의 tcp 스택에서 전송할 패킷 단위로 패킷 헤더 생성 ( 여기에 위의 4계층 주소가 있다.)
	5) 패킷 단위의 4계층 송신 
	6) -  3~2 계층에서의 송신 과정은 생략 - 
	7) 서버 호스트에서 해당 http 통신에서의 모든 패킷을 수신 후  ssl 복호화


결과적으로 클라이언트에서 암호화를 했어도 호스트를 잘 찾아갔고, 잘 복호화 될 수 있습니다.


<br></br>


####  Origin 헤더의 자동 추가

- **크로스-오리진 요청**: 브라우저는 크로스-오리진 HTTP 요청을 생성할 때 자동으로 `Origin` 헤더를 추가합니다. 이 헤더는 요청을 발생시킨 페이지의 프로토콜, 호스트, 포트를 포함합니다.
- **CORS 정책 검증**: `Origin` 헤더는 서버가 요청의 출처를 검증하고, CORS 정책에 따라 요청을 수락하거나 거부하는 데 사용됩니다.


일반적인 사용 방법은 , 백엔드에서 cors 정책을 통해  특정 주소가 아닌 origin 에서 인입되는 주소만 허용을 해주는 방식으로 CSRF 공격을 막을 수 있습니다 .

모두가 사용하는 RESR API 에서는 사용이 제한되기 때문에 보안적으로 사용하기 힘들 수 있습니다 .

cors 만으로는 보안에 대한 충분한 대처가 아닐 수 있습니다. 브라우저의 일부 요청은 origin 헤더를 추가하지 않을 수 있습니다.
예를 들어, html 의 \<img src= {image url}\> 태그의 이미지를 불러오는 get 요청은 origin 헤더를 포함하지 않습니다.



<br></br>
### Referer 헤더의 자동 추가

- **이전 페이지 정보**: 브라우저는 사용자가 다른 페이지로 이동할 때 자동으로 `Referer` 헤더를 추가하여, 현재 요청이 어떤 페이지에서 발생했는지를 서버에 알립니다.
- **보안 및 분석**: `Referer` 헤더는 보안 검증, 트래픽 분석, 사용자 탐색 경로 추적 등에 활용됩니다. 그러나 개인정보 보호를 위해 일부 브라우저나 설정에서는 이 헤더의 사용을 제한하거나 수정할 수 있습니다.


<br></br>


## CSRF 공격 방법

#### CSRF 시나리오

1. 사용자가 A.com에 로그인하여 세션 쿠키(JSESSIONID)를 받습니다.
2. 악의적인 사이트 B.com에는 사용자를 공격하기 위한 이미지 태그가 있습니다: `<img src="http://A.api/money/send?to=me&amount=10000"/>`.
3. A.com에 로그인한 상태에서 사용자가 B.com을 방문하면, B.com 페이지에 포함된 이미지 태그가 브라우저로 하여금 A.api에 요청을 보내게 합니다.
4. 이 요청에는 사용자의 세션 쿠키가 포함되어 있으므로, A.api는 요청을 합법적인 것으로 간주하고 처리합니다.

```html
 <img src = "http://A.api/money/send?to=me&amount=10000won/>
```
4. A.com 에 로그인 된 유저가 잘못된 이메일, 클릭유도 로 인해 B.com 을 실행하면 실제로 위의 요청이 수행된다.
5. CSRF 에 대한 캐어가 안된 api 라면 실제로 돈이 출금된다 .



<br></br>

#### 요청 헤더

```http
GET /money/send?to=me&amount=999999 
HTTP/1.1 Host: A.api 
ser-Agent: [사용자의 브라우저 정보] 
Accept: [MIME 타입] 
Accept-Language: [언어 설정] 
Accept-Encoding: [인코딩 설정] 
Content-Type: application/x-www-form-urlencoded 
Content-Length: [요청 본문의 길이] 
Referer: http://B.com/attack-page 
Cookie: [A.api 도메인에 대한 사용자의 쿠키] 
Origin: http://B.com Connection: keep-alive
```


<br></br>


## 실제 공격 예시 .

#### 프론트 엔드 공격자 코드

팁 : 아래는 간략하게 하기 위해 img & get 의 조합이지만 , 실제로는 post 요청을 더 많이 씁니다.
html 태그안에 공격자 코드를 두지 말고 클릭 이벤트에 axios 를 달아 두어서 직접 공격자  요청을 어느정도 변조하여 전송할 수 있음에 주의합니다.


```javascript

import "./App.css";
import logo from './logo.svg';

import './App.css';

  

function App() {

return (

	<div className="App">
	
		<header className="App-header">
		
			<img src={logo} className="App-logo" alt="logo" />
			
			<p>
			
				Edit <code>src/App.js</code> and save to reload.
				
			</p>
			
			<a
			
			className="App-link"
			
			href="https://reactjs.org"
			
			target="_blank"
			
			rel="noopener noreferrer"
			
>			
			
				<img
				
				src="http://localhost8080/hello/get"
				
				className="App-logo"
				
				alt="logo"
				
				/>
				
				Learn React
			
			</a>
		
		</header>
	
	</div>

);

}

  

export default App;
```



위의 코드는 create-react-app 을 통해 생성되는 기본 코드에,
"http://localhost:8080/hello/get" 경로에 대한 csrf 공격을 추가한 코드 입니다.



이런 페이지가 표시 되고 , 저 버튼을 누르면 host:8080/get 경로의 api 를 호출합니다 .

![img](/projectDocs/images/리액트앱.png)


#### 결과


![img](/projectDocs/images/csrf.png)



위의 이미지는 실제로 JSESSIONID 를 포함한 요청이 전송되어 응답을 받은 예시를 보여줍니다.

1. 이미지를 호출하는 get 요청
2. 200 ok : 실제로 요청이 수행 되었음
3. request header 의 Cookie 값 : localhost:8080 으로  전송 된 쿠키값
4. redis cli  : 서버 사이드에서 실제로 보관하고 있는 세션 키 값 -> 인증이 통과 되었다 .


#### CSRF 토큰 사용하기

CSRF 토큰을 통한 방어 메커니즘은 각 폼이나 요청에 대해 서버 측에서 생성한 고유하고 예측할 수 없는 토큰을 사용합니다.  서버는 요청을 받을 때마다 이 토큰을 검증하여, 해당 요청이 실제 사용자의 의도적인 행동에서 비롯된 것인지를 확인합니다.

보통 SSR 을 기반으로 하는 웹 페이지에서는 From 의 태그에 csrf 토큰을 숨기는 방식으로 csrf 토큰을 제공하고, rest api 같은 경우엔 클라이언트 사이드로 토큰을 전해 줄 방식을  커스텀해서 구현해야 합니다.



# 우리 서비스에서 CSRF 공격 막기.


## CSRF 토큰 검증 시나리오

1. **로그인 성공 후 CSRF 토큰 발급**: 사용자가 로그인에 성공하면, 서버는 HTTPS 응답 본문을 통해 CSRF 토큰을 제공합니다.
2. **클라이언트 사이드에서 토큰 전송**: 클라이언트는 POST, DELETE, PUT, PATCH와 같은 요청을 보낼 때, JavaScript 코드를 통해 로그인 성공 시 받은 CSRF 토큰을 `X-CSRF-TOKEN` 헤더에 포함하여 전송합니다.
3. **서버 사이드에서 토큰 검증**: 서버는 요청에 포함된 CSRF 토큰이 Redis에 존재하는지 확인하여 요청의 유효성을 검증합니다.



## 로그인 성공 시 세션과 CSRF 토큰 등록

### 로그인 성공 시 CSRF 토큰 반환

로그인 요청이 성공하면, 서버는 CSRF 토큰을 HTTPS 응답 본문에 담아 반환합니다.

```java
/**  
 * Github 로그인 요청을 처리하고 CSRF 토큰을 반환합니다. * * @param requestBody Github 로그인 요청 정보  
 * @param request     HTTP 요청 객체  
 * @return CSRF 토큰을 담은 응답  
 */@PostMapping  
@RequestMapping("/github")  
public ResponseEntity<CsrfToken> githubLogin(@RequestBody GithubLoginRequest requestBody, HttpServletRequest request) {  
    // login 성공 여부 확인  
    githubLoginUsecase.login(new GithubLoginRequest(requestBody.code()));  
    // 성공 시, csrf 토큰을 바디에 담아서 전송  
    return ResponseEntity.ok(conditionalCsrfTokenRepository.loadToken(request));  
}
```


### 로그인 성공 핸들러

로그인 성공 시, 세션을 생성하고 CSRF 토큰을 등록합니다.

```java

@Service  
@RequiredArgsConstructor  
public class LoginSuccessHandler {  
  
    private final LoginSessionRegister loginSessionRegister;  
    private final CsrfTokenRegister csrfTokenRegister;  
  
    private final HttpServletRequest httpServletRequest;  
    private final HttpServletResponse httpServletResponse;  
  
  
    /**  
     * 로그인 성공 시 세션을 생성하고 CSRF 토큰을 등록함.     *     * @param userId    로그인한 사용자의 ID (메타 유저 아이디 )  
     * @param userRoles Set<UserRole>  
     */  
    public void createSession(Long userId, Set<UserRole> userRoles ){  
        loginSessionRegister.registerLoginSession(userId,userRoles);  
        // CSRF 토큰 등록  
        csrfTokenRegister.registerToken(httpServletRequest, httpServletResponse);  
    }  
}
```


### 커스텀 CSRF 토큰 저장소

로그인 세션이 유효한 경우에만 CSRF 토큰을 발급하고, 로그인 성공 시 직접 호출하여 응답 본문에 토큰을 포함시킵니다.
```java
/*  
* 커스텀 csrf 토큰 레포지토리  
* 유저 로그인 세션이 있는 경우에만, csrf 토큰을 발급.  
*  
* 로그인 성공 시, CsrfTokenRegister 를 직접 호출해서 body 에 토큰을 담아서 전달.  
* */  
public class ConditionalCsrfTokenRepository implements CsrfTokenRepository{  
  
    private CsrfTokenRepository delegatedRepository = new HttpSessionCsrfTokenRepository();  
  
    // 로그인 인증된 유저에 대해서만 csrf 토큰 발급  
    @Override  
    public CsrfToken generateToken(HttpServletRequest request) {  
        return isAuthenticated(request) ? delegatedRepository .generateToken(request) : null;  
    }  
  
    // csrf 값을 바꾸지 않음 .  
    @Override  
    public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response) {  
        if (token != null) {  
            delegatedRepository.saveToken(token, request, response);  
        }  
    }  
  
    @Override  
    public CsrfToken loadToken(HttpServletRequest request) {  
        return delegatedRepository.loadToken(request);  
    }  
  
    // 로그인 인증된 유저에 대해서만 csrf 토큰 발급  
    private boolean isAuthenticated(HttpServletRequest request) {  
        // 사용자 세션이 인증된 상태인지 확인합니다.  
        return request.getSession(false) != null &&  
                request.getSession().getAttribute("USER_INFO") != null;  
    }  
}
```

### CSRF 토큰 저장소 등록 및 보안 구성

Spring Security 설정에서 커스텀 CSRF 토큰 저장소를 등록하고, 필요한 보안 설정을 적용합니다.
spring security 6 스타일의 설정을 적용합니다.

```java
/**  
 * Same-Site 속성 설정 * * @return Same-Site 속성 설정  
 */@Bean  
public CookieSameSiteSupplier applicationCookieSameSiteSupplier() {  
    return CookieSameSiteSupplier.ofLax();  
}  
  
/**  
 * Spring Security 필터 체인 구성 */@Bean  
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {  
  
    http  
            // Form 로그인 및 HTTP 기본 인증 비활성화  
            .httpBasic(HttpBasicConfigurer::disable)  
            .formLogin(FormLoginConfigurer::disable)  
            .rememberMe(RememberMeConfigurer::disable)  
            // CSRF 토큰 저장소 설정  
            .csrf((csrf) -> csrf  
                    .csrfTokenRepository(conditionalCsrfTokenRepository())  
                    .ignoringRequestMatchers("/v1/user/**")  
            )  
            // 기본 CORS 설정 적용  
            .cors(Customizer.withDefaults())  
  
            // 세션 기반 인증을 위한 커스텀 필터 등록  
            .addFilterBefore(new SessionBasedAuthFilter(), RequestHeaderAuthenticationFilter.class)  
  
            // 로그인, 가입, 세션 관련 기능에 대한 CSRF 및 인증 필터 제외 설정  
            .authorizeHttpRequests((authorize) -> authorize  
                    .requestMatchers("/v1/user/**").permitAll()  
                    .anyRequest().authenticated()  
            )  
  
            // 커스텀 예외 메시지 설정  
            .exceptionHandling((exception) -> exception  
                    .authenticationEntryPoint(new CustomAuthenticationEntryPoint())  
                    .accessDeniedHandler(new CustomAccessDeniedHandler())  
            )  
  
    ;  
    return http.build();  
}  
  
/**  
 * 커스텀 CSRF 토큰 저장소 빈 등록 * */@Bean  
public ConditionalCsrfTokenRepository conditionalCsrfTokenRepository() {  
    return new ConditionalCsrfTokenRepository();  
}
```


### CSRF 토큰 등록 컴포넌트

로그인 성공 시 CSRF 토큰을 직접 등록하는 컴포넌트입니다.
```java
/*  
* 로그인 성공 시, CsrfTokenRegister 를 직접 호출해서 body 에 토큰을 담아서 전달.  
* */  
@Component  
@RequiredArgsConstructor  
public class CsrfTokenRegister {  
    private final ConditionalCsrfTokenRepository conditionalCsrfTokenRepository;  
  
    public void registerToken(HttpServletRequest request, HttpServletResponse response) {  
        CsrfToken csrfToken = conditionalCsrfTokenRepository.generateToken(request);  
        conditionalCsrfTokenRepository.saveToken(csrfToken, request, response);  
    }  
  
}
```