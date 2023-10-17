#!/bin/sh

# Компиляция исходного кода
javac -d bin/main src/main/java/tree/*.java

# Генерация документации
javadoc -d docs src/main/java/tree/*.java

# Запуск приложения
jar cvfm tree.jar manifest.mf -C bin/main .
java -jar tree.jar