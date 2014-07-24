package com.turpgames.maze.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.badlogic.gdx.Gdx;

public class Util {
	public static void write(Object obj, String path) {
		byte[] data = serialize(obj);
		Gdx.files.internal(path).writeBytes(data, false);
	}

	public static byte[] readBytes(InputStream inputStream) throws IOException {	
		ByteArrayOutputStream baos = new ByteArrayOutputStream();				
		byte[] buffer = new byte[1024];
		int read = 0;
		while ((read = inputStream.read(buffer, 0, buffer.length)) != -1) {
			baos.write(buffer, 0, read);
		}		
		baos.flush();		
		return baos.toByteArray();
	}
	
	public static Object load(String path) {
		try {
			InputStream is = Gdx.files.internal(path).read();
			byte[] data = readBytes(is);
			return deserialize(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private static byte[] serialize(Object obj) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
		try {
			ObjectOutputStream oos = new ObjectOutputStream(baos); 
			oos.writeObject(obj);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		byte[] data = baos.toByteArray();
		return data;
	}
	
	private static Object deserialize(byte[] data) throws IOException,
			ClassNotFoundException {
		ByteArrayInputStream bais = new ByteArrayInputStream(data); 
		ObjectInputStream ois = new ObjectInputStream(bais);
		return ois.readObject();
	}
}
