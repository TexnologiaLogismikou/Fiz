/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.configurations.tools;

/**
 *
 * @author arxa
 */

// This Class produces a very large string with 120000 characters
class LargeStringFactory
{
    private int linesLeft;
    private String line;
    private static String largeString;

    public LargeStringFactory(String line, int count)
    {
        linesLeft = count;
        this.line = line;
    }
    
    public static String getLargeString()
    {
        LargeStringFactory scanner = new LargeStringFactory("test", 30000);
        StringBuilder builder = new StringBuilder();
        while (scanner.hasNext())
        {
            builder.append(scanner.next());
        }    
        largeString = builder.toString(); // someString = 120000
        return largeString;
    }

    public boolean hasNext()
    {
        return linesLeft > 0;
    }

    public String next()
    {
        linesLeft--;
        return line;
    }
}
