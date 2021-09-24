package com.neosoft.user.User.entity;

import com.neosoft.user.User.enumeration.Gender;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name="tbluser")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="userid")
    private Long userId;
    @Size(min=3,max=20, message = "Size should be between 3 and 20")
    @Column(name="username",length = 50,nullable = false)
    private String userName;
    @Size(min=3,max=20, message = "Size should be between 3 and 20")
    @Column(name="lastname",length = 50,nullable = false)
    private String lastName;
    @Email(message = "Email Not Valid")
    @Column(name="email",length = 50,nullable=false,unique = true)
    private String email;
    @Column(name="address")
    private String address;
    @Pattern(regexp="[0-9]{10}",message="Mobile No should be 10 digit")
    @Column(name="phone",unique = true)
    private String phone;
    @Enumerated(EnumType.STRING)
    @Column(name="gender")
    private Gender gender;
    @Column(name="pincode")
    private Integer pincode;
    @Column(name="dob")
    private Date dob;
    @Column(name="joining")
    private Date joining;
    @Column(name="isDeleted")
    private Boolean isDeleted;


    public User() {
        this.isDeleted=false;
    }

    public User(String userName, String lastName, String email, String address, String phone, Gender gender,Integer pincode,Date dob,Date joining) {
        this();
        this.userName = userName;
        this.lastName=lastName;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.gender=gender;
        this.pincode=pincode;
        this.dob=dob;
        this.joining=joining;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId.equals(user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
