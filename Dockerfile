# Usa una imagen de base de OpenJDK con JDK 17
FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia directamente el archivo JAR generado previamente
COPY build/libs/orden-0.0.1-SNAPSHOT.jar /app/orden.jar
# Copia el código fuente al contenedor
COPY ./src ./src
# Expon el puerto 8080 (puerto donde corre Spring Boot)
EXPOSE 8080

# Ejecuta la aplicación Spring Boot utilizando el archivo .jar generado
ENTRYPOINT ["java", "-jar", "orden.jar"]
