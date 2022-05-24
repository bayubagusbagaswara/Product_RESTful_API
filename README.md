# Product Restful API

- Kita contoh Reddit Clone versi lama
- yakni mengadopsi sistem JWT Authentication lama, nonton aja video nya
- Jadi kita juga buat entity untuk User, Konfigurasi Web Security, dan login logout menggunakan refresh token
- Tapi ingat, ini sistem lama atau tidak menggunakan OAuth

# Authentication 

- yang bisa akses API kita harus jadi User dulu, jadi harus terdaftar dulu dalam sistem kita
- tapi untuk GET API kita perbolehkan
- jadi ini kita anggap tidak ada authorization
- Prosesnya, pertama ada 2 kondisi, apakah User akan Login atau SignUp (Register)
- Jika login, maka request pertama kita ijinkan, lalu jika berhasil autentikasi username dan password, maka user akan mendapatkan Token JWT, yang digunakan untuk request selanjutnya

# Authorization

- ini menambahkan hak ases terhadap User yang sudah terdaftar, misal nya User nya memiliki authorization sebagai USER_NORMAL, MEMBER, atau ADMIN
- misalnya untuk UPDATE dan DELETE product hanya diperbolehkan bagi User yang memiliki Role ADMIN
- Selain role ADMIN, maka User tidak bisa melakukan UPDATE dan DELETE

# Register User

- Pertama kali User SignUp / Register, maka dia akan mendapatkan role yang NORMAL atau user biasa
- Atau kita beri contoh pertama kali, kita insert ke database satu User dengan ROLE ADMIN
- Karena hanya user ADMIN, yang bisa menambahkan sebuah ROLE ke user yang lain
- Jadi setelah User teregister, dan mendapatkan Role User, maka dia hanya bisa mengakses endpoint yang hanya diperbolehkan untuk ROLE USER

# Data ROLE

- ROLE_C_LEVEL
- ROLE_VICE_PRESIDENT
- ROLE_MANAGER
- ROLE_STAFF

# JWT Authentication

- Bagaimana cara membuat token JWT atau generate token?
- Lalu cara membuat refresh token?

## Create New Product

Request :
- Method : POST
- Endpoint : `/api/products`
- Header :
    - Content-Type (consumes): application/json
    - Accept (produces): application/json
- Body :
```json
{
  "id" : "string unique",
  "name" : "string",
  "price" : "long",
  "quantity" : "integer",
  "description": "string"
}
```

Response :

```json
{
  "code": "number",
  "status": "string",
  "data": {
    "id": "string unique",
    "name": "string",
    "price": "long",
    "quantity": "integer",
    "description": "string",
    "createdAt": "date",
    "updatedAt": "date"
  }
}
```

## Get Product By ID
Request :
- Method : GET
- Endpoint : `/api/products/{id_product}`
- Header :
    - Accept (produces): application/json
      Response :
```json
{
  "code" : "number",
  "status" : "string",
  "data" : {
    "id" : "string unique",
    "name" : "string",
    "price" : "long",
    "quantity" : "integer",
    "description": "string",
    "createdAt" : "date",
    "updatedAt" : "date"
  }
}
```

## Update Product
Request :
- Method : PUT
- Endpoint : `/api/products/{id_product}`
- Header :
    - Content-Type: application/json
    - Accept: application/json
- Body :

```json
{
  "name" : "string",
  "price" : "long",
  "quantity" : "integer",
  "description": "string"
}
```

Response :

```json
{
  "code" : "number",
  "status" : "string",
  "data" : {
    "id" : "string unique",
    "name" : "string",
    "price" : "long",
    "quantity" : "integer",
    "description": "string",
    "createdAt" : "date",
    "updatedAt" : "date"
  }
}
```

## List Product

Request :
- Method : GET
- Endpoint : `/api/products`
- Header :
    - Accept: application/json
- Query Param :
    - size : number,
    - page : number,
    - sortField: string,
    - sortBy: string

Response :

```json
{
  "code" : "number",
  "status" : "string",
  "data" : [
    {
      "id" : "string unique",
      "name" : "string",
      "price" : "long",
      "quantity" : "integer",
      "createdAt" : "date",
      "updatedAt" : "date"
    },
    {
      "id" : "string unique",
      "name" : "string",
      "price" : "long",
      "quantity" : "integer",
      "createdAt" : "date",
      "updatedAt" : "date"
    }
  ]
}
```

```json
{
  "code": "number",
  "status": "string",
  "data": [
    {
      "data": [
        {
          "id" : "string unique",
          "name" : "string",
          "price" : "long",
          "description": "string",
          "quantity" : "integer",
          "createdAt" : "date",
          "updatedAt" : "date"
        },
        {
          "id" : "string unique",
          "name" : "string",
          "price" : "long",
          "description": "string",
          "quantity" : "integer",
          "createdAt" : "date",
          "updatedAt" : "date"
        }
      ],
      "pageNumber": "number",
      "pageSize": "number",
      "totalPage": "number",
      "totalElements": "number",
      "isLast": "boolean"
    }
  ]
}
```