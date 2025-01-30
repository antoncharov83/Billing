package ru.antoncharov.billing.spec;

import org.springframework.data.jpa.domain.Specification;
import ru.antoncharov.billing.dto.SearchUserRequest;
import ru.antoncharov.billing.model.Email;
import ru.antoncharov.billing.model.Phone;
import ru.antoncharov.billing.model.UserEntity;

import javax.persistence.criteria.Join;

public class UserSpecification {
    public static Specification<UserEntity> toPredicate(SearchUserRequest request) {
        Specification<UserEntity> spec = Specification.where(null);
        if (request.getDateOfBirth() != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("dateOfBirth"), request.getDateOfBirth()));
        }
        if (request.getEmail() != null) {
            spec = spec.and(hasEmail(request.getEmail()));
        }
        if (request.getPhone() != null) {
            spec = spec.and(hasPhone(request.getPhone()));
        }
        if (request.getName() != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), request.getName()+"%"));
        }

        return spec;
    }

    public static Specification<UserEntity> hasEmail(String email) {
        return (root, query, criteriaBuilder) -> {
            Join<Email, UserEntity> emails = root.join("emails");
            return criteriaBuilder.equal(emails.get("email"), email);
        };
    }

    public static Specification<UserEntity> hasPhone(String phone) {
        return (root, query, criteriaBuilder) -> {
            Join<Phone, UserEntity> phones = root.join("phones");
            return criteriaBuilder.equal(phones.get("phone"), phone);
        };
    }
}
