# Product Restful API

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