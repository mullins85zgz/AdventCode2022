package day7;

import utils.Constantes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedList;

public class Day7_1 {


    static String currDir = "/";
    static LinkedList<String> bufferDir = new LinkedList<String>();
    static MiDirectorio2 raizStatic = new MiDirectorio2("/");
    public static void main(String[] args) {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        bufferDir.add(new String(currDir));



        try {
            archivo = new File (Constantes.inputRoot + "input7.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;
            while((linea=br.readLine())!=null){
                if(linea.trim().length()!=0){
                    raizStatic = procesaLinea(raizStatic,linea);
                }
            }

            raizStatic = init(raizStatic);

            pintaEstructura(0,raizStatic);
            System.out.println(sumaCantidad(0,raizStatic));

        }
        catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if( null != fr ){
                    fr.close();
                }
            }catch (Exception e2){
                e2.printStackTrace();
            }
        }
    }

    public static MiDirectorio2 init(MiDirectorio2 raiz){
        while(raiz.getParentFolder()!=null){
            raiz = init(raiz.getParentFolder());
        }
        return raiz;
    }

    public static void pintaEstructura(int cont, MiDirectorio2 raiz){
        System.out.println(tabs(cont) + "DIR> " + raiz.getFolderName());
        for(MiDirectorio2 dir:raiz.getDirs().values()){
            pintaEstructura(++cont,dir);
            raiz.setTotalSize(raiz.getTotalSize()+ dir.getTotalSize());
        }
        for(MiFichero2 fichero:raiz.getFiles().values()){
            raiz.setTotalSize(raiz.getTotalSize()+ fichero.getSize());
            System.out.println(tabs(cont) + "  FILE> " + fichero.getFileName() + " - " + fichero.getSize());
        }
    }

    public static int sumaCantidad(int ret, MiDirectorio2 raiz){
        if(raiz.getTotalSize()<=100000) {
            ret += raiz.getTotalSize();
            System.out.println("Directorio[" + raiz.getFolderName() + "] Suma " + raiz.getTotalSize());;
        }
        for(MiDirectorio2 dir:raiz.getDirs().values()){
            ret=sumaCantidad(ret,dir);
        }
        return ret;
    }

    public static String tabs(int cont){
        String tabs="";
        for(int i=0;i<cont;i++){
            tabs+="     ";
        }
        return tabs;
    }

    public static MiDirectorio2 procesaLinea(MiDirectorio2 raiz, String linea){
        //Cambio de directorio
        if(!linea.startsWith("$ cd ..") && linea.startsWith("$ cd")) {
            String parentFolderAux = new String(currDir);
            currDir = linea.substring(5, linea.length());
//            System.out.println(currDir);
            bufferDir.add(new String(currDir));
            if(currDir.equals("/")){
                return raiz;
            }else{
                raiz.getDirs().get(currDir).setParentFolderStr(parentFolderAux);
                raiz.getDirs().get(currDir).setParentFolder(raiz);
                return raiz.getDirs().get(currDir); //Devolvemos el que nos acabamos de meter
            }
        }
        //Subimos directorio
        if(linea.startsWith("$ cd ..")) {
            bufferDir.removeLast();
            currDir = bufferDir.getLast();
//            System.out.println("RETORNO: " + currDir);
            return raiz.getParentFolder()==null?raiz:raiz.getParentFolder();  //Devolvemos el superior
        }
        //Actualizamos directorio si es que no existia
        if(linea.startsWith("dir")){
            String subDir =  linea.substring(4, linea.length());
            MiDirectorio2 miSubDir = raiz.getDirs().get(subDir);
            //Si no existe el dir lo creamos
            if(miSubDir==null){
                miSubDir=new MiDirectorio2(subDir);
                raiz.getDirs().put(subDir,miSubDir);
            }
            return raiz;
        }
        //Ignoramos el LS
        if(linea.startsWith("ls")){
            //Do nothing
            return raiz;
        }
        //Es fichero
        if(Character.isDigit(linea.charAt(0))){
            String[] datosFichero = linea.split(" ");
            int size = Integer.valueOf(datosFichero[0]);
            String fileName = datosFichero[1];
            raiz.getFiles().put(fileName,new MiFichero2(fileName,size));
            return raiz;
        }
        return raiz;
    }

}

class MiFichero{
    String fileName;
    int size;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public MiFichero(String fileName, int size) {
        this.fileName = fileName;
        this.size = size;
    }
}

class MiDirectorio{
    String folderName;
    String parantFolderStr;
    MiDirectorio2 parentFolder;
    int totalSize = 0;
    HashMap<String, MiDirectorio2> dirs= new HashMap<String, MiDirectorio2>();
    HashMap<String, MiFichero2> files= new HashMap<String, MiFichero2>();

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public HashMap<String, MiDirectorio2> getDirs() {
        return dirs;
    }

    public void setDirs(HashMap<String, MiDirectorio2> dirs) {
        this.dirs = dirs;
    }

    public HashMap<String, MiFichero2> getFiles() {
        return files;
    }

    public void setFiles(HashMap<String, MiFichero2> files) {
        this.files = files;
    }

    public MiDirectorio(String folderName) {
        this.folderName = folderName;
    }

    public MiDirectorio() {
    }



    public String getParantFolderStr() {
        return parantFolderStr;
    }

    public void setParentFolderStr(String parantFolderStr) {
        this.parantFolderStr = parantFolderStr;
    }

    public MiDirectorio2 getParentFolder() {
        return parentFolder;
    }

    public void setParentFolder(MiDirectorio2 parentFolder) {
        this.parentFolder = parentFolder;
    }
}
