package it.academy.service.contollers;

import it.academy.service.dto.ServiceCenterDTO;
import it.academy.service.dto.TablePageReq;
import it.academy.service.dto.forms.TablePage;
import it.academy.service.services.ServiceCenterService;
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

import static it.academy.service.utils.UIConstants.*;
import static it.academy.utils.MockConstant.*;
import static it.academy.utils.MockUtils.buildServiceCenter;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class ServiceCenterControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceCenterService serviceCenterService;

    @Autowired
    private ServiceCenterController serviceCenterController;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(serviceCenterController).build();
    }

    @Test
    void showPageTest() throws Exception {

        TablePage<ServiceCenterDTO> pageForDisplay = mock(TablePage.class);
        TablePageReq req = new TablePageReq(null, FIRST_PAGE, SERVICE_CENTER_SORT_FIELD, Sort.Direction.ASC.name(), StringUtils.EMPTY);

        when(serviceCenterService.findForPage(req)).thenReturn(pageForDisplay);

        mockMvc.perform(get("/service-centers/page/{pageNum}", FIRST_PAGE)
                .param(SORT_FIELD, SERVICE_CENTER_SORT_FIELD)
                .param(SORT_DIR, Sort.Direction.ASC.name())
                .param(KEYWORD, StringUtils.EMPTY))
                .andExpect(status().isOk())
                .andExpect(view().name(SERVICE_CENTER_TABLE))
                .andExpect(model().attribute(TABLE_PAGE, pageForDisplay));

        verify(serviceCenterService, times(1)).findForPage(req);
    }


    @Test
    void showCreatePageTest() throws Exception {
        mockMvc.perform(get("/service-centers/service-center-create"))
                .andExpect(status().isOk())
                .andExpect(view().name(ADD_SERVICE_CENTER_PAGE));
    }

    @Test
    void createTest() throws Exception {
        ServiceCenterDTO serviceCenterDTO = buildServiceCenter(SERVICE_CENTER_NAME);
        when(serviceCenterService.createOrUpdate(serviceCenterDTO)).thenReturn(serviceCenterDTO);

        MockHttpServletRequestBuilder request = post("/service-centers/service-center-create")
                .flashAttr("serviceCenterDTO", serviceCenterDTO);

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/service-centers"));
    }

    @Test
    void createFailedTest() throws Exception {
        ServiceCenterDTO serviceCenterDTO = buildServiceCenter(SERVICE_CENTER_NAME);
        serviceCenterDTO.setEmail(SERVICE_CENTER_INVALID_EMAIL);
        performSaveOrUpdateForFailedCase(serviceCenterDTO, "/service-centers/service-center-create", ADD_SERVICE_CENTER_PAGE);
    }

    @Test
    void showUpdatePageTest() throws Exception {
        ServiceCenterDTO serviceCenterDTO = mock(ServiceCenterDTO.class);

        when(serviceCenterService.findById(FIRST_ID)).thenReturn(serviceCenterDTO);

        mockMvc.perform(get("/service-centers/service-center-update/{id}", FIRST_ID))
                .andExpect(status().isOk())
                .andExpect(view().name(UPDATE_SERVICE_CENTER_PAGE))
                .andExpect(model().attribute(SERVICE_CENTER, serviceCenterDTO));

        verify(serviceCenterService, times(1)).findById(FIRST_ID);
    }

    @Test
    void updateTest() throws Exception {
        ServiceCenterDTO serviceCenterDTO = buildServiceCenter(SERVICE_CENTER_NAME);
        when(serviceCenterService.createOrUpdate(serviceCenterDTO)).thenReturn(serviceCenterDTO);

        MockHttpServletRequestBuilder request = post("/service-centers/service-center-update")
                .flashAttr("serviceCenterDTO", serviceCenterDTO);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name(SERVICE_CENTER_TABLE));

        verify(serviceCenterService, times(1)).createOrUpdate(serviceCenterDTO);
    }

    @Test
    void updateFailedTest() throws Exception {
        ServiceCenterDTO serviceCenterDTO = buildServiceCenter(SERVICE_CENTER_NAME);
        serviceCenterDTO.setEmail(SERVICE_CENTER_INVALID_EMAIL);
        performSaveOrUpdateForFailedCase(serviceCenterDTO, "/service-centers/service-center-update", UPDATE_SERVICE_CENTER_PAGE);
    }


    @Test
    void deleteTest() throws Exception {
        mockMvc.perform(get("/service-centers/service-center-delete/{id}", FIRST_ID))
                .andExpect(status().isOk())
                .andExpect(view().name(SERVICE_CENTER_TABLE));
    }

    private void performSaveOrUpdateForFailedCase(ServiceCenterDTO serviceCenterDTO, String postUrl, String expectedUrl) throws Exception {

        MockHttpServletRequestBuilder request = post(postUrl)
                .flashAttr("serviceCenterDTO", serviceCenterDTO);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name(expectedUrl))
                .andExpect(model().attribute(ERROR_MESSAGE, INVALID_EMAIL_MESSAGE));

    }
}