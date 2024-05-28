package it.academy.service.repositories;

import it.academy.service.entity.SparePartOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SparePartOrderRepository extends JpaRepository<SparePartOrder, Long>{

    List<SparePartOrder> findAllByRepairId(Long id);

}
