package ru.antoncharov.billing.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.antoncharov.billing.model.Account;
import ru.antoncharov.billing.repository.AccountRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransferServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private TransferService transferService;

    private Account fromAccount;

    private Account toAccount;

    @BeforeEach
    public void setup() {
        fromAccount = Account.builder()
                .id(1L)
                .balance(BigDecimal.valueOf(100))
                .version(0).
                build();

        toAccount = Account.builder()
                .id(1L)
                .balance(BigDecimal.valueOf(100))
                .version(0).
                build();

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(1L);
    }

    @AfterEach
    public void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void transferMoney() {
        // given
        given(accountRepository.findByIdForUpdate(any())).willReturn(Optional.of(fromAccount));
        given(accountRepository.findByNameForUpdate(any())).willReturn(Optional.of(toAccount));

        // when
        transferService.transferMoney("user2", BigDecimal.valueOf(50L));

        // then
        assertEquals(BigDecimal.valueOf(150), toAccount.getBalance());
        assertEquals(BigDecimal.valueOf(50), fromAccount.getBalance());
    }
}