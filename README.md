<!--<div align="center">
    <img width="75" src="/FCL/src/main/res/drawable/img_app.png"></img>
</div>

----->

# CraftLauncher

---

**Craft Launcher** is a lightweight, flexible, and open-source launcher for **Minecraft Java Edition** and **Bedrock Edition** on **Android**, developed by **Craft Team**.

Built on the backend of [PojavLauncher](https://github.com/PojavLauncherTeam/PojavLauncher), [Boat](https://github.com/AOF-Dev/Boat), and [LeviLauncher](https://github.com/LiteLDev/LeviLaunchroid), it provides a seamless cross-edition experience in a single app. Craft Launcher allows users to run both Minecraft Java and Bedrock without system installation, supporting reliable multi-version management with full isolation between installations, as well as external module loading to enhance gameplay.

It also includes built-in tools for managing **resources**, **worlds**, and **modifications**, making it a complete solution for players who want to manage versions, test mods, or optimize their gaming experience — all in one launcher.

## Features

---

**Full Minecraft Version Support**
- Native support for all Minecraft versions (including the latest snapshots)
- Mod loader support: Forge, NeoForge, LiteLoader, OptiFine, Fabric, Quilt, and more

**Java Runtime Management**
- Built-in multi-version Java runtimes (Java 8/11/17/21)
- Custom Java import: you can add your own Java runtime
- OpenJDK Mobile port: ARM32, ARM64, x86, x86_64 (Java 8, 17, 21)

**Modular & Advanced Technology**
<!--- Supports renderer pluginization ([FCLRendererPlugin](https://github.com/ShirosakiMio/FCLRendererPlugin))-->
- Shaders support (VirGL/Zink/MG renderers)
- OpenGL in the OpenJDK environment
- OpenAL (audio) for wider compatibility

**Resource Management & Customization**
- Dynamic management: mods, modpacks, textures, shaders, saves, resource packs, and worlds
- Import, export, and back up resource packs and worlds with the built-in manager
- Personalized themes: customizable backgrounds and color schemes
- Zoomable game surface

**Input & Controls**
- Virtual mouse support and customizable key mapping
- New native input pipe implementation for high performance and compatibility
- Completely rewritten, more flexible controls system

**APK Installation & Integration**
- **APK Import & Installation-Free Launching:** Import the official Minecraft APK and run it directly without system installation
- **SO Module Loading:** Able to load external native SO modules for additional features or performance

**Accounts & Version Management**
- Multi-version management & version isolation: each version runs separately with unique configuration
- Multiple Xbox account management: easily manage and switch Xbox accounts

**Mod Installer**
- Headless mod installer & GUI mod installer

**Minecraft Compatibility**
- Supports Minecraft 1.12.2 and below
- Supports Minecraft 1.13 and above
- Supports Minecraft 1.17 (22w13a) and above

---

### Current Status

- [x] OpenJDK 8, 17, 21 Mobile port (ARM32/ARM64/x86/x86_64)
- [x] Headless and GUI mod installer
- [x] OpenGL/OpenAL in OpenJDK
- [x] Surface zoom
- [x] Native input pipe
- [x] Rewritten controls system
- [x] Cross-version Minecraft compatibility
- [ ] Advanced features are under development!

## Installation

---

1. Visit the [Releases Page](https://github.com/Craft-Team/CraftLauncher/releases/) and download the latest APK build
2. Open your device Settings and navigate to Security or Applications
3. Enable "Unknown Sources" or "Allow installation from unknown sources" to permit APK installation
4. Locate the downloaded APK file using your file manager and tap to install
5. Grant the necessary permissions when prompted during installation
6. Once installed, open LeviLauncher from your application drawer

## Contribution

---

Contributions are welcome! We welcome any type of contribution, not only code. For example, you can help improve the [wiki](https://craftteam.github.io/), contribute to the [translations](https://crowdin.com/project/craftlauncher), or submit bug reports and feature requests.

Any code change should be submitted as a pull request. The description should explain what the code does and give steps to execute it.

## License

---

CraftLauncher is licensed under the [GNU General Public License v3.0](LICENSE).

## Support & Contact

---

**Author / Team**: Craft Team

**Project Repository:** [https://github.com/Craft-Team/CraftLauncher](https://github.com/Craft-Team/CraftLauncher)

**Report Issues:** [GitHub Issues Page](https://github.com/Craft-Team/CraftLauncher/issues)

You can reach out via [Craft-Team GitHub Organization](https://github.com/Craft-Team).

---
