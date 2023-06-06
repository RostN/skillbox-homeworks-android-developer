package com.example.hw12.ui.main

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PersonalInfo(
    @Json(name = "results") val results: List<User>
)

//Класс отвечающий за всю инф о пользователе
@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "gender") var gender: String? = null, //Пол
    @Json(name = "name") var name: Name, //Имя +
    @Json(name = "location") var location: Location, //Локация +
    @Json(name = "email") var email: String? = null, //Электронка
    @Json(name = "login") var login: Login, //Логин
    @Json(name = "dob") var dob: DOB, //Дата рождения
    @Json(name = "registered") var registered: Registered, //Инф об регистрации
    @Json(name = "phone") var phone: String? = null, //Телефон
    @Json(name = "cell") var cell: String? = null, //хз что это даже :)
    @Json(name = "id") var id: ID, //Идентификатор
    @Json(name = "picture") var picture: Picture, //Фотка
    @Json(name = "nat") var nat: String? = null //Национальность
)

//Класс отвечающий за Фото
@JsonClass(generateAdapter = true)
data class Picture(
    @Json(name = "large") var large: String? = null, //Большая фотка
    @Json(name = "medium") var medium: String? = null, //Средняя фотка
    @Json(name = "thumbnail") var thumbnail: String? = null //Мелкая фотка
)

//Класс отвечающий за ИД
@JsonClass(generateAdapter = true)
data class ID(
    @Json(name = "name") var name: String? = null, //ИД
    @Json(name = "value") var value: String? = null //value
)

//Класс отвечающий за инф о регистрации
@JsonClass(generateAdapter = true)
data class Registered(
    @Json(name = "date") var date: String? = null, //Дата регистрации
    @Json(name = "age") var age: String? = null //Сколько прошло лет полных
)

//Класс отвечающий возраст
@JsonClass(generateAdapter = true)
data class DOB(
    @Json(name = "date") var date: String? = null, //ДР
    @Json(name = "age") var age: String? = null //Возраст
)

//Класс отвечающий за логин
@JsonClass(generateAdapter = true)
data class Login(
    @Json(name = "uuid") var uuid: String? = null, //UUID
    @Json(name = "username") var username: String? = null, //Имя пользователя
    @Json(name = "password") var password: String? = null, //Пароль
    @Json(name = "salt") var salt: String? = null, //Какая-то соль :)
    @Json(name = "md5") var md5: String? = null, //md5
    @Json(name = "sha1") var sha1: String? = null, //sha1
    @Json(name = "sha256") var sha256: String? = null //sha256
)

//Класс отвечающий за локацию
@JsonClass(generateAdapter = true)
data class Location(
    @Json(name = "country") var country: String? = null, //Страна
    @Json(name = "postcode") var postcode: String? = null, //Почтовый индекс
    @Json(name = "state") var state: String? = null, //Регион
    @Json(name = "city") var city: String? = null, //Город
    @Json(name = "street") var street: Street? = null, //Удица
    @Json(name = "coordinates") var coordinates: Coordinates, //Координаты
    @Json(name = "timezone") var timezone: Timezone, //Временная зона
)

//Класс отвечающий за временную зону
@JsonClass(generateAdapter = true)
data class Timezone(
    @Json(name = "offset") var offset: String? = null, //Часовой полис
    @Json(name = "description") var description: String? = null, //Описание
)

//Класс отвечающий за координаты
@JsonClass(generateAdapter = true)
data class Coordinates(
    @Json(name = "latitude") var latitude: String? = null, //широта
    @Json(name = "longitude") var longitude: String? = null, //долгота
)

//Класс отвечающий за инф об улице
@JsonClass(generateAdapter = true)
data class Street(
    @Json(name = "number") var number: Int? = null, //Индекс
    @Json(name = "name") var name: String? = null //Название улицы
)

//Класс отвечающий за имя
@JsonClass(generateAdapter = true)
data class Name(
    @Json(name = "title") var title: String? = null, //Форма обращения
    @Json(name = "first") var first: String? = null, //Имя
    @Json(name = "last") var last: String? = null //Фамилия
)