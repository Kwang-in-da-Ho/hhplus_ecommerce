# 1. Project Overview
* 포인트 충전, 상품 조회, 주문, 결제 기능을 갖춘 간단한 e-commerce 서버
* TDD 방법론으로 개발
* 외부 API  호출
* 상품 재고에 대한 동시성 이슈 고려
> 💡 동시에 여러 주문이 들어올 경우, 유저의 보유 잔고에 대한 처리가 정확해야 합니다.\
> 💡 각 상품의 재고 관리가 정상적으로 이루어져 잘못된 주문이 발생하지 않도록 해야 합니다.

# 2. APIs
## 2-1. 포인트 충전
### 2-1-1. Requirements
* 결제에 사용될 금액을 충전하는 API 를 작성합니다.
* 사용자 식별자 및 충전할 금액을 받아 잔액을 충전합니다.
* 사용자 식별자를 통해 해당 사용자의 잔액을 조회합니다.
### 2-1-2. API 명세
| HTTP Method | URL |
| --- |---|
| POST | /points/charge |
### 2-1-3. Sequence Diagram
```mermaid
sequenceDiagram
    actor Customer
    participant PointController
    participant PointChargeUseCase
    participant PointComponent
    
    Customer ->> PointController : POST /points/charge
    PointController ->> PointChargeUseCase : charge(request)
    
    opt request.point <= 0
        PointChargeUseCase -->> PointController : InvalidPointException()
        PointController -->> Customer : 400 Bad Request
    end
    
    PointChargeUseCase ->> PointComponent : charge(pointToCharge)
    PointComponent -->> PointChargeUseCase : charge result
    PointChargeUseCase ->> PointHistoryComponent : createPointHistory()
    PointHistoryComponent -->> PointChargeUseCase : history create result
    PointChargeUseCase -->> PointController : charge result
    PointController -->> Customer : 200 Success
    
```
## 2-2. 상품 조회
### 2-2-1. Requirements
* 상품 정보 ( ID, 이름, 가격, 잔여수량 ) 을 조회하는 API 를 작성합니다.
* 조회시점의 상품별 잔여수량이 정확하면 좋습니다.
### 2-2-2. API 명세
| HTTP Method | URL |
|-------------|--|
| GET         | /products/{productId} |
### 2-2-3. Sequence Diagram
```mermaid
sequenceDiagram
    actor Customer
    participant ProductController
    participant ProductRetrieveUseCase
    participant ProductComponent
    participant InventoryComponent
    
    Customer ->> ProductController :  GET /products/{productId}
    ProductController ->> ProductComponent : retrieveProduct(productId)
    ProductComponent -->> ProductRetrieveUseCase : productInfo
    
    opt productInfo == null
        ProductRetrieveUseCase -->> ProductController:  InvalidProductException()
        ProductController -->> Customer : 400 Bad Request
    end
    
    ProductRetrieveUseCase ->> InventoryComponent: retrieveInventory(productId)
    InventoryComponent -->> ProductRetrieveUseCase : inventoryInfo
    ProductRetrieveUseCase ->> ProductRetrieveUseCase : response.productInfo = productInfo, response.inventory = inventoryInfo
    
    
```

## 2-3. 주문
### 2-3-1. Requirements
* 사용자 식별자와 (상품 ID, 수량) 목록을 입력받아 주문하고 결제를 수행하는 API 를 작성합니다.
* 결제는 기 충전된 잔액을 기반으로 수행하며 성공할 시 잔액을 차감해야 합니다.
* 데이터 분석을 위해 결제 성공 시에 실시간으로 주문 정보를 데이터 플랫폼에 전송해야 합니다. ( 데이터 플랫폼이 어플리케이션 외부 라는 가정만 지켜 작업해 주시면 됩니다 )
### 2-3-2. API 명세
| HTTP Method | URL    |
|-------------|--------|
| POST        | /order |
### 2-3-3. Sequence Diagram
```mermaid
sequenceDiagram
    actor Customer
    participant OrderController
    participant OrderUseCase
    participant ProductComponent
    participant InventoryComponent
    participant OrderComponent
    participant PointComponent
    participant PaymentComponent
    participant PointHistoryComponent
    participant OrderItemComponent
    
    Customer->>OrderController: POST /order
    OrderController ->> OrderUseCase: order(request)
    
    loop request.orderItems
        opt orderItem.quantity <= 0
            OrderUseCase -->> OrderController: InvalidOrderQuantityException()
            OrderController -->> Customer: 400 Bad Request
        end
        
        OrderUseCase ->> ProductComponent: retrieveProduct(orderItem.productId)
        ProductComponent ->> OrderUseCase: productInfo
        
        opt productInfo == null
            OrderUseCase -->> OrderController: InvalidProductException()
            OrderController -->> Customer: 400 Bad Request
        end
        
        OrderUseCase ->> OrderUseCase: remainingInventory.quantity = InventoryInfo.quantiy - orderItem.quantity
        
        OrderUseCase ->> InventoryComponent: updateInventory(remainingInventory)
        InventoryComponent -->> OrderUseCase: updatedInventoryRows
        
        opt updatedInventoryRows == 0
            OrderUseCase -->> OrderController : OutOfStockException()
            OrderController -->> Customer : 409 Conflict
        end
        
        OrderUseCase ->> OrderUseCase: totalPrice += (orderItem.quantity * productInfo.price)
    end
    
    OrderUseCase ->> OrderComponent: createOrder(customerId, totalPrice)
    OrderComponent -->> OrderUseCase: orderInfo
    
    OrderUseCase ->> PointComponent : retrieveCustomerPoint(customerId)
    PointComponent -->> OrderUseCase: customerPoint
    
    opt customerPoint < totalPrice
        OrderUseCase -->> OrderController: InsufficientPointException()
        OrderController -->> Customer: 409 Conflict
    end
    OrderUseCase ->> PaymentComponent: createPayment()
    PaymentComponent -->> OrderUseCase: paymentResult
    OrderUseCase ->> PointComponent : use()
    PointComponent -->> OrderUseCase : updatePointResult
    
    opt paymentResult != "SUCCESS"
        OrderUseCase -->> OrderController: PaymentFailureException()
        OrderController -->> Customer: 500 Internal Error
    end

    OrderUseCase ->> PointHistoryComponent : createPointHistory()
    PointHistoryComponent -->> OrderUseCase: : history create result
    
    OrderUseCase ->> OrderItemComponent: createAllOrderItems()
    OrderItemComponent -->> OrderUseCase: orderItemcreateResult
    
    OrderUseCase ->> Data Platform: <<async>> POST /order/statistics
    
    OrderUseCase -->> OrderController: orderResult
    OrderController -->> Customer: 200 Success
```

## 2-4. 상위 상품 조회
### 2-4-1. Requirements
* 최근 3일간 가장 많이 팔린 상위 5개 상품 정보를 제공하는 API 를 작성합니다.
* 통계 정보를 다루기 위한 기술적 고민을 충분히 해보도록 합니다.
### 2-4-2. API 명세
| HTTP Method | URL |
|-------------|--|
| GET         | /products/top-sellers |
### 2-4-3. Sequence Diagram
```mermaid
sequenceDiagram
    actor Customer
    participant ProductController
    participant ProductTopSellerRetrieveUseCase
    participant ProductComponent
    
    Customer ->> ProductController : GET /products/top-sellers
    ProductTopSellerRetrieveUseCase ->> PointComponent : retrieveTopSellers()
    PointComponent -->> ProductTopSellerRetrieveUseCase : top selling product List
    ProductTopSellerRetrieveUseCase -->> ProductController : top selling product List
    ProductController -->> Customer : 200 Success
```
---
> 👨‍💻 심화 과제 : 장바구니 기능
## 2-5. 장바구니 조회

## 2-6. 장바구니 추가

## 2-7. 장바구니 삭제
# 3. ERD
```mermaid
erDiagram
  CUSTOMER {
    long customer_id PK
    string customer_name

  }
  POINT {
    long customer_id PK, FK
    long amount
  }
  CUSTOMER ||--|o POINT : has
  
  POINT_HISTORY {
      long point_history_id PK
      long customer_id FK
      long amount
      string point_status
      datetime last_updated
  }
  
  POINT ||--|{ POINT_HISTORY : history

  PRODUCT {
    long product_id PK
    string product_name
    string category
    long price
  }
  PRODUCT_INVENTORY {
    long product_id PK,FK
    long quantity
    datetime last_updated
  }
  PRODUCT ||--|o PRODUCT_INVENTORY: has

  ORDER {
    long order_id PK
    long customer_id FK
    datetime order_datetime
    string order_status
    long total_price
  }

  ORDER_ITEM {
    long order_item_id PK
    long order_id FK
    long product_id FK
    long order_item_price
  }
  ORDER ||--o{ ORDER_ITEM: contains
  CUSTOMER ||--o{ ORDER : places
  PRODUCT ||--o{ ORDER_ITEM: composes

  PAYMENT{
    long payment_id PK
    long customer_id FK
    long order_id FK
    string payment_method
    long pay_amount
    datetime pay_datetime
    string pay_status
  }
  CUSTOMER ||--o{ PAYMENT : makes
  PAYMENT ||--|| ORDER: fulfills

  CART {
      long cart_id PK
      long customer_id FK
  }
  CART_ITEMS {
      long cart_item_id PK
      long cart_id FK
      long product_id FK
      long quantity 
  }
  CUSTOMER ||--o{ CART : has
  CART ||--|{ CART_ITEMS : contains
```
* 재고에 대한 I/O가 많으므로 `PRODUCT`테이블로부터 분리하여 `INVENTORY`테이블로 관리함
* 주문 시 각 주문 상품에 대한 상태가 별도로 관리될 수 있으므로 `ORDER` 테이블과 `ORDER_ITEM` 테이블을 분리하여 관리
* 장바구니도 장바구니에 담은 각 상품별 수량, 상태 등이 관리될 수 있도록 `CART` 테이블과 `CART_ITEMS` 테이블로 분리함