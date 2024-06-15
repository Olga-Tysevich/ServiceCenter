package it.academy.service.services.impl;

import it.academy.service.dto.ServiceCenterDTO;
import it.academy.service.dto.TablePageReq;
import it.academy.service.dto.forms.TablePage;
import it.academy.service.repositories.AccountRepository;
import it.academy.service.repositories.ServiceCenterRepository;
import it.academy.service.services.ServiceCenterService;
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
import static it.academy.utils.MockUtils.buildServiceCenter;
import static it.academy.utils.MockUtils.buildServiceCenterList;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureDataJdbc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ServiceCenterServiceImplTest {
    @Autowired
    private ServiceCenterRepository serviceCenterRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ServiceCenterService serviceCenterService;

    @AfterEach
    public void clearDB() {
        accountRepository.deleteAll();
        serviceCenterRepository.deleteAll();
    }

    @Test
    public void createOrUpdateTest() {
        ServiceCenterDTO serviceCenterDTO = buildServiceCenter(SERVICE_CENTER_NAME);
        ServiceCenterDTO actual = serviceCenterService.createOrUpdate(serviceCenterDTO);

        Assertions.assertNotEquals(serviceCenterDTO.getId(), actual.getId());
        Assertions.assertEquals(serviceCenterDTO.getServiceName(), actual.getServiceName());
        Assertions.assertEquals(serviceCenterDTO.getBankName(), actual.getBankName());
        Assertions.assertEquals(serviceCenterDTO.getBankAccount(), actual.getBankAccount());
        Assertions.assertEquals(serviceCenterDTO.getBankCode(), actual.getBankCode());
        Assertions.assertEquals(serviceCenterDTO.getBankAddress(), actual.getBankAddress());
        Assertions.assertEquals(serviceCenterDTO.getFullName(), actual.getFullName());
        Assertions.assertEquals(serviceCenterDTO.getLegalAddress(), actual.getLegalAddress());
        Assertions.assertEquals(serviceCenterDTO.getActualAddress(), actual.getActualAddress());
        Assertions.assertEquals(serviceCenterDTO.getPhone(), actual.getPhone());
        Assertions.assertEquals(serviceCenterDTO.getEmail(), actual.getEmail());
        Assertions.assertEquals(serviceCenterDTO.getTaxpayerNumber(), actual.getTaxpayerNumber());
        Assertions.assertEquals(serviceCenterDTO.getRegistrationNumber(), actual.getRegistrationNumber());
        Assertions.assertEquals(serviceCenterDTO.getIsActive(), actual.getIsActive());

        actual.setServiceName(SERVICE_CENTER_NEW_NAME);

        ServiceCenterDTO afterUpdate = serviceCenterService.createOrUpdate(actual);
        Assertions.assertEquals(actual.getId(), afterUpdate.getId());
        Assertions.assertEquals(SERVICE_CENTER_NEW_NAME, afterUpdate.getServiceName());

    }

    @Test
    public void deleteTest() {
        ServiceCenterDTO serviceCenterDTO = buildServiceCenter(SERVICE_CENTER_NAME);
        ServiceCenterDTO result = serviceCenterService.createOrUpdate(serviceCenterDTO);

        serviceCenterService.delete(result.getId());

        ServiceCenterDTO actual = serviceCenterService.findById(result.getId());

        Assertions.assertNull(actual);
    }

    @Test
    public void findForPageTest() {
        List<ServiceCenterDTO> forSave = buildServiceCenterList(LIST_SIZE);
        forSave.get(0).setServiceName(SERVICE_CENTER_NAME_FOR_SEARCH);
        forSave.forEach(serviceCenterService::createOrUpdate);

        TablePageReq req = new TablePageReq(null, FIRST_PAGE, SERVICE_CENTER_SORT_FIELD, Sort.Direction.ASC.name(), StringUtils.EMPTY);

        TablePage<ServiceCenterDTO> firstPageWithoutSearch =
                serviceCenterService.findForPage(req);

        TablePageReq reqByServiceName = new TablePageReq(null, FIRST_PAGE, SERVICE_CENTER_SORT_FIELD, Sort.Direction.ASC.name(), SERVICE_CENTER_NAME_FOR_SEARCH);

        TablePage<ServiceCenterDTO> pageDataByServiceName = serviceCenterService.findForPage(reqByServiceName);

        Assertions.assertEquals(firstPageWithoutSearch.getListForTable().size(), PAGE_SIZE);
        Assertions.assertEquals(pageDataByServiceName.getListForTable().size(), PAGE_SIZE_BY_SEARCH);
    }

    @Test
    public void findActiveServiceCentersTest() {
        List<ServiceCenterDTO> forSave = buildServiceCenterList(LIST_SIZE);
        forSave.get(0).setIsActive(false);
        forSave.forEach(serviceCenterService::createOrUpdate);

        List<ServiceCenterDTO> resultByIsActiveTrue = serviceCenterService.findActiveServiceCenters();

        Assertions.assertEquals(resultByIsActiveTrue.size(), LIST_SIZE - 1);
    }

}