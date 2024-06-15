package it.academy.service.contollers;

import it.academy.service.dto.BrandDTO;
import it.academy.service.dto.TablePageReq;
import it.academy.service.dto.forms.TablePage;
import it.academy.service.services.BrandService;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static it.academy.service.utils.Constants.TABLE_PAGE;
import static it.academy.service.utils.UIConstants.*;
import static it.academy.utils.MockConstant.*;
import static it.academy.utils.MockUtils.buildBrand;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class BrandControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BrandService brandService;

    @Autowired
    private BrandController brandController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(brandController).build();
    }

    @Test
    void showPageTest() throws Exception {

        TablePage<BrandDTO> pageForDisplay = mock(TablePage.class);
        TablePageReq req = new TablePageReq(null, FIRST_PAGE, BRAND_SORT_FIELD, Sort.Direction.ASC.name(), StringUtils.EMPTY);

        when(brandService.findForPage(req)).thenReturn(pageForDisplay);

        mockMvc.perform(get("/brands/page/{pageNum}", FIRST_PAGE)
                .param(SORT_FIELD, BRAND_SORT_FIELD)
                .param(SORT_DIR, Sort.Direction.ASC.name())
                .param(KEYWORD, StringUtils.EMPTY))
                .andExpect(status().isOk())
                .andExpect(view().name(BRAND_TABLE))
                .andExpect(model().attribute(TABLE_PAGE, pageForDisplay));

        verify(brandService, times(1)).findForPage(req);
    }


    @Test
    void showCreateAdminPageTest() throws Exception {
        mockMvc.perform(get("/brands/brand-create"))
                .andExpect(status().isOk())
                .andExpect(view().name(ADD_BRAND_PAGE));
    }


    @Test
    void createTest() throws Exception {
        BrandDTO brandDTO = buildBrand(String.format(BRAND_NAME, 1));
        when(brandService.createOrUpdate(brandDTO)).thenReturn(brandDTO);

        MockHttpServletRequestBuilder request = post("/brands/brand-create")
                .flashAttr("brandDTO", brandDTO);

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/brands"));
    }


    @Test
    void showUpdatePageTest() throws Exception {
        BrandDTO brandDTO = mock(BrandDTO.class);

        when(brandService.findById(FIRST_ID)).thenReturn(brandDTO);

        mockMvc.perform(get("/brands/brand-update/{id}", FIRST_ID))
                .andExpect(status().isOk())
                .andExpect(view().name(UPDATE_BRAND_PAGE))
                .andExpect(model().attribute(BRAND, brandDTO));

        verify(brandService, times(1)).findById(FIRST_ID);
    }

    @Test
    void updateTest() throws Exception {
        BrandDTO brandDTO = buildBrand(String.format(BRAND_NAME, 1));
        when(brandService.createOrUpdate(brandDTO)).thenReturn(brandDTO);

        MockHttpServletRequestBuilder request = post("/brands/brand-update")
                .flashAttr("brandDTO", brandDTO);

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/brands"));

        verify(brandService, times(1)).createOrUpdate(brandDTO);
    }

    @Test
    void deleteTest() throws Exception {
        mockMvc.perform(get("/brands/brand-delete/{id}", FIRST_ID))
                .andExpect(status().isOk())
                .andExpect(view().name(BRAND_TABLE));
    }

}