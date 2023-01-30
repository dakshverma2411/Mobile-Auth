/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dakshverma.mobileauth.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
public class GetOtpRequest {
    
    @NotBlank(message="Phone number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number is invalid")
    private String phoneNumber;
}
