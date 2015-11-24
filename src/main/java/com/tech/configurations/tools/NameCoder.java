/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.configurations.tools;

/**
 *
 * @author KuroiTenshi
 */
public class NameCoder {
    
    /**
     * 
     * @param userid
     * @param tmstamp
     * @return part1 = 178 , part2 = 456 -> returns 1245678 which is 1_2_456_78 part1 cut in 2 parts.. 
     */
    public static Long nameConverter(Long userid,int tmstamp) { 
        String str = Long.toString(userid);
        String finalString;
        char firstChar = str.charAt(0);
        if (str.length() > 1 ) {
            String otherChars = str.substring(1);
            if (otherChars.length() > 9) {
                finalString = firstChar + otherChars.length() + Integer.toString(tmstamp) + otherChars;                
            } else {
                finalString = firstChar + "0" + otherChars.length() + Integer.toString(tmstamp) + otherChars;                
            }            
        } else {
            finalString = firstChar + "00" + Integer.toString(tmstamp);
        }
        return Long.parseLong(finalString);
    }

    public static String pathConverter(Long hsTag){
        Long userid;
        String str = Long.toString(hsTag);
        String tmStr = str.substring(0,1) + str.substring(str.length() - Integer.parseInt(str.substring(1,3)));
        userid = Long.parseLong(tmStr);
        
        String finalPath = Attr.IMAGES_OUTPUT_FOLDER.getData() + "\\" + userid + "\\" + hsTag + ".jpg";
        
        return finalPath;        
    }
}
