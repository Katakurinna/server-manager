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
    private String address;
    private Integer port;
    private String password;
}