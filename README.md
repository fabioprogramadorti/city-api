# City API - RESTful Web Service for City Management

## 📌 Overview
City API is a RESTful web service designed to manage city data efficiently. It provides endpoints to retrieve, create, update, and delete city records. Additionally, it supports bulk uploads via CSV files, making it easy to handle large datasets. Built with **Spring Boot**, this API ensures high scalability, performance, and seamless integration with other systems.

## 🚀 Features
- ✅ Retrieve all cities or a specific city by ID
- ✅ Create and update city records
- ✅ Delete city records
- ✅ Upload city data via CSV files
- ✅ RESTful API with **Swagger (OpenAPI 3)** documentation

## 🛠️ Tech Stack
- **Java 17+**
- **Spring Boot**
- **MongoDB** (required for database storage)
- **Swagger (OpenAPI 3)** for API documentation

## 📂 Endpoints
### 🔹 Retrieve Cities
- `GET /cities` → Fetch all cities
- `GET /cities/{id}` → Fetch a city by ID

### 🔹 Create and Update Cities
- `POST /cities` → Create a new city
- `PUT /cities/{id}` → Update an existing city

### 🔹 Delete a City
- `DELETE /cities/{id}` → Remove a city by ID

### 🔹 Bulk Upload (CSV File)
- `POST /cities/upload` → Upload city data via a CSV file

## ▶️ How to Run
1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/city-api.git
   cd city-api
   ```
2. **Configure the database connection** (MongoDB is required for this project)
3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```
4. **Access API documentation** via Swagger UI:
   - `http://localhost:8080/api/swagger-ui.html`

## 📌 CSV Upload Format
The CSV file must include the following columns:
```
ibge_id,name,uf,capital,lat,lon
123456,City Name,SP,1,-23.5505,-46.6333
```
- **ibge_id**: Unique city ID (integer)
- **name**: City name (string)
- **uf**: State abbreviation (string, e.g., SP, RJ)
- **capital**: 1 if the city is a capital, 0 otherwise
- **lat**: Latitude (decimal)
- **lon**: Longitude (decimal)

### 📂 Included CSV File
A sample CSV file is included in the project to facilitate bulk city uploads. You can find it in the `archive` directory within the project structure and use it for testing.

## 📄 License
This project is licensed under the MIT License.

## 🤝 Contributing
Feel free to submit pull requests or open issues to improve the API.

---
Made with ❤️ using **Spring Boot** 🚀

