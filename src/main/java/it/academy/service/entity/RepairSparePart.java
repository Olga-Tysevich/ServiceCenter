package it.academy.service.entity;

import it.academy.service.entity.embeddable.RepairSparePartPK;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "repairs_spare_parts")
@Entity
public class RepairSparePart implements Serializable {

    @EmbeddedId
    private RepairSparePartPK primaryKey;

    private Integer quantity;
}