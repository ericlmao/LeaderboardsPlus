# LeaderboardsPlus
Repository and API for LeaderboardsPlus. Create and track your own custom statistics and automatically create leaderboards!

============

More info here (spigot link)

# Wiki

## PlaceholderAPI Placeholders
```
Name of a position
%leaderboardsplus_<statistic identifier>_position_name_<place number>%

Value of a position
%leaderboardsplus_<statistic identifier>_position_value_<place number>%

Position of You (the player)
%leaderboardsplus_<statistic identifier>_self_position%

Value of You (the player)
%leaderboardsplus_<statistic identifier>_self_value%
```
### Example
```YAML
# Holographic Displays
topjoins:
  location: world, 140.772, 73.603, 259.817
  lines:
  - '&6&lTop Joins'
  - '{fast}&e&l#1 &f%leaderboardsplus_Joins_position_name_1%&7: &6%leaderboardsplus_Joins_position_value_1%'
  - '{fast}&e&l#2 &f%leaderboardsplus_Joins_position_name_2%&7: &6%leaderboardsplus_Joins_position_value_2%'
  - '{fast}&e&l#3 &f%leaderboardsplus_Joins_position_name_4%&7: &6%leaderboardsplus_Joins_position_value_3%'
  - '{fast}&e&l#4 &f%leaderboardsplus_Joins_position_name_4%&7: &6%leaderboardsplus_Joins_position_value_4%'
  - '{fast}&e&l#5 &f%leaderboardsplus_Joins_position_name_5%&7: &6%leaderboardsplus_Joins_position_value_5%'
  - ''
  - '{fast}&e&LYOUR POSITION: &f&l#%leaderboardsplus_Joins_self_position%&7: &e%leaderboardsplus_Joins_self_value%'
```

## Creating your own Statistics (API)
It's overall pretty simple, you need to register the statistics identifier in your onEnable (or somewhere, as long as they are registered) and simply add/take from stats when necessary, the core plugin does the rest! 

```JAVA
// Registering Statistics
LBManager.getInstance().registerStatistics(
    "Blocks-Broken", "Blocks-Placed"
);

// Modifying Statistics
// Example: Tracking the amount of joins for a "Top Joins" Leaderboard
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        String stat = "Joins";
        Player player = event.getPlayer();
        LBPlayer joinPlayer = LBManager.getInstance().getProfile(player.getUniqueId());
        joinPlayer.setStatistic(stat, (joinPlayer.getStatistic(stat) + 1));
    }
```

## Maven Repository

**Repository**:

```
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>
```

**Dependency (type latest version in for version)**

```
<!-- LeaderBoardsPlus API -->
<dependency>
  <groupId>com.github.NegativeKB</groupId>
  <artifactId>LeaderboardsPlus</artifactId>
  <version>VERSION</version>
  <scope>provided</scope>  
</dependency>
```
