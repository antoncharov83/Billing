package ru.antoncharov.billing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import ru.antoncharov.billing.model.Account;

import javax.persistence.LockModeType;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @Lock(LockModeType.PESSIMISTIC_FORCE_INCREMENT)
    @Query("SELECT a from Account a WHERE a.user.name = :name")
    Optional<Account> findByNameForUpdate(String name);

    @Lock(LockModeType.PESSIMISTIC_FORCE_INCREMENT)
    @Query("SELECT a from Account a WHERE a.user.id = :id")
    Optional<Account> findByIdForUpdate(Long id);
}
