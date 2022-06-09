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

## Testing

- Register Success
```json
{
    "success": true,
    "message": "User registered successfully"
}
```

- Login Success
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

- Login Gagal

```json
{
  "success": false,
  "message": "Bad credentials, User is not authenticated. Username or password do not match"
}
```

- Refresh Token Success

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
- Refresh Token Invalid
```json
{
  "success": false,
  "message": "Invalid refresh token"
}
```

- Create User Success

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

- Create User Failed username is exists
```json
{
  "success": false,
  "message": "Username is already taken"
}
```
- Create User Failes email is exists
```json
{
  "success": false,
  "message": "Email is already taken"
}
```
- Create User Failes, request gak lengkap
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

# Update Error (Forbidden)
```json
{
  "success": false,
  "message": "You don't have permission to update profile of: bayu_bagaswara"
}
```

# Update Success
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

- Update Success Login as Admin

```json
{
  "success": true,
  "message": "User updated successfully",
  "data": {
    "id": 3,
    "firstName": "Albert",
    "lastName": "Einstein",
    "username": "albert",
    "password": "$2y$10$n31bCaqP9lD5t5GFfCHTYuibz9ATLp0U9piNGbCkQy84WMQ0AbZtO",
    "email": "alberte@gmail.com",
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

# Add Role Success from Admin to User

```json
{
  "success": true,
  "message": "Successfully added role to user: albert"
}
```

# Add role failed from User to another User
```json
{
  "success": false,
  "message": "Access Denied. You don't have permission to access this resource"
}
```

# Delete User failed
```json
{
    "success": false,
    "message": "Access Denied. You don't have permission to access this resource"
}
```

# Delete User Success with Admin
```json
{
  "success": true,
  "message": "You successfully deleted profile of: budi_anggara"
}
```

# Remove Admin Success

```json
{
  "success": true,
  "message": "You took ADMIN role from user: albert"
}
```

