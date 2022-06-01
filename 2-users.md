# Users

- Enpoint untuk API /api/users ini harus terautentikasi semua dulu, karena tidak ada method GET
- Jadi user harus login dulu, karena accessToken akan dibawa user untuk mengakses tiap endpoint
- Nanti kita coba, kalau user tidak login apakah response nya?
- Lalu jika accessTokenya kadaluarsa
- Untuk method CREATE dan UPDATE bisa dilakukan oleh semua User yang berhasil login (dan membawa accessToken)
- Untuk method ADD_ROLE_TO_USER, DELETE USER, dan REMOVE_ADMIN hanya bisa dilakukan oleh ROLE ADMIN

# Create User

- Method : POST
- URL : `http://localhost:8080/api/users`
- Request Body

```json
{
  "firstName": "string",
  "lastName": "string",
  "username": "string",
  "password": "string",
  "email": "string"
}
```
- Response

```json
{
  "id": "number",
  "firstName": "string",
  "lastName": "string",
  "username": "string",
  "password": "string",
  "email": "string",
  "roles": []
}
```

# Update User

- Method : PUT
- URL : `http://localhost:8080/api/users/{username}`
- Request Body :

```json
{
  "firstName": "string",
  "lastName": "string",
  "username": "string",
  "password": "string",
  "email": "string"
}
```

- Response :

```json
{
  "id": "number",
  "firstName": "string",
  "lastName": "string",
  "username": "string",
  "password": "string",
  "email": "string",
  "roles": []
}
```

# Add Role To User

- Hanya bisa dilakukan oleh user yang memiliki ROLE ADMIN
- Method : PUT
- URL : `http://localhost:8080/api/users/{username}/addRole`
- Request Body :

```json
{
  "roleName": "string"
}
```

- Response

```json
{
  "message": "Success"
}
```

# Delete User

- Method : DELETE
- URL : `http://localhost:8080/api/users/{username}`
- Response :

```json
{
  "success": "string",
  "message": "string"
}
```