package com.mygdx.isometricpractice.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.isometricpractice.IsoCamTest;
import com.mygdx.isometricpractice.IsometricPractice;
import com.mygdx.isometricpractice.MaxmanGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;
		config.height = 720;
		config.title = "Max-Man";
		config.useGL30 = true;
		new LwjglApplication(new IsometricPractice(), config);
	}
}
