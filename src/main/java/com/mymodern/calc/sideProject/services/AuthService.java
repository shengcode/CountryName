package com.mymodern.calc.sideProject.services;

import java.util.HashMap;

import com.mymodern.calc.sideProject.entity.ApiResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    ApiResponse<HashMap<String, String>> generateApiClientCredentials(HashMap<String, String> consumer);
    ApiResponse <String> generateApiToken(HashMap<String,String> consumer, boolean immutable);
    ApiResponse <String> verifyApiToken (String url, HttpServletRequest request, HttpServletResponse response);
   
}
