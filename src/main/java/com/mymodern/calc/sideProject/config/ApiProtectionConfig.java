package com.mymodern.calc.sideProject.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties("apitoken")
@Configuration
@EnableConfigurationProperties
public class ApiProtectionConfig {
     private String headname;
     private List<ConsumerConfig> consumer;

    public List<ConsumerConfig> getConsumer() {
        return consumer;
    }

    public void setConsumer(List<ConsumerConfig> consumer) {
        this.consumer = consumer;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ApiProtectionConfig{");
        sb.append("headname=").append(headname);
        sb.append(", consumer=").append(consumer);
        sb.append('}');
        return sb.toString();
    }
    
    // non-injected method
    public String getConsumerSecret (String clientid){
         for(ConsumerConfig c: consumer){
             if(clientid.equals(c.name)){
                return c.getSecret();
             }
         }
         return null;
    }
    
    public ConsumerConfig getConsumer(String clientid){
         for(ConsumerConfig c: consumer){
             if(clientid.equals(c.name)){
                return c;
             }
         }

        return null;
    }
     




public static class ConsumerConfig{
    private String name;
    private String secret;
    private List<String> allowurl;
    private List<String> denyurl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public List<String> getAllowurl() {
        return allowurl;
    }

    public void setAllowurl(List<String> allowurl) {
        this.allowurl = allowurl;
    }

    public List<String> getDenyurl() {
        return denyurl;
    }

    public void setDenyurl(List<String> denyurl) {
        this.denyurl = denyurl;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ConsumerConfig{");
        sb.append("name=").append(name);
        sb.append(", secret=").append(secret);
        sb.append(", allowurl=").append(allowurl);
        sb.append(", denyurl=").append(denyurl);
        sb.append('}');
        return sb.toString();
    }

}

}
