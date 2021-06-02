package me.cerratolabs.rust.servermanager.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Data
@Configuration
@ConfigurationProperties(prefix = "rust")
@PropertySource("classpath:application.properties")
public class RustConfig {
    private String secretKey;
    private String steamKey;
    private Integer serverId;
    private Boolean sendKills = true;
    private Boolean sendMobEvents = true;
    private Boolean saveData = true;

}