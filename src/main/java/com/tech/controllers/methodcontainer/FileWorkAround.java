/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.controllers.methodcontainer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

/**
 *
 * @author KuroiTenshi
 */
public class FileWorkAround {
    
    public boolean fileWorkAroundCall(String filePath,byte[] b,StandardOpenOption openOption) throws IOException {
        File newFile = new File(filePath);
        if (!newFile.getParentFile().exists()){
             newFile.getParentFile().mkdirs(); //need check if creation fails?
            }
        Files.write(newFile.toPath(), b, openOption);//if file exists?
        return true;
    }
    
}
