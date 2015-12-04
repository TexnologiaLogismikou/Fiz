/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech;

import net.minidev.json.JSONObject;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author KuroiTenshi
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(
        classes=Application.class) // The test runner will start the application using the main application class 
                                   // just as it would be starting normally
public abstract class AbstractTest {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    protected JSONObject json;
}
