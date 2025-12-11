# 🖥️ Backend do Projeto **SIMBIOSYS**

<p align="center">
  <img src="https://imgur.com/6s2lH3n.png" alt="Simbiosys Logo">
</p>

<p align="center">
API oficial do projeto <strong>SIMBIOSYS</strong>, responsável por toda a lógica de negócios, integração com o banco de dados e comunicação com o frontend.
</p>

---

## ⚙️ Tecnologias Utilizadas

- **Java 17+**
- **Spring Boot**
- **Spring Web**
- **Spring Data JPA**
- **MySQL** / MariaDB
- **Maven**
- **Docker** (opcional)
- **Lombok**
- **Swagger/OpenAPI** (se configurado)

---

## 🚀 1. Clonar o Repositório

```bash
git clone https://github.com/projeto-simbiosys/BackEnd
cd BackEnd
```
## 🐳 2. **Rodar Banco com Docker**
Subir o banco via Docker:
```
docker compose up -d
```
Ou, se houver apenas o container do MySQL:
```
docker run --name simbiosys-db -e MYSQL_ROOT_PASSWORD=1234 -e MYSQL_DATABASE=simbiosys -p 3306:3306 -d mysql:8
```

## ▶️ 4. **Rodar o Backend em Desenvolvimento**
Maven:
```
mvn spring-boot:run
````

Ou via IDE:

IntelliJ

VSCode com extensão Spring

Eclipse

A API rodará em:
```
http://localhost:8080
```

## 📚 5. **Documentação da API**

Se o Swagger estiver habilitado, acesse:
```
http://localhost:8080/swagger-ui/index.html
```

## 🗂️ 6. **Estrutura do Projeto (Padrão Spring)**
```
src/
├── main/
│   ├── java/
│   │   └── com.simbiosys.backend/
│   │        ├── controller/    # Endpoints da API
│   │        ├── service/       # Regras de negócio
│   │        ├── repository/    # Interfaces JPA
│   │        ├── model/         # Entidades
│   │        └── SimbiosysApplication.java
│   └── resources/
│        ├── application.properties
│        └── static/
└── test/
```

## 🔗 7. **Conexão com o FrontEnd**

Certifique-se de liberar o CORS (se necessário):
```
@Bean
public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedOrigins("*")
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH");
        }
    };
}
```
