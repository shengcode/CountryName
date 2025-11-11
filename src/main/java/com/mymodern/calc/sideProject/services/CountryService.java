package com.mymodern.calc.sideProject.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.mymodern.calc.sideProject.entity.CountryName;
import com.mymodern.calc.sideProject.mapper.CountryMapper;
import com.mymodern.calc.sideProject.util.SpringUtil;

public class CountryService{
@Autowired
CountryMapper countryMapper = SpringUtil.getBean(CountryMapper.class);

public CountryName CountryName(int id){

    return countryMapper.CountryName(id);
}
}