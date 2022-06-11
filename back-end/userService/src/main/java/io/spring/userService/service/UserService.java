package io.spring.userService.service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTPartsParser;
import io.spring.userService.dto.UserDto;
import io.spring.userService.entity.Role;
import io.spring.userService.entity.User;
import io.spring.userService.repository.RoleRepository;
import io.spring.userService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService implements UserDetailsService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public UserDetails loadUserByUsername(String username){
        User user = userRepository.findByUsername(username);
        if(user != null) {
            user.setLastBeenDate(Date.valueOf(LocalDate.now()));
            userRepository.save(user);
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            user.getRoles().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            });
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        }
        return null;
            }

    public UserDto saveUser(UserDto userDto){
        userDto.setCreateDate(Date.valueOf(LocalDate.now()));
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user = userRepository.save(modelMapper.map(userDto, User.class));
        addRoleToUser(userDto.getUsername(), "ROLE_USER");
        return modelMapper.map(user, UserDto.class);
    }

    public void addRoleToUser(String username, String roleName){
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }


    public List<UserDto> getUsers(){
        return userRepository.findAll().stream().map(a -> {
          UserDto userDto = modelMapper.map(a, UserDto.class);
            userDto.setIsBlocked(!a.getIsBlocked() ? "BLOCKED" : "ACTIVE");
            return userDto;
        }).collect(Collectors.toList());
    }


    public void deleteUsers(List<Integer> nums){
        userRepository.deleteAllById(nums);
    }

    public void blockUsers(List<Integer> nums){
        userRepository.findAll().stream()
                .map(a -> {
                    if(nums.contains(a.getId())){
                        a.setIsBlocked(false);
                        userRepository.save(a);
                    }
                    return null;
                }).collect(Collectors.toList());
    }

    public void unBlockUsers(List<Integer> nums){
            userRepository.findAll().stream()
                    .map(a -> {
                        if(nums.contains(a.getId())){
                            a.setIsBlocked(true);
                            userRepository.save(a);
                        }
                        return null;
                    }).collect(Collectors.toList());
        }


    public boolean checkUser(String token) {
        String username = getUserNameInToken(token);
        if(userRepository.findByUsername(username) == null)
            return false;
        if(userRepository.findByUsername(username).getIsBlocked()){
            return true;
        }
        return false;
    }

    private String getUserNameInToken(String token){
        token = token.substring(7);
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getSubject();
    }

}
