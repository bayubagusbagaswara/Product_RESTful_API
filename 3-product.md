# Product

- untuk mengakses semua endpoint `/api/products` juga harus terautentikasi
- dan setiap enpoint juga ada yang bergantung dengan ROLE yang didapat tiap user

# Create Product Success

```json
{
  "success": true,
  "message": "Product was created successfully",
  "data": {
    "id": "92bf82e3-6ad8-49a8-b161-4644004c27eb",
    "name": "Product A",
    "price": 100000,
    "quantity": 55,
    "description": "Ini product A",
    "createdBy": "alberte@gmail.com",
    "createdAt": "2022-06-09T13:37:26.245322200Z"
  }
}
```

# Create Product Failed (Request data tidak lengkap)
```json
{
  "success": false,
  "message": "Bad Request",
  "data": {
    "price": "Price must be greater than or equal to 1",
    "name": "Name must not be blank",
    "description": "Description length minimum must be 10 characters"
  }
}
```

# Update Product Success

```json
{
  "success": true,
  "message": "Product updated successfully",
  "data": {
    "id": "92bf82e3-6ad8-49a8-b161-4644004c27eb",
    "name": "Product A update",
    "price": 55000000,
    "quantity": 123,
    "description": "Ini product A",
    "createdBy": "alberte@gmail.com",
    "createdAt": "2022-06-09T13:37:26.245322Z"
  }
}
```

# Delete Product Success
```json
{
  "success": true,
  "message": "Product deleted successfully",
  "data": null
}
```


# Delete Product Failed (Forbidden)

```json
{
  "success": false,
  "message": "Access Denied. You don't have permission to access this resource"
}
```

# Get Product By ID Success

```json
{
  "success": true,
  "message": "Product successfully retrieved based on id",
  "data": {
    "id": "macbook-m1-2020",
    "name": "Apple MacBook Pro M1 2020",
    "price": 19140000.00,
    "quantity": 35,
    "description": "This is Apple MacBook Pro M1 2020 description",
    "createdBy": "User",
    "createdAt": "2022-06-09T13:31:06.311620Z"
  }
}
```

# Get All Products Success

```json
{
  "success": true,
  "message": "All products successfully retrieved",
  "data": {
    "productResponses": [
      {
        "id": "acer-aspire-3",
        "name": "Acer Aspire 3 a314-32",
        "price": 4937500.00,
        "quantity": 50,
        "description": "This is Acer Aspire 3 a314-32 description",
        "createdBy": "User",
        "createdAt": "2022-06-09T13:31:35.893792Z"
      },
      {
        "id": "acer-aspire-5",
        "name": "Acer Aspire 5 A514",
        "price": 8750000.00,
        "quantity": 35,
        "description": "This is Acer Aspire 5 A514 description",
        "createdBy": "User",
        "createdAt": "2022-06-09T13:31:35.893792Z"
      },
      {
        "id": "acer-aspire-e5",
        "name": "Acer Aspire E5-475G",
        "price": 6100000.00,
        "quantity": 37,
        "description": "This is Acer Aspire E5-475G description",
        "createdBy": "User",
        "createdAt": "2022-06-09T13:31:35.893792Z"
      },
      {
        "id": "acer-nitro-5",
        "name": "Acer Nitro 5",
        "price": 10701000.00,
        "quantity": 30,
        "description": "This is Acer Nitro 5 description",
        "createdBy": "User",
        "createdAt": "2022-06-09T13:31:35.893792Z"
      },
      {
        "id": "hp-envy-x360",
        "name": "HP Envy X360",
        "price": 10149000.00,
        "quantity": 8,
        "description": "This is HP Envy X360 description",
        "createdBy": "User",
        "createdAt": "2022-06-09T13:31:41.776439Z"
      },
      {
        "id": "hp-pavilion-x360",
        "name": "HP Pavilion X360",
        "price": 7599000.00,
        "quantity": 5,
        "description": "This is HP Pavilion X360 description",
        "createdBy": "User",
        "createdAt": "2022-06-09T13:31:41.776439Z"
      },
      {
        "id": "lenovo-legion-5",
        "name": "Lenovo Legion 5 Pro",
        "price": 18199000.00,
        "quantity": 27,
        "description": "This is Lenovo Legion 5 Pro description",
        "createdBy": "User",
        "createdAt": "2022-06-09T13:31:26.958637Z"
      },
      {
        "id": "lenovo-thinkpad-t420",
        "name": "Lenovo ThinkPad T420",
        "price": 16999000.00,
        "quantity": 17,
        "description": "This is Lenovo ThinkPad T420 description",
        "createdBy": "User",
        "createdAt": "2022-06-09T13:31:26.958637Z"
      },
      {
        "id": "lenovo-thinkpad-t430",
        "name": "Lenovo ThinkPad T430",
        "price": 6279000.00,
        "quantity": 28,
        "description": "This is Lenovo ThinkPad T430 description",
        "createdBy": "User",
        "createdAt": "2022-06-09T13:31:26.958637Z"
      },
      {
        "id": "lenovo-thinkpad-x260",
        "name": "Lenovo ThinkPad X260",
        "price": 15400000.00,
        "quantity": 24,
        "description": "This is Lenovo ThinkPad X260 description",
        "createdBy": "User",
        "createdAt": "2022-06-09T13:31:26.958637Z"
      }
    ],
    "pageNo": 0,
    "pageSize": 10,
    "totalElements": 14,
    "totalPages": 2,
    "last": false
  }
}
```

# Get Product By Name Success 
name = macbook
```json
{
  "success": true,
  "message": "All products successfully retrieved",
  "data": [
    {
      "id": "macbook-air-m1-2020",
      "name": "Apple MacBook Air M1 2020",
      "price": 12950000.00,
      "quantity": 24,
      "description": "This is Apple MacBook Air M1 2020 description",
      "createdBy": "User",
      "createdAt": "2022-06-09T13:31:06.311620Z"
    },
    {
      "id": "macbook-m1-2020",
      "name": "Apple MacBook Pro M1 2020",
      "price": 19140000.00,
      "quantity": 35,
      "description": "This is Apple MacBook Pro M1 2020 description",
      "createdBy": "User",
      "createdAt": "2022-06-09T13:31:06.311620Z"
    },
    {
      "id": "macbook-air-2019",
      "name": "Apple MacBook Air 2019",
      "price": 13490000.00,
      "quantity": 35,
      "description": "This is Apple MacBook Air 2019 description",
      "createdBy": "User",
      "createdAt": "2022-06-09T13:31:06.311620Z"
    },
    {
      "id": "macbook-pro-14-2021",
      "name": "Apple MacBook Pro 14-inch 2021",
      "price": 33390000.00,
      "quantity": 20,
      "description": "This is Apple MacBook Pro 14-inch 2021 description",
      "createdBy": "User",
      "createdAt": "2022-06-09T13:31:06.311620Z"
    }
  ]
}
```

# Get Product By Name And Price Between Success
- name = Macbook range harga 1000000 - 15000000
```json
{
  "success": true,
  "message": "All products successfully retrieved",
  "data": [
    {
      "id": "macbook-air-m1-2020",
      "name": "Apple MacBook Air M1 2020",
      "price": 12950000.00,
      "quantity": 24,
      "description": "This is Apple MacBook Air M1 2020 description",
      "createdBy": "User",
      "createdAt": "2022-06-09T13:31:06.311620Z"
    },
    {
      "id": "macbook-air-2019",
      "name": "Apple MacBook Air 2019",
      "price": 13490000.00,
      "quantity": 35,
      "description": "This is Apple MacBook Air 2019 description",
      "createdBy": "User",
      "createdAt": "2022-06-09T13:31:06.311620Z"
    }
  ]
}
```
