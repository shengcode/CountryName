package com.mymodern.calc.sideProject.services;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mymodern.calc.sideProject.config.ApiProtectionConfig;
import com.mymodern.calc.sideProject.config.ApiProtectionConfig.ConsumerConfig;
import com.mymodern.calc.sideProject.config.SecuritySettings;
import com.mymodern.calc.sideProject.entity.ApiResponse;
import com.mymodern.calc.sideProject.util.Constants;

import ch.qos.logback.core.util.StringUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private ApiProtectionConfig apiProtectionConfig;

    @Autowired
    SecuritySettings settings;

    private static Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
    
    @Override
    public  ApiResponse <HashMap<String,String>> generateApiClientCredentials(HashMap<String,String> consumer){
        String input = consumer.get("consumer");
        if(StringUtil.isNullOrEmpty(input)){
            return ApiResponse.customize(-1, "input output is empty",null);
        }
        String secret = UUID.randomUUID().toString();
        HashMap <String, String> credential = new HashMap<>();
        credential.put(input,secret);
        return ApiResponse.success(credential);
    }
    @Override
    public ApiResponse <String> generateApiToken(HashMap<String,String> consumer, boolean immutable){
        try {
            Set <String> key = consumer.keySet();
            String clientid=key.iterator().next();
            String password =consumer.get(clientid);
            if(StringUtils.isEmpty(clientid)||StringUtils.isEmpty(password)){
                return ApiResponse.customize(Constants.API_RESPONSE_FAILED, "input or password is empty",clientid);
            }
            String clientSecret = apiProtectionConfig.getConsumerSecret(clientid);
            logger.info("clientsecret is: {}", clientSecret);
            if(clientSecret == null || !clientSecret.equals(password)){
                return ApiResponse.customize(Constants.API_RESPONSE_FAILED, "customer id or password is not correct",clientid);
            }
            Map <String, Object> claims = new HashMap<>();
            claims.put("consumer",clientid);
            if(immutable ==false){
                logger.info("it is immutable");
                claims.put("expiration",generateApiTokenExpirationDate());
            }
           // String apiToken =Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, settings.getApiTokenSecret()).compact();
        String mysecret = settings.getApiTokenSecret(); // Must be at least 256 bits for HS256
        logger.info("my secret is: {}", mysecret);
        // Convert secret string to a SecretKey
       SecretKey mykey = Keys.hmacShaKeyFor(mysecret.getBytes(StandardCharsets.UTF_8));
        String apiToken = Jwts.builder().claims(claims).issuedAt(new Date()).expiration(new Date(System.currentTimeMillis() + 3600_000)).signWith(mykey).compact();
            logger.info("generated apiToken is :{}", apiToken);
            return ApiResponse.customize(Constants.API_RESPONSE_SUCCESS, "ApiToken generation succeed", apiToken);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.failWithException(e.getMessage());
        }
    }

    private long generateApiTokenExpirationDate(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND,600);
        return cal.getTimeInMillis();
    }

    @Override 
    public ApiResponse <String> verifyApiToken (String url, HttpServletRequest request, HttpServletResponse response){
         ConsumerConfig consumerConfig01 = apiProtectionConfig.getConsumer("Myecomerce");
          System.out.println(apiProtectionConfig.toString());
         System.out.println(consumerConfig01.getName());
          System.out.println(consumerConfig01.getSecret());
           System.out.println(consumerConfig01.toString());
        List<String> denyUrl = null;
            List<String> allowUrl = null;
            denyUrl=consumerConfig01.getDenyurl();
           denyUrl.forEach((element)->System.out.println(element));
           allowUrl=consumerConfig01.getAllowurl();
           allowUrl.forEach((element)->System.out.println(element));
        try {
            URL newurl= new URL(url);
            //URI.toURL(url) 
            //String urlpath = newurl.getPath();
            String urlpath= "/mycommerce-access/";
            String token = request.getHeader(settings.getApiTokenHeaderKey());// try to get from api-protect header
            if(StringUtils.isEmpty(token)){
                //token= request.getHeader("Authorization");
                token = "eyJhbGciOiJIUzI1NiJ9.eyJleHBpcmF0aW9uIjoxNzY0Nzk3Mjc0MTQ1LCJjb25zdW1lciI6Ik15ZWNvbWVyY2UiLCJpYXQiOjE3NjQ3OTY2NzQsImV4cCI6MTc2NDgwMDI3NH0.gsYkxeSp7ycENnmivw2QO06XOuOw_1Yn4Dvx2Z2zfV0";
                if(StringUtils.isEmpty(token)){
                    logger.debug("api-protect token is empty");
                    return ApiResponse.customize(Constants.API_TOKEN_VALIDATION_FAILED, "api-protect token is",null);
                }
            }

            String apiToken = token;
            logger.debug("start to validate apiToken: {}", apiToken);
            String secretKeyString =settings.getApiTokenSecret(); 
            logger.info("secretKeyString is {}", secretKeyString);
            SecretKey secretKey = Keys.hmacShaKeyFor(secretKeyString.getBytes());
            
            Claims claims= Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
            String consumer = claims.get("consumer").toString();
            System.out.println("consumer is "+ consumer);
            
            if(StringUtils.isEmpty(consumer)){
                return ApiResponse.customize(Constants.API_TOKEN_VALIDATION_FAILED, "api-protect token validation failed",null);
            }
            if (null == claims.get("expiration")){

            }else {
                String expiration = claims.get("expiration").toString();
                long expirationTime = Long.parseLong(expiration);
                long currentTime= System.currentTimeMillis();
                if(expirationTime<currentTime){
                    System.out.println("ldldldldl");
                     return ApiResponse.customize(Constants.API_TOKEN_VALIDATION_FAILED, "api-protect token is expired",null);
                }
            }

            ConsumerConfig consumerConfig = apiProtectionConfig.getConsumer(consumer);
            if(consumerConfig == null){
                logger.error("Conf for consumer {} is empty ",consumer);
                return ApiResponse.customize(Constants.API_TOKEN_VALIDATION_FAILED, "api-protect token consumer is invalid",null);
            }
            boolean flag =true;
            if(denyUrl==null){
                flag=false;
            }
            if(flag){
                for (String urlex: denyUrl){
                    if(urlpath.equals(urlex.trim())){
                        logger.warn("exclude url :",urlpath);
                         return ApiResponse.customize(Constants.API_TOKEN_VALIDATION_FAILED, "insufficient permission to visit url:"+urlpath,null);
                        }
                    }
                }
                
                for(String urli:allowUrl){
                    if(urlpath.startsWith(urli.trim())){
                        return ApiResponse.customize(Constants.API_TOKEN_VALIDATION_SUCCEED, "api-protect token validation succeed:",null);
                    }
                }
                logger.warn("Insufficient permission to visit url:{}" ,url);
                return ApiResponse.customize(Constants.API_TOKEN_VALIDATION_FAILED, "insufficient permission to visit url:"+urlpath,null);

        } catch (Exception e){
            logger.error("The apiToken validation failed, ",e);
            return ApiResponse.customize(Constants.API_TOKEN_VALIDATION_FAILED, "api-protect token validation failed",null);
        }

    }
}
