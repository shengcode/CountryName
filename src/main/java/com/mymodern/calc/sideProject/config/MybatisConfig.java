package com.mymodern.calc.sideProject.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;


@Configuration
@MapperScan(basePackages="com.mymodern.calc.sideProject.mapper")
public class MybatisConfig {


   @Bean
   public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {


       final SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
       sqlSessionFactory.setDataSource(dataSource);
       sqlSessionFactory.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
       sqlSessionFactory.setFailFast(true);
       return sqlSessionFactory.getObject();
   }
}


