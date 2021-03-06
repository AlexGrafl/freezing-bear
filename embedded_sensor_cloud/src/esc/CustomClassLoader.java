package esc;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Hashtable;

public class CustomClassLoader extends ClassLoader {
    private Hashtable classes = new Hashtable();

    public CustomClassLoader(){
        super(CustomClassLoader.class.getClassLoader());
    }

    public Class loadClass(String className) throws ClassNotFoundException {
        return findClass(className);
    }

    public Class findClass(String className){
        byte classByte[];
        Class result;
        result = (Class)classes.get(className);
        if(result != null){
            return result;
        }

        try{
            return findSystemClass(className);
        }catch(Exception ignored){

        }
        try{
            String classPath = ClassLoader.getSystemResource(className.replace('.',
                    File.separatorChar)+".class").getFile().substring(1);
            classByte = loadClassData(classPath);
            result = defineClass(className,classByte,0,classByte.length,null);
            //noinspection unchecked
            classes.put(className,result);
            return result;
        }catch(Exception e){
            return null;
        }
    }

    private byte[] loadClassData(String className) throws IOException{

        File f ;
        f = new File(className);
        int size = (int)f.length();
        byte buff[] = new byte[size];
        FileInputStream fis = new FileInputStream(f);
        DataInputStream dis = new DataInputStream(fis);
        dis.readFully(buff);
        dis.close();
        return buff;
    }
}