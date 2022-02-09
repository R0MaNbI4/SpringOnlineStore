package ru.rompet.shop.service.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rompet.shop.repository.security.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLogin(username)
                .map(user -> new User(
                                user.getLogin(),
                                user.getPassword(),
                                user.getAuthorities()
                                        .stream()
                                        .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                                        .collect(Collectors.toSet())
                        )
                ).orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }

    public List<ru.rompet.shop.model.security.User> getAll() {
        return userRepository.findAll();
    }
}
