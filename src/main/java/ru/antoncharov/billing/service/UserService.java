package ru.antoncharov.billing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.antoncharov.billing.dto.SearchUserRequest;
import ru.antoncharov.billing.dto.UserDto;
import ru.antoncharov.billing.mapper.UserMapper;
import ru.antoncharov.billing.model.Email;
import ru.antoncharov.billing.model.Phone;
import ru.antoncharov.billing.model.UserEntity;
import ru.antoncharov.billing.repository.EmailRepository;
import ru.antoncharov.billing.repository.PhoneRepository;
import ru.antoncharov.billing.repository.UserRepository;
import ru.antoncharov.billing.spec.UserSpecification;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PhoneRepository phoneRepository;
    private final EmailRepository emailRepository;
    private final UserMapper mapper;

    @Transactional
    public void addPhone(String number) {
        Long userId = getUserId();
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Неверный идентификатор аккаунта id = + " + userId));

        phoneRepository.save(
                Phone.builder()
                        .phone(number)
                        .user(user)
                        .build()
        );
    }

    @Transactional
    public void addEmail(String email) {
        Long userId = getUserId();
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Неверный идентификатор аккаунта id = + " + userId));

        emailRepository.save(
                Email.builder()
                        .email(email)
                        .user(user)
                        .build()
        );
    }

    @Transactional
    public void changePhone(String oldNumber, String newNumber) {
        Long userId = getUserId();
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Неверный идентификатор аккаунта id = + " + userId));

        Phone phone = phoneRepository.findByUserAndPhone(user, oldNumber)
                .orElseThrow(() -> new IllegalArgumentException("Неверный идентификатор email аккаунта id = + " + userId + ", email = " + oldNumber));

        phone.setPhone(newNumber);
        phoneRepository.save(phone);
    }

    @Transactional
    public void changeEmail(String oldEmail, String newEmail) {
        Long userId = getUserId();
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Неверный идентификатор аккаунта id = + " + userId));

        Email email = emailRepository.findByUserAndEmail(user, oldEmail)
                .orElseThrow(() -> new IllegalArgumentException("Неверный идентификатор email аккаунта id = + " + userId + ", email = " + oldEmail));

        email.setEmail(newEmail);
        emailRepository.save(email);
    }

    public void deletePhone(String oldNumber) {
        Long userId = getUserId();
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Неверный идентификатор аккаунта id = + " + userId));

        Phone phone = phoneRepository.findByUserAndPhone(user, oldNumber)
                .orElseThrow(() -> new IllegalArgumentException("Неверный идентификатор email аккаунта id = + " + userId + ", email = " + oldNumber));

        phoneRepository.delete(phone);
    }

    public void deleteEmail(String oldEmail) {
        Long userId = getUserId();
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Неверный идентификатор аккаунта id = + " + userId));

        Email email = emailRepository.findByUserAndEmail(user, oldEmail)
                .orElseThrow(() -> new IllegalArgumentException("Неверный идентификатор email аккаунта id = + " + userId + ", email = " + oldEmail));

        emailRepository.delete(email);
    }

    private Long getUserId() {
        Authentication authenticationToken = SecurityContextHolder.getContext().getAuthentication();
        return Long.valueOf(authenticationToken.getPrincipal().toString());
    }

    @Transactional(readOnly = true)
    public List<UserDto> search(SearchUserRequest searchUserRequest) {
        Pageable page = PageRequest.of(searchUserRequest.getPage(), searchUserRequest.getPageSize());
        Page<UserEntity> users = userRepository.findAll(UserSpecification.toPredicate(searchUserRequest), page);
        return users.getContent().stream().map(mapper::toDto).collect(Collectors.toList());
    }
}
