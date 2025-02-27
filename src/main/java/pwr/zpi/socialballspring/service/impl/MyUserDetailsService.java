package pwr.zpi.socialballspring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pwr.zpi.socialballspring.model.User;
import pwr.zpi.socialballspring.repository.UserDao;

import java.util.Optional;

/**
 * This class allows use different user
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userDao.findByEmail(email);
        if (user.isPresent()) {
            User u = user.get();
            return new org.springframework.security.core.userdetails.User(u.getEmail(), new String(u.getPassword()), AuthorityUtils.createAuthorityList("USER")); // TODO: roles
        } else {
            throw new UsernameNotFoundException("User " + email + " not found");
        }
    }
}
