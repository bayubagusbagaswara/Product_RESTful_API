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