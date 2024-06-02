package it.academy.service.services.impl;

import it.academy.service.dto.AccountDTO;
import it.academy.service.dto.ServiceCenterDTO;
import it.academy.service.dto.forms.TablePage;
import it.academy.service.entity.RoleEnum;
import it.academy.service.mappers.ServiceCenterMapper;
import it.academy.service.repositories.AccountRepository;
import it.academy.service.repositories.ServiceCenterRepository;
import it.academy.service.services.AccountService;
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
import static it.academy.utils.MockConstant.LIST_SIZE;
import static it.academy.utils.MockUtils.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureDataJdbc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AccountServiceImplTest {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ServiceCenterRepository serviceCenterRepository;

    @AfterEach
    void clearDB() {
        accountRepository.deleteAll();
        serviceCenterRepository.deleteAll();
    }


    @Test
    public void createOrUpdateTest() {
        ServiceCenterDTO forSave = buildServiceCenter(SERVICE_CENTER_NAME);
        Long id = serviceCenterRepository.save(ServiceCenterMapper.INSTANCE.toEntity(forSave)).getId();

        AccountDTO accountDTO = buildAccount(ACCOUNT_EMAIL, RoleEnum.SERVICE_CENTER, id);
        AccountDTO actual = accountService.createOrUpdate(accountDTO);

        Assertions.assertNotEquals(accountDTO.getId(), actual.getId());
        Assertions.assertEquals(id, actual.getServiceCenterId());
        Assertions.assertEquals(accountDTO.getRole(), actual.getRole());
        Assertions.assertEquals(accountDTO.getEmail(), actual.getEmail());
        Assertions.assertEquals(accountDTO.getUserName(), actual.getUserName());
        Assertions.assertEquals(accountDTO.getUserSurname(), actual.getUserSurname());
        Assertions.assertEquals(accountDTO.getIsActive(), actual.getIsActive());

        actual.setUserName(ACCOUNT_NEW_USER_NAME);

        AccountDTO afterUpdate = accountService.createOrUpdate(actual);
        Assertions.assertEquals(actual.getId(), afterUpdate.getId());
        Assertions.assertEquals(ACCOUNT_NEW_USER_NAME, afterUpdate.getUserName());

    }

    @Test
    public void deleteTest() {
        ServiceCenterDTO forSave = buildServiceCenter(SERVICE_CENTER_NAME);
        Long id = serviceCenterRepository.save(ServiceCenterMapper.INSTANCE.toEntity(forSave)).getId();

        AccountDTO accountDTO = buildAccount(ACCOUNT_EMAIL, RoleEnum.SERVICE_CENTER, id);
        AccountDTO result = accountService.createOrUpdate(accountDTO);

        accountService.delete(result.getId());

        AccountDTO actual = accountService.findById(result.getId());

        Assertions.assertNull(actual);
    }

    @Test
    public void findForPageTest() {
        ServiceCenterDTO forSave = buildServiceCenter(SERVICE_CENTER_NAME);
        Long id = serviceCenterRepository.save(ServiceCenterMapper.INSTANCE.toEntity(forSave)).getId();

        List<AccountDTO> accounts = buildAccountList(LIST_SIZE, RoleEnum.SERVICE_CENTER, id);
        accounts.get(0).setUserName(ACCOUNT_NEW_USER_NAME);
        accounts.forEach(accountService::createOrUpdate);

        TablePage<AccountDTO> firstPageWithoutSearch =
                accountService.findForPage(FIRST_PAGE, ACCOUNT_SORT_FIELD, Sort.Direction.ASC.name(), StringUtils.EMPTY);

        TablePage<AccountDTO> pageDataByUserName =
                accountService.findForPage(FIRST_PAGE, ACCOUNT_SORT_FIELD, Sort.Direction.ASC.name(), ACCOUNT_NEW_USER_NAME);

        Assertions.assertEquals(firstPageWithoutSearch.getListForTable().size(), PAGE_SIZE);
        Assertions.assertEquals(pageDataByUserName.getListForTable().size(), PAGE_SIZE_BY_SEARCH);
    }

}