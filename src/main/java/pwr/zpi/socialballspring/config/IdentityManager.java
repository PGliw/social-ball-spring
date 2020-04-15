package pwr.zpi.socialballspring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import pwr.zpi.socialballspring.model.User;
import pwr.zpi.socialballspring.repository.UserDao;

@Component
public class IdentityManager implements IIdentityManager {
    @Autowired
    private UserDao userDao;

    public User getCurrentUser(){
        String username =((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        return userDao.findByEmail(username).get();
    }
}
