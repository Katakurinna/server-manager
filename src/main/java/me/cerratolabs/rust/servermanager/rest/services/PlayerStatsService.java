package me.cerratolabs.rust.servermanager.rest.services;

import me.cerratolabs.rust.servermanager.entity.entities.PlayerEntity;
import me.cerratolabs.rust.servermanager.entity.entities.PlayerSeason;
import me.cerratolabs.rust.servermanager.entity.entities.ServerEntity;
import me.cerratolabs.rust.servermanager.entity.entities.WipeEntity;
import me.cerratolabs.rust.servermanager.entity.jentity.PlayerStats;
import me.cerratolabs.rust.servermanager.entity.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerStatsService {

    @Autowired
    private WipeEntityService wipeService;

    @Autowired
    private PlayerSeasonService playerSeasonService;

    @Autowired
    private PlayerEntityService playerService;

    @Autowired
    private DeathEventService deathService;

    @Autowired
    private SteamDataService steamDataService;

    public PlayerStats getPlayerStatsFromSteamID(Long steamID, ServerEntity server) {
        return getPlayerStats(playerService.findBySteamId(steamID), server);
    }

    public PlayerStats getPlayerStatsFromDiscordID(String discordID, ServerEntity server) {
        PlayerEntity player = playerService.findByDiscord(discordID);
        if (player == null || player.getId() == null) {
            throw new NullPointerException("Discord player doesn't exist");
        }
        return getPlayerStats(player, server);
    }

    public PlayerStats getPlayerStatsAndSaveDiscordID(Long steamID, String discordID, ServerEntity server) {
        return getPlayerStats(playerService.addDiscordToEntity(steamID, discordID), server);
    }

    private PlayerStats getPlayerStats(PlayerEntity player, ServerEntity server) {
        PlayerStats stats = new PlayerStats();
        stats.setPlayer(player);
        PlayerSeason playerSeason = playerSeasonService.findMostRecentlyPlayerSeason(player);
        WipeEntity wipe = wipeService.findWipeByServer(server);

        stats.setWipeKills(deathService.getWipeKills(playerSeason, wipe, server));
        stats.setWipeDeaths(deathService.getWipeDeaths(playerSeason, wipe, server));

        stats.setWipeKDR(calculateKDR(stats.getWipeKills(), stats.getWipeDeaths()));

        stats.setTotalKills(deathService.getTotalKills(playerSeason, server));
        stats.setTotalDeaths(deathService.getTotalDeaths(playerSeason, server));

        stats.setTotalKDR(calculateKDR(stats.getTotalKills(), stats.getTotalDeaths()));
        stats.setAvatar(steamDataService.getAvatarFull(player.getSteamId()));

        stats.setFurthestMurder(deathService.obtainFurthestMurder(playerSeason, server));
        stats.setAverageHeadshot(deathService.obtainAverageHeadshot(playerSeason, server));
        stats.setMostUsedWeapon(deathService.obtainMostUsedWeapon(playerSeason, server));
        return stats;
    }

    private Float calculateKDR(Integer kills, Integer deaths) {
        return (float) kills / (float) deaths;
    }

}