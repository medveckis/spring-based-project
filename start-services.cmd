CALL mvn clean package -f .\discovery-service\pom.xml
CALL mvn clean package -f .\api-gateway\pom.xml
CALL mvn clean package -f .\book-service\pom.xml
CALL mvn clean package -f .\user-service\pom.xml
CALL mvn clean package -f .\borrow-management-service\pom.xml

START "Discovery Server" java -jar discovery-service/target/discovery-service-0.0.1-SNAPSHOT.jar
START "API Gateway" java -jar api-gateway/target/api-gateway-0.0.1-SNAPSHOT.jar
START "Book Service" java -jar book-service/target/book-service-0.0.1-SNAPSHOT.jar
START "User Service" java -jar user-service/target/user-service-0.0.1-SNAPSHOT.jar
START "Borrow Management Service" java -jar borrow-management-service/target/borrow-management-service-0.0.1-SNAPSHOT.jar

