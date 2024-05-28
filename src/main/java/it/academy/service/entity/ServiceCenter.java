package it.academy.service.entity;

import it.academy.service.entity.embeddable.BankAccount;
import it.academy.service.entity.embeddable.Requisites;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "service_centers", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"service_name"})
})
public class ServiceCenter implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "service_name")
    private String serviceName;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "fullName", column = @Column(name = "full_name")),
            @AttributeOverride(name = "actualAddress", column = @Column(name = "actual_address")),
            @AttributeOverride(name = "legalAddress", column = @Column(name = "legal_address")),
            @AttributeOverride(name = "taxpayerNumber", column = @Column(name = "taxpayer_number")),
            @AttributeOverride(name = "registrationNumber", column = @Column(name = "registration_number"))
    })
    private Requisites requisites;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "bankAccount", column = @Column(name = "bank_account")),
            @AttributeOverride(name = "bankCode", column = @Column(name = "bank_code")),
            @AttributeOverride(name = "bankName", column = @Column(name = "bank_name")),
            @AttributeOverride(name = "bankAddress", column = @Column(name = "bank_address"))
    })
    private BankAccount bankAccount;

    @Column(name = "active")
    private Boolean isActive;

}
