package it.academy.service.services.impl;

import it.academy.service.dto.DeviceTypeDTO;
import it.academy.service.dto.TablePageReq;
import it.academy.service.dto.forms.TablePage;
import it.academy.service.repositories.DeviceTypeRepository;
import it.academy.service.services.DeviceTypeService;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.AutoConfigureDataJdbc;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static it.academy.utils.MockConstant.*;
import static it.academy.utils.MockUtils.buildDeviceType;
import static it.academy.utils.MockUtils.buildDeviceTypeList;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureDataJdbc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DeviceTypeServiceImplTest {
    @Autowired
    private DeviceTypeRepository deviceTypeRepository;

    @Autowired
    private DeviceTypeService deviceTypeService;

    @AfterEach
    void clearDB() {
        deviceTypeRepository.deleteAll();
    }


    @Test
    public void createOrUpdateTest() {
        DeviceTypeDTO forSave = buildDeviceType(DEVICE_TYPE_NAME);
        DeviceTypeDTO actual = deviceTypeService.createOrUpdate(forSave);

        Assertions.assertNotEquals(forSave.getId(), actual.getId());
        Assertions.assertEquals(DEVICE_TYPE_NAME, actual.getName());
        Assertions.assertEquals(forSave.getIsActive(), actual.getIsActive());

        actual.setName(DEVICE_TYPE_NEW_NAME);

        DeviceTypeDTO afterUpdate = deviceTypeService.createOrUpdate(actual);
        Assertions.assertEquals(actual.getId(), afterUpdate.getId());
        Assertions.assertEquals(DEVICE_TYPE_NEW_NAME, afterUpdate.getName());

    }

    @Test
    public void deleteTest() {
        DeviceTypeDTO forSave = buildDeviceType(DEVICE_TYPE_NAME);
        DeviceTypeDTO actual = deviceTypeService.createOrUpdate(forSave);

        deviceTypeService.delete(actual.getId());

        DeviceTypeDTO result = deviceTypeService.findById(actual.getId());

        Assertions.assertNull(result);
    }

    @Test
    public void findForPageTest() {
        List<DeviceTypeDTO> accounts = buildDeviceTypeList(LIST_SIZE);
        accounts.get(0).setName(DEVICE_TYPE_NEW_NAME);
        accounts.forEach(deviceTypeService::createOrUpdate);

        TablePageReq req = new TablePageReq(null, FIRST_PAGE, DEVICE_TYPE_SORT_FIELD, Sort.Direction.ASC.name(), StringUtils.EMPTY);

        TablePage<DeviceTypeDTO> firstPageWithoutSearch =
                deviceTypeService.findForPage(req);

        TablePageReq reqByDeviceTypeName = new TablePageReq(null, FIRST_PAGE, DEVICE_TYPE_SORT_FIELD, Sort.Direction.ASC.name(), DEVICE_TYPE_NEW_NAME);

        TablePage<DeviceTypeDTO> pageDataByName = deviceTypeService.findForPage(reqByDeviceTypeName);

        Assertions.assertEquals(firstPageWithoutSearch.getListForTable().size(), PAGE_SIZE);
        Assertions.assertEquals(pageDataByName.getListForTable().size(), PAGE_SIZE_BY_SEARCH);
    }

}