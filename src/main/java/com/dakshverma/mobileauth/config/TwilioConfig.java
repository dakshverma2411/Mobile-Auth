/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dakshverma.mobileauth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Daksh
 */
@Configuration
@ConfigurationProperties(prefix = "twilio")
@Data
public class TwilioConfig {
    private String accountSid;
    private String authToken;
    private String phoneNumber;
}
