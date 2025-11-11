package com.mymodern.calc.sideProject.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mymodern.calc.sideProject.entity.CountryName;
import com.mymodern.calc.sideProject.factory.CountryServiceFactory;
import com.mymodern.calc.sideProject.services.CountryService;


@CrossOrigin
@RestController
public class CountryController{
   final CountryServiceFactory countryServiceFactory = new CountryServiceFactory();

    @GetMapping("/countryNamePath")
    public CountryName getCountryName(@RequestParam int id) {
        CountryService countryService = countryServiceFactory.creaCountryService();
        return countryService.CountryName(id);
    }
    

}
