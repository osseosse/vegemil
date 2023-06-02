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
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@MapperScan(value="com.vegemil.mapper", sqlSessionFactoryRef="vegemilSessionFactory")
@EnableTransactionManagement
public class DBConfigurationVegemil {
	
	@Primary
	@Bean(name="vegemilDataSource")
	@ConfigurationProperties(prefix="spring.vegemil.datasource.hikari")
	public DataSource vegemilDataSource() {
		//application.properties에서 정의한 DB 연결 정보를 빌드
		return DataSourceBuilder.create().build();
	}
	
	@Primary
	@Bean(name="vegemilSessionFactory")
	public SqlSessionFactory vegemilSqlSessionFactory(@Qualifier("vegemilDataSource") DataSource masterDataSource, ApplicationContext applicationContext) throws Exception{
		//세션 생성 시, 빌드된 DataSource를 세팅하고 SQL문을 관리할 mapper.xml의 경로를 알려준다.
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(masterDataSource);
		sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mappers/**/*Mapper.xml"));
		sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:/mybatis/mybatis-config.xml"));
		return sqlSessionFactoryBean.getObject();
	}
	
	@Primary
	@Bean(name="vegemilSqlSessionTemplate")
	public SqlSessionTemplate masterSqlSessionTemplate(@Qualifier("vegemilSessionFactory") SqlSessionFactory masterSqlSessionFactory) throws Exception{
		return new SqlSessionTemplate(masterSqlSessionFactory);
	}
	
}
