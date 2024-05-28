package it.academy.service.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "spare_parts", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name"})
})
public class SparePart implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String name;

    @Column(name = "active")
    private Boolean isActive;

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "models_spare_parts",
            joinColumns = {@JoinColumn(name = "spare_part_id")},
            inverseJoinColumns = {@JoinColumn(name = "model_id")})
    private Set<Model> models = new HashSet<>();

}
