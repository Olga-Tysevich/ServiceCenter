package it.academy.service.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class SparePartOrder implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "repair_id")
    private Repair repair;

    @Column(name = "order_date", updatable = false)
    @CreationTimestamp
    private Date orderDate;

    @Column(name = "departure_date")
    private Date departureDate;

    @Column(name = "delivery_date")
    private Date deliveryDate;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "sparePartOrder", cascade = {CascadeType.ALL})
    private Set<OrderItem> orderItems;

}
