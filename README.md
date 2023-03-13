# Project4-Hibernate
## Краткое описание проекта
Проект учебный, предназначен для решения проблемы запроса списка городов из предоставленной БД MySQL. 
Предложеное решение - сохранения часто запращиваемых данных в Redis (in memory storage типа ключ-значение).

###Описание основных классов
1. В пакете src/main/java/ru/javarush/hibernate/domain содержатся классы мапинга на одноименные таблицы в БД.
2. В пакете src/main/java/ru/javarush/hibernate/dao содержатся классы для получения необходимых данных из таблиц БД
3. В пакете src/main/java/ru/javarush/hibernate/feature содержатся классы для описания необходимых сущностей для Redis и MySQL
4. В пакете src/main/java/ru/javarush/hibernate/repository инициализирует работу приложения (параметры подключения к БД MySQL, Redis, создание сессии, необходимых объектов)
5. В пакете src/main/java/ru/javarush/hibernate/service описывается логика получения данных их каждой БД
6. В пакете src/main/resources содержится файл spy.properties для просмотра запросов с параметрами, которые выполняет Hibernate

###Описание логики работы приложения
В классе Main инициализируются объекты приложения.
Далее из БД выбираются данные по id (город вместе со страной).
Осуществляется сравнение времени, которое требуется в 2х хранилищах для получения данных по одним и тем же id городов.

Результат моего теста:
Connected to Redis

Redis:	98 ms
MySQL:	76 ms
���. 13, 2023 1:29:18 AM org.hibernate.engine.jdbc.connections.internal.DriverManagerConnectionProviderImpl$PoolState stop
INFO: HHH10001008: Cleaning up connection pool [jdbc:p6spy:mysql://localhost:3306/world]

Process finished with exit code 0

###Используемые средства и технологии
- IDEA Ultimate
- Maven (для сборки проекта)
- Docker (для запуска MySQL, Redis)
- Workbench (клиент для MySQL)