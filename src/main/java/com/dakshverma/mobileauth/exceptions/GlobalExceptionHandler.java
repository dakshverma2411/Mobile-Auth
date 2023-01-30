/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dakshverma.mobileauth.exceptions;
import com.google.common.collect.HashBiMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 *
 * @author Daksh
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> invalidArgument(MethodArgumentNotValidException e){
        Map<String, Object> res = new HashMap<>();
        res.put("msg", "Invalid request body");
        
        List<String> errors = e.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.toList());
        
        res.put("errors", errors);
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(value = InvalidOtpException.class)
    public ResponseEntity<Map<String, Object>> invlaidOTP(InvalidOtpException e){
        Map<String, Object> res = new HashMap<>();
        res.put("msg", "Invalid OTP");
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(value = OtpTimeoutException.class)
    public ResponseEntity<Map<String, Object>> otpTimeOut(OtpTimeoutException e){
        Map<String, Object> res = new HashMap<>();
        res.put("msg", "OTP Expired");
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(value = UserAlreadyExistException.class)
    public ResponseEntity<Map<String, Object>> userAlreadyExists(UserAlreadyExistException e){
        Map<String, Object> res = new HashMap<>();
        res.put("msg", "User Already exists");
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }
    
   
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> badFormat(HttpMessageNotReadableException e){
        Map<String, Object> res = new HashMap<>();
        res.put("msg", "Bad format request");
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Map<String, Object>> generic(Exception e){
        Map<String, Object> res = new HashMap<>();
//        res.put("msg", "Internal server error "+ e.getClass().getSimpleName());
        res.put("msg", "Internal server error.");
        return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    
}
