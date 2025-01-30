package ru.antoncharov.billing.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import ru.antoncharov.billing.model.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {
    Optional<UserEntity> findByName(String name);

    @Query("SELECT u FROM UserEntity u JOIN u.emails JOIN u.phones")
    List<UserEntity> findBySpec(Specification<UserEntity> spec, Pageable pageable);
}
