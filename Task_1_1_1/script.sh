#!/bin/sh

# Компиляция исходного кода
javac -d bin/main src/main/java/*.java

# Генерация документации
javadoc -d docs src/main/java/*.java

# Запуск приложения
java -cp bin/main CheckSort