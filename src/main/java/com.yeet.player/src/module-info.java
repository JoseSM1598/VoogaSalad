module com.yeet.player {
    requires java.desktop;
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.media;
    requires com.yeet.audio;
    requires com.yeet.input;
    requires com.yeet.messenger;
    requires com.yeet.renderer;
    requires com.google.common;
    requires com.yeet.data;
    requires com.yeet.physics;
    requires com.yeet.replay;
    exports player.external;
}