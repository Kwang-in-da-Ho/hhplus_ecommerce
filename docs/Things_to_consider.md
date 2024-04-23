# Things to Consider
## 1. API
### 1-1. 잔액충전
* 동시성 처리? 사용자가 악의적으로 동시에 여러 번 요청할 경우
### 1-2. 상품조회
* 조회 시점의 정보를 최대한 정확하도록 처리
### 1-3. 주문 / 결제
#### 순서
* 재고 차감 후 Order Item에 삽입?
* 재고 확인 절차가 별도로 필요한가?
    * 재고 update 후 값을 반환받기는 한다.
        * 이 값이 음수이면 `OutOfStockException()` 던지고 rollback?
#### 외부 시스템(PG사, 데이터 플랫폼) 연계
* PG사 와의 연계는 나중에 한번 생각해볼 것
* 데이터 플랫폼 전송
    * 비동기적 호출 (응답을 기다리지 않음) -> 전송 후 데이터허브로부터 callback을 받아서 처리함
    * Retry 로직
        * 3회 재시도 후 계속 timeout 혹은 fail 메시지 반환 받으면 DB에 적재 후 나중에 batch로 재시도
* 외부 시스템과의 연계는 어느 패키지에 하는 것이 좋은가?
* 주문 시점의 재고 확인
* 재고에 대한 동시성 처리 - 한 번에 여러 요청이 들어왔을 때 재고가 정확히 처리되어야 한다
    * DB - Pessimistic Lock 처리 필요할 듯
* 데이터 플랫폼으로의 전송
    * Mock API, Fake Module
    * 이 API 호출이 실패하더라도 주문 및 결제 요청은 정상적으로 처리되어야 함
### 1-4. 결제
* 주문과 동시에 이뤄짐
* 사용자의 잔액 확인
    * 잔액 부족 시 에러 메시지 표시
### 1-5. 장바구니 추가
* 장바구니 넣는 시점에서의 재고 확인

## 2. Architecture
* Clean Architecture + Layered Architecture 채택 (허재 코치님)
* 패키지 구조
```
    api/
        [domain-name]/
            controller/
            dto/
            usecase/
    domian/
        [domain-name]/
            model/
            components/
            repository/
            infrastructure/ => implementations of repository interfaces
```
## 3. Domain, Entity 식별
### 3-1. 사용자 - user
* 로그인 및 회원 관리 기능은 최소한으로 할 것이기 때문에 생략 가능
### 3-2. 포인트 - point
* 결제 시 사용하는 포인트
* 충전/차감
### 3-3. 상품 - product
* 상품id, 상품명, 카테고리, 가격
### 3-4. 재고 - inventory
* write 작업이 빈번할 것이기 때문에 상품과 별도로 관리
### 3-5. 주문 - order
* 주문id, 사용자id, 상품id, 주문개수, 주문일시, 총 주문 금액
* 사용자의 주문내역 관리
### 3-6. 주문항목 - order_item
* 
### 3-6. 장바구니 - cart
* 장바구니id, 사용자id, 상품id, 장바구니 담은 개수
* 장바구니 담기/ 장바구니에서 선택한 품