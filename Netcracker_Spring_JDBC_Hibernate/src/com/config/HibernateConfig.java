package com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;
import java.util.Properties;
import org.hibernate.SessionFactory;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "com")
@PropertySource(value= "classpath:application.properties")
public class HibernateConfig {
    @Autowired
    public DataSource dataSource;

    @Value("${hibernate.dialect}")
    private String hibernateDialect;

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", hibernateDialect);
        return properties;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);
        return txManager;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setPackagesToScan("com/model");
        sessionFactoryBean.setHibernateProperties(hibernateProperties());
        return sessionFactoryBean;
    }
}