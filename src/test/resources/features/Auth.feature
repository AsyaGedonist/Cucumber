
#language:ru

Функциональность: Авторизация и перевод 5.000 р на карту *0001

  # пример теста с одним набором параметров
  Структура сценария: : Авторизация в личном кабинете (позитивный)
    Пусть открыта страница с формой авторизации <url>
    Когда пользователь пытается авторизоваться с именем <login> и паролем <password>
    И пользователь вводит проверочный код 'из смс' <verificationCode>
    И пользователь вызывает кнопку Пополнения карты с маскированным номером <maskCard>
    И пользователь вводит сумму <sum> для пополнения с карты <cardNumber>
    Тогда баланс карты с маскированным номером <maskCard> == <balance>
    Примеры:
      | url                     | login   | password    | verificationCode | maskCard              | sum | cardNumber|balance|
      | "http://localhost:9999" | "vasya" | "qwerty123" | "12345"          | "**** **** **** 0001" | 5000|"5559 0000 0000 0002"|15000|
