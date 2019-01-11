FROM java:8-jdk-alpine
WORKDIR /usr/src
ENV MYSQL_DATABASE=moviedb
ENV MYSQL_USER=root
ENV MYSQL_PASSWORD=root
ENV MYSQL_CI_URL=jdbc:mysql://localhost:3306/moviedb?createDatabaseIfNotExist=true
ADD ./target/movie-cruiser-api-1.0.0.jar /usr/src/movie-cruiser-api.jar
ENTRYPOINT [ "java", "-jar", "movie-cruiser-api.jar" ]