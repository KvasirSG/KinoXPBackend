
# 🎬 KinoXP Backend

*This is a coursework to show what we have learned so far and is a Minimum Viable Product and not to be seen as a real life finished project*

Welcome to the KinoXP Backend repository! This backend service powers the KinoXP cinema booking system, handling user authentication, movie listings, seat reservations, and more.


## Authors

- [Kenneth(@KvasirSG)](https://github.com/KvasirSG)


## 🚀 Features
- RESTful API for managing movies, showtimes, and bookings
- Integration with a relational database (e.g., MySQL)

## 🛠️ Technologies Used
- **Java & Spring Boot** – Backend framework
- **JPA (Hibernate)** – Database ORM
- **MySQL/PostgreSQL** – Database system
- **Swagger** – API documentation
## 📦 Installation
### Prerequisites
- Java 17+
- Maven
- MySQL(configured in application.properties)
    
## Run Locally

Clone the project

```bash
    git clone https://github.com/KvasirSG/KinoXPBackend.git
```

Go to the project directory

```bash
    cd KinoXPBackend
```

Configure the database in src/main/resources/application.properties:

```bash
    spring.datasource.url=jdbc:mysql://localhost:3306/kinoxp
    spring.datasource.username=root
    spring.datasource.password=yourpassword
```

Build and run the application:

```bash
  mvn spring-boot:run
```
The API should now be accessible at:

```bash
  http://localhost:8080/api
```


## API Reference

#### Retrieve all movies

```http
  GET /api/movies
```

#### Retrieve a specific movie

```http
  GET /api/movies/{id}
```
#### Add a new movie

```http
  POST /api/movies
```

#### Delete a movie

```http
  DELETE /api/movies/{id}
```
#### Full API documentation is available via Swagger:
```bash
http://localhost:8080/swagger-ui.html
```


## License

[MIT](https://choosealicense.com/licenses/mit/)

