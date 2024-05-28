package it.academy.service.entity;

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
@Table(name = "repair_types", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name"})
})
public class RepairType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String name;

    @Column
    private String code;

    @Column
    private String level;

    @Column(name = "active")
    private Boolean isActive;

}
