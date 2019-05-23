package dataSystem;

public enum FilePath {
    GAMEPATH("/src/main/java/com.yeet.main/resources"),
    STAGEPATH("/stages"),
    CHARACTERPATH("/characters"),
    DATAPATH("/data"),
    BACKGROUNDPATH("/data/background"),
    BGMPATH("/data/bgm"),
    TILEPATH("/data/tiles"),
    SPLASHPATH("/data/splash"),
    STAGEPROPERTIES("/stageproperties.xml"),
    ATTACK("/attacks"),
    SOUND("/sounds"),
    SPRITE("/sprites"),
    CHARACTERPROPERTIES("/characterproperties.xml"),
    ATTACKPROPERTIES("/attacks/attackproperties.xml"),
    SOUNDPROPERTIES("/sounds/soundproperties.xml"),
    SPRITEPROPERTIES("/sprites/spriteproperties.xml"),
    GAMEPROPERTIES("/gameproperties.xml"),
    MODE("/modes"),
    TIME("/modes/time"),
    STOCK("/modes/stock"),
    DEFAULTGAMEPATH("/src/main/java/com.yeet.main/resources/defaultgame"),
    DEFAULTBACKGROUND("/data/background/fd.jpg"),
    DEFAULTBGM("/data/bgm/BGM.mp3"),
    DEFAULTTILE("/data/tiles/acacia_planks.png"),
    DEFAULTSPLASH("/data/splash/splash.png"),
    DEFAULTCOMBO("/combosetup.xml");

    private String myPath;

    FilePath(String path) {
        myPath = path;
    }

    public String getPath() {
        return myPath;
    }
}
