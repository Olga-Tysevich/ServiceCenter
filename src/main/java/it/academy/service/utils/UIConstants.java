package it.academy.service.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UIConstants {
    //common
    public static final int PAGE_SIZE = 20;
    public static final String ERROR_MESSAGE = "errorMessage";

    //pages
    public static final String LOGIN_PAGE = "login";
    public static final String MAIN_PAGE = "mainPage";
    public static final String REPAIR_TABLE = "repairTable";
    public static final String REPAIR_TABLE_PAGE = "/repairs/page/";
    public static final String ADD_REPAIR_PAGE = "addRepair";
    public static final String REPAIR_PAGE = "repairPage";
    public static final String UPDATE_REPAIR_PAGE = "updateRepair";
    public static final String REPAIR_ORDER_LIST_PAGE = "orderList";
    public static final String COMPLETE_REPAIR_PAGE = "completeRepair";
    public static final String REPAIR_SPARE_PARTS_PAGE = "repairSpareParts";
    public static final String REPAIR_TYPE_TABLE = "repairTypeTable";
    public static final String REPAIR_TYPE_TABLE_PAGE = "/repair-types/page/";
    public static final String ADD_REPAIR_TYPE_PAGE = "addRepairType";
    public static final String UPDATE_REPAIR_TYPE_PAGE = "updateRepairType";
    public static final String MODEL_TABLE_PAGE = "/models/page/";
    public static final String SPARE_PART_ORDER_TABLE_PAGE = "/spare-part-orders/page/";
    public static final String DEVICE_TYPE_TABLE_PAGE = "/device-types/page/";
    public static final String BRAND_TABLE_PAGE = "/brands/page/";
    public static final String SPARE_PART_TABLE_PAGE = "/spare-parts/page/";
    public static final String SPARE_PART_TABLE = "sparePartTable";
    public static final String ADD_SPARE_PART_PAGE = "addSparePart";
    public static final String UPDATE_SPARE_PART_PAGE = "updateSparePart";
    public static final String UPDATE_SPARE_PART_ORDER_PAGE = "updateSparePartOrder";
    public static final String MODEL_TABLE = "modelTable";
    public static final String DEVICE_TYPE_TABLE = "deviceTypeTable";
    public static final String BRAND_TABLE = "brandTable";
    public static final String ADD_MODEL_PAGE = "addModel";
    public static final String ADD_DEVICE_TYPE_PAGE = "addDeviceType";
    public static final String ADD_BRAND_PAGE = "addBrand";
    public static final String UPDATE_MODEL_PAGE = "updateModel";
    public static final String UPDATE_DEVICE_TYPE_PAGE = "updateDeviceType";
    public static final String UPDATE_BRAND_PAGE = "updateBrand";
    public static final String SERVICE_CENTER_TABLE_PAGE = "/service-centers/page/";
    public static final String SERVICE_CENTER_TABLE = "serviceCenterTable";
    public static final String ADD_SERVICE_CENTER_PAGE = "addServiceCenter";
    public static final String UPDATE_SERVICE_CENTER_PAGE = "updateServiceCenter";
    public static final String ACCOUNT_TABLE = "accountTable";
    public static final String SPARE_PART_ORDER_TABLE = "sparePartOrderTable";
    public static final String ADD_SERVICE_CENTER_ACCOUNT_PAGE = "addServiceCenterAccount";
    public static final String ADD_ADMIN_ACCOUNT_PAGE = "addAdminAccount";
    public static final String UPDATE_ACCOUNT_PAGE = "updateAccount";

    //attributes
    public static final String REPAIR = "repair";
    public static final String REPAIR_ID = "repairId";
    public static final String MODEL_ID = "modelId";
    public static final String REPAIR_NUMBER = "repairNumber";
    public static final String CATEGORY_LIST = "categoryList";
    public static final String BRAND_LIST = "brandList";
    public static final String MODEL_LIST = "modelList";
    public static final String DEVICE_TYPE_LIST = "deviceTypeList";
    public static final String ORDER_LIST = "orderList";
    public static final String SPARE_PART_LIST = "sparePartList";
    public static final String REPAIR_TYPE_LIST = "repairTypeList";
    public static final String REPAIR_TYPE = "repairType";
    public static final String SPARE_PART = "sparePart";
    public static final String SPARE_PART_ORDER = "sparePartOrder";
    public static final String MODEL = "model";
    public static final String DEVICE_TYPE = "deviceType";
    public static final String BRAND = "brand";
    public static final String SERVICE_CENTER_ID = "serviceCenterId";
    public static final String SERVICE_CENTER = "serviceCenter";
    public static final String SERVICE_CENTERS = "serviceCenters";
    public static final String ACCOUNT = "account";
    public static final String REPAIR_SPARE_PARTS = "repairSpareParts";

    //redirects
    public static final String REPAIRS_PAGE_REDIRECT = "redirect:/repairs";
    public static final String SPARE_PARTS_PAGE_REDIRECT = "redirect:/spare-parts";
    public static final String SERVICE_CENTERS_PAGE_REDIRECT = "redirect:/service-centers";
    public static final String ACCOUNTS_PAGE_REDIRECT = "redirect:/accounts";
    public static final String DEVICE_TYPES_PAGE_REDIRECT = "redirect:/device-types";
    public static final String BRANDS_PAGE_REDIRECT = "redirect:/brands";
    public static final String SPARE_PART_ORDER_PAGE_REDIRECT = "redirect:/spare-part-orders";

    //message
    public static final String UPDATE_SUCCESSFULLY = "Обновление успешно";
    public static final String UPDATE_FAILED = "Обновление не удалось";
    public static final String EMAIL_ALREADY_EXISTS = "Email уже зарегистрирован!";
    public static final String MODEL_ALREADY_EXISTS = "Модель уже добавлена!";
    public static final String SPARE_PART_ALREADY_EXISTS = "Запчасть уже добавлена!";
    public static final String DEVICE_TYPE_ALREADY_EXISTS = "Тип устройства уже добавлен!";
    public static final String BRAND_ALREADY_EXISTS = "Бренд уже добавлен!";
    public static final String REPAIR_TYPE_ALREADY_EXISTS = "Тип ремонта уже добавлен!";
    public static final String USER_IS_BLOCKED = "Пользователь заблокирован!";
    public static final String DELETE_FAILED = "Удаление невозможно! Есть связанные записи";
    public static final String ORDER_IS_EMPTY = "Заказ не может быть пустой!";
    public static final String DEPARTURE_DATE_IS_EMPTY = "Дата отправки не должна быть пустой!";

}
