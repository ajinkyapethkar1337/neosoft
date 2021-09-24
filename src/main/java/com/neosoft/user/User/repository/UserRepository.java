package com.neosoft.user.User.repository;

import com.neosoft.user.User.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value="SELECT * from tbluser where username=:username or lastname=:lastname or pincode=:pincode",nativeQuery = true)
    List<User> getName(@Param("username") String username, @Param("lastname") String lastname, @Param("pincode")String pincode);

}
