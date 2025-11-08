# UltraMovement - Unleash Extreme Movement in Minecraft



UltraMovement is a Spigot/Paper plugin that introduces dynamic and advanced movement mechanics, elevating the parkour and exploration experience on your server. Use special items to defy gravity and move like never before!

⚙️ Included Features

This plugin focuses on three item-driven movement mechanics:

1. 🚀 Iron Wing Boots (Double Jump)

The double jump ability is activated when equipping the Iron Wing Boots.

Ability: Allows players to jump a second time while airborne.

Anti-Damage Mechanic: Includes a brief Slow Falling effect to neutralize accidental fall damage immediately after a jump, significantly improving fluidity.

Recharge: Automatically recharges upon touching the ground.

2. ⚡ Dash Amulet (Dash)

Activated by equipping the Speed Amulet (currently a Rabbit's Foot) in the main hand.

Activation: Activated by performing a Right Click action while holding the item.

Effect: Launches the player forward with a quick horizontal boost, ideal for crossing large gaps.

Cooldown: Includes a built-in cooldown period to balance the ability.

3. 🐸 Pogo Block (Bounce Block)

The special Pogo Block (currently identified as a Slime Block) allows for rebounding.

Activation: Activated by Left-Clicking the block (Slime Block) while near it or upon landing on it (based on code intent).

Effect: Provides a strong vertical and horizontal rebound, enabling jump combos and advanced parkour routes.

🕹️ Commands

The plugin uses one main command for administrative control. It requires the permission ultramovement.admin.

Command-------------------------------------Description------------------------------------Permission

/um or /ultramovement     -       Displays plugin usage information.         -          ultramovement.admin

/um enable         -           Activates all UltraMovement abilities.         -         ultramovement.admin

/um disable       -       Deactivates all UltraMovement abilities instantly.      -     ultramovement.admin

/um get [ability]       -       It lets you obtain one of the objects 
                                    integrated into the plugin                -        ultramovement.admin

🛠️ Installation and Usage

Download: Get the latest version of UltraMovement-X.X.jar from the releases page.
Move: Place the .jar file into your server's /plugins/ directory (Paper or Spigot).
Start/Reload: Restart or use /reload in the server console.

Development and Compilation

If you wish to compile the source code yourself:

Clone the repository: git clone https://github.com/AndresTube/Ultra-Movement.git
Navigate to the project folder: cd UltraMovement
Compile using Gradle: ./gradlew clean shadowJar

The resulting JAR file will be located in the build/libs/ folder.

🤝 Contributions

Contributions are welcome! If you want to add a new movement mechanic, report a bug, or improve the code, please open an Issue or submit a Pull Request to this repository.

📝 License
This project is licensed under the MIT License.

This project is licensed under the MIT License.
