package com.mymodern.calc.sideProject.factory;

import com.mymodern.calc.sideProject.services.CountryService;

public class CountryServiceFactory {
  public CountryService creaCountryService(){
    return new CountryService();
  }
}
