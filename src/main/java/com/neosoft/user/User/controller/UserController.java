package com.neosoft.user.User.controller;

import com.neosoft.user.User.dto.UserDTO;
import com.neosoft.user.User.entity.User;
import com.neosoft.user.User.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping(value="/v1/api/user")
@RestController
public class UserController {
    @Autowired
    UserService userService;

    //GET All Users
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<UserDTO>> getUser(){
        log.info("Enters User Controller GET All Method");
        List<UserDTO> list=userService.getAllUsers();
        log.info("Exits User Controller Get All Method");
        return ResponseEntity.ok(list);
    }

    //GET Users By Search
    @RequestMapping(value = "/search",method = RequestMethod.GET)
    public ResponseEntity<List<User>> getUserBySearch(@RequestParam(value = "username") String username,@RequestParam(value = "lastname") String lastname,@RequestParam(value = "pincode") String pincode){
        log.info("Enters User Controller Search Method with parameters "+username+","+lastname+","+pincode);
        List<User> list=userService.getAllUsersBySearch(username,lastname,pincode);
        log.info("Exits User Controller Search Method with parameters "+username+","+lastname+","+pincode);
        return ResponseEntity.ok(list);
    }

    //GET Users By Sort
    @RequestMapping(value = "/sort",method = RequestMethod.GET)
    public ResponseEntity<List<User>> getUserBySort(@RequestParam(value = "type") String type){
        log.info("Enters User Controller Sort Method Method with type by sort="+type);
        List<User> list=userService.getAllUsersBySort(type);
        log.info("Exits User Controller Sort Method Method with type by sort="+type);
        return ResponseEntity.ok(list);
    }

    //Get User By Id
    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public User getUserById(@PathVariable Long id) {
        log.info("Enters User Controller Get Method Method with id="+id);
        User updateUser=userService.getUserById(id);
        log.info("Exits User Controller Get Method Method with id="+id);
        return updateUser;
    }

    //Update User By Id
    @RequestMapping(value="/{id}",method = RequestMethod.PUT)
    public User updateUserById(@PathVariable Long id,@RequestBody User UserDetails) {
        log.info("Enters User Controller Update Method Method with id="+id);
        User updateUser=userService.updateUser(id,UserDetails);
        log.info("Exits User Controller Update Method Method with id="+id);
        return updateUser;
    }

    //Hard Delete User By Id
    @RequestMapping(value="/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<Map<String, Boolean>> deleteUser(@PathVariable Long id) {
        log.info("Enters User Controller Hard Delete Method Method with id="+id);
        userService.deleteHardUser(id);
        Map<String, Boolean> response=new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        log.info("Exits User Controller Hard Delete Method Method with id="+id);
        return ResponseEntity.ok(response);
    }

    //Soft Delete User By Id
    @RequestMapping(value="/soft/{id}",method = RequestMethod.DELETE)
    public User deleteSoftUser(@PathVariable Long id) {
        log.info("Enters User Controller Soft Delete Method Method with id="+id);
        User updateUser=userService.deleteSoftUser(id);
        log.info("Exits User Controller Soft Delete Method Method with id="+id);
        return updateUser;
    }

    //Post User
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDTO userDTO){
        log.info("Enters User Controller Post Method Method");
        User saveuser=userService.createUser(userDTO);
        log.info("Exits User Controller Post Method Method");
        return ResponseEntity.ok(saveuser);
    }

}
