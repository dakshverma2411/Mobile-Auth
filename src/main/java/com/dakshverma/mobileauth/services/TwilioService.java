/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dakshverma.mobileauth.services;

import com.dakshverma.mobileauth.config.TwilioConfig;
import com.dakshverma.mobileauth.exceptions.TwilioException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import java.text.DecimalFormat;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Daksh
 */
@Service
public class TwilioService {
    
    @Autowired
    private TwilioConfig twilioConfig;
    
    public String sendOTP(String number) throws TwilioException{
        
        try{
            
            PhoneNumber fromNumber = new PhoneNumber(twilioConfig.getPhoneNumber());
            PhoneNumber toNumber = new PhoneNumber("+91"+number);
            String otp = generateOTP();
            String content = "Dear user, Your OTP to login in PGI-IIH app is ##" + otp + "##. The OTP will expire in 5 mins.";
            Message message = Message.creator(toNumber,
                    fromNumber,
                    content)
                    .create();
            return otp;
        } catch (Exception e) {
            throw new TwilioException();
        }
    }
    
    private String generateOTP(){
        return new DecimalFormat("000000").format(new Random().nextInt(999999));
    }
    
}
