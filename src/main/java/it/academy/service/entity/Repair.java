package it.academy.service.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "repairs")
public class Repair implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_center_id")
    private ServiceCenter serviceCenter;

    @Enumerated(EnumType.STRING)
    private RepairStatus status;

    @Enumerated(EnumType.STRING)
    private RepairCategory category;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "device_id")
    private Device device;

    @Column(name = "defect_description")
    private String defectDescription;

    @Column(name = "repair_number")
    private String repairNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "repair_type_id")
    private RepairType repairType;

    @Column(name = "start_date", updatable = false)
    @CreationTimestamp
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

}
