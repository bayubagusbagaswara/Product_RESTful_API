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

