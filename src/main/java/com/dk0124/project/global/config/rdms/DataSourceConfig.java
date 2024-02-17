package com.dk0124.project.global.config.rdms;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {
    @Bean
    public HikariConfig coreHikariConfig() {
        return new HikariConfig();
    }

    @Bean
    public HikariDataSource dataSource(HikariConfig config) {
        return new HikariDataSource(config);
    }
}
