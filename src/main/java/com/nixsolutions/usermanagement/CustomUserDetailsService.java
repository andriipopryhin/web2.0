package com.nixsolutions.usermanagement;

import java.util.ArrayList;
import java.util.Collection;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nixsolutions.usermanagement.db.UserDao;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    private UserDao userDao;



    @Transactional
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException, DataAccessException {
        User user = userDao.findByLogin(username);
        if (user != null) {
            Collection<GrantedAuthorityImpl> authorities =
                    new ArrayList<GrantedAuthorityImpl>();
            authorities.add(new GrantedAuthorityImpl("ROLE_USER"));

            return new org.springframework.security.core.userdetails.User(
                    user.getLogin(), user.getPassword(), true, true, true,
                   true, authorities);
        } else {
            throw new UsernameNotFoundException("User not found!");
        }

    }

}