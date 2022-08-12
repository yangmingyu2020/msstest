# 코딩테스트 과제

## 개발 환경
* Java(1.8)
* Spring Boot(2.7.2)
* JPA
* h2(db)
* Swagger(3.0.0)

### Swagger
* Swagger : http://localhost:8080/swagger-ui.html

### h2-console
* h2-console : http://localhost:8080/h2-console
* JDBC URL : jdbc:h2:mem:productdb
* User Name : root
* Password : Asdf1234!

## API 과제
### 필수
- 모든 카테고리의 상품을 브랜드 별로 자유롭게 선택해서 모든 상품을 구매할 때 최저가 조회 : GET
   ```HTTP
   http://localhost:8080/product/all/minimum
   ```
- 한 브랜드에서 모든 카테고리의 상품을 한꺼번에 구매할 경우 최저가 및 브랜드 조회 : GET
   ```HTTP
   http://localhost:8080/product/all/minimum/brand
   ```
- 각 카테고리 이름으로 최소, 최대 가격 조회  : GET
   ```HTTP
   http://localhost:8080/product/price/min-and-max/{category}
   ```


### 선택
- 쇼핑몰 상품 가격 추가 : POST
   ```HTTP
   http://localhost:8080/product
   ```
    ```json
       {
         "brand": "브랜드명",
         "category": "카테고리명",
         "price": 0
       }
    ```

- 쇼핑몰 상품 가격 업데이트 : PUT
   ```HTTP
   http://localhost:8080/product
   ```
    ```json
       {
         "id": "ID",
	 "brand": "브랜드명",
         "category": "카테고리명",
         "price": 0
       }
    ```
	
- 쇼핑몰 상품 가격 삭제 : DELETE
   ```HTTP
   http://localhost:8080/product/{id}
   ```
   
    
