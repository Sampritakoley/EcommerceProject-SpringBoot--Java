

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
![login](https://github.com/Sampritakoley/EcommerceProject-SpringBoot--Java/assets/109060246/cb0d9483-1b0b-44fe-a6ed-724e1de5d6b7)
 ADMIN ![admin](https://github.com/Sampritakoley/EcommerceProject-SpringBoot--Java/assets/109060246/73b5bcce-955f-4321-9494-aea190482eb6) USER ![user](https://github.com/Sampritakoley/EcommerceProject-SpringBoot--Java/assets/109060246/0eca6854-4ab8-4f86-ab0d-29682fccd50a)
 
 ## Category, Product, and Product Item Features:
 This e-commerce project provides a flexible and robust system for managing product categories, products, and product items. 
 This structure allows for complex inventory organization, supporting various e-commerce use cases. The platform also provides 
 comprehensive management capabilities for administrators, including CRUD (Create, Read, Update, Delete) operations for categories, products, and product items.
![category](https://github.com/Sampritakoley/EcommerceProject-SpringBoot--Java/assets/109060246/a67cd73d-23f9-41f3-8421-b178ff9a372a)

![brand](https://github.com/Sampritakoley/EcommerceProject-SpringBoot--Java/assets/109060246/66f1587c-a94b-40b9-92b2-b02c139ae948)

![item](https://github.com/Sampritakoley/EcommerceProject-SpringBoot--Java/assets/109060246/bedc0f92-a504-41e5-bf23-4a99793276cb)

![product](https://github.com/Sampritakoley/EcommerceProject-SpringBoot--Java/assets/109060246/10e3042f-8935-463e-b3d5-d03ac13fba3c)


## Address Book and Billing Address : 
This Spring Boot-based e-commerce platform includes an address book feature, allowing users to manage multiple shipping and billing addresses conveniently.
This feature simplifies the checkout process, enabling users to quickly select from their saved addresses, reducing friction during online shopping.

![address](https://github.com/Sampritakoley/EcommerceProject-SpringBoot--Java/assets/109060246/1c30d8b4-be7e-4b9a-acbe-fff40b3b07b9)
![billing address](https://github.com/Sampritakoley/EcommerceProject-SpringBoot--Java/assets/109060246/9fbf8a88-876f-44c1-b93e-8f17c9e6c201)


## Add to cart, wishlist :
This Spring Boot-based e-commerce platform includes robust "Add to Cart" and "Wishlist" features. These features play a crucial role in the
online shopping experience, allowing users to select products for immediate purchase or save them for future consideration.

![cart](https://github.com/Sampritakoley/EcommerceProject-SpringBoot--Java/assets/109060246/ee98e4d6-fe1f-477a-8f6a-859b3342c589)

![wishlist](https://github.com/Sampritakoley/EcommerceProject-SpringBoot--Java/assets/109060246/3f2f990e-e422-4526-9691-bbecbde7f646)

Notification Service
Our project includes a robust notification service implemented using WebSockets. This service is designed to provide real-time updates to clients. Specifically, all administrators connected to the WebSocket receive notifications as soon as a user places an order. Similarly, users receive notifications when an administrator processes the order status, including shipment or delivery-related updates.
