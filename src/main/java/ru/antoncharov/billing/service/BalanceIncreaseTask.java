package ru.antoncharov.billing.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.antoncharov.billing.model.Account;
import ru.antoncharov.billing.repository.AccountRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component
public class BalanceIncreaseTask {

    private final AccountRepository accountRepository;

    public BalanceIncreaseTask(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Scheduled(fixedRate = 30000)
    public void increaseBalances() {
        List<Account> accounts = accountRepository.findAll();

        for (Account account : accounts) {
            BigDecimal currentBalance = account.getBalance();
            BigDecimal increasedBalance = currentBalance.multiply(BigDecimal.valueOf(1.1)).min(BigDecimal.valueOf(207)).setScale(2, RoundingMode.HALF_UP);

            account.setBalance(increasedBalance);
            accountRepository.save(account);
        }
    }
}
