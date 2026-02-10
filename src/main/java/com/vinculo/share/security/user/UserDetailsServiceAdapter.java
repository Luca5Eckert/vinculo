package com.vinculo.share.security.user;

import com.vinculo.module.person.domain.exception.PersonNotExistException;
import com.vinculo.module.person.domain.model.Person;
import com.vinculo.module.person.domain.port.PersonRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class UserDetailsServiceAdapter implements UserDetailsService {

    private final PersonRepository personRepository;

    public UserDetailsServiceAdapter(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personRepository.findByEmail(username)
                .orElseThrow(PersonNotExistException::new);

        List<? extends GrantedAuthority> authorities = List.of(
                () -> "ROLE_" + person.getRole().name()
        );

        return new UserDetailsAdapter(
                person.getEmail(),
                person.getPassword(),
                authorities
        );
    }

}
