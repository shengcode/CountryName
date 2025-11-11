
package com.mymodern.calc.sideProject.mapper;
import org.springframework.stereotype.Repository;

import com.mymodern.calc.sideProject.entity.CountryName;

@Repository
@SuppressWarnings("rawtypes")

public interface CountryMapper{
   CountryName CountryName(int id);
}