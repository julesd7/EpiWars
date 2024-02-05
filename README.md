# EpiWars

Project developed as part of a HACKATON.

EpiWars is a Minecraft plugin inspired by the famous SkyWars game. This project was completed in 3 days without prior knowledge of the JAVA language.

**Plugin Framework:** Spigot

---


## Installation

1. Clone the repository:
    ```bash
    git clone https://github.com/julesd7/EpiWars.git
    ```

2. Navigate to the project directory:
    ```bash
    cd EpiWars
    ```

3. Copy the EpiWars-1-0-SNAPSHOT.jar to the 'plugins' folder of your Minecraft server.
    ```bash
    cp EpiWars-1-0-SNAPSHOT.jar /path/to/your/server/plugins/
    ```

    Replace /path/to/your/server/ with the actual path to your server folder. This command will copy the 'EpiWars-1-0-SNAPSHOT.jar' file to the 'plugins' directory of your server.

4. Reload your server.

The 'EpiWars' folder is now created in your 'plugins' directory, containing a 'GameMaps' folder and a 'config.yml' configuration file.

---

### GameMaps

In the 'GameMaps' folder, add a world where the game will take place. By default, you can use the 'EpiwarsDefaultMap' world folder for testing purposes.

To do this, navigate back to the root of the cloned folder and copy the 'EpiwarsDefaultMap' folder to your server's 'GameMaps' directory using the following command:

```bash
cp -r EpiwarsDefaultMap /path/to/your/server/plugins/EpiWars/GameMaps/
```

Replace /path/to/your/server/ with the actual path to your server folder. This command will recursively copy the 'EpiwarsDefaultMap' folder and its contents to the 'GameMaps' directory of your EpiWars plugin.

---

### config.yml

The plugin comes with a default configuration file (`config.yml`) that is generated upon first use. Below are some key settings you might want to be aware of:

- **Map Settings:**
  - `map-size`: Defines the maximum number of players.
  - `map-name`: Specifies the default map name.
  - `min-players`: Sets the minimum number of players required to start a game.
  - `player-to-win`: Specifies the number of players required to win.
  - `countdown`: Sets the time players spend in the waiting zone before the game starts.
  - `auto-start`: Specifies the time before the game automatically starts.

- **Spawn Points:**
  - The default map, named 'EpiwarsDefaultMap,' comes with predefined spawn points. Players will start at these locations when the game begins.

- **Loot Items:**
  - The loot items available in the game are predefined and include various weapons, armor, and other items. Each item has a chance of appearing during the game.

To customize these settings, you can modify the `config.yml` file in the 'plugins/EpiWars' directory after the first use.

Keep in mind that the default map 'EpiwarsDefaultMap' is included for initial plugin testing. Make sure to replace it with your custom maps for a more tailored gaming experience.

---


## Commands

- **`/setspawn`**

  This command should be used in your default world. Players will be teleported there upon connecting to the server and at the end of each game.

  ⚠️ **Warning ⚠️**
  If this command is not executed, and no spawn is set, the 'epiwars' commands will not work.

  ⚠️ This command requires being a server operator.
  

- **`/spawn`**

  This command allows players to teleport to the spawn if it is set.
  

- **`/epiwars join`**

  This command allows players to join the waiting list for the upcoming game if it's not full. Once the minimum number of players for a game is reached, a timer starts to automatically launch the game.
  

- **`/epiwars leave`**

  This command allows players to leave the waiting list for the upcoming game. If the minimum number of players for a game is not reached, the timer is stopped to prevent automatic game launch.
  
 
 - **`/epiwars start`**

    Allows manually starting the game.

    ⚠️ **Warning ⚠️**
    If the minimum number of players is not reached, it is not possible to execute the command.
  
    ⚠️ This command requires being a server operator.
   

- **`/epiwars stop`**

  Allows manually stopping the ongoing game.
  
  ⚠️ This command requires being a server operator.
  

- **`/epiwars reset`**

  Allows resetting the map and chests.

  ⚠️ **_Careful: This command does not stop the ongoing game. It is necessary to stop the game before executing this command to avoid any issues._**

  ⚠️ This command requires being a server operator.
  
---


## Have Fun

Enjoy your EpiWars experience!
