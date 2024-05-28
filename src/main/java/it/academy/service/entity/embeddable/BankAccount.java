package it.academy.service.entity.embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class BankAccount implements Serializable {

    private String bankAccount;

    private String bankCode;

    private String bankName;

    private String bankAddress;

}
