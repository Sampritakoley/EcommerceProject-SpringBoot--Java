

# E-Commerce Platform

Here, I have designed and developed a feature-rich e-commerce platform to provide an online shopping experience. 
It includes product management, user authentication, shopping cart functionality, order processing, and more.
![home](https://github.com/Sampritakoley/EcommerceProject-SpringBoot--Java/assets/109060246/fd8d8e65-1467-4fcb-9736-a0b8df476dc4)
## This project has  a modular structure with a focus on separation of concerns, enabling easy development, testing, and maintenance

## Model Layer: 
Contains entities or domain objects representing the core business concepts, such as Product, User, Order, CartItem, etc.
## Repository Layer:
Uses Spring Data JPA for database interactions. This layer provides CRUD operations for entities.
## Service Layer: 
Contains business logic. This layer orchestrates the interaction between repositories and controllers.
## Controller Layer:
Handles HTTP requests and maps them to specific endpoints. Typically uses RESTful architecture.
## Configuration Layer:
Contains Spring Boot configuration files, beans, and other setup-related code.
## Security Layer: 
Handles user authentication and authorization. Spring Security is commonly used for this purpose.

## Features

## Login and Signup: 
For login, users submit their credentials, which are authenticated against a database or other identity provider.
For signup, users provide registration details, which are validated and saved to the database. here I have used the 
spring security by configuring the authentication mechanism in a security configuration class and specified
user details service, password encoder, and custom authentication logic.
Role-based authorization is used, where users are assigned roles that grant or restrict access to certain resources or operations. 

 ADMIN ![admin](https://github.com/Sampritakoley/EcommerceProject-SpringBoot--Java/assets/109060246/73b5bcce-955f-4321-9494-aea190482eb6) USER ![user](https://github.com/Sampritakoley/EcommerceProject-SpringBoot--Java/assets/109060246/0eca6854-4ab8-4f86-ab0d-29682fccd50a)
 
 ## Category, Product, and Product Item Features:
 This e-commerce project provides a flexible and robust system for managing product categories, products, and product items. 
 This structure allows for complex inventory organization, supporting various e-commerce use cases. The platform also provides 
 comprehensive management capabilities for administrators, including CRUD (Create, Read, Update, Delete) operations for categories, products, and product items.
Category ![category](https://github.com/Sampritakoley/EcommerceProject-SpringBoot--Java/assets/109060246/6df35907-2615-4254-a478-a28d3d87991b)
Product ![product](https://github.com/Sampritakoley/EcommerceProject-SpringBoot--Java/assets/109060246/8d6fd2c2-dc12-4ac0-ae12-689bc2f7e436)
Product Item ![item-final](https://github.com/Sampritakoley/EcommerceProject-SpringBoot--Java/assets/109060246/05e0f4d4-ab0b-4047-a8b9-d037f3e5709a)

## Address Book and Billing Address : 
This Spring Boot-based e-commerce platform includes an address book feature, allowing users to manage multiple shipping and billing addresses conveniently.
This feature simplifies the checkout process, enabling users to quickly select from their saved addresses, reducing friction during online shopping.

![address](https://github.com/Sampritakoley/EcommerceProject-SpringBoot--Java/assets/109060246/1c30d8b4-be7e-4b9a-acbe-fff40b3b07b9)
![billing address](https://github.com/Sampritakoley/EcommerceProject-SpringBoot--Java/assets/109060246/9fbf8a88-876f-44c1-b93e-8f17c9e6c201)


## Add to cart, wishlist :
This Spring Boot-based e-commerce platform includes robust "Add to Cart" and "Wishlist" features. These features play a crucial role in the
online shopping experience, allowing users to select products for immediate purchase or save them for future consideration.

![wishlist](https://github.com/Sampritakoley/EcommerceProject-SpringBoot--Java/assets/109060246/79a32f6e-3d5a-4732-a784-ecdb3bf19008)

![cart item](https://github.com/Sampritakoley/EcommerceProject-SpringBoot--Java/assets/109060246/96f3b57f-ef09-49bd-a8bc-b64f50530c02)
