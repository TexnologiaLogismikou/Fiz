/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.configurations.tools;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author iwann
 */
public class FileTools {
    static public boolean deleteFullDirectory(File path) {
        if( path.exists() ) {
          File[] files = path.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteFullDirectory(file);
                } else {
                    file.delete();
                }
            }
        }
        return( path.delete() );
    }
    
    static public boolean deleteTestFiles(File path ) throws IOException{
        List<byte[]> tstFiles = new ArrayList();
        tstFiles.add(Files.readAllBytes(new File(Attr.TESTING_IMAGES.getData() + "\\testImg.jpg").toPath()));
        tstFiles.add(Files.readAllBytes(new File(Attr.TESTING_IMAGES.getData() + "\\testImg2.jpg").toPath()));
        tstFiles.add(Files.readAllBytes(new File(Attr.TESTING_IMAGES.getData() + "\\testImg3.jpg").toPath()));
        
        if( path.exists() ) {
        File[] files = path.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteTestFiles(file);
                } else {
                    byte[] f = Files.readAllBytes(file.toPath());
                    for (byte[] vLookUp:tstFiles){
                        if (Arrays.equals(f, vLookUp)){
                            file.delete();
                        }
                    }
                }
            }
        }
        return true;
    }
    
}
