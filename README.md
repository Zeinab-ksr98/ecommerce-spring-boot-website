# ecommerce-spring-boot-website
# ZShop
it is an Ecommerce website dedicated to sharing the beauty of Islamic culture through meticulously curated items, including
artwork, literature, fashion, and home decor. Our commitment to quality, authenticity, and cultural
significance ensures a rich representation of Islamic heritage. Explore, connect, and discover the
essence of faith with our thoughtfully chosen products
## Back-end language:
- Java Spring Boot
- JPA / Hibernate
- MYSQL
- Thymeleaf
- Maven
- Lombok
- SQL Query
## Front-end language
- JavaScript
- Html
- CSS
- Bootstrap
## Requirements
For building and running the application you need:
- [Maven 3](https://maven.apache.org)
## Running the application locally
## Quickstart
1. Clone the repository
2. Open the project in your IDE: IntelliJ IDEA (recommended) 
3. Configure the database connection in `application.properties` file (check the DataBase section below for more info) and comment the following lines:<br>
```
 spring.datasource.url=jdbc:mysql://containers-us-west-39.railway.app:7799/railway
spring.datasource.username=root
spring.datasource.password=Ssbv4Pc8FXkEdRDHSFza
```
4. Run the project 
5. Open http://localhost:8080/Main in your browser!
### Database

MySQLcan be used as the database for this project. The database connection can be configured in the `application.properties` file, with the appropriate values for the following properties:

```
properties
db.url=jdbc:mysql://[ip address of db]:[port of db]
    db.username=[username]
    db.password=[password, if any]
```
similar to the commented lines:
```
#to use local sql
#spring.datasource.url=jdbc:mysql://localhost:3306/finalspringboot
#spring.datasource.username=root
#spring.datasource.password=Yazahraa98alireda
```

## demo
[    ecommerce-project-zeinabksr.up.railway.app/main
](https://ecommerce-project-zeinabksr.up.railway.app/Main)  
  - Admin account:azk
  - Customeraccount:czksr (or create yours by sign up)
  - pasward: 123
## step by step how to use
the following link is step by step tutorial for features

https://github.com/Zeinab-ksr98/ecommerce-spring-boot-website/assets/87263087/22f1a3a0-508a-45cc-bc5e-a4e32990935f

https://scribehow.com/shared/Ecommerce_spring_boot_website__vD8Fb2BQS3iDc22W8dwWGQ

## features
1. create a customer roled account 
2. log in
3. logout 
4. forget password 
5. customer can:
- view all product 
- add to wish list 
- view wish list 
- remove from wish list 
- add to cart if available
- remove from cart
- modify the quantity
- choose payment method
- check out
- user cannot check out without filling all his details
- edit his profile
- add addresses up to 2
- delete address
- view order history and status
- reset his passward
6. admin(product provider) can:
- all the customer features 
- manage users(create admin, activate, deactivate any user and block a customer)
- manage categories (add ,delete ,modify)
- manage his products (add ,delete ,modify)
- sort products accourding to availabitity
- manage orders (modify their status)
- view wish list of each user

