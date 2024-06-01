package com.demoProject.configs;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "productServiceEntityManagerFactory",
        basePackages 	 = {"com.demoProject.respositories"},
        transactionManagerRef = "productServiceTransactionManager"
)
public class ProductServiceConfig {

    @Autowired
    Environment env;

    @Primary
    @Bean(name= "productServiceDataSource")
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUrl(env.getProperty("spring.productservice.datasource.url"));
        ds.setUsername(env.getProperty("spring.productservice.datasource.username"));
        ds.setPassword(env.getProperty("spring.productservice.datasource.password"));
        ds.setDriverClassName(Objects.requireNonNull(env.getProperty("spring.productservice.datasource.driver-class-name")));
        return ds;
    }


    @Primary
    @Bean(name= "productServiceEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManager() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPackagesToScan("com.demoProject.model");
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        entityManagerFactoryBean.setJpaProperties(properties);
        return entityManagerFactoryBean;

    }

    @Primary
    @Bean("productServiceTransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("productServiceEntityManagerFactory") EntityManagerFactory entityManagerFactory ) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
