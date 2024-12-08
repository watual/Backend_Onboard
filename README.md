# **Spring 기반 인증 및 보안 시스템 구현**

---

# **Spring Security 기본 이해**

## **1. Filter란 무엇인가? (with Interceptor, AOP)**

### **Filter**

- **정의**:  
  Filter는 클라이언트에서 오는 요청(Request)과 서버에서 보내는 응답(Response)을 가로채어 특정 작업을 수행하거나 요청/응답을 수정하는 컴포넌트입니다.
- **특징**:
    - **Servlet Container 레벨**에서 동작.
    - 요청/응답 객체를 변경하거나 처리 흐름을 제어할 수 있음.
    - 모든 HTTP 요청에 대해 적용 가능.
- **사용 사례**:
    - 요청 로그 기록.
    - 인증/인가 처리.
    - CORS 설정.

### **Interceptor**

- **정의**:  
  Interceptor는 Spring의 DispatcherServlet 단계에서 동작하며, 컨트롤러 호출 전후의 특정 작업을 처리하는 역할을 합니다.
- **특징**:
    - **Spring 레벨**에서 동작.
    - 주로 비즈니스 로직 전후의 작업에 사용.
    - `HandlerInterceptor` 인터페이스를 구현하여 작성.
- **사용 사례**:
    - 사용자 인증 정보 확인.
    - 요청 데이터 검증.
    - 컨트롤러 반환 값 처리.

### **AOP (Aspect-Oriented Programming)**

- **정의**:  
  AOP는 애플리케이션의 핵심 로직에 영향을 주지 않고 부가적인 관심사(공통 기능, Cross-Cutting Concerns)를 분리하여 처리하는 프로그래밍 패러다임입니다.
- **특징**:
    - 특정 메소드 호출 전후나 예외 발생 시 로직 실행.
    - `@Aspect`와 `@Around` 등으로 설정.
- **사용 사례**:
    - 로깅, 트랜잭션 관리.
    - 보안 검사.
    - 성능 모니터링.

### **Filter, Interceptor, AOP 비교**

| 특성        | Filter               | Interceptor   | AOP             |
|-----------|----------------------|---------------|-----------------|
| **동작 레벨** | Servlet Container 레벨 | Spring 레벨     | 메소드/로직 레벨       |
| **적용 대상** | HTTP 요청/응답           | 컨트롤러 호출 전후    | 메소드 호출 전후/예외 처리 |
| **사용 목적** | 요청/응답 처리 및 수정        | 비즈니스 로직 전후 작업 | 공통 관심사 분리 및 처리  |

---

## **2. Spring Security란?**

> **Spring Security**: Spring 기반 애플리케이션에서 인증(Authentication)과 인가(Authorization)를 처리하는 강력한 보안 프레임워크.

### **핵심 구성 요소**

- **AuthenticationManager**: 사용자의 인증 처리.
- **SecurityContext**: 현재 인증된 사용자 정보를 저장.
- **Filter Chain**: 요청별로 보안 로직이 적용되는 필터 체인.

### **주요 기능**

- 인증과 인가를 쉽게 구현.
- CSRF, 세션 고정 공격, CORS 등 다양한 보안 위협 방지.
- 커스터마이징이 가능하여 특정 요구사항 반영 용이.

---

# **JWT 기본 이해**

## **1. JWT란 무엇인가요?**

> **JWT**: JSON Web Token(JWT)은 JSON 객체를 사용하여 정보(Claim)를 안전하게 전송하기 위한 컴팩트한 토큰 기반 인증 방식.

### **구조**

- **Header**: 토큰 타입(JWT)과 서명 알고리즘 정보.
- **Payload**: 사용자 정보 및 기타 데이터(Claim).
- **Signature**: Header와 Payload를 인코딩한 값으로, 무결성을 보장.

### **특징**

- 사용자 인증 정보가 포함되어 서버에서 상태를 유지할 필요가 없음 (**Stateless**).
- **Base64 URL-safe**로 인코딩되어 전송.
- 클라이언트와 서버 간의 인증, 정보 전달, 권한 부여 등에 사용.

---

# **Access / Refresh Token 발행과 검증에 관한 테스트 시나리오**

## **1. Access Token 발행**

> **목표**: Access Token이 정상적으로 발행되는지 확인.

### **테스트 단계**

1. 유효한 사용자 인증 정보로 Access Token 발행 요청.
2. 서버가 올바른 Access Token을 반환했는지 확인:
    - 유효기간, 형식, 서명 유효성.
3. 발행된 토큰의 Payload에 포함된 사용자 정보 확인.

---

## **2. Refresh Token 발행**

> **목표**: Refresh Token이 정상적으로 발행되는지 확인.

### **테스트 단계**

1. Access Token 요청 시 Refresh Token도 함께 요청.
2. 서버가 유효한 Refresh Token을 반환했는지 확인:
    - 유효기간, 형식, 서명 유효성.
3. Refresh Token의 저장 정책(DB, Redis 등)에 따라 발행 여부 확인.

---

## **3. Access Token 검증**

> **목표**: 발행된 Access Token이 유효한지 검증.

### **테스트 단계**

1. 유효한 Access Token으로 보호된 리소스 요청.
2. 서버가 정상적으로 요청을 처리했는지 확인.
3. 만료되거나 잘못된 Access Token으로 요청 시 거부 응답 확인.

---

## **4. Access Token 갱신**

> **목표**: Refresh Token으로 Access Token을 갱신할 수 있는지 확인.

### **테스트 단계**

1. 유효한 Refresh Token으로 Access Token 갱신 요청.
2. 서버가 새로운 Access Token을 발행했는지 확인.
3. 새 Access Token이 유효한지 검증.
4. 잘못된 Refresh Token으로 요청 시 서버의 거부 응답 확인.

---

## **5. Refresh Token 만료**

> **목표**: 만료된 Refresh Token 사용 시 올바르게 처리되는지 확인.

### **테스트 단계**

1. Refresh Token 만료 후 Access Token 갱신 요청.
2. 서버가 거부 응답을 반환했는지 확인.
3. 로그아웃 또는 재인증 절차를 클라이언트가 정상적으로 수행했는지 확인.

---

## **6. 토큰 변조 및 위변조 방지**

> **목표**: 변조된 토큰에 대한 대응 확인.

### **테스트 단계**

1. Header/Payload 조작 후 Access Token으로 요청.
2. 서명이 변경된 Refresh Token으로 요청.
3. 서버가 요청을 거부하는지 확인.

---

## **7. 로그아웃 시 토큰 무효화**

> **목표**: 로그아웃 요청으로 모든 토큰이 무효화되는지 확인.

### **테스트 단계**

1. 로그아웃 API 호출 후 Access/Refresh Token으로 요청.
2. 서버가 거부 응답을 반환했는지 확인.
3. 무효화된 토큰 상태(DB, 캐시 등)를 점검.
