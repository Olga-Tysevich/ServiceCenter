package it.academy.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CustomCrudRepository<T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
}
