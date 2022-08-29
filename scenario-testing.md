# Scenario Testing

## User

1. Register User
2. Activation User
3. Create Password
4. Reset Password
5. Forgot Password
6. Login

## Workflow Register User

1. User melakukan register pertama kali, jika berhasil maka responsenya adalah berupa pemberitahuan untuk aktivasi email. Misalnya, `Reset Password telah dikiriman ke email anda, silahkan periksa email anda`
2. User akan membuka emailnya, lalu ada pesan `Silahkan klik link dibawah untuk melakukan reset password`. Disini API akan menerima request endpoint reset password dan juga user akan mengirimkan `email_user`, `password` dan `confirm password`. Kemudian akan disimpan didalam database. Responsenya adalah `Berhasil melakukan reset password`
3. User bisa login
4. Skenario forgot password. User akan hit endpoint reset-password lagi. Response nya adalah `Reset Password telah dikiriman ke email anda, silahkan periksa email anda`. Kemudian user akan melakukan klik link di emailnya
