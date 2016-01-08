/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.controllers;

import com.tech.AbstractControllerTest;
import com.tech.configurations.InitializeValidators;
import com.tech.configurations.tools.Attr;
import com.tech.configurations.tools.FileTools;
import com.tech.configurations.tools.JSONToolConverter;
import com.tech.configurations.tools.Pair;
import com.tech.configurations.tools.Responses;
import com.tech.configurations.tools.ValidationScopes;
import com.tech.configurations.tools.customvalidators.elements.numbervalidators.NotEmptyValidatorN;
import com.tech.configurations.tools.customvalidators.elements.stringvalidators.MaxLengthValidator;
import com.tech.configurations.tools.customvalidators.elements.stringvalidators.NoSpacesValidator;
import com.tech.configurations.tools.customvalidators.elements.stringvalidators.NotMatchValidator;
import com.tech.controllers.methodcontainer.FileWorkAround;
import com.tech.exceptions.customexceptions.InappropriateValidatorException;
import com.tech.exceptions.customexceptions.ValidatorNotListedException;
import com.tech.models.dtos.chatroom.ChatroomDeleteDTO;
import com.tech.models.entities.ImagesMod;
import com.tech.models.entities.user.User;
import com.tech.services.ImagesService;
import com.tech.services.user.UserService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.transaction.Transactional;
import net.minidev.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author iwann
 */
@Transactional
@ActiveProfiles({"iwanna","iwanna"})
@PrepareForTest(FileWorkAround.class)
public class ImagesControllerTest extends AbstractControllerTest{
    String uri;
    
    @Mock
    private ImagesService imagesService;
    
    @Mock
    private UserService userService;
    
    @InjectMocks
    private ImagesController controller;
    
    @BeforeClass
    public static void setUpClass()
    {
        ImagesController.cleanValidator();
        InitializeValidators.CleanCustomValidators();
    }
    
    @AfterClass
    public static void tearDownClass()
    {     
        InitializeValidators.InitializeCustomValidators();
    }    
    
    @Before
    @Override
    public void setUp() throws Exception{ 
        MockitoAnnotations.initMocks(this);
        
        super.setUp(controller);
        
        uri = "/images";
    }
    
    @After
    @Override
    public void tearDown() {   
        super.tearDown();
        uri = null;
        
        try {
            FileTools.deleteTestFiles(new File(Attr.IMAGES_OUTPUT_FOLDER.getData()));
        } catch (IOException ex) {
            Logger.getLogger(ImagesControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ImagesController.cleanValidator();
    }
    
    @Test
    public void testLoadImages() throws Exception{
        byte[] f = Files.readAllBytes(new File(Attr.TESTING_IMAGES.getData() + "\\testImg.jpg").toPath());
        MockMultipartFile MF = new MockMultipartFile("file","psaraki.jpg", "multipart/form-data", f);     
        
        when(userService.checkUsername("iwanna")).thenReturn(true);
        when(userService.getUserByUsername("iwanna")).thenReturn(new User(2L,"iwanna","iwanna",true));
        doNothing().when(imagesService).addImage(any(ImagesMod.class));
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .fileUpload(uri)
                .file(MF)
                .param("username", "iwanna"))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
               
        verify(userService,times(1)).checkUsername("iwanna");
        verify(imagesService,times(1)).addImage(any(ImagesMod.class));
        verify(userService,times(1)).getUserByUsername("iwanna");
        
        Assert.assertEquals("Fail expected status 200 but was " + status, 200, status);
        Assert.assertTrue("Fail expected Response Body to be '" + Responses.SUCCESS.getData() + "'",
                content.equals(Responses.SUCCESS.getData()));        
    }   
    
    @Test
    public void testLoadImagesNull() throws Exception{
        byte[] f = null;
        MockMultipartFile MF = new MockMultipartFile("file","psaraki.jpg", "multipart/form-data", f);     
        
        when(userService.checkUsername("iwanna")).thenReturn(true);
        when(userService.getUserByUsername("iwanna")).thenReturn(new User(2L,"iwanna","iwanna",true));
        doNothing().when(imagesService).addImage(any(ImagesMod.class));
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .fileUpload(uri)
                .file(MF)
                .param("username", "iwanna"))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
               
        verify(userService,times(1)).checkUsername("iwanna");
        verify(imagesService,times(0)).addImage(any(ImagesMod.class));
        verify(userService,times(1)).getUserByUsername("iwanna");
        
        Assert.assertEquals("Fail expected status 204 but was " + status, 204, status);
        Assert.assertTrue("Fail expected Response Body to be '" + Responses.FILE_WAS_EMPTY.getData() + "'",
                content.equals(Responses.FILE_WAS_EMPTY.getData()));        
    }    
    
    @Test
    public void testLoadImagesWrongUsername() throws Exception{
        byte[] f = Files.readAllBytes(new File(Attr.TESTING_IMAGES.getData() + "\\testImg.jpg").toPath());
        MockMultipartFile MF = new MockMultipartFile("file","psaraki.jpg", "multipart/form-data", f);     
        
        when(userService.checkUsername("notIwanna")).thenReturn(false);
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .fileUpload(uri)
                .file(MF)
                .param("username", "notIwanna"))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
               
        verify(userService,times(1)).checkUsername("notIwanna");
        verify(imagesService,times(0)).addImage(any(ImagesMod.class));
        verify(userService,times(0)).getUserByUsername(anyString());
        
        Assert.assertEquals("Fail expected status 404 but was " + status, 404, status);
        Assert.assertTrue("Fail expected Response Body to be '" + Responses.NOT_AVAILABLE.getData() + "'",
                content.equals(Responses.NOT_AVAILABLE.getData()));        
    }

    @Test
    public void testLoadImagesUsernameWrongFormat() throws Exception {
        byte[] f = Files.readAllBytes(new File(Attr.TESTING_IMAGES.getData() + "\\testImg.jpg").toPath());
        MockMultipartFile MF = new MockMultipartFile("file","psaraki.jpg", "multipart/form-data", f);     
        
        try 
        {
            ImagesController.registerValidator(new NotMatchValidator("^[A-Za-z]"), ValidationScopes.USER_NAME);
        } 
        catch (InappropriateValidatorException | ValidatorNotListedException ex) 
        {
            Logger.getLogger(ImagesController.class.getName()).log(Level.SEVERE, null, ex);
            Assert.fail("something went wrong while registering");
        } 
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .fileUpload(uri)
                .file(MF)
                .param("username", "@Iwanna"))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
               
        verify(userService,times(0)).checkUsername(anyString());
        verify(imagesService,times(0)).addImage(any(ImagesMod.class));
        verify(userService,times(0)).getUserByUsername(anyString());
        
        Assert.assertEquals("Fail expected status 406 but was " + status, 406, status);
        Assert.assertTrue("Fail expected Response Body to be '" + Responses.STRING_INAPPROPRIATE_FORMAT.getData() + "'",
                content.equals(Responses.STRING_INAPPROPRIATE_FORMAT.getData())); 
        
        ImagesController.cleanValidator();
    }
    
    @Test
    public void testLoadImagesPathExists() throws Exception{
        byte[] f = Files.readAllBytes(new File(Attr.TESTING_IMAGES.getData() + "\\testImg.jpg").toPath());
        MockMultipartFile MF = new MockMultipartFile("file","psaraki.jpg", "multipart/form-data", f);     
        
        when(userService.checkUsername("iwanna")).thenReturn(true);
        when(userService.getUserByUsername("iwanna")).thenReturn(new User(2L,"iwanna","iwanna",true));
        doNothing().when(imagesService).addImage(any(ImagesMod.class));
        
        File file = new File("C:\\FizData\\Images\\2");
        Assert.assertTrue(file.exists()); //checks if the folder exists before the controller call
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .fileUpload(uri)
                .file(MF)
                .param("username", "iwanna"))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
               
        verify(userService,times(1)).checkUsername("iwanna");
        verify(imagesService,times(1)).addImage(any(ImagesMod.class));
        verify(userService,times(1)).getUserByUsername("iwanna");
        
        Assert.assertTrue(file.exists()); //verifies that folder still exists after the controller call
        Assert.assertEquals("Fail expected status 200 but was " + status, 200, status);
        Assert.assertTrue("Fail expected Response Body to be '" + Responses.SUCCESS.getData() + "'",
                content.equals(Responses.SUCCESS.getData()));        
    } 
    
    @Test
    public void testLoadImagesPathDoesntExist() throws Exception{
        byte[] f = Files.readAllBytes(new File(Attr.TESTING_IMAGES.getData() + "\\testImg.jpg").toPath());
        MockMultipartFile MF = new MockMultipartFile("file","psaraki.jpg", "multipart/form-data", f);     
        
        when(userService.checkUsername("someoneElse")).thenReturn(true);
        when(userService.getUserByUsername("someoneElse")).thenReturn(new User(5L,"someoneElse","someoneElse",true));
        doNothing().when(imagesService).addImage(any(ImagesMod.class));
        
        File file = new File("C:\\FizData\\Images\\5");
        Assert.assertFalse(file.exists()); //checks if the folder exists before the controller call
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .fileUpload(uri)
                .file(MF)
                .param("username", "someoneElse"))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
               
        verify(userService,times(1)).checkUsername("someoneElse");
        verify(imagesService,times(1)).addImage(any(ImagesMod.class));
        verify(userService,times(1)).getUserByUsername("someoneElse");
        
        Assert.assertTrue(file.exists()); //verifies that folder still exists after the controller call
        Assert.assertEquals("Fail expected status 200 but was " + status, 200, status);
        Assert.assertTrue("Fail expected Response Body to be '" + Responses.SUCCESS.getData() + "'",
                content.equals(Responses.SUCCESS.getData()));        
        
        FileTools.deleteFullDirectory(file);
    }  
    
    @Test
    public void testHandleCorrectImage() throws IOException, Exception{
        /* 
            Google suggestion.. DONT mock a file.. just create it.
        */
        File file = new File(Attr.TESTING_IMAGES.getData() + "\\testImg.jpg");
        ImagesMod Im = new ImagesMod(2L);
        Im.setImagePath(file.getPath());
        
        when(imagesService.checkImagesByHashtag(12345678L)).thenReturn(true);
        when(imagesService.getImageByHashtag(12345678L)).thenReturn(Im);             

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/get/12345678.jpg"))
                .andReturn();
        
        verify(imagesService,times(1)).checkImagesByHashtag(12345678L);
        verify(imagesService,times(1)).getImageByHashtag(12345678L);
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        
        Assert.assertFalse("Failure - expected response to have a body",content.isEmpty());
        Assert.assertTrue("Failure - expected response status to be '200'",status == 200);
    }
    
    @Test 
    public void testHandleWrongImage() throws Exception {        
        when(imagesService.checkImagesByHashtag(12345678L)).thenReturn(false);     

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/get/12345678.jpg"))
                .andReturn();
        
        verify(imagesService,times(1)).checkImagesByHashtag(12345678L);
        verify(imagesService,times(0)).getImageByHashtag(anyLong());
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        
        Assert.assertFalse("Failure - expected response to have a body",content.isEmpty());
        Assert.assertTrue("Failure - expected response status to be '200'",status == 200);      
    }
    
    @Test
    public void testHandleWrongExtension() throws Exception {        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/get/123124.mp3")).andReturn();        
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        
        verify(imagesService,times(0)).checkImagesByHashtag(anyLong());
        verify(imagesService,times(0)).getImageByHashtag(anyLong());
        
        Assert.assertTrue("Failure - expected response body to be null",content.isEmpty());
        Assert.assertTrue("Failure - expected response status to be '200'",status == 200);      
    }
    
    @Test
    public void testIOException() throws Exception{
            byte[] f = Files.readAllBytes(new File(Attr.TESTING_IMAGES.getData() + "\\testImg.jpg").toPath());
            MockMultipartFile MF = new MockMultipartFile("file","psaraki.jpg", "multipart/form-data", f);              
            
            when(userService.checkUsername("iwanna")).thenReturn(true);
            when(userService.getUserByUsername("iwanna")).thenReturn(new User(2L,"iwanna","iwanna",true));
            doNothing().when(imagesService).addImage(any(ImagesMod.class));
            
            FileWorkAround FWA = mock(FileWorkAround.class);
            when(FWA.fileWorkAroundCall(anyString(),any(byte[].class), any(StandardOpenOption.class))).thenThrow(IOException.class);
            controller.setFileWorkAround(FWA);
            
            MvcResult result = mvc.perform(MockMvcRequestBuilders
                .fileUpload(uri)
                .file(MF)
                .param("username", "iwanna"))
                .andReturn();
        
            String content = result.getResponse().getContentAsString();
            int status = result.getResponse().getStatus();
             
            verify(userService,times(1)).checkUsername("iwanna");
            verify(imagesService,times(0)).addImage(any(ImagesMod.class));
            verify(userService,times(1)).getUserByUsername("iwanna");
        
            Assert.assertEquals("Fail expected status 304 but was " + status, 304, status);
            Assert.assertTrue("Fail expected Response Body to be '" + Responses.FILE_ERROR.getData() + "'",
                content.equals(Responses.FILE_ERROR.getData()));           
    }  
    
    @Test
    public void testRegisterUserNameValidator() throws Exception 
    {
        List<String> str = ImagesController.getValidatorList(ValidationScopes.USER_NAME);
        junit.framework.Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        ImagesController.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);
        
        str = ImagesController.getValidatorList(ValidationScopes.USER_NAME);
        junit.framework.Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
        junit.framework.Assert.assertEquals("Failure - expected validatorList's first element to be NoSpacesValidator", "1: NoSpacesValidator", str.get(0));   
    }
    
    @Test
    public void testRegisterNotListedStringValidator() 
    {
        try 
        {
            List<String> str = ImagesController.getValidatorList(ValidationScopes.USER_NAME);
            junit.framework.Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
            ImagesController.registerValidator(new NoSpacesValidator(), ValidationScopes.MODE);
        } 
        catch (ValidatorNotListedException ex) 
        {
            junit.framework.Assert.assertTrue("Exception should be ValidatorNotListedException",
                    ex.getMessage().equals(new ValidatorNotListedException().getMessage()));
        } 
        catch (InappropriateValidatorException ex) 
        {
            junit.framework.Assert.fail("Exception should be ValidatorNotListedException not InappropriateValidatorException");
        }     
    }
    
    @Test
    public void testRegisterInappropriateValidator()
    {
        try 
        {
            List<String> str = ImagesController.getValidatorList(ValidationScopes.USER_NAME);
            junit.framework.Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
            ImagesController.registerValidator(new NotEmptyValidatorN(), ValidationScopes.USER_NAME);
        } 
        catch(ValidatorNotListedException ex) 
        {
            junit.framework.Assert.fail("Exception should be InappropriateValidatorException not ValidatorNotListedException");
        } 
        catch (InappropriateValidatorException ex) 
        {
            junit.framework.Assert.assertTrue("Exception should be InappropriateValidatorException",
                    ex.getMessage().equals(new InappropriateValidatorException().getMessage()));
        } 
    }
    
    @Test
    public void testCleanValidator() throws Exception 
    {
        ImagesController.registerValidator(new MaxLengthValidator(2), ValidationScopes.USER_NAME);
        ImagesController.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);
        List<String> str = ImagesController.getValidatorList(ValidationScopes.USER_NAME);
        
        junit.framework.Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        ImagesController.cleanValidator();
        str = ImagesController.getValidatorList(ValidationScopes.USER_NAME);
        
        junit.framework.Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size()); 
    }
    
    @Test
    public void testGetValidatorList() throws Exception 
    {
        ImagesController.registerValidator(new MaxLengthValidator(2), ValidationScopes.USER_NAME);
        ImagesController.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);
        List<String> str = ImagesController.getValidatorList(ValidationScopes.USER_NAME);
        
        junit.framework.Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
    }
    
    @Test
    public void testRemoveUserNameValidator() throws Exception 
    {
        ImagesController.registerValidator(new MaxLengthValidator(2), ValidationScopes.USER_NAME);
        ImagesController.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);
        List<String> str = ImagesController.getValidatorList(ValidationScopes.USER_NAME);
        
        junit.framework.Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        ImagesController.removeValidator(ValidationScopes.USER_NAME, 1);
        
        str = ImagesController.getValidatorList(ValidationScopes.USER_NAME);
        
        junit.framework.Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
    }
    
    @Test
    public void testRemoveValidatorOnZeroSpot() throws Exception
    {
        junit.framework.Assert.assertFalse(ImagesController.removeValidator(ValidationScopes.USER_NAME, 0));
    }
    
    @Test
    public void testRemoveValidatorUsernameNonExist() throws Exception
    {
        junit.framework.Assert.assertFalse(ImagesController.removeValidator(ValidationScopes.USER_NAME, 110));
    }
    
    @Test
    public void testRemoveValidatorNotListed()
    {
        try 
        {
            ImagesController.removeValidator(ValidationScopes.PROFILE_PHOTO,1);            
        } 
        catch (ValidatorNotListedException ex) 
        {
            junit.framework.Assert.assertTrue("Expected ValidatorNotListedException",
                    ex.getMessage().equals(new ValidatorNotListedException().getMessage()));
        } 
        catch (InappropriateValidatorException ex) 
        {
            junit.framework.Assert.fail("InappropriateValidatorException should not rise here");
        }
    }
    
    @Test 
    public void testGetValidatorListNotListed()
    {
        try 
        {
            ImagesController.getValidatorList(ValidationScopes.PROFILE_PHOTO);            
        } 
        catch (ValidatorNotListedException ex) 
        {
            junit.framework.Assert.assertTrue("Expected ValidatorNotListedException",
                    ex.getMessage().equals(new ValidatorNotListedException().getMessage()));
        }   
    }
    
    @Test
    public void testValidateSuccess() throws Exception 
    {
        InitializeValidators.InitializeCustomValidators();
        
        JSONObject json = new JSONObject();
        
        ImagesController MHRDTO = new ImagesController(); 
        
        Pair<Boolean,ResponseEntity> r = MHRDTO.validate("mixalis");
        
        junit.framework.Assert.assertTrue("Failure expected true",r.getLeft());
    }
    
    @Test
    public void testValidateFailUserName() throws Exception 
    {
        InitializeValidators.InitializeCustomValidators();
        
        ImagesController MHRDTO = new ImagesController();  
        
        Pair<Boolean,ResponseEntity> r = MHRDTO.validate("mix");
        
        junit.framework.Assert.assertFalse("Failure expected true",r.getLeft());
    }    
}
