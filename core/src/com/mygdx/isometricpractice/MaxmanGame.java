package com.mygdx.isometricpractice;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;

public class MaxmanGame implements Screen, InputProcessor {
	Texture texture;
	private TiledMap map;
	private IsometricTiledMapRenderer renderer;
	OrthographicCamera camera;
	
	SpriteBatch batch;
	final Sprite[][] sprites = new Sprite[20][20];
	final Matrix4 matrix = new Matrix4();
	
	final Vector3 intersection = new Vector3();
	Sprite lastSelectedTile = null;
	
	private final Plane xzPlane = new Plane(new Vector3(0, 1, 0), 0);
	private final Vector3 curr = new Vector3();
	private final Vector3 last = new Vector3(-1, -1, -1);
	private final Vector3 delta = new Vector3();
	
	public MaxmanGame(){
		
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();		
		
		renderer.setView(camera);
		renderer.render();

		//renderer.getSpriteBatch().setProjectionMatrix(camera.combined);
		//renderer.getSpriteBatch().setTransformMatrix(matrix);
		
		checkTileTouched();
		
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportHeight = height;
		camera.viewportWidth = width;
		camera.update();

	}

	@Override
	public void show() {
		texture = new Texture(Gdx.files.internal("stone44.jpg"));
		map = new TmxMapLoader().load("maps/isomap.tmx");
		renderer = new IsometricTiledMapRenderer(map);

		camera = new OrthographicCamera();
		camera.position.set(600, 10, 10);
		//camera.direction.set(-1, -1, -1);
		camera.near = 1;
		camera.far = 100;		
		matrix.setToRotation(new Vector3(1, 0, 0), 90);
		
		for(int z = 0; z < 10; z++) {
			for(int x = 0; x < 10; x++) {
				sprites[x][z] = new Sprite();
				sprites[x][z].setPosition(x,z);
				sprites[x][z].setSize(1, 1);
			}
		}
 
		batch = new SpriteBatch();
		
		Gdx.input.setInputProcessor(this);
	}
	
	private void checkTileTouched() {
		if(Gdx.input.justTouched()){
			Ray pickRay = camera.getPickRay(Gdx.input.getX(), Gdx.input.getY());
			Intersector.intersectRayPlane(pickRay, xzPlane, intersection);
			int x = (int)intersection.x;
			int z = (int)intersection.z;
			if(x>=0 && x<10 && z>=0 && z<10){
				if(lastSelectedTile!=null){
					lastSelectedTile.setColor(1,1,1,1);
				}
				Sprite sprite = sprites[x][z];
				sprite.setColor(Color.CYAN);
				lastSelectedTile = sprite;
			}
		}
	}

	@Override
	public void hide() {
		dispose();

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		map.dispose();
		renderer.dispose();

	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		last.set(-1, -1, -1);
		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		Ray pickRay = camera.getPickRay(x, y);
		Intersector.intersectRayPlane(pickRay, xzPlane, curr);
		
		if(!(last.x==-1 && last.y==-1 && last.z==-1)){
			pickRay = camera.getPickRay(last.x, last.y);
			Intersector.intersectRayPlane(pickRay, xzPlane, delta);
			delta.sub(curr);
			camera.position.add(delta.x, delta.y, delta.z);
		}
		last.set(x, y, 0);
		
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
