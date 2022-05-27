# Users

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