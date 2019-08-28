## Intalación de la aplicación android
<img src="https://raw.githubusercontent.com/JosePatricio/App-Android-SpringRest/master/xOtvYY_u.png" width="400" />



### Opción 1
Para probar la app, descargar el proyecto y abrirlo desde Android Studio y ejecutarlo en un emulador previamente instalada.

### Opción 2
Descargar el archivo apk e instalarlo desde el telefono


## Intalación de la aplicación BackEnd

Los servicios Rest se los realizó usando el framework Spring Boot.


### Paso 1
Para esto se utilizó el gestor de base de datos PostreSql
El Script de la base de datos es el siguiente:

CREATE TABLE persona (
  id SERIAL PRIMARY KEY,
  nombre varchar(250) NOT NULL,
  apellido varchar(250) NOT NULL,
  ruc int NOT NULL,
  direccion varchar(250) NOT NULL,
  telefono varchar(50) NOT NULL,
  estado boolean NOT NULL,
  imagen text DEFAULT NULL
);


### Paso 2
Editar el archivo de propiedades de conección de base de datos application.properties (SpringBoot automáticamente toma este archivo, asi que no se necesita ninguna configuración desde código)


### Paso 3
Ejecutar el archivo App.java



*Para mayor facilidad de instalación de la aplicación los servicios Rest han sido subidos a la nube, la dirección del servicio es https://servrest.herokuapp.com/persona/all, asi que facilmente se pueden hacer pruebas con el archivo APK adjunto.*



### Realizado por Jose Patricio Isama



