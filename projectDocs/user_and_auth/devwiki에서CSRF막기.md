

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