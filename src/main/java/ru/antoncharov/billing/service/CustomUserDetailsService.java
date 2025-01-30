package ru.antoncharov.billing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.antoncharov.billing.model.UserEntity;
import ru.antoncharov.billing.repository.UserRepository;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserEntity user= userRepository.findByName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("Unknown user: "+userName));

        return User.builder()
                .username(user.getId().toString())
                .password(user.getPassword())
                .authorities(Set.of(new SimpleGrantedAuthority("user")))
                .build();
    }
}
