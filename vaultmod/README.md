# Vault Auto Click (Fabric, Minecraft Java 26.3)

Rechtsklickt automatisch auf einen Tresor, sobald dieser einen Heavy Core
oder Dreizack anzeigt. Voraussetzungen, damit der Klick ausgeloest wird:
- Du schaust den Tresor an (Fadenkreuz)
- Du haeltst einen Trial Key / Ominous Trial Key in der Haupthand
- Der Tresor ist aktiv (Anzeige dreht sich)

## Bauen
1. JDK 25 installieren.
2. Projekt in IntelliJ IDEA oeffnen (Gradle-Import) ODER im Ordner:
       gradle wrapper --gradle-version 9.6.0
       ./gradlew build      (Windows: gradlew.bat build)
3. Fertige Mod: build/libs/vaultautoclick-1.0.0.jar
4. In .minecraft/mods legen, zusammen mit Fabric Loader 0.19.5+ und Fabric API 0.162.0+26.3.
