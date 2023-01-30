/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dakshverma.mobileauth.requests;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Daksh
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    
    @NotBlank(message = "Name is required")
    private String name;
    
    @NotBlank(message="Phone number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number is invalid")
    private String phoneNumber;
    
    @NotNull(message = "Password is required")
    @Size(min=8)
    private String password;
    
    @NotNull(message = "OTP is required")
    private String otp;
    
    @NotNull(message = "Key is required")
    private String key;
}
