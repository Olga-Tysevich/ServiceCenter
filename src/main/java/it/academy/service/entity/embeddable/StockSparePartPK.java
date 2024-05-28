package it.academy.service.entity.embeddable;
import it.academy.service.entity.SparePart;
import it.academy.service.entity.SparePartOrder;
import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class StockSparePartPK  implements Serializable {
    @ManyToOne
    @JoinColumn(nullable = false,name = "spare_part_id")
    private SparePart sparePart;

    @ManyToOne
    @JoinColumn(name = "spare_part_order_id")
    private SparePartOrder order;
}
