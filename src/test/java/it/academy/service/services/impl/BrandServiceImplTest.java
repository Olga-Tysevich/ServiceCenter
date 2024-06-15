package it.academy.service.services.impl;

import it.academy.service.dto.BrandDTO;
import it.academy.service.dto.TablePageReq;
import it.academy.service.dto.forms.TablePage;
import it.academy.service.repositories.BrandRepository;
import it.academy.service.services.BrandService;
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
import static it.academy.utils.MockConstant.PAGE_SIZE_BY_SEARCH;
import static it.academy.utils.MockUtils.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureDataJdbc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BrandServiceImplTest {
    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private BrandService brandService;

    @AfterEach
    void clearDB() {
        brandRepository.deleteAll();
    }


    @Test
    public void createOrUpdateTest() {
        BrandDTO forSave = buildBrand(BRAND_NAME);
        BrandDTO actual = brandService.createOrUpdate(forSave);

        Assertions.assertNotEquals(forSave.getId(), actual.getId());
        Assertions.assertEquals(BRAND_NAME, actual.getName());
        Assertions.assertEquals(forSave.getIsActive(), actual.getIsActive());

        actual.setName(BRAND_NEW_NAME);

        BrandDTO afterUpdate = brandService.createOrUpdate(actual);
        Assertions.assertEquals(actual.getId(), afterUpdate.getId());
        Assertions.assertEquals(BRAND_NEW_NAME, afterUpdate.getName());

    }

    @Test
    public void deleteTest() {
        BrandDTO forSave = buildBrand(BRAND_NAME);
        BrandDTO actual = brandService.createOrUpdate(forSave);

        brandService.delete(actual.getId());

        BrandDTO result = brandService.findById(actual.getId());

        Assertions.assertNull(result);
    }

    @Test
    public void findForPageTest() {
        List<BrandDTO> accounts = buildBrandList(LIST_SIZE);
        accounts.get(0).setName(BRAND_NEW_NAME);
        accounts.forEach(brandService::createOrUpdate);

        TablePageReq req = new TablePageReq(null, FIRST_PAGE, BRAND_SORT_FIELD, Sort.Direction.ASC.name(), StringUtils.EMPTY);

        TablePage<BrandDTO> firstPageWithoutSearch =
                brandService.findForPage(req);

        TablePageReq reqByBrandName = new TablePageReq(null, FIRST_PAGE, BRAND_SORT_FIELD, Sort.Direction.ASC.name(), BRAND_NEW_NAME);

        TablePage<BrandDTO> pageDataByName =
                brandService.findForPage(reqByBrandName);

        Assertions.assertEquals(firstPageWithoutSearch.getListForTable().size(), PAGE_SIZE);
        Assertions.assertEquals(pageDataByName.getListForTable().size(), PAGE_SIZE_BY_SEARCH);
    }

}