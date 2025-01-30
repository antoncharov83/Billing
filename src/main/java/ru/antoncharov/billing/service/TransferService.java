package ru.antoncharov.billing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.antoncharov.billing.exception.InsufficientFundsException;
import ru.antoncharov.billing.model.Account;
import ru.antoncharov.billing.repository.AccountRepository;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransferService {
    private final AccountRepository accountRepository;

    /**
     * Метод для перевода средств с одного аккаунта на другой
     *
     * @param toUsername  ID получающего аккаунта
     * @param amount        сумма перевода
     */
    @Transactional
    public void transferMoney(String toUsername, BigDecimal amount) {
        Long fromUserId = getUserId();
        final Account fromAccount = accountRepository.findByIdForUpdate(fromUserId)
                .orElseThrow(() -> new IllegalArgumentException("Неверный идентификатор аккаунта id = + " + fromUserId));
        final Account toAccount = accountRepository.findByNameForUpdate(toUsername)
                .orElseThrow(() -> new IllegalArgumentException("Неверный идентификатор аккаунта username = + " + toUsername));

        validateTransferAmount(fromAccount, amount);

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }

    /**
     * Проверяет возможность выполнения перевода
     *
     * @param account Аккаунт-отправитель
     * @param amount  Сумма перевода
     */
    private void validateTransferAmount(Account account, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Сумма перевода должна быть положительной");
        }

        if (account.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Недостаточно средств на счету");
        }
    }

    private Long getUserId() {
        Authentication authenticationToken = SecurityContextHolder.getContext().getAuthentication();
        return Long.valueOf(authenticationToken.getPrincipal().toString());
    }
}
