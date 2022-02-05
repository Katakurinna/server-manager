package me.cerratolabs.rust.servermanager.entity.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.SneakyThrows;
import me.cerratolabs.rust.servermanager.config.RustConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SteamDataService {

    @Autowired
    private RustConfig config;


    @SneakyThrows
    public JsonNode getPlayerData(Long steamID) {
        String url = "http://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/?key=" + config.getSteamKey() + "&steamids=" + steamID;
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, ObjectNode.class).get("response").get("players").get(0);
    }

    public String getCommunityVisibilityState(Long steamID) {
        return getPlayerData(steamID)
            .get("communityvisibilitystate")
            .asText();
    }

    public String getProfileState(Long steamID) {
        return getPlayerData(steamID)
            .get("profilestate")
            .asText();
    }

    public String getPersonaName(Long steamID) {
        return getPlayerData(steamID)
            .get("personaname")
            .asText();
    }

    public String getCommentPermission(Long steamID) {
        return getPlayerData(steamID)
            .get("commentpermission")
            .asText();
    }

    public String getProfileURL(Long steamID) {
        return getPlayerData(steamID)
            .get("profileurl")
            .asText();
    }

    public String getAvatar(Long steamID) {
        return getPlayerData(steamID)
            .get("avatar")
            .asText();
    }

    public String getAvatarMedium(Long steamID) {
        return getPlayerData(steamID)
            .get("avatarmedium")
            .asText();
    }
    public String getAvatarFull(Long steamID) {
        return getPlayerData(steamID)
            .get("avatarfull")
            .asText();
    }

    public String getAvatarHash(Long steamID) {
        return getPlayerData(steamID)
            .get("avatarhash")
            .asText();
    }

    public String getLastLogOff(Long steamID) {
        return getPlayerData(steamID)
            .get("lastlogoff")
            .asText();
    }

    public String getPersonaState(Long steamID) {
        return getPlayerData(steamID)
            .get("personastate")
            .asText();
    }

    public String getRealName(Long steamID) {
        return getPlayerData(steamID)
            .get("realname")
            .asText();
    }

    public String getPrimaryClanId(Long steamID) {
        return getPlayerData(steamID)
            .get("primaryclanid")
            .asText();
    }

    public String getTimeCreated(Long steamID) {
        return getPlayerData(steamID)
            .get("timecreated")
            .asText();
    }

    public String getPersonaStateFlags(Long steamID) {
        return getPlayerData(steamID)
            .get("personastateflags")
            .asText();
    }

    public String getLocCountryCode(Long steamID) {
        return getPlayerData(steamID)
            .get("loccountrycode")
            .asText();
    }
    public String getLocStateCode(Long steamID) {
        return getPlayerData(steamID)
            .get("locstatecode")
            .asText();
    }

    public String getLocCityId(Long steamID) {
        return getPlayerData(steamID)
            .get("loccityid")
            .asText();
    }

}