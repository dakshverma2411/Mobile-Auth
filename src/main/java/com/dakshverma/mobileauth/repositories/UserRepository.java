/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.dakshverma.mobileauth.repositories;

import com.dakshverma.mobileauth.models.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Daksh
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>{
 
    @Query("SELECT u from User u where u.phoneNumber = :number")
    public User findByPhoneNumber(@Param("number") String number);
}
