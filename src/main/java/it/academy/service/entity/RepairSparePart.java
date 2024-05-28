package it.academy.service.entity;

import it.academy.service.entity.embeddable.RepairSparePartPK;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "repairs_spare_parts")
@Entity
public class RepairSparePart {

    @EmbeddedId
    private RepairSparePartPK primaryKey;

    private Integer quantity;
}