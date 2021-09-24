package com.neosoft.user.User.service;

import com.neosoft.user.User.dto.UserDTO;
import com.neosoft.user.User.entity.User;
import com.neosoft.user.User.exception.APIException;
import com.neosoft.user.User.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    ModelMapper mapper=null;
    public UserDTO toDTO(User user){
        mapper=new ModelMapper();
        UserDTO map=mapper.map(user,UserDTO.class);
        return map;
    }
    public List<UserDTO> getAllUsers() {
        log.info("Entered User Service Get All Users");
        List<User> user = userRepository.findAll();
        log.info("Exited User Service Get All Users");
        return user.stream().map(this::toDTO).collect(Collectors.toList());
    }
    public User createUser(@RequestBody UserDTO userDTO){
        log.info("Entered Create User Service");
        User saveUser=this.userRepository.save(new User(userDTO.getUserName(), userDTO.getLastName(),userDTO.getEmail(),userDTO.getAddress(),userDTO.getPhone(),userDTO.getGender(),userDTO.getPincode(),userDTO.getDob(),userDTO.getJoining()));
        log.info("Exited Create User Service");
        return saveUser;
    }
    public void deleteHardUser(Long userId){
        log.info("Entered Delete User Service");
        this.userRepository.deleteById(userId);
        log.info("Exited Delete User Service");
    }

    public User updateUser(Long id, User userDetails) {
        log.info("Entered Update User Service");
        User user= userRepository.findById(id).orElseThrow(() ->{throw new APIException("Invalid Id");});
        user.setUserName(userDetails.getUserName());
        user.setLastName(userDetails.getLastName());
        user.setAddress(userDetails.getAddress());
        user.setEmail(userDetails.getEmail());
        user.setGender(userDetails.getGender());
        user.setPhone(userDetails.getPhone());
        user.setPincode(userDetails.getPincode());
        user.setDob(userDetails.getDob());
        user.setJoining(userDetails.getJoining());
        User updateUser=userRepository.save(user);
        log.info("Exited Update User Service");
        return updateUser;
    }

    public User deleteSoftUser(Long id) {
        log.info("Entered Soft Delete User Service");
        User user=userRepository.findById(id).orElseThrow(() ->{throw new APIException("Invalid Id");});
        user.setIsDeleted(Boolean.TRUE);
        User deleteUser=userRepository.save(user);
        log.info("Exited Soft Delete User Service");
        return deleteUser;
    }

    public List<User> getAllUsersBySearch(String username,String lastname,String pincode) {
        log.info("Entered Search User Service");
        log.info("Exited Search User Service");
        return userRepository.getName(username,lastname,pincode);
    }

    public List<User> getAllUsersBySort(String type) {
        log.info("Entered Sort User Service");
        List<User> user = userRepository.findAll(Sort.by(Sort.Direction.ASC, type));
        log.info("Exited Sort User Service");
        return user;
    }

    public User getUserById(Long id) {
        User user= userRepository.findById(id).orElseThrow(() ->{throw new APIException("Invalid Id");});
        return user;
    }
}
