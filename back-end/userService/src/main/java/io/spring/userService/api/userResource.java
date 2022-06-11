package io.spring.userService.api;


import io.spring.userService.dto.UserDto;
import io.spring.userService.entity.Role;
import io.spring.userService.entity.User;
import io.spring.userService.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class userResource {
    private final UserService userService;



    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getUsers(@RequestHeader("Authorization") String language){
        return userService.checkUser(language)?
                ResponseEntity.ok().body(userService.getUsers())
                :ResponseEntity.status(403).body(null);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> saveUser(@RequestBody UserDto userDto){
        System.out.println(userDto);
            return userService.loadUserByUsername(userDto.getUsername()) == null ?
                    ResponseEntity.ok().body(userService.saveUser(userDto))
                    :ResponseEntity.status(401).body(null);
    }

    @DeleteMapping("/edit/delete")
    private void Delete(@RequestBody List<Integer> nums){
       userService.deleteUsers(nums);
    }

    @PutMapping("/edit/block")
    private void blockUsers(@RequestBody List<Integer> nums){
        userService.blockUsers(nums);
    }

    @PutMapping("edit/unblock")
    private void unBlockUsers(@RequestBody List<Integer> nums){
        userService.unBlockUsers(nums);
    }
}
