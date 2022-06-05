# Authentication

# Register

- Method : POST
- URL : `http://localhost:8080/api/auth/signup`
- Request Body

```json
{
  "firstName": "string",
  "lastName": "string",
  "username": "string",
  "email": "string",
  "password": "string"
}
```

- Response Success

```json
{
  "success": "true",
  "message": "User registered successfully"
}
```

# Login

- Method : POST
- URL : `http://localhost:8080/api/auth/login`
- Request

```json
{
  "usernameOrEmail": "string",
  "password": "string"
}
```

- Response Success

```json
{
  "success": "string",
  "message": "string",
  "data": {
    "accessToken": "string",
    "refreshToken": "string",
    "expiresAt": "date",
    "tokenType": "Bearer",
    "username": "string"
  }
}
```

- Response Failed

```json
{
  "success": "false",
  "message": "Bad credentials, User is not authenticated. Username or password do not match"
}
```

# Refresh Token

- Method : POST
- URL : `http://localhost:8080/api/auth/refresh/token`
- Request Body

```json
{
  "refreshToken": "string"
}
```

- Response Success

```json
{
  "success": "true",
  "message": "Refresh token successfully updated",
  "data": {
    "accessToken": "string",
    "refreshToken": "string",
    "expiresAt": "date",
    "tokenType": "Bearer",
    "username": "string"
  }
}
```

# Logout

- Method : POST
- URL : `http://localhost:8080/api/auth/logout`
- Request Body

```json
{
  "usernameOrEmail": "string",
  "refreshToken": "string"
}
```

- Response Success

```json
{
  "success": "true",
  "message": "Refresh Token Deleted Successfully"
}
```