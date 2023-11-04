#!/bin/sh

# Компиляция исходного кода
javac -d bin/main src/main/java/graph/*.java

# Генерация документации
javadoc -d docs src/main/java/graph/*.java

# Запуск приложения
jar cvfm graph.jar manifest.mf -C bin/main .
java -jar graph.jar