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
            if (otherChars.length() > 7) {
//                finalString = firstChar + Integer.toString(otherChars.length()) + Integer.toString(hashCodeValidator(tmstamp)) + otherChars;  
                  throw new NumberFormatException("Number was too big");
            } else {
                finalString = firstChar + "0" + otherChars.length() + Integer.toString(hashCodeValidator(tmstamp)) + otherChars;                
            }            
        } else {
            finalString = firstChar + "00" + Integer.toString(hashCodeValidator(tmstamp));
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
    
    /**
     * When the encoded name is invalid this converter should be used
     * @param hsTag
     * @return 
     */
    public static String invalidPathConvertrer(Long hsTag){
        String finalPath = Attr.IMAGES_OUTPUT_FOLDER.getData() + "\\" + "WrongName" + "\\" + hsTag + ".jpg";
        
        return finalPath; 
    }
    
    private static int hashCodeValidator(int hash){
        if (hash < 0 ){
            return -hash;
        }
        return hash;
    }
}
