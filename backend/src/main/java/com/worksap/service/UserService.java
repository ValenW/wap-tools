package com.worksap.service;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.worksap.model.dto.CustomUserDetails;
import com.worksap.model.entity.User;
import com.worksap.repository.UserRepository;

/**
 * @author Xiaoyue Xiao
 */
@Service
public class UserService  implements UserDetailsService{

    @Resource
    private  UserRepository userRepository;


    public List<User> findAll(){
        return (List<User>)userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Could not find the user '" + username + "'");
        }

        // Not involve authorities, so pass null to authorities
        return new CustomUserDetails(user, true, true, true, true, null);
    }


    public Optional<User> getUserById(Long id) {
        return Optional.ofNullable(userRepository.findOne(id));
    }


    public boolean saveUser(User user) {
        return userRepository.save(user) !=null;
    }


    public void deleteUserById(Long id) {
         userRepository.delete(id);
    }

}
