package me.cerratolabs.rust.servermanager.rest.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.cerratolabs.rust.servermanager.entity.jentity.ServerStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Server {

    private String address;

    private Integer port;

    private String password;

    private ServerStatus status;

    private String name;

    private String image;


    public boolean containsNulls() {
        if (address == null) return true;
        if (port == null) return true;
        if (password == null) return true;
        return name == null;
    }
}
