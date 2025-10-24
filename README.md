Touchstone Ollama User - Quick start
-----------------------------------

1) Ensure MySQL is running and you have created database 'touchstone_db':
   CREATE DATABASE touchstone_db;

2) Update src/main/resources/application.properties if needed (DB credentials, Ollama model).

3) Ensure Ollama is running locally and the model (codegemma:2b or mistral) is pulled:
   ollama pull codegemma:2b
   ollama run codegemma:2b

4) Build and run:
   mvn clean package
   mvn spring-boot:run

5) Open http://localhost:8080 and use the chat UI.
   Example command:
   create user vikram joshi email vikrmadip32@gmail.com roles OS Patching,JVM Start Stop departments UPI,CBX
