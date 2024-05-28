package it.academy.service.repositories;

import it.academy.service.entity.RepairSparePart;
import it.academy.service.entity.embeddable.RepairSparePartPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RepairSparePartRepository extends JpaRepository<RepairSparePart, RepairSparePartPK>,
        JpaSpecificationExecutor<RepairSparePart> {

}
