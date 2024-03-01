package ru.sportswearcompany.sportswearsshop.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.sportswearcompany.sportswearsshop.model.User;
import ru.sportswearcompany.sportswearsshop.repo.UserRepository;

@Service
@RequiredArgsConstructor
public class UserPrincipalDetailsService implements UserDetailsService {
//этот класс реализует проверку прав юзера

    private final UserRepository userRepository;

    public Authentication getAuth(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user=userRepository.findByUsername(s);
        return new UserPrincipal(user);
    }
}
