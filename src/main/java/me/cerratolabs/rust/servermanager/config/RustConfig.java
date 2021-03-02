package me.cerratolabs.rust.servermanager.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "rust")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class RustConfig {
    private String secretKey;
    private String steamKey;
    private Integer serverId;
    private Boolean sendKills = true;
    private Boolean sendMobEvents = true;
    private Boolean saveData = true;

}