package org.example.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class DataSourceConfig {

    private static HikariDataSource instance;

    @Bean
    public HikariDataSource dataSource(
            @Value("${spring.datasource.url}") String url,
            @Value("${spring.datasource.username}") String user,
            @Value("${spring.datasource.password}") String pass) {

        if (instance == null) {
            synchronized (DataSourceConfig.class) {
                if (instance == null) {
                    HikariDataSource ds = new HikariDataSource();
                    ds.setJdbcUrl(url);
                    ds.setUsername(user);
                    ds.setPassword(pass);
                    ds.setMaximumPoolSize(10);
                    instance = ds;
                }
            }
        }
        return instance;               // always the same instance
    }
}