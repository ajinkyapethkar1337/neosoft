package com.neosoft.user.User.dto;

import com.neosoft.user.User.enumeration.Gender;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long userId;
    private String userName;
    private String lastName;
    private String email;
    private String address;
    private String phone;
    private Gender gender;
    private Integer pincode;
    private Date dob;
    private Date joining;
    private Boolean isDeleted;
}
