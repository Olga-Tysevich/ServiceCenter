package it.academy.service.contollers;

import it.academy.service.dto.AccountDTO;
import it.academy.service.dto.forms.TablePage;
import it.academy.service.entity.RoleEnum;
import it.academy.service.services.AccountService;
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
import static it.academy.utils.MockUtils.buildAccount;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Autowired
    private AccountController accountController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
    }

    @Test
    void showPageTest() throws Exception {

        TablePage<AccountDTO> pageForDisplay = mock(TablePage.class);

        when(accountService.findForPage(FIRST_PAGE, ACCOUNT_SORT_FIELD, Sort.Direction.ASC.name(), StringUtils.EMPTY)).thenReturn(pageForDisplay);

        mockMvc.perform(get("/accounts/page/{pageNum}", FIRST_PAGE)
                .param(SORT_FIELD, ACCOUNT_SORT_FIELD)
                .param(SORT_DIR, Sort.Direction.ASC.name())
                .param(KEYWORD, StringUtils.EMPTY))
                .andExpect(status().isOk())
                .andExpect(view().name(ACCOUNT_TABLE))
                .andExpect(model().attribute(TABLE_PAGE, pageForDisplay));

        verify(accountService, times(1)).findForPage(FIRST_PAGE, ACCOUNT_SORT_FIELD,
                Sort.Direction.ASC.name(), StringUtils.EMPTY);
    }


    @Test
    void showCreateAdminPageTest() throws Exception {
        mockMvc.perform(get("/accounts/account-create-admin"))
                .andExpect(status().isOk())
                .andExpect(view().name(ADD_ADMIN_ACCOUNT_PAGE));
    }

    @Test
    void showCreatePageTest() throws Exception {
        mockMvc.perform(get("/accounts/account-create-service-center"))
                .andExpect(status().isOk())
                .andExpect(view().name(ADD_SERVICE_CENTER_ACCOUNT_PAGE));
    }

    @Test
    void createTest() throws Exception {
        AccountDTO accountDTO = buildAccount(String.format(ACCOUNT_EMAIL, 1), RoleEnum.ADMIN, null);
        when(accountService.createOrUpdate(accountDTO)).thenReturn(accountDTO);

        MockHttpServletRequestBuilder request = post("/accounts/account-create")
                .flashAttr("accountDTO", accountDTO);

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/accounts"));
    }

    @Test
    void createFailedTest() throws Exception {
        AccountDTO accountDTO = buildAccount(ACCOUNT_EMAIL, RoleEnum.ADMIN, null);
        performSaveOrUpdateForFailedCase(accountDTO, "/accounts/account-create", "add/addAdminAccount");
    }

    @Test
    void showUpdatePageTest() throws Exception {
        AccountDTO accountDTO = mock(AccountDTO.class);

        when(accountService.findById(FIRST_ID)).thenReturn(accountDTO);

        mockMvc.perform(get("/accounts/account-update/{id}", FIRST_ID))
                .andExpect(status().isOk())
                .andExpect(view().name(UPDATE_ACCOUNT_PAGE))
                .andExpect(model().attribute(ACCOUNT, accountDTO));

        verify(accountService, times(1)).findById(FIRST_ID);
    }

    @Test
    void updateTest() throws Exception {
        AccountDTO accountDTO = buildAccount(String.format(ACCOUNT_EMAIL, 1), RoleEnum.ADMIN, null);
        when(accountService.createOrUpdate(accountDTO)).thenReturn(accountDTO);

        MockHttpServletRequestBuilder request = post("/accounts/account-update")
                .flashAttr("accountDTO", accountDTO);

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/accounts"));

        verify(accountService, times(1)).createOrUpdate(accountDTO);
    }

    @Test
    void updateFailedTest() throws Exception {
        AccountDTO accountDTO = buildAccount(ACCOUNT_EMAIL, RoleEnum.ADMIN, null);
        performSaveOrUpdateForFailedCase(accountDTO, "/accounts/account-update", "update/updateAccount");
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(get("/accounts/account-delete/{id}", FIRST_ID))
                .andExpect(status().isOk())
                .andExpect(view().name(ACCOUNT_TABLE));
    }

    private void performSaveOrUpdateForFailedCase(AccountDTO accountDTO, String postUrl, String expectedUrl) throws Exception {

        when(accountService.createOrUpdate(accountDTO)).thenReturn(accountDTO);

        MockHttpServletRequestBuilder request = post(postUrl)
                .flashAttr("accountDTO", accountDTO);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name(expectedUrl))
                .andExpect(model().attribute(ERROR_MESSAGE, INVALID_EMAIL_MESSAGE));

    }
}