package com.turpgames.editor.data;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.turpgames.editor.CanvasCenter;
import com.turpgames.editor.CanvasObject;
import com.turpgames.editor.Coordinates;

public class MapData {
	Map<Coordinates, CanvasObject> objects;
	List<CanvasCenter> centers;
	
	public MapData(Map<Coordinates, CanvasObject> objects, List<CanvasCenter> centers) {
		this.objects = objects;
		this.centers = centers;
	}
	
	public void write(String path) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
		try {
			ObjectOutputStream oos = new ObjectOutputStream(baos); 
			oos.writeObject(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		byte[] data = baos.toByteArray();
		Gdx.files.internal(path).writeBytes(data, false);
	}
	
	private static byte[] readBytes(InputStream inputStream) throws IOException {	
		ByteArrayOutputStream baos = new ByteArrayOutputStream();				
		byte[] buffer = new byte[1024];
		int read = 0;
		while ((read = inputStream.read(buffer, 0, buffer.length)) != -1) {
			baos.write(buffer, 0, read);
		}		
		baos.flush();		
		return baos.toByteArray();
	}
	
	public static MapData load(String path) {
		MapData mapData = null;
		try {
			InputStream is = Gdx.files.internal(path).read();
			byte[] data = readBytes(is);
			ByteArrayInputStream bais = new ByteArrayInputStream(data); 
			ObjectInputStream ois = new ObjectInputStream(bais);
			mapData = (MapData) ois.readObject();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mapData;
	}
}
