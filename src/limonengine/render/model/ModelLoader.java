package limonengine.render.model;

/*
 * .OBJ format model loader made by miker9(Andrew silkin) on 02.04.2013
 * Converts .OBJ files into buffers later to be used for rendering VBO's
 * You are allowed to copy, modify and spread code, if you specify the author
 * 
 * You can contact me on 00miker9@gmail.com
 * */


import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;


public class ModelLoader {
	
	private static ArrayList<float[]> vertice = new ArrayList<float[]>();
	private static ArrayList<float[]> normal = new ArrayList<float[]>();
	private static ArrayList<float[]> texcoord = new ArrayList<float[]>();
	private static ArrayList<int[]> faces = new ArrayList<int[]>();
	
	private static HashMap<String, Model> models = new HashMap<String, Model>();
	private static int num;
	
	
	public static void load(int count) {
		File[] fileList;
		fileList = getFiles();
		
		for(int i=num; i<fileList.length && i<num+count; i++) {
				models.put(fileList[i].getName(), loadModel("/res/models/"+fileList[i].getName()));
		}
		
		num += count;
	}
	
	
	public static Model loadModel(String path) {
		FileReader fr = null;
		try {
			fr = new FileReader(new File(".").getCanonicalPath() + path);
		} catch (IOException e) {
			e.printStackTrace();
		}
        assert fr != null;
        BufferedReader br = new BufferedReader(fr);
		cleanup();
		
		try {
			return loadObject(br);
		} catch (IOException e) {
            e.printStackTrace();
		}
		
		return null;
	}
	
	
	
	
	
	
	
	
	public static Model get(String s) {
		return models.get(s);
	}
	
	public static int getModelNum() {
		return models.size();
	}
	
	public static int getSRCModelNum() {
		return getFiles().length;
	}
	
	private static File[] getFiles() {
		File[] fileList;
		fileList = new File("res/models").listFiles();
		int texcount=0;
        assert fileList != null;
        for (File aFileList1 : fileList) {
            if (aFileList1.getName().contains(".obj")) {
                texcount++;
            }
        }
		
		File[] texfiles = new File[texcount];
		int x=0;
        for (File aFileList : fileList) {
            if (aFileList.getName().contains(".obj")) {
                texfiles[x] = aFileList;
                x++;
            }
        }
		return texfiles;
	}
	
	
	private static void cleanup() {
		vertice.clear();
		normal.clear();
		texcoord.clear();
		faces.clear();
	}
	
	
	private static Model loadObject(BufferedReader br) throws IOException {
		String line;
		
		while ((line = br.readLine()) != null) {
			line = line.trim();
			
			if(line.isEmpty()) {
				continue;
			}


            String[] array = line.split("\\s+");
	        String type = array[0];
			
			if(type.equals("v")) {
				float components[] = new float[3];
				components[0] =  Float.parseFloat(array[1]);
				components[1] =  Float.parseFloat(array[2]);
				components[2] =  Float.parseFloat(array[3]);
				vertice.add(components);
			}
			if(type.equals("vt")) {
				float components[] = new float[2];
				components[0] =  Float.parseFloat(array[1]);
				components[1] =  1-Float.parseFloat(array[2]);
				texcoord.add(components);
			}
			if(type.equals("vn")) {
				float components[] = new float[3];
				components[0] =  Float.parseFloat(array[1]);
				components[1] =  Float.parseFloat(array[2]);
				components[2] =  Float.parseFloat(array[3]);
				normal.add(components);
			}
			if(type.equals("f")) {
				for(int i=0; i<3; i++) {
					String parts[] = array[i+1].split("/");
					int components[] = new int[3];
					
					components[0] = Integer.parseInt(parts[0]);
					components[1] = Integer.parseInt(parts[1]);
					components[2] = Integer.parseInt(parts[2]);
					
					faces.add(components);
				}
			}
		}
		
		
		
		FloatBuffer vertices = BufferUtils.createFloatBuffer(faces.size() * 3);
		FloatBuffer normals = BufferUtils.createFloatBuffer(faces.size() * 3);
		FloatBuffer texcoords = BufferUtils.createFloatBuffer(faces.size() * 2);

        for (int[] components : faces) {
            vertices.put(vertice.get(components[0] - 1));
            texcoords.put(texcoord.get(components[1] - 1));
            normals.put(normal.get(components[2] - 1));

        }
		
		vertices.flip();
		texcoords.flip();
		normals.flip();
		
		int VBOVertex = GL15.glGenBuffers();
		int VBOTexture = GL15.glGenBuffers();
		int VBONormal = GL15.glGenBuffers();
		
		writeToVBO(VBOVertex, vertices);
		writeToVBO(VBOTexture, texcoords);
		writeToVBO(VBONormal, normals);

        return new Model(VBOVertex, VBONormal, VBOTexture, 0, 0, 0, faces.size()/3, faces.size());
		
	}

    private static void writeToVBO(int VBOHandle, FloatBuffer data) {
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBOHandle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, data, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }
}
