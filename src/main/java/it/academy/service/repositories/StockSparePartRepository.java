package it.academy.service.repositories;

import it.academy.service.entity.StockSparePart;
import it.academy.service.entity.embeddable.StockSparePartPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface StockSparePartRepository extends JpaRepository<StockSparePart, StockSparePartPK>,
        JpaSpecificationExecutor<StockSparePart> {

}
