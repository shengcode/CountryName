package com.mymodern.calc.sideProject.controller;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mymodern.calc.sideProject.entity.ApiResponse;
import com.mymodern.calc.sideProject.services.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@CrossOrigin
@RestController
public class AuthController{

    @Autowired
    AuthService authService;
   
    private static Logger logger = LoggerFactory.getLogger(AuthController.class);
  

    @PostMapping(value = "apiclient/credentialsecret/create")
     public ApiResponse <HashMap<String, String>> generateApiClientCredential(@RequestBody HashMap<String, String> consumer){
       
        return authService.generateApiClientCredentials(consumer);
    }
   
   @PostMapping(value = "apiclient/token/create")
   public ApiResponse <String> generateApiTokenMutable(@RequestBody HashMap<String, String> consumerInfo, HttpServletResponse response){
    return generateApiToken(consumerInfo, false, response);
    }

    private ApiResponse<String> generateApiToken(HashMap<String, String> consumerInfo,boolean immutable, HttpServletResponse response){
         String [] input = consumerInfo.toString().substring(1,consumerInfo.toString().length()-1).split("=");
         String clientid = input[0];
         String password = input[1];
        logger.info("create token, clientid:{}",clientid);
        HashMap <String,String> consumer = new HashMap<String,String>();
        consumer.put(clientid, password);
        ApiResponse<String> apiToken =authService.generateApiToken(consumer,immutable);
        response.setHeader("Authorization", apiToken.getData());
        return apiToken;
    }
    
   @PostMapping(value = "apiclient/requesttoken/validate")
   public ApiResponse <String> verifyApiTokenMutable(@RequestParam(value="url") String url, HttpServletRequest request, HttpServletResponse response){
   
       return authService.verifyApiToken(url, request, response);
    }

}
