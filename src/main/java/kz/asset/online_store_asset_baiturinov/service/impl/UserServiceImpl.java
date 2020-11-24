package kz.asset.online_store_asset_baiturinov.service.impl;

import kz.asset.online_store_asset_baiturinov.models.Users;
import kz.asset.online_store_asset_baiturinov.repo.UserRepository;
import kz.asset.online_store_asset_baiturinov.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Users myUser = userRepository.findByEmail(s);

        if (myUser != null){
            User secUser = new User(myUser.getEmail(), myUser.getPassword(), myUser.getRoles());
            return secUser;
        }


        throw new UsernameNotFoundException("User Not Found");
    }

    @Override
    public Users getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
