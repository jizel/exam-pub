package cz.etyka.exam.pub.security;

import cz.etyka.exam.pub.entity.User;
import cz.etyka.exam.pub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class SpringUserDetailsService implements UserDetailsService {

    @Autowired private UserService userService;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userService.findUserByName(name);

        return new org.springframework.security.core.userdetails.User(
                user.getName(), passwordEncoder.encode(user.getPassword()), getAuthorities(user));
    }

    private Collection<GrantedAuthority> getAuthorities(User user) {
        String[] userRoles = user.getRoles().stream()
                .map(r -> r.getRoleType().toString())
                .toArray(String[]::new);

        return AuthorityUtils.createAuthorityList(userRoles);
    }
}
