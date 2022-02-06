  FROM openjdk:11
  ADD target/todo-app-0.0.1-SNAPSHOT.jar .
  CMD java -Dspring.profiles.active=prod -Dserver.port=$PORT -Xmx128m -jar todo-app-0.0.1-SNAPSHOT.jar