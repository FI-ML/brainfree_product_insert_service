FROM maven:3.8.6-amazoncorretto-17 AS BUILD
COPY pom.xml                 /tmp/
COPY product_insert_rest     /tmp/product_insert_rest/
COPY product_insert_webapp   /tmp/product_insert_webapp/
WORKDIR                      /tmp/
EXPOSE 8090
RUN mvn clean install

FROM amazoncorretto:17
COPY --from=BUILD /tmp/product_insert_rest/target/product-insert.jar app.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod", "app.jar"]
