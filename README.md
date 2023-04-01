# Exam Volha Hapanovich 



## 1. Network Layer.
Есть 2 раздельных запроса:
a. Bitbucket
b. Github

Требуется поддержать возможность выполнения обоих запросов параллельно.
Для этого необходимо представить свою реализацию сетевого слоя приложения.

Сетевой слой должен обеспечивать:
Построение BaseURL (Done)
Добавление параметров в запрос (Done)
Обработку основных ошибок удобным для пользователя образом (поп-ап). (TODO: handle response's status codes. Handle no/bad internet connection)
## 2. User Interface.
Принцип построения интерфейса - любой.
Дизайн произвольный, но он должен учитывать следующие требования.

a. Приложение должно состоять из 2х экранов.
i. Список загруженных объектов в виде таблицы или коллекции. (Done)
ii. Экран с деталями о конкретном репозитории (Done)
(Открывается по нажатию на ячейку).

b. Ячейка должна содержать:
i. Иконка пользователя. (Done)
ii. Заголовок. (Done)
iii. Описание репозитория. (Done)
iv. Показатель, откуда пришли данные (Bitbucket / Github). (Done)

3. Дополнительная функциональность.
a. Навигация по проекту должна использовать панель навигации. (Done)
b. Коллекция / таблица должна поддерживать swipe-to-refresh. (Done)
c. Необходимо реализовать сортировку репозиториев по:
i. Источнику информации. (Done)
ii. По алфавиту / в обратном порядке / восстановление порядка. (Done)

d. Необходимо реализовать фильтрацию по:
i. Имени пользователя. (Done)
ii. Названию репозитория. (Done)
iii. Источнику информации. (Done)
