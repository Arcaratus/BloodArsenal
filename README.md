#Blood Arsenal  [![Build Status](http://tehnut.info/jenkins/job/Blood%20Arsenal%202/job/1.9/badge/icon)](http://tehnut.info/jenkins/job/Blood%20Arsenal%202/job/1.9/)

###[Downloads](http://minecraft.curseforge.com/projects/blood-magic-addon-blood-arsenal/files)

##Information

Addon based around Blood Magic!

##Links
* Twitter: [@Arcaratus](https://twitter.com/Arcaratus)
* Wiki: Found at [FTBWiki](http://ftbwiki.org/Blood_Magic) --Not actually on the wiki yet
* [Minecraft Forum Thread](http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/2231288-blood-magic-addon-blood-arsenal)
* [Curseforge](http://minecraft.curseforge.com/projects/blood-magic-addon-blood-arsenal/)
* [Donate](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=J7SNY7L82PQ82)
* [Patreon](https://www.patreon.com/Arcaratus)

##Development Setup

1. Fork this project to your own Github repository and clone it to your desktop.
2. Navigate to the directory you cloned to. Open a command window there and run `gradlew setupDevWorkspace` then (if you use Eclipse) `gradlew eclipse` or (if you use IDEA) `gradlew idea`. 
3. This process will setup [Forge](http://www.minecraftforge.net/forum/), your workspace, and link [MineTweaker](http://minetweaker3.powerofbytes.com/) as a dependency.
4. Open the project in your IDE of choice.
5. Set `../src/api/java` as a source directory.

####IntelliJ IDEA extra steps

[Setup video](https://www.youtube.com/watch?v=8VEdtQLuLO0&feature=youtu.be) by LexManos. For more information, refer to the [Forge Forums](http://www.minecraftforge.net/forum/index.php/topic,14048.0.html).

##Custom Builds

**Custom builds are *unsupported*. If you have an issue while using an unofficial build, it is not guaranteed that you will get support.**

####How to make a custom build:

1. Clone directly from this repository to your desktop.
2. Navigate to the directory you cloned to. Open a command window there and run `gradlew build`
3. Once it completes, your new build will be found at `../build/libs/BloodArsenal-*.jar`. You can ignore the `deobf`, `sources`, jars.

##License

![CCA4.0](https://licensebuttons.net/l/by/4.0/88x31.png)

Blood Arsenal by Arcaratus is licensed under a [Creative Commons Attribution 4.0 International License](http://creativecommons.org/licenses/by/4.0/).

##Installation Instructions

This mod requires "Minecraft Forge" in order to operate. It is incredibly easy to download and set up, so might as well get to it!

1. Download [Forge](http://files.minecraftforge.net/). Usually "Recommended" is best - you want the "universal", not the source. Forge also has an "install" option now, so all you need to do is launch that program and it will make a lovely Forge profile. If you haven't, look up how to use the installer and the new 1.6.x launcher if you are confused!

2. Download the latest version of BloodMagic from [Curseforge](http://minecraft.curseforge.com/mc-mods/224791-blood-magic).

3. Place the mod in the **mods** folder of your .minecraft. If you are unsure of where that is located, it is here: `../Users/you/AppData/roaming/.minecraft`.
