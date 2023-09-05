# Приложение "Стоматологическая клиника"


## Описание
В данном репозитории находится приложение "Стоматологической клиники". Данное приложение разработано в качестве курсовой работы в 6-ом семестре обучения в РТУ МИРЭА по дисциплине **«Разработка клиент-серверных приложений»**
##  1. Описание функциональности

Приложение использует разделение ролей пользователей:
- Администратор
- Врач
- Пациент

# Функции для каждой роли:

**Неавторизованные пользователи**
- Просмотр главной страницы сайта с краткой информацией о клинике
- Просмотр прейскуранта услуг
- Авторизация
- Регистрацию с ролью «Пациент»

**Администратор**
- Добавление нового сотрудника
- Изменение сведения о сотруднике
- Архивирование (удаление) сотрудника
- Создание и изменение расписания приема врачей (через изменения сведений о сотрудниках)
- Создание новой платной медицинской услуги
- Изменение данных о платной медицинской услуге
- Архивирование (удаление) платной медицинской услуги
- Создание нового пользователя
- Присвоение ролей пользователю
- Изменение данных пользователей
- Блокировка (архивирование) пользователя

**Врач**
- Просмотр списка записанных пациентов
- Добавление заключения
- Добавление списка оказанных платных медицинских услуг
- Просмотр ранее составленных записей

**Пациент**
- Просмотр прейскуранта стоматологии
- Просмотр расписания приема врачей
- Запись на прием к врачу
- Отмена записи
- Просмотр выполненных платных медицинских услуг и их стоимости
- Просмотр заключения врача
- Изменение своих данных

##  2. Требования к системе

Для сборки и запуска проекта должны выполняться следующие системные требования:

Приложение работает на всех ОС, которые поддерживают работу Docker/

Все таблицы создадутся автоматически.
Также необходимо освободить порты 8086 и 5430.

По умолчанию создана учетная запись администратора:
- логин: `admin`
- пароль: `admin`

По умолчанию создана учетная запись врача:
- логин: `doctor`
- пароль: `doctor`

По умолчанию создана учетная запись пациента:
- логин: `patient`
- пароль: `patient`


## 4.Установка и настройка

Необходимо склонировать данный репозиторий, с помощью командной строки перейти в корневую папку проекта и ввести команду `docker-compose up --build`
Серверная часть запуститься по адресу http://localhost:8086/
Само приложение запустится по адресу  http://localhost/

Коллекция запросов для тестирования доступна по ссылке https://gold-firefly-583647.postman.co/workspace/Team-Workspace~63e788f6-1c0b-437d-8635-9e5a5aa63865/collection/20983878-56711c53-7817-443a-b940-cb80c5e5b15b?action=share&creator=20983878

[Руководство пользователя][doc]

[doc]: Руководство%20пользователя.pdf