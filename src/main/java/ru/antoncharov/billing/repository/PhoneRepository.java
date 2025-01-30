package ru.antoncharov.billing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.antoncharov.billing.model.Phone;
import ru.antoncharov.billing.model.UserEntity;

import java.util.Optional;

public interface PhoneRepository extends JpaRepository<Phone, Long> {

    Optional<Phone> findByUserAndPhone(UserEntity user, String number);
}
