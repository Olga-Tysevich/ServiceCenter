package it.academy.service.contollers;

import it.academy.service.dto.DeviceTypeDTO;
import it.academy.service.dto.forms.TablePage;
import it.academy.service.services.DeviceTypeService;
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
import static it.academy.utils.MockUtils.buildDeviceType;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class DeviceTypeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeviceTypeService deviceTypeService;

    @Autowired
    private DeviceTypeController deviceTypeController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(deviceTypeController).build();
    }

    @Test
    void showPageTest() throws Exception {

        TablePage<DeviceTypeDTO> pageForDisplay = mock(TablePage.class);

        when(deviceTypeService.findForPage(FIRST_PAGE, DEVICE_TYPE_SORT_FIELD, Sort.Direction.ASC.name(), StringUtils.EMPTY)).thenReturn(pageForDisplay);

        mockMvc.perform(get("/device-types/page/{pageNum}", FIRST_PAGE)
                .param(SORT_FIELD, DEVICE_TYPE_SORT_FIELD)
                .param(SORT_DIR, Sort.Direction.ASC.name())
                .param(KEYWORD, StringUtils.EMPTY))
                .andExpect(status().isOk())
                .andExpect(view().name(DEVICE_TYPE_TABLE))
                .andExpect(model().attribute(TABLE_PAGE, pageForDisplay));

        verify(deviceTypeService, times(1)).findForPage(FIRST_PAGE, DEVICE_TYPE_SORT_FIELD,
                Sort.Direction.ASC.name(), StringUtils.EMPTY);
    }


    @Test
    void showCreateAdminPageTest() throws Exception {
        mockMvc.perform(get("/device-types/device-type-create"))
                .andExpect(status().isOk())
                .andExpect(view().name(ADD_DEVICE_TYPE_PAGE));
    }


    @Test
    void createTest() throws Exception {
        DeviceTypeDTO deviceTypeDTO = buildDeviceType(String.format(DEVICE_TYPE_NAME, 1));
        when(deviceTypeService.createOrUpdate(deviceTypeDTO)).thenReturn(deviceTypeDTO);

        MockHttpServletRequestBuilder request = post("/device-types/device-type-create")
                .flashAttr("deviceTypeDTO", deviceTypeDTO);

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/device-types"));
    }


    @Test
    void showUpdatePageTest() throws Exception {
        DeviceTypeDTO deviceTypeDTO = mock(DeviceTypeDTO.class);

        when(deviceTypeService.findById(FIRST_ID)).thenReturn(deviceTypeDTO);

        mockMvc.perform(get("/device-types/device-type-update/{id}", FIRST_ID))
                .andExpect(status().isOk())
                .andExpect(view().name(UPDATE_DEVICE_TYPE_PAGE))
                .andExpect(model().attribute(DEVICE_TYPE, deviceTypeDTO));

        verify(deviceTypeService, times(1)).findById(FIRST_ID);
    }

    @Test
    void updateTest() throws Exception {
        DeviceTypeDTO deviceTypeDTO = buildDeviceType(String.format(BRAND_NAME, 1));
        when(deviceTypeService.createOrUpdate(deviceTypeDTO)).thenReturn(deviceTypeDTO);

        MockHttpServletRequestBuilder request = post("/device-types/device-type-update")
                .flashAttr("deviceTypeDTO", deviceTypeDTO);

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/device-types"));

        verify(deviceTypeService, times(1)).createOrUpdate(deviceTypeDTO);
    }

    @Test
    void deleteTest() throws Exception {
        mockMvc.perform(get("/device-types/device-type-delete/{id}", FIRST_ID))
                .andExpect(status().isOk())
                .andExpect(view().name(DEVICE_TYPE_TABLE));
    }

}