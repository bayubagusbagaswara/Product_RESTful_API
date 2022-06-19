# Product RESTful API 

Build Restful CRUD API for a product online store.

## Techologies 

- Java JDK 1.8
- Spring Boot version 2.6.7
- Spring Data JPA, Hibernate
- Spring Security
- JWT
- PostgreSQL database
- Flyway migration
- JUnit testing

## Steps to Setup

**1. Clone the application**

```bash
git clone https://github.com/bayubagusbagaswara/product-restful-api.git
```

**2. Create PostgreSQL database**

```bash
CREATE DATABASE yourdbname;
CREATE USER youruser WITH ENCRYPTED PASSWORD 'yourpass';
GRANT ALL PRIVILEGES ON DATABASE yourdbname TO youruser;
```

**3. Change mysql username and password as per your installation**

+ open `src/main/resources/application.properties`
+ change `spring.datasource.username` and `spring.datasource.password` as per your postgres installation

**4. Install library**

```bash
mvc clean install  compile test-compile
```

**5. Run the app using maven**

```bash
mvn spring-boot:run
```
The app will start running at <http://localhost:8080>

## Explore Rest APIs

The app defines following CRUD APIs.

### Roles

| ID  | Name   |
|-----|--------|
| 1   | ADMIN  |
| 2   | USER   |

### Auth

| Method | URL                     | Description   | Request Body          | Response Success                 | Response Error                      |
|--------|-------------------------|---------------|-----------------------|----------------------------------|-------------------------------------|
| POST   | /api/auth/signup        | Sign up       | [JSON](#signup)       | [JSON](#signup_response_success) | [JSON](#create_user_response_error) |
| POST   | /api/auth/signin        | Log in        | [JSON](#signin)       | [JSON](#signin_response)         | [JSON](#signin-response-error)      | 
| POST   | /api/auth/refresh/token | Refresh Token | [JSON](#refreshtoken) | [JSON](#refresh_token_response)  | [JSON](#refresh_token_invalid)      |
| POST   | /api/auth/logout        | Log out       | [JSON](#logout)       | [JSON](#logout_response)         |                                     | 


### Users

| Method | URL                               | Description                                                                   | Request Body | Response Success                      | Response Error                      |
|--------|-----------------------------------|-------------------------------------------------------------------------------|--------------|---------------------------------------|-------------------------------------|
| POST   | /api/users                        | Create new user                                                               |              | [JSON](#create_user_response_success) | [JSON](#create_user_response_error) |
| PUT    | /api/users/{username}             | Update user (If profile belongs to logged in user or logged in user is admin) |              | [JSON](#update_user_success)          | [JSON](#update_user_error)          |
| PUT    | /api/users/{username}/addRole     | Add role to another user (only for admins)                                    |              | [JSON](#add_role_success)             | [JSON](#add_role_error)             |
| DELETE | /api/users/{usernaame}            | Delete user (Only admin)                                                      |              | [JSON](#delete_user_success)          | [JSON](#delete_user_error)          |
| PUT    | /api/users/{username}/giveAdmin   | Give admin role to user (only for admins)                                     |              |                                       |                                     |
| PUT    | /api/users/{username}/removeAdmin | Take admin role from user (only for admins)                                   |              | [JSON](#remove_admin_success)         |                                     |


### Product

| Method | URL                             | Description                      | Request Param            | Request Body            | Response Success                   | Response Error                |
|--------|---------------------------------|----------------------------------|--------------------------|-------------------------|------------------------------------|-------------------------------|
| POST   | /api/products                   | Create new product               |                          | [JSON](#create_product) | [JSON](#create_product_success)    | [JSON](#create_product_error) |
| PUT    | /api/products/{productId}       | Update product (user or admin)   |                          | [JSON](#update_product) | [JSON](#update_product_success)    |                               |
| DELETE | /api/products/{productId}       | Delete product (only for admins) |                          | -                       | [JSON](#delete_product_success)    | [JSON](#delete_product_error) |
| GET    | /api/products                   | Get all products                 |                          | -                       | [JSON](#get_product)               |                               |
| GET    | /api/products/{productId}       | Get product by ID                |                          | -                       | [JSON](#get_all_products)          |                               |
| GET    | /api/products/name/{name}       | Get products by containing name  |                          | -                       | [JSON](#get_product_by_name)       |                               |
| GET    | /api/products/name/{name}/price | Get products by name and price   | name, priceMin, priceMax | -                       | [JSON](#get_product_by_name_price) |                               |


Test them using postman or any other rest client.

## Sample Valid JSON Request Bodys

#### <a id="signup">Signup Request Body</a>
```json
{
  "firstName": "Bayu",
  "lastName": "Bagaswara",
  "username": "bayu_bagaswara",
  "password": "B@gaswara12",
  "email": "bayubagaswara@mail.com"
}
```

#### <a id="signup_response_success">Signup Response Success</a>
```json
{
  "success": true,
  "message": "User registered successfully"
}
```

#### <a id="signin">Sign In Request Body</a>
```json
{
  "usernameOrEmail": "string",
  "password": "string"
}
```

#### <a id="signin_response">Sign In Response Success</a>
```json
{
  "success": true,
  "message": "User authenticated successfully",
  "data": {
    "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaXNzIjoiUHJvZHVjdCBBUEkiLCJpYXQiOjE2NTQ3NzcyMDEsImV4cCI6MTY1NDc3OTAwMX0.D0ncWGECngNl0wXrwwVw9qiZlyyLJK3oGwPVlTafLORhpAokMwcAYC7vUPnsWeoVDghz-HrjpKGuPDwcfRo08g",
    "refreshToken": "ca86a55f-efa1-4730-b78f-eb990bb3362e",
    "expiresAt": "2022-06-09T12:50:01.804945700Z",
    "tokenType": "Bearer",
    "username": "bagaszwara12@gmail.com"
  }
}
```

#### <a id="signin_response_error">Sign In Response Error</a>
```json
{
  "success": false,
  "message": "Bad credentials, User is not authenticated. Username or password do not match"
}
```

#### <a id="refresh_token_response">Refresh Token Response Success</a>
```json
{
  "success": true,
  "message": "Refresh token successfully updated",
  "data": {
    "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaXNzIjoiUHJvZHVjdCBBUEkiLCJpYXQiOjE2NTQ3NzczNTAsImV4cCI6MTY1NDc3OTE1MH0.whDrThQh2a3oDx8GprpMQOM7Do5TA38W12gjsmybJjVCNk35OzcgI0oHyqOgdMyTqVoKYVvtZyMIZ6P70W77fg",
    "refreshToken": "c2017c2f-b5f5-45b4-8d89-e261d248120d",
    "expiresAt": "2022-06-09T12:52:30.383943200Z",
    "tokenType": "Bearer",
    "username": "bayu_bagaswara"
  }
}
```

#### <a id="refresh_token_invalid">Refresh Token Invalid</a>
```json
{
  "success": false,
  "message": "Invalid refresh token"
}
```

#### <a id="create_user_response_success">Create User Success Response</a>
```json
{
  "success": true,
  "message": "User was created successfully",
  "data": {
    "id": 8,
    "firstName": "Budi",
    "lastName": "Anggara",
    "username": "budi_anggara",
    "password": "$2y$10$/NDA7IGW66y.krPuJwbcteRG4D4qjnOzMvm2OaiwN4qFrYPSuAk0W",
    "email": "budi@gmail.com",
    "roles": [
      {
        "createdAt": "2022-06-09T12:07:03.763821Z",
        "updatedAt": "2022-06-09T12:07:03.763821Z",
        "id": 4,
        "name": "USER"
      }
    ]
  }
}
```

#### <a id="create_user_response_error">Create User Error Response</a>
```json
{
  "success": false,
  "message": "Username is already taken"
}
```
```json
{
  "success": false,
  "message": "Email is already taken"
}
```
```json
{
  "success": false,
  "message": "Bad Request",
  "data": {
    "lastName": "Lastname must not be blank",
    "firstName": "Firstname must not be blank",
    "password": "Password minimum length 6 characters and maximum 100 characters",
    "email": "Email must not be blank",
    "username": "Username must not be blank"
  }
}
```

#### <a id="update_user_success">Update User Success Response</a>
```json
{
  "success": true,
  "message": "User updated successfully",
  "data": {
    "id": 3,
    "firstName": "Albert update",
    "lastName": "Einstein",
    "username": "albert12",
    "password": "$2y$10$WyyBoSYn/5usw61dNi8ZguVczhyStl483xSBsvEO.JmYX9PuGWh1C",
    "email": "alberteins@gmail.com",
    "roles": [
      {
        "createdAt": "2022-06-09T12:07:03.763821Z",
        "updatedAt": "2022-06-09T12:07:03.763821Z",
        "id": 4,
        "name": "USER"
      }
    ]
  }
}
```

#### <a id="update_user_error">Update User Error Response</a>
```json
{
  "success": false,
  "message": "You don't have permission to update profile of: bayu_bagaswara"
}
```

#### <a id="add_role_success">Add Role Success Response</a>

```json
{
  "success": true,
  "message": "Successfully added role to user: albert"
}
```

#### <a id="add_role_error">Add Role Error Response</a>
```json
{
  "success": false,
  "message": "Access Denied. You don't have permission to access this resource"
}
```

#### <a id="delete_user_success">Delete User Success Response</a>
```json
{
  "success": true,
  "message": "You successfully deleted profile of: budi_anggara"
}
```

#### <a id="delete_user_error">Delete User Error Response</a>
```json
{
    "success": false,
    "message": "Access Denied. You don't have permission to access this resource"
}
```

#### <a id="remove_admin_success">Remove Admin Success Response</a>
```json
{
  "success": true,
  "message": "You took ADMIN role from user: albert"
}
```

#### <a id="create_product">Create Product Request Body</a>
```json
{
  "name": "Product A",
  "price": 1000000,
  "quantity": 10,
  "description": "Product A description"
}
```

#### <a id="create_product_success">Create Product Success Response</a>
```json
{
  "success": true,
  "message": "Product was created successfully",
  "data": {
    "id": "92bf82e3-6ad8-49a8-b161-4644004c27eb",
    "name": "Product A",
    "price": 100000,
    "quantity": 55,
    "description": "Product A description",
    "createdBy": "bayu@gmail.com",
    "createdAt": "2022-06-09T13:37:26.245322200Z"
  }
}
```

#### <a id="create_product_error">Create Product Error Response</a>
```json
{
  "success": false,
  "message": "Bad Request",
  "data": {
    "price": "Price must be greater than or equal to 1",
    "name": "Name must not be blank",
    "description": "Description length minimum must be 10 characters"
  }
}
```

#### <a id="update_product"><Update Product Request Body</a>
```json
{
  "name": "Product A",
  "price": 500000,
  "quantity": 50,
  "description": "Product A description"
}
```

#### <a id="update_product_success">Update Product Success Response</a>
```json
{
  "success": true,
  "message": "Product updated successfully",
  "data": {
    "id": "92bf82e3-6ad8-49a8-b161-4644004c27eb",
    "name": "Product A update",
    "price": 55000000,
    "quantity": 123,
    "description": "Product A description",
    "createdBy": "albert@gmail.com",
    "createdAt": "2022-06-09T13:37:26.245322Z"
  }
}
```

#### <a id="delete_product_success">Delete Product Success Response</a>
```json
{
  "success": true,
  "message": "Product deleted successfully",
  "data": null
}
```

#### <a id="delete_product_error">Delete Product Error Response</a>
```json
{
  "success": false,
  "message": "Access Denied. You don't have permission to access this resource"
}
```

#### <a id="get_product">Get Product By Id Response</a>
```json
{
  "success": true,
  "message": "Product successfully retrieved based on id",
  "data": {
    "id": "macbook-m1-2020",
    "name": "Apple MacBook Pro M1 2020",
    "price": 19140000.00,
    "quantity": 35,
    "description": "This is Apple MacBook Pro M1 2020 description",
    "createdBy": "User",
    "createdAt": "2022-06-09T13:31:06.311620Z"
  }
}
```

#### <a id="get_all_products">Get All Products Response</a>
```json
{
  "success": true,
  "message": "All products successfully retrieved",
  "data": {
    "productRespons": [
      {
        "id": "acer-aspire-3",
        "name": "Acer Aspire 3 a314-32",
        "price": 4937500.00,
        "quantity": 50,
        "description": "This is Acer Aspire 3 a314-32 description",
        "createdBy": "User",
        "createdAt": "2022-06-09T13:31:35.893792Z"
      },
      {
        "id": "acer-aspire-5",
        "name": "Acer Aspire 5 A514",
        "price": 8750000.00,
        "quantity": 35,
        "description": "This is Acer Aspire 5 A514 description",
        "createdBy": "User",
        "createdAt": "2022-06-09T13:31:35.893792Z"
      },
      {
        "id": "acer-aspire-e5",
        "name": "Acer Aspire E5-475G",
        "price": 6100000.00,
        "quantity": 37,
        "description": "This is Acer Aspire E5-475G description",
        "createdBy": "User",
        "createdAt": "2022-06-09T13:31:35.893792Z"
      },
      {
        "id": "acer-nitro-5",
        "name": "Acer Nitro 5",
        "price": 10701000.00,
        "quantity": 30,
        "description": "This is Acer Nitro 5 description",
        "createdBy": "User",
        "createdAt": "2022-06-09T13:31:35.893792Z"
      },
      {
        "id": "hp-envy-x360",
        "name": "HP Envy X360",
        "price": 10149000.00,
        "quantity": 8,
        "description": "This is HP Envy X360 description",
        "createdBy": "User",
        "createdAt": "2022-06-09T13:31:41.776439Z"
      },
      {
        "id": "hp-pavilion-x360",
        "name": "HP Pavilion X360",
        "price": 7599000.00,
        "quantity": 5,
        "description": "This is HP Pavilion X360 description",
        "createdBy": "User",
        "createdAt": "2022-06-09T13:31:41.776439Z"
      },
      {
        "id": "lenovo-legion-5",
        "name": "Lenovo Legion 5 Pro",
        "price": 18199000.00,
        "quantity": 27,
        "description": "This is Lenovo Legion 5 Pro description",
        "createdBy": "User",
        "createdAt": "2022-06-09T13:31:26.958637Z"
      },
      {
        "id": "lenovo-thinkpad-t420",
        "name": "Lenovo ThinkPad T420",
        "price": 16999000.00,
        "quantity": 17,
        "description": "This is Lenovo ThinkPad T420 description",
        "createdBy": "User",
        "createdAt": "2022-06-09T13:31:26.958637Z"
      },
      {
        "id": "lenovo-thinkpad-t430",
        "name": "Lenovo ThinkPad T430",
        "price": 6279000.00,
        "quantity": 28,
        "description": "This is Lenovo ThinkPad T430 description",
        "createdBy": "User",
        "createdAt": "2022-06-09T13:31:26.958637Z"
      },
      {
        "id": "lenovo-thinkpad-x260",
        "name": "Lenovo ThinkPad X260",
        "price": 15400000.00,
        "quantity": 24,
        "description": "This is Lenovo ThinkPad X260 description",
        "createdBy": "User",
        "createdAt": "2022-06-09T13:31:26.958637Z"
      }
    ],
    "pageNo": 0,
    "pageSize": 10,
    "totalElements": 14,
    "totalPages": 2,
    "last": false
  }
}
```

#### <a id="get_product_by_name">Get Product By Name Success</a>
```json
{
  "success": true,
  "message": "All products successfully retrieved",
  "data": [
    {
      "id": "macbook-air-m1-2020",
      "name": "Apple MacBook Air M1 2020",
      "price": 12950000.00,
      "quantity": 24,
      "description": "This is Apple MacBook Air M1 2020 description",
      "createdBy": "User",
      "createdAt": "2022-06-09T13:31:06.311620Z"
    },
    {
      "id": "macbook-m1-2020",
      "name": "Apple MacBook Pro M1 2020",
      "price": 19140000.00,
      "quantity": 35,
      "description": "This is Apple MacBook Pro M1 2020 description",
      "createdBy": "User",
      "createdAt": "2022-06-09T13:31:06.311620Z"
    },
    {
      "id": "macbook-air-2019",
      "name": "Apple MacBook Air 2019",
      "price": 13490000.00,
      "quantity": 35,
      "description": "This is Apple MacBook Air 2019 description",
      "createdBy": "User",
      "createdAt": "2022-06-09T13:31:06.311620Z"
    },
    {
      "id": "macbook-pro-14-2021",
      "name": "Apple MacBook Pro 14-inch 2021",
      "price": 33390000.00,
      "quantity": 20,
      "description": "This is Apple MacBook Pro 14-inch 2021 description",
      "createdBy": "User",
      "createdAt": "2022-06-09T13:31:06.311620Z"
    }
  ]
}
```

#### <a id="get_product_by_name_price">Get Product By Name And Price Between Success</a>
```json
{
  "success": true,
  "message": "All products successfully retrieved",
  "data": [
    {
      "id": "macbook-air-m1-2020",
      "name": "Apple MacBook Air M1 2020",
      "price": 12950000.00,
      "quantity": 24,
      "description": "This is Apple MacBook Air M1 2020 description",
      "createdBy": "User",
      "createdAt": "2022-06-09T13:31:06.311620Z"
    },
    {
      "id": "macbook-air-2019",
      "name": "Apple MacBook Air 2019",
      "price": 13490000.00,
      "quantity": 35,
      "description": "This is Apple MacBook Air 2019 description",
      "createdBy": "User",
      "createdAt": "2022-06-09T13:31:06.311620Z"
    }
  ]
}
```
