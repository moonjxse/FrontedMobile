# FrontedMobile
1. Nombre del proyecto

TiendaApp – Sistema de Gestión y Venta de Productos

Backend: Spring Boot
Frontend: Android (Jetpack Compose + MVVM)

2. Integrantes

Victoria Castillo

José Díaz

3. Funcionalidades
	1. Autenticación y usuarios

	-Registro de usuarios con rol CLIENTE
	-Inicio de sesión
	-Gestión de sesión de usuario
	-Visualización de perfil

	2. Roles del sistema

	-Administrador
	-Cliente
	-Vendedor
	-Repartidor

Cada rol tiene acceso a distintas funcionalidades según permisos.

	3.Productos

	-Listado de productos
	-Ver detalle de producto
	-Crear, editar y eliminar productos (Administrador / Vendedor)
	-Control de stock

	4.Pedidos

	-Crear pedidos
	-Listar pedidos
	-Visualizar estado del pedido
	-Gestión de pedidos según rol

	5.Reparto

	-Visualización de pedidos asignados (Repartidor)
	-Cambio de estado del pedido

	6.Contacto

	-Envío de formulario de contacto desde la app

4. Endpoints utilizados (Spring Boot)
	1. Autenticación

	- POST /api/auth/login

	- POST /api/auth/register

	2. Usuarios

	- GET /api/users/{id}

	- GET /api/users

	3. Productos

	- GET /api/products

	- GET /api/products/{id}

	-POST /api/products

	- PUT /api/products/{id}

	- DELETE /api/products/{id}

	4. Pedidos

	- GET /api/orders

	- POST /api/orders

	- PUT /api/orders/{id}

	5. Contacto

	- POST /api/contact

5. Tecnologías utilizadas
Backend

	Java 17

	Spring Boot

	Spring Data JPA

	PostgreSQL

	Maven

	Lombok

	Frontend	

	Kotlin

	Android Studio

	Jetpack Compose

	MVVM

	Retrofit

	Coroutines

	StateFlow

6. Pasos para ejecutar el proyecto
	 Backend (Spring Boot)

1-Descargar el proyecto backend

2-Crear la base de datos en PostgreSQL

3-CREATE DATABASE tiendaapp;


4- Configurar application.properties:

spring.datasource.url=jdbc:postgresql://localhost:5432/tiendaapp
spring.datasource.username=postgres
spring.datasource.password=tu_password


5- Ejecutar el proyecto Spring Boot

6- Verificar que la API esté activa en:

http://localhost:8080



	Frontend (Android)

1- Descargar el proyecto frontend

2- Abrir en Android Studio

3- Verificar la URL de conexión:

BASE_URL = "http://10.0.2.2:8080/"


4- Ejecutar la aplicación: La navegación principal se realiza desde AppNavigation

7. Arquitectura del proyecto
Backend
controller
service
repository
model

Frontend
view (screens)
viewmodel
repository
model
navigation

8. Pruebas

Pruebas unitarias implementadas en:

ViewModel

Repository

Uso de:

JUnit

MockK

Cobertura mínima de lógica: 80%
