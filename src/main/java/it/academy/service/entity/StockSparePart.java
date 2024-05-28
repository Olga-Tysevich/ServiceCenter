package it.academy.service.entity;

import it.academy.service.entity.embeddable.StockSparePartPK;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "stock_spare_parts")
public class StockSparePart {

    @EmbeddedId
    private StockSparePartPK primaryKey;

    @Column(nullable = false)
    private Integer quantity;

}
