# Product RESTful API 

Build Restful CRUD API for a product online store.

## Techologies 

- Java JDK 1.8
- Spring Boot
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

### Auth

| Method | URL              | Description | Sample Valid Request Body | Response                 |
| ------ |------------------|-------------| --------------------------- |--------------------------|
| POST   | /api/auth/signup | Sign up     | [JSON](#signup) | [JSON](#signup_response) |
| POST   | /api/auth/signin | Log in      | [JSON](#signin) |                          |

Test them using postman or any other rest client.

## Sample Valid JSON Request Bodys

#### <a id="signup">Sign Up Request Body</a>

```json
{
  "firstName": "Bayu",
  "lastName": "Bagaswara",
  "username": "bayu_bagaswara",
  "password": "B@gaswara12",
  "email": "bayubagaswara@mail.com"
}
```

#### <a id="signup_response">Sign Up Response</a>
```json
{
  "success": true,
  "message": "User registered successfully"
}
```

