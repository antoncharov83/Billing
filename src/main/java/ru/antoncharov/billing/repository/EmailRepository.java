package ru.antoncharov.billing.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.antoncharov.billing.model.Email;
import ru.antoncharov.billing.model.UserEntity;

import java.util.Optional;

public interface EmailRepository extends JpaRepository<Email, Long> {

    Optional<Email> findByUserAndEmail(UserEntity user, String email);
}
