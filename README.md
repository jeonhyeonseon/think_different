# 🍎 Think_Different  🍎

## 🔗 Demo
http://43.201.67.221:8080

## 📌 프로젝트 소개

Think_Different는 Spring Boot 기반 개인 웹 서비스입니다.  

회원 인증(Spring Security)을 기반으로  
게시판, WebSocket 실시간 채팅, 가계부 기능을 통합 구현했습니다.

백엔드 구조 설계, 엔티티 모델링, 인증 처리,  
실시간 메시징(WebSocket + STOMP), DB 연동(JPA/MySQL)을 중심으로 개발했습니다.

## 🚀 주요 기능

### 🔐 회원 인증 (Spring Security)
 - 회원가입 / 로그인 / 로그아웃
 - 인증 기반 접근 제어

### 💰 가계부
 - 지출 / 수입 CRUD
 - 카테고리 분류
 - 월별 조회

### 📝 게시판
 - 게시글 CRUD
 - 검색 기능
 - 페이징 처리

### 💬 실시간 채팅 (WebSocket + STOMP)
 - 채팅방 구독 / 발행 구조
 - 실시간 메시지 전송 / 수신
 - WebSocket 기반 양방향 통신

🛠 기술 스택

#### Backend
 - Java
 - Spring Boot
 - JPA (Hibernate)
 - Spring Security

#### Frontend
 - Thymeleaf
 - HTML / CSS / JavaScript
 - WebSocket / STOMP

#### Database
 - MySQL

#### Infra / DevOps
 - Docker
 - AWS EC2

### 🎯 개발 포인트
 - Spring Security 기반 인증 처리 구조 이해
 - JPA 엔티티 설계 및 연관관계 매핑
 - WebSocket + STOMP 실시간 메시징 구현
 - 페이징 / 검색 / 상태 관리 로직 구현
 - Docker 기반 애플리케이션 컨테이너화
 - AWS EC2 배포
