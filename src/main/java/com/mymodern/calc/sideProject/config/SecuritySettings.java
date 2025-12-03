package com.mymodern.calc.sideProject.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SecuritySettings {
  
    @Value ("${tokengen.consumerssss.secretssss}")
    private String apiTokenSecret;

    @Value ("${apitokenheader.headerkey}")
    private String apiTokenHeaderKey;

    public String getApiTokenSecret(){
        return apiTokenSecret;
    }

    public void setApiTokenSecret(String apiTokenSecret) {
        this.apiTokenSecret = apiTokenSecret;
    }

    public String getApiTokenHeaderKey() {
        return apiTokenHeaderKey;
    }

    public void setApiTokenHeaderKey(String apiTokenHeaderKey) {
        this.apiTokenHeaderKey = apiTokenHeaderKey;
    }


    
}
