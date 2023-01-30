/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dakshverma.mobileauth.controllers;

import com.dakshverma.mobileauth.exceptions.InvalidOtpException;
import com.dakshverma.mobileauth.exceptions.OtpTimeoutException;
import com.dakshverma.mobileauth.exceptions.TwilioException;
import com.dakshverma.mobileauth.exceptions.UserAlreadyExistException;
import com.dakshverma.mobileauth.requests.GetOtpRequest;
import com.dakshverma.mobileauth.requests.RegisterRequest;
import com.dakshverma.mobileauth.services.AuthService;
import com.dakshverma.mobileauth.services.TwilioService;
import com.dakshverma.mobileauth.services.UserService;
import jakarta.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.NoSuchPaddingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Daksh
 */
@RestController
public class ApiController {
    
    @Autowired
    private TwilioService twilioService;
    
    @Autowired
    private AuthService authService;
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@Valid @RequestBody(required = true) RegisterRequest request) throws InvalidOtpException, OtpTimeoutException, UserAlreadyExistException{
        Map<String, Object> res = new HashMap<>();
        authService.validateOtp(request.getKey(), request.getPhoneNumber(),  request.getOtp());
        userService.saveUser(request.getName(), request.getPassword(), request.getPhoneNumber());
        res.put("msg", "user created");
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
    
    @PostMapping("/otp")
    public ResponseEntity<Map<String, Object>> getOtp(@Valid @RequestBody(required = true) GetOtpRequest request) throws TwilioException, UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidKeySpecException, InvalidKeySpecException, UserAlreadyExistException{
        Map<String, Object> res = new HashMap<>();
        userService.checkIfUserExists(request.getPhoneNumber());
        String otp = twilioService.sendOTP(request.getPhoneNumber());
        String key = authService.generateOtpHash(otp, request.getPhoneNumber());
        res.put("key",key);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    
}
