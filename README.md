# Формат практических работ на курсе ООП

## Организация работы
Для выполнения и сдачи задач потребуется:
- Создать публичный репозиторий под названием “OOP” на GitHub. Добавить в
“коллабораторы” проекта (Settings -> Collaborators) аккаунты лектора
alexander-a-vlasov и преподавателя семинарских занятий.
- В репозитории подключить GitHub workflow, используя эту инструкцию.
- Установить и настроить Git.
Общие требования к рабочему процессу
- Язык программирования -- Java. JDK 11 и выше.
- Система сборки -- Gradle.
- Каждую задачу необходимо выполнять в отдельном Gradle проекте, который
по завершении должен успешно собираться из командной строки командой
gradle build.
- Система контроля версий -- Git. Репозиторий, созданный через GitHub, нужно
склонировать на свою машину и далее работать внутри него. Этот
репозиторий будет общим для всех задач и первого, и второго семестра.
- Структура репозитория фиксирована: каждая задача должна находиться в
отдельной директории ``Task_S_N``, где S -- номер семестра, а N -- номер задачи.
Директория является корнем соответствующего Gradle проекта.
- Каждая задача выполняется в отдельной ветке ``task-S-N``, где S -- номер
семестра, а N -- номер задачи. Рекомендуется делать коммит в репозиторий (и
локальный, и публичный) сразу после внесения очередных логически
смежных изменений. Ваш рабочий процесс должен выглядеть следующим
образом: добавили новую функциональность -> написали новые тесты ->
проверили успешность завершения команды gradle build -> git add -> git commit
-> git push.
- Наличие тестов обязательно (см. модульное тестирование, unit testing). Тесты
должны проверять корректность выполненного задания и покрывать более
80% написанного кода. Используйте библиотеку ``JUnit 5``.
- Для автоматической проверки покрытия подключите ``Gradle`` плагин ``jacoco``.
Инструкция тут.
- Наличие документации ``javadoc`` обязательно.
- По завершении очередной задачи для ее сдачи еще раз убедитесь в
соответствии всем требованиям в условии и требованиям выше. На GitHub
создайте pull request (далее PR) из рабочей ветки ``task-S-N`` в ветку main с
названием ``Task_S_N`` (совпадает с названием проекта). В качестве ``Reviewer``
укажите преподавателя семинарских занятий, так вы автоматически
запросите у него ревью. Пример PR’а тут.
- По завершении ревью преподаватель либо запрашивает правки, либо
одобряет ваши изменения. И то, и другое отображается в интерфейсе GitHub. В
первом случае вы должны внести необходимые правки и запросить
очередную итерацию ревью, во втором -- задача считается сданной, можете
сделать ``merge`` в репозиторий и приступать к следующей задаче.
- Правки можно обсуждать, но самостоятельно закрывать обсуждение (resolve),
инициированное преподавателем, можно только с его разрешения.

## Система оценивания
У каждой задачи есть ограничения на срок сдачи -- мягкий и жесткий дедлайны. Их
точные даты для вашей группы будут озвучены на первом семинаре. Для
прохождения мягкого дедлайна нужно показать первую версию решения и
согласовать с преподавателем дальнейшие доработки. На этом этапе необязательно,
но удобно сразу оформлять изменения в PR. Для прохождения жесткого дедлайна в
указанную дату или ранее готовый PR с решением должен быть одобрен
преподавателем семинарских занятий.

#### В зависимости от соблюдения дедлайнов меняется оценка за решение. За каждую задачу вы получаете:
- 1 балл, если уложились и в мягкий, и в жесткий дедлайны;
- 0.5 баллов, если уложились только в жесткий дедлайн;
- 0 баллов, если не уложились в дедлайны, но сдали задачу;
- -0.5 баллов, если не сдали задачу в течение семестра.
Дополнительный +1 балл можно получить:
- За реализацию дополнительного условия, если таковое указано в задаче (не
привязано к дедлайнам).
- За успешную сдачу пятиминуток на знание лекционного материала.

## Итоговая оценка за семестр зависит от суммы заработанных баллов:
- “отлично”: 8+ баллов и сделанная задача 2.2
- "хорошо": 6+ баллов
- "удовлетворительно": 4+ балла

<br> <br>

# Инструкции для подключения Workflow

## Workflow файл

Для подключения workflow необходимо скопировать директорию `.github` в корень вашего репозитория (Можно запушить сразу в `master`/`main`).  
Теперь после каждого пуша в pull request (пуш в ветку, на которой открыт pull request) будут запускаться проверки, которые включают:  
- Сборка Gradle проекта
- Запуск тестов
- Проверка покрытия кода тестами (должно быть больше 80%, отчёт будет прикреплён как комментарий в pull request)
- Генерация javadoc и публикация их в ветку `gh-pages`
- Проверка кода на соответствие Google Java Style (замечания будут отображаться во вкладке `Files changed`)

ВАЖНО: при открытии pull request его имя должно совпадать с именем папки, в которой находится код вашей лабораторной, например, `Task_1_1_1` для лабораторной в первом семестре, из первого раздела номер один.  

## Настройки репозитория

Для того, чтобы у вас был доступ к опубликованной документации лабораторных, необходимо зайти в `Settings > Pages` и в секции `Build and deployment` выбрать следующие параметры  
![image](https://user-images.githubusercontent.com/34095512/188311837-7168faff-b67b-4a58-afeb-1ba15552f658.png)

После этого вы сможете открывать вашу документацию по адресу `https://<Github username>.github.io/OOP/<Lab name>/`

## Настройка `build.gradle`

Для составления отчётов по покрытию тестами вашего кода, необходимо подключить в ваш gradle скрипт плагин `jacoco`. Для этого:
- Добавьте строчку `id 'jacoco'` в плагины
```Groovy
plugins {
    id 'java'
    id 'jacoco'
}
```
- Измените задачу `jacocoTestReport`, чтобы она генерировала отчёт в формате `.xml`, а не только `.html` (достаточно скопировать код в конец вашего `build.gradle`)
```Groovy
jacocoTestReport {
    reports {
        xml.required = true
    }
}
```
