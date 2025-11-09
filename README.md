<!--<div align="center">
    <img width="75" src="/FCL/src/main/res/drawable/img_app.png"></img>
</div>-->

---

# CraftLauncher

---

**Craft Launcher** is a lightweight, flexible, and open-source launcher for **Minecraft Java Edition** and **Bedrock Edition** on **Android**, developed by **Craft Team**.

Built on the backend of [PojavLauncher](https://github.com/PojavLauncherTeam/PojavLauncher), [Boat](https://github.com/AOF-Dev/Boat), and [LeviLauncher](https://github.com/LiteLDev/LeviLaunchroid), it provides a seamless cross-edition experience in a single app. Craft Launcher allows users to run both Minecraft Java and Bedrock without system installation, supporting reliable multi-version management with full isolation between installations, as well as external module loading to enhance gameplay.

It also includes built-in tools for managing **resources**, **worlds**, and **modifications**, making it a complete solution for players who want to manage versions, test mods, or optimize their gaming experience — all in one launcher.

## Features 

---

**Full Minecraft Version Support**
- Native support for semua versi Minecraft (termasuk snapshot terbaru)
- Mod loader support: Forge, NeoForge, LiteLoader, OptiFine, Fabric, Quilt, dan lainnya

**Java Runtime Management**
- Built-in multi-version Java runtimes (Java 8/11/17/21)
- Custom Java import: kamu bisa memasukkan Java runtime sendiri
- OpenJDK Mobile port: ARM32, ARM64, x86, x86_64 (Java 8, 17, 21)

**Modular & Advanced Technology**
<!--- Mendukung renderer pluginization ([FCLRendererPlugin](https://github.com/ShirosakiMio/FCLRendererPlugin))-->
- Shaders support (VirGL/Zink/MG renderers)
- OpenGL di lingkungan OpenJDK
- OpenAL (audio) untuk kompatibilitas lebih luas

**Resource Management & Customization**
- Manajemen dinamis: mods, modpacks, textures, shaders, saves, resource packs, serta dunia
- Import, export, back up resource packs dan dunia dengan built-in manager
- Tema personalisasi: background & color scheme pilihan
- Zoom game surface

**Input & Controls**
- Virtual mouse support dan key mapping yang bisa di-custom
- Input pipe baru, diimplementasikan native untuk performa dan kompatibilitas tinggi
- Sistem kontrol sepenuhnya baru dan lebih fleksibel

**Installasi & Integrasi APK**
- **APK Import & Installation-Free Launching:** Import APK Minecraft resmi dan jalankan langsung tanpa install sistem
- **SO Module Loading:** Bisa memuat modul native eksternal (SO) untuk menambah fitur atau performa

**Akun & Versi**
- Multi-version management & version isolation: Setiap versi terpisah dan dapat dikonfigurasi unik
- Multiple Xbox account management: Kelola dan switch akun Xbox dengan mudah

**Mod Installer**
- Headless mod installer & mod installer GUI

**Minecraft Compatibility**
- Support untuk Minecraft 1.12.2 dan di bawahnya
- Support untuk Minecraft 1.13 dan di atas
- Support untuk Minecraft 1.17 (22w13a) dan di atas

---

### Current Status

- [x] OpenJDK 8, 17, 21 Mobile port (ARM32/ARM64/x86/x86_64)
- [x] Headless dan GUI mod installer
- [x] OpenGL/OpenAL pada OpenJDK
- [x] Surface zoom
- [x] Input pipe native
- [x] Sistem kontrol baru
- [x] Kompatibilitas lintas versi Minecraft
- [ ] Fitur-lanjutan sedang dikembangkan!


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

Feel free to fork and submit pull requests. All contributions are welcome! Check for existing issues and create one if you would like a new feature.

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
