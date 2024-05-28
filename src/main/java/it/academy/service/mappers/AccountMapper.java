package it.academy.service.mappers;

import it.academy.service.dto.AccountDTO;
import it.academy.service.entity.Account;
import it.academy.service.entity.ServiceCenter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Optional;

@Mapper
public interface AccountMapper extends CustomMapper<Account, AccountDTO> {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    @Mappings({
            @Mapping(target = "password", ignore = true),
            @Mapping(source = "serviceCenter.id", target = "serviceCenterId"),
            @Mapping(source = "serviceCenter.serviceName", target = "serviceCenterName")
    })
    AccountDTO toDTO(Account account);

    @Mappings({
            @Mapping(expression = "java(createServiceCenter(accountDTO.getServiceCenterId()))", target = "serviceCenter")
    })
    Account toEntity(AccountDTO accountDTO);

    List<AccountDTO> toDTOList(List<Account> accountList);

    default ServiceCenter createServiceCenter(Long id) {
        return Optional.ofNullable(id)
                .map(i -> ServiceCenter.builder()
                        .id(i)
                        .build())
                .orElse(null);
    }
}
