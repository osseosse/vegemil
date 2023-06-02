package com.vegemil.configuration;


import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@MapperScan(value="com.vegemil.mapperEday", sqlSessionFactoryRef="edaymallSessionFactory")
@EnableTransactionManagement
public class DBConfigurationEday {

	@Bean(name="Edaymall")
	@ConfigurationProperties(prefix="spring.edaymall.datasource.hikari")
	public DataSource edayDataSource() {
		//application.properties에서 정의한 DB 연결 정보를 빌드
		return DataSourceBuilder.create().build();
	}
	
	@Bean(name="edaymallSessionFactory")
	public SqlSessionFactory edaySqlSessionFactory(@Qualifier("Edaymall") DataSource slave1DataSource, ApplicationContext applicationContext) throws Exception{
		//세션 생성 시, 빌드된 DataSource를 세팅하고 SQL문을 관리할 mapper.xml의 경로를 알려준다.
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(slave1DataSource);
		sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:/mappersEday/**/*Mapper.xml"));
		sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:/mybatis/mybatis-config.xml"));
		return sqlSessionFactoryBean.getObject();
	}
 	
	
	@Bean(name="edaymallSqlSessionTemplate")
	public SqlSessionTemplate edaySqlSessionTemplate(@Qualifier("edaymallSessionFactory")SqlSessionFactory edaymallSessionFactory) throws Exception{
		return new SqlSessionTemplate(edaymallSessionFactory);
	}
	
}