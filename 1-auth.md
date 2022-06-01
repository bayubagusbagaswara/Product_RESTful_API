# Authentication

# Register

- Jika register berhasil, maka user pertama akan mendapatkan roleh MEMBER dan ADMIN
- Method : POST
- URL : `http://localhost:8080/api/auth/signup`
- Request Body

```json
{
  "firstName": "Albert",
  "lastName": "Einstein",
  "username": "albert99",
  "email": "albert@gmail.com",
  "password": "albert123"
}
```
- Response

# Login

- Method : POST
- URL : `http://localhost:8080/api/auth/login`
- Request

```json
{
  "usernameOrEmail": "string",
  "password": "albert123"
}
```
- Response

```json
{
  "accessToken": "string",
  "refreshToken": "string",
  "expiresAt": "date",
  "tokenType": "Bearer",
  "username": "string"
}
```

# Refresh Token

- Method : POST
- URL : `http://localhost:8080/api/auth/refresh/token`
- Request Body

```json
{
  "refreshToken": "string",
  "username": "string"
}
```

- Response 

```json
{
  "accessToken": "string",
  "refreshToken": "string",
  "expiresAt": "date",
  "tokenType": "Bearer",
  "username": "string"
}
```

# Logout

- Method : POST
- URL : `http://localhost:8080/api/auth/logout`
- Request Body

```json
{
  "refreshToken": "string"
}
```

- Response

```json
{
  "message": "Refresh Token Deleted Successfully"
}
```


# Create User

## First User

```json
{
  "firstName": "Bayu",
  "lastName": "Bagaswara",
  "email": "bagaszwara12@gmail.com",
  "username": "bayu_bagaswara",
  "password": "B@gaswara12"
}
```

## Admin

```json
{
  "firstName": "Albert",
  "lastName": "Einstein",
  "email": "albert@gmail.com",
  "username": "albert",
  "password": "albert123"
}
```

## User

```json
{
  "firstName": "Nikola",
  "lastName": "Tesla",
  "email": "tesla@gmail.com",
  "username": "tesla99",
  "password": "tesla123"
}
```

```json
{
  "firstName": "Isaac",
  "lastName": "Newton",
  "email": "newton@gmail.com",
  "username": "newton_12",
  "password": "newton123"
}
```

```json
{
  "firstName": "James",
  "lastName": "Watt",
  "email": "james@gmail.com",
  "username": "james_watt",
  "password": "james123"
}
```