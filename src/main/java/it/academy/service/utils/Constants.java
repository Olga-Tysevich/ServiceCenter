package it.academy.service.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {
    public static final int FIRST_PAGE = 1;
    public static final Long ID_FOR_CHECK = 0L;
    public static final Long DEFAULT_ID = 1L;
    public static final Integer ZERO = 0;
    public static final String PAGE_NUM = "pageNum";
    public static final String SORT_FIELD = "sortField";
    public static final String SORT_DIR = "sortDir";
    public static final String ASC = "ASC";
    public static final String KEYWORD = "keyword";
    public static final String TABLE_PAGE = "tablePage";
    public static final String DEVICE_DESCRIPTION_PATTERN = "%s %s %s";
    public static final String LIKE_QUERY_PATTERN = "%%%s%%";
    public static final String SPACE = " ";
    public static final String OBJECT_ID = "id";
    public static final String REPAIR_STATUS = "status";
    public static final String METHOD_STARTED = "Method started: ";
    public static final String METHOD_FINISHED = "Method finished: ";
    public static final String EXCEPTION_IN_METHOD = "Exception in method: ";

    public static final String INVALID_ROLE = "Некорректная роль!";
    public static final String EMAIL_IS_EMPTY = "Email не может быть пустым!";
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9-.]+@([a-zA-Z-]+\\.)+[a-zA-Z-]{2,4}$";
    public static final String INVALID_EMAIL = "Ведите email в формате email@mail.com";
    public static final String USER_NAME_IS_EMPTY = "Имя пользователя не может быть пустым!";
    public static final String LAST_NAME_IS_EMPTY = "Фамилия пользователя не может быть пустой!";
    public static final String NAME_IS_EMPTY = "Название не должно быть пустым!";
    public static final String SERIAL_NUMBER_IS_EMPTY = "Серийный номер обязателеный параметр!";
    public static final String DEFECT_DESCRIPTION_IS_EMPTY = "Описание дефекта обязателеный параметр!";
    public static final String REPAIR_NUMBER_IS_EMPTY = "Номер ремонта обязателеный параметр!";
    public static final String DATE_OF_SALE_IS_EMPTY = "Дата продажи обязателеный параметр!";
    public static final String SALESMAN_NAME_IS_EMPTY = "Название продавца обязателеный параметр!";
    public static final String SALESMAN_PHONE_IS_EMPTY = "Телефон продавца обязателеный параметр!";
    public static final String BUYER_NAME_IS_EMPTY = "Имя покупателя обязателеный параметр!";
    public static final String BUYER_SURNAME_IS_EMPTY = "Фамилия покупателя обязателеный параметр!";
    public static final String BUYER_PHONE_IS_EMPTY = "Телефон покупателя обязателеный параметр!";
    public static final String CODE_IS_EMPTY = "Код не может быть пустым!";
    public static final String LEVEL_IS_EMPTY = "Уровень не может быть пустым!";
    public static final String BANK_NAME_IS_EMPTY = "Название банка не может быть пустым!";
    public static final String BANK_ACCOUNT_IS_EMPTY = "Банковский аккаунт не может быть пустым!";
    public static final String BANK_CODE_IS_EMPTY = "Код банка не может быть пустым!";
    public static final String BANK_ADDRESS_IS_EMPTY = "Адреес банка не может быть пустым!";
    public static final String FULL_NAME_IS_EMPTY = "Название сервисного центра не может быть пустым!";
    public static final String LEGAL_ADDRESS_IS_EMPTY = "Юр. адрес не может быть пустым!";
    public static final String ACTUAL_ADDRESS_IS_EMPTY = "Фактический адрес не может быть пустым!";
    public static final String PHONE_IS_EMPTY = "Телефон не может быть пустым!";
    public static final String PHONE_REGEX = "^(\\+375|80) ?(29|25|44|33) ?(\\d{3})\\-?(\\d{2})\\-?(\\d{2})$";
    public static final String INVALID_PHONE_NUMBER = "Номер телефона должен быть в формате +375 29 111-11-11/80 29 111-11-11";
    public static final String TAXPAYER_NUMBER_IS_EMPTY = "УНП не может быть пустыми!";
    public static final String REGISTRATION_IS_EMPTY = "ОКПО не может быть пустыми!";
    public static final String MODELS_IS_EMPTY = "Нужно выбрать хотя бы одну модель!";
    public static final String ORDER_ITEMS_IS_EMPTY = "В заказе должна быть хотя бы одна запчасть!";

}
