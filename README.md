# Product Service (Spring Boot + PostgreSQL)

Микросервис для управления товарами в интернет-магазине.  
Предоставляет CRUD-операции для работы с товарами, включает валидацию данных, глобальную обработку ошибок и автоматическое логирование.

---

## 🚀 Возможности

- Создание, чтение и получение списка товаров
- Валидация входных данных (название, цена, остаток)
- Глобальный обработчик ошибок
- Автоматическое логирование запросов
- Автоматическое добавление префикса `/api/v1` ко всем эндпоинтам
- Unit и интеграционные тесты с использованием H2 в памяти
- Поддержка аудита (дата создания/обновления товара)

---

## ⚙️ Запуск проекта

### 1. Через Docker Compose (рекомендуется)
```bash
# Скопировать и заполнить переменные окружения
cp .env.example .env

# Запуск сервисов
docker-compose up --build
```

- сервис будет доступен на: **http://localhost:8082**
- PostgreSQL поднимется на порту **5434**

---

### 2. Локально в IntelliJ IDEA
1. Установить PostgreSQL (создать базу `productservice_db`).  
2. Заполнить переменные в `.env` (см. ниже).  
3. Запустить `ProductServiceApplication`.  

---

## 🔑 Файл `.env.example`

```env
# Database
DB_USER=your_username
DB_PASSWORD=your_password
DB_URL=jdbc:postgresql://localhost:5432/your_db_name
```

Создай файл `.env` на основе этого примера и подставь свои значения.

---

## 🛠️ Используемый стек
- **Java 17**
- **Spring Boot 3**
- **Spring Data JPA + PostgreSQL**
- **Lombok**
- **JUnit5, Mockito, AssertJ**
- **Docker, Docker Compose**
- **HikariCP** (пул соединений)
- **Hibernate** (ORM)

---

## ✅ Тестирование
Запустить тесты:
```bash
./mvnw test
```

---

## 📂 Структура проекта
```
src/
 └── main/java/com/amazingshop/personal/productservice
      ├── config        # Конфигурация Web
      ├── controllers   # REST контроллеры + GlobalException
      ├── dto           # DTO для запросов/ответов
      ├── models        # Сущности JPA
      ├── repositories  # Spring Data JPA репозитории
      ├── services      # Бизнес-логика и конвертация
      └── util          # Пользовательские исключения
```

---

## 🔑 Пример запроса

### Создание товара
```http
POST /api/v1/products
Content-Type: application/json

{
  "name": "iPhone 15",
  "price": 999.99,
  "stock": 10
}
```

### Успешный ответ
```json
{
  "id": 1,
  "name": "iPhone 15",
  "price": 999.99,
  "stock": 10
}
```

### Получение товара по ID
```http
GET /api/v1/products/1
```

### Получение списка всех товаров
```http
GET /api/v1/products

```
