/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.configurations;

import com.tech.configurations.tools.ValidationScopes;
import com.tech.configurations.tools.customvalidators.elements.EmptyFloatValidator;
import com.tech.configurations.tools.customvalidators.elements.floatvalidator.FloatNotNaNValidator;
import com.tech.configurations.tools.customvalidators.elements.floatvalidator.LongitudeValidator;
import com.tech.configurations.tools.customvalidators.elements.numbervalidators.NotEmptyValidatorN;
import com.tech.configurations.tools.customvalidators.elements.numbervalidators.NotNegativeValidator;
import com.tech.configurations.tools.customvalidators.elements.stringvalidators.IncludeInListValidator;
import com.tech.configurations.tools.customvalidators.elements.stringvalidators.MatchValidator;
import com.tech.configurations.tools.customvalidators.elements.stringvalidators.MaxLengthValidator;
import com.tech.configurations.tools.customvalidators.elements.stringvalidators.MinLenghtValidator;
import com.tech.configurations.tools.customvalidators.elements.stringvalidators.NoSpacesValidator;
import com.tech.models.dtos.chatroom.ChatroomUpdateDTO;
import com.tech.configurations.tools.customvalidators.elements.stringvalidators.NotEmptyValidatorS;
import com.tech.configurations.tools.customvalidators.elements.stringvalidators.NotMatchValidator;
import com.tech.exceptions.customexceptions.InappropriateValidatorException;
import com.tech.exceptions.customexceptions.ValidatorNotListedException;
import com.tech.models.dtos.FriendDTO;
import com.tech.models.dtos.MessageDTO;
import com.tech.models.dtos.MessageHistoryRequestDTO;
import com.tech.models.dtos.chatroom.ChatroomBlacklistDTO;
import static com.tech.models.dtos.chatroom.ChatroomBlacklistDTO.registerValidator;
import com.tech.models.dtos.chatroom.ChatroomCheckInsideDTO;
import com.tech.models.dtos.chatroom.ChatroomConnectionMemberDTO;
import com.tech.models.dtos.chatroom.ChatroomCreationDTO;
import com.tech.models.dtos.chatroom.ChatroomDeleteDTO;
import com.tech.models.dtos.chatroom.ChatroomLocationDTO;
import com.tech.models.dtos.chatroom.ChatroomLocationUpdateDTO;
import com.tech.models.dtos.chatroom.ChatroomMemberDTO;
import com.tech.models.dtos.chatroom.ChatroomQuitMemberDTO;
import com.tech.models.dtos.chatroom.ChatroomWhitelistDTO;
import com.tech.models.dtos.user.LoginUserDTO;
import com.tech.models.dtos.user.RegisteredUserDTO;
import com.tech.models.dtos.user.UserDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KuroiTenshi
 */
public class InitializeValidators {
    public static void InitializeCustomValidators(){
 
        List<String> accessMethodList = new ArrayList<>();
        List<String> privilegeList = new ArrayList<>();
        List<String> mode = new ArrayList<>();
        
        accessMethodList.add("blacklist");
        accessMethodList.add("whitelist");
        privilegeList.add("PUBLIC");
        privilegeList.add("PRIVATE");
        mode.add("ADD");
        mode.add("DELETE");
        
        
        try {
            
            FriendDTO.registerValidator(new NotEmptyValidatorS(), ValidationScopes.USER_NAME);
            FriendDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);
            FriendDTO.registerValidator(new MaxLengthValidator(16), ValidationScopes.USER_NAME);
            FriendDTO.registerValidator(new MinLenghtValidator(4), ValidationScopes.USER_NAME);
            FriendDTO.registerValidator(new MatchValidator("[^A-Za-z0-9]"), ValidationScopes.USER_NAME);
            FriendDTO.registerValidator(new NotMatchValidator("^[A-Za-z]"), ValidationScopes.USER_NAME);
            
            MessageDTO.registerValidator(new MaxLengthValidator(16), ValidationScopes.STRING);
            
            MessageDTO.registerValidator(new NotEmptyValidatorS(), ValidationScopes.USER_NAME);
            MessageDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);
            MessageDTO.registerValidator(new MaxLengthValidator(16), ValidationScopes.USER_NAME);
            MessageDTO.registerValidator(new MinLenghtValidator(4), ValidationScopes.USER_NAME);
            MessageDTO.registerValidator(new MatchValidator("[^A-Za-z0-9]"), ValidationScopes.USER_NAME);
            MessageDTO.registerValidator(new NotMatchValidator("^[A-Za-z]"), ValidationScopes.USER_NAME);
           
            MessageDTO.registerValidator(new NotEmptyValidatorS(), ValidationScopes.ROOM_NAME);
            MessageDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
            MessageDTO.registerValidator(new MaxLengthValidator(16), ValidationScopes.ROOM_NAME);
            MessageDTO.registerValidator(new MinLenghtValidator(4), ValidationScopes.ROOM_NAME);
            MessageDTO.registerValidator(new MatchValidator("[^A-Za-z0-9]"), ValidationScopes.ROOM_NAME);
            MessageDTO.registerValidator(new NotMatchValidator("^[A-Za-z]"), ValidationScopes.ROOM_NAME);
            
            MessageDTO.registerValidator(new LongitudeValidator(), ValidationScopes.LONGITUDE);
            MessageDTO.registerValidator(new FloatNotNaNValidator(), ValidationScopes.LONGITUDE);
           
            MessageDTO.registerValidator(new LongitudeValidator(), ValidationScopes.LATITUDE);
            MessageDTO.registerValidator(new FloatNotNaNValidator(), ValidationScopes.LATITUDE);
            
            MessageDTO.registerValidator(new NotNegativeValidator(), ValidationScopes.TTL);
            MessageDTO.registerValidator(new NotEmptyValidatorN(), ValidationScopes.TTL);  
            
            ChatroomWhitelistDTO.registerValidator(new NotEmptyValidatorS(), ValidationScopes.ROOM_NAME);
            ChatroomWhitelistDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
            ChatroomWhitelistDTO.registerValidator(new MaxLengthValidator(16), ValidationScopes.ROOM_NAME);
            ChatroomWhitelistDTO.registerValidator(new MinLenghtValidator(4), ValidationScopes.ROOM_NAME);
            ChatroomWhitelistDTO.registerValidator(new MatchValidator("[^A-Za-z0-9]"), ValidationScopes.ROOM_NAME);
            ChatroomWhitelistDTO.registerValidator(new NotMatchValidator("^[A-Za-z]"), ValidationScopes.ROOM_NAME);
            
            ChatroomWhitelistDTO.registerValidator(new NotEmptyValidatorS(), ValidationScopes.USER_NAME);
            ChatroomWhitelistDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);
            ChatroomWhitelistDTO.registerValidator(new MaxLengthValidator(16), ValidationScopes.USER_NAME);
            ChatroomWhitelistDTO.registerValidator(new MinLenghtValidator(4), ValidationScopes.USER_NAME);
            ChatroomWhitelistDTO.registerValidator(new MatchValidator("[^A-Za-z0-9]"), ValidationScopes.USER_NAME);
            ChatroomWhitelistDTO.registerValidator(new NotMatchValidator("^[A-Za-z]"), ValidationScopes.USER_NAME);
            
            ChatroomWhitelistDTO.registerValidator(new NotEmptyValidatorS(), ValidationScopes.MODE);
            ChatroomWhitelistDTO.registerValidator(new MatchValidator("[^A-Za-z0-9]"), ValidationScopes.MODE);
            ChatroomWhitelistDTO.registerValidator(new MaxLengthValidator(6), ValidationScopes.MODE);
            ChatroomWhitelistDTO.registerValidator(new MinLenghtValidator(3), ValidationScopes.MODE);
            ChatroomWhitelistDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.MODE);
            ChatroomWhitelistDTO.registerValidator(new IncludeInListValidator(mode), ValidationScopes.MODE);
            
            ChatroomQuitMemberDTO.registerValidator(new NotEmptyValidatorS(), ValidationScopes.ROOM_NAME);
            ChatroomQuitMemberDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
            ChatroomQuitMemberDTO.registerValidator(new MaxLengthValidator(16), ValidationScopes.ROOM_NAME);
            ChatroomQuitMemberDTO.registerValidator(new MinLenghtValidator(4), ValidationScopes.ROOM_NAME);
            ChatroomQuitMemberDTO.registerValidator(new MatchValidator("[^A-Za-z0-9]"), ValidationScopes.ROOM_NAME);
            ChatroomQuitMemberDTO.registerValidator(new NotMatchValidator("^[A-Za-z]"), ValidationScopes.ROOM_NAME);
            
            ChatroomQuitMemberDTO.registerValidator(new NotEmptyValidatorS(), ValidationScopes.USER_NAME);
            ChatroomQuitMemberDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);
            ChatroomQuitMemberDTO.registerValidator(new MaxLengthValidator(16), ValidationScopes.USER_NAME);
            ChatroomQuitMemberDTO.registerValidator(new MinLenghtValidator(4), ValidationScopes.USER_NAME);
            ChatroomQuitMemberDTO.registerValidator(new MatchValidator("[^A-Za-z0-9]"), ValidationScopes.USER_NAME);
            ChatroomQuitMemberDTO.registerValidator(new NotMatchValidator("^[A-Za-z]"), ValidationScopes.USER_NAME);
            
            LoginUserDTO.registerValidator(new NotEmptyValidatorS(), ValidationScopes.USER_NAME);
            LoginUserDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);
            LoginUserDTO.registerValidator(new MaxLengthValidator(16), ValidationScopes.USER_NAME);
            LoginUserDTO.registerValidator(new MinLenghtValidator(4), ValidationScopes.USER_NAME);
            LoginUserDTO.registerValidator(new MatchValidator("[^A-Za-z0-9]"), ValidationScopes.USER_NAME);
            LoginUserDTO.registerValidator(new NotMatchValidator("^[A-Za-z]"), ValidationScopes.USER_NAME);
            
            LoginUserDTO.registerValidator(new NotEmptyValidatorS(), ValidationScopes.PASSWORD);
            LoginUserDTO.registerValidator(new MaxLengthValidator(15), ValidationScopes.PASSWORD);
            LoginUserDTO.registerValidator(new MinLenghtValidator(5), ValidationScopes.PASSWORD);
            LoginUserDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.PASSWORD);
            
            RegisteredUserDTO.registerValidator(new NotEmptyValidatorS(), ValidationScopes.USER_NAME);
            RegisteredUserDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);
            RegisteredUserDTO.registerValidator(new MaxLengthValidator(16), ValidationScopes.USER_NAME);
            RegisteredUserDTO.registerValidator(new MinLenghtValidator(4), ValidationScopes.USER_NAME);
            RegisteredUserDTO.registerValidator(new MatchValidator("[^A-Za-z0-9]"), ValidationScopes.USER_NAME);
            RegisteredUserDTO.registerValidator(new NotMatchValidator("^[A-Za-z]"), ValidationScopes.USER_NAME);
            
            RegisteredUserDTO.registerValidator(new NotEmptyValidatorS(), ValidationScopes.PASSWORD);
            RegisteredUserDTO.registerValidator(new MaxLengthValidator(15), ValidationScopes.PASSWORD);
            RegisteredUserDTO.registerValidator(new MinLenghtValidator(5), ValidationScopes.PASSWORD);
            RegisteredUserDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.PASSWORD);
            
            RegisteredUserDTO.registerValidator(new NotEmptyValidatorS(), ValidationScopes.EMAIL);
            RegisteredUserDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.EMAIL);
            RegisteredUserDTO.registerValidator(new MaxLengthValidator(25), ValidationScopes.EMAIL);
            RegisteredUserDTO.registerValidator(new MinLenghtValidator(7), ValidationScopes.EMAIL);
            RegisteredUserDTO.registerValidator(new NotMatchValidator("^[A-Za-z]"), ValidationScopes.EMAIL);
            
            UserDTO.registerValidator(new NotEmptyValidatorS(), ValidationScopes.USER_NAME);
            UserDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);
            UserDTO.registerValidator(new MaxLengthValidator(16), ValidationScopes.USER_NAME);
            UserDTO.registerValidator(new MinLenghtValidator(4), ValidationScopes.USER_NAME);
            UserDTO.registerValidator(new MatchValidator("[^A-Za-z0-9]"), ValidationScopes.USER_NAME);
            UserDTO.registerValidator(new NotMatchValidator("^[A-Za-z]"), ValidationScopes.USER_NAME);
            
            UserDTO.registerValidator(new NotEmptyValidatorS(), ValidationScopes.PASSWORD);
            UserDTO.registerValidator(new MaxLengthValidator(15), ValidationScopes.PASSWORD);
            UserDTO.registerValidator(new MinLenghtValidator(5), ValidationScopes.PASSWORD);
            UserDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.PASSWORD);
            
            UserDTO.registerValidator(new NotEmptyValidatorS(), ValidationScopes.EMAIL);
            UserDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.EMAIL);
            UserDTO.registerValidator(new MaxLengthValidator(25), ValidationScopes.EMAIL);
            UserDTO.registerValidator(new MinLenghtValidator(7), ValidationScopes.EMAIL);
            UserDTO.registerValidator(new NotMatchValidator("^[A-Za-z]"), ValidationScopes.EMAIL); 
            
            UserDTO.registerValidator(new NotEmptyValidatorS(), ValidationScopes.STRING);
            UserDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.STRING);
            UserDTO.registerValidator(new MaxLengthValidator(20), ValidationScopes.STRING);
            UserDTO.registerValidator(new MinLenghtValidator(1), ValidationScopes.STRING);
            UserDTO.registerValidator(new MatchValidator("[^A-Za-z0-9]"), ValidationScopes.STRING);
            UserDTO.registerValidator(new NotMatchValidator("^[A-Za-z]"), ValidationScopes.STRING);
            
            UserDTO.registerValidator(new NotEmptyValidatorS(), ValidationScopes.PROFILE_PHOTO);
            UserDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.PROFILE_PHOTO);
            UserDTO.registerValidator(new MaxLengthValidator(500), ValidationScopes.PROFILE_PHOTO);
            UserDTO.registerValidator(new MinLenghtValidator(1), ValidationScopes.PROFILE_PHOTO);
            UserDTO.registerValidator(new NotMatchValidator("^[A-Za-z]"), ValidationScopes.PROFILE_PHOTO);
            
            UserDTO.registerValidator(new NotEmptyValidatorS(), ValidationScopes.STATUS);
            UserDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.STATUS);
            UserDTO.registerValidator(new MaxLengthValidator(1000), ValidationScopes.STATUS);
            UserDTO.registerValidator(new MinLenghtValidator(0), ValidationScopes.STATUS);
           
            ChatroomUpdateDTO.registerValidator(new NotEmptyValidatorN(),ValidationScopes.NUMBER);
            ChatroomUpdateDTO.registerValidator(new NotEmptyValidatorS(),ValidationScopes.STRING);
            ChatroomUpdateDTO.registerValidator(new NoSpacesValidator(),ValidationScopes.STRING);
            
            ChatroomBlacklistDTO.registerValidator(new NotEmptyValidatorS(), ValidationScopes.ROOM_NAME);
            ChatroomBlacklistDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
            ChatroomBlacklistDTO.registerValidator(new MaxLengthValidator(16), ValidationScopes.ROOM_NAME);
            ChatroomBlacklistDTO.registerValidator(new MinLenghtValidator(4), ValidationScopes.ROOM_NAME);
            ChatroomBlacklistDTO.registerValidator(new MatchValidator("[^A-Za-z0-9]"), ValidationScopes.ROOM_NAME);
            ChatroomBlacklistDTO.registerValidator(new NotMatchValidator("^[A-Za-z]"), ValidationScopes.ROOM_NAME);
            
            ChatroomBlacklistDTO.registerValidator(new NotEmptyValidatorS(), ValidationScopes.USER_NAME);
            ChatroomBlacklistDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);
            ChatroomBlacklistDTO.registerValidator(new MaxLengthValidator(16), ValidationScopes.USER_NAME);
            ChatroomBlacklistDTO.registerValidator(new MinLenghtValidator(4), ValidationScopes.USER_NAME);
            ChatroomBlacklistDTO.registerValidator(new MatchValidator("[^A-Za-z0-9]"), ValidationScopes.USER_NAME);
            ChatroomBlacklistDTO.registerValidator(new NotMatchValidator("^[A-Za-z]"), ValidationScopes.USER_NAME);
            
            ChatroomCheckInsideDTO.registerValidator(new NotEmptyValidatorN(), ValidationScopes.LONGITUDE);
            ChatroomCheckInsideDTO.registerValidator(new LongitudeValidator(), ValidationScopes.LONGITUDE);
            ChatroomCheckInsideDTO.registerValidator(new FloatNotNaNValidator(), ValidationScopes.LONGITUDE);
            
            ChatroomCheckInsideDTO.registerValidator(new NotEmptyValidatorN(), ValidationScopes.LATITUDE);
            ChatroomCheckInsideDTO.registerValidator(new LongitudeValidator(), ValidationScopes.LATITUDE);
            ChatroomCheckInsideDTO.registerValidator(new FloatNotNaNValidator(), ValidationScopes.LATITUDE);
            
            ChatroomCheckInsideDTO.registerValidator(new NotEmptyValidatorS(), ValidationScopes.ROOM_NAME);
            ChatroomCheckInsideDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
            ChatroomCheckInsideDTO.registerValidator(new MaxLengthValidator(16), ValidationScopes.ROOM_NAME);
            ChatroomCheckInsideDTO.registerValidator(new MinLenghtValidator(4), ValidationScopes.ROOM_NAME);
            ChatroomCheckInsideDTO.registerValidator(new MatchValidator("[^A-Za-z0-9]"), ValidationScopes.ROOM_NAME);
            ChatroomCheckInsideDTO.registerValidator(new NotMatchValidator("^[A-Za-z]"), ValidationScopes.ROOM_NAME);
            
            ChatroomCheckInsideDTO.registerValidator(new NotEmptyValidatorS(), ValidationScopes.USER_NAME);
            ChatroomCheckInsideDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);
            ChatroomCheckInsideDTO.registerValidator(new MaxLengthValidator(16), ValidationScopes.USER_NAME);
            ChatroomCheckInsideDTO.registerValidator(new MinLenghtValidator(4), ValidationScopes.USER_NAME);
            ChatroomCheckInsideDTO.registerValidator(new MatchValidator("[^A-Za-z0-9]"), ValidationScopes.USER_NAME);
            ChatroomCheckInsideDTO.registerValidator(new NotMatchValidator("^[A-Za-z]"), ValidationScopes.USER_NAME);
            
            ChatroomConnectionMemberDTO.registerValidator(new NotEmptyValidatorS(), ValidationScopes.USER_NAME);
            ChatroomConnectionMemberDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);
            ChatroomConnectionMemberDTO.registerValidator(new MaxLengthValidator(16), ValidationScopes.USER_NAME);
            ChatroomConnectionMemberDTO.registerValidator(new MinLenghtValidator(4), ValidationScopes.USER_NAME);
            ChatroomConnectionMemberDTO.registerValidator(new MatchValidator("[^A-Za-z0-9]"), ValidationScopes.USER_NAME);
            ChatroomConnectionMemberDTO.registerValidator(new NotMatchValidator("^[A-Za-z]"), ValidationScopes.USER_NAME);
            
            ChatroomConnectionMemberDTO.registerValidator(new NotEmptyValidatorS(), ValidationScopes.USER_NAME);
            ChatroomConnectionMemberDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);
            ChatroomConnectionMemberDTO.registerValidator(new MaxLengthValidator(16), ValidationScopes.USER_NAME);
            ChatroomConnectionMemberDTO.registerValidator(new MinLenghtValidator(4), ValidationScopes.USER_NAME);
            ChatroomConnectionMemberDTO.registerValidator(new MatchValidator("[^A-Za-z0-9]"), ValidationScopes.USER_NAME);
            ChatroomConnectionMemberDTO.registerValidator(new NotMatchValidator("^[A-Za-z]"), ValidationScopes.USER_NAME);
            
            ChatroomConnectionMemberDTO.registerValidator(new NotEmptyValidatorS(), ValidationScopes.PASSWORD);
            ChatroomConnectionMemberDTO.registerValidator(new MaxLengthValidator(15), ValidationScopes.PASSWORD);
            ChatroomConnectionMemberDTO.registerValidator(new MinLenghtValidator(5), ValidationScopes.PASSWORD);
            ChatroomConnectionMemberDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.PASSWORD);
            
            ChatroomConnectionMemberDTO.registerValidator(new NotEmptyValidatorS(), ValidationScopes.ROOM_ACCESS_METHOD);
            ChatroomConnectionMemberDTO.registerValidator(new MatchValidator("[^A-Za-z0-9]"), ValidationScopes.ROOM_ACCESS_METHOD);
            ChatroomConnectionMemberDTO.registerValidator(new MaxLengthValidator(6), ValidationScopes.ROOM_ACCESS_METHOD);
            ChatroomConnectionMemberDTO.registerValidator(new MinLenghtValidator(3), ValidationScopes.ROOM_ACCESS_METHOD);
            ChatroomConnectionMemberDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_ACCESS_METHOD);
            ChatroomConnectionMemberDTO.registerValidator(new IncludeInListValidator(accessMethodList), ValidationScopes.ROOM_ACCESS_METHOD);
            
            ChatroomConnectionMemberDTO.registerValidator(new NotEmptyValidatorN(), ValidationScopes.LONGITUDE);
            ChatroomConnectionMemberDTO.registerValidator(new LongitudeValidator(), ValidationScopes.LONGITUDE);
            ChatroomConnectionMemberDTO.registerValidator(new FloatNotNaNValidator(), ValidationScopes.LONGITUDE);
            
            ChatroomConnectionMemberDTO.registerValidator(new NotEmptyValidatorN(), ValidationScopes.LATITUDE);
            ChatroomConnectionMemberDTO.registerValidator(new LongitudeValidator(), ValidationScopes.LATITUDE);
            ChatroomConnectionMemberDTO.registerValidator(new FloatNotNaNValidator(), ValidationScopes.LATITUDE);
            
            ChatroomCreationDTO.registerValidator(new NotEmptyValidatorS(), ValidationScopes.USER_NAME);
            ChatroomCreationDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);
            ChatroomCreationDTO.registerValidator(new MaxLengthValidator(16), ValidationScopes.USER_NAME);
            ChatroomCreationDTO.registerValidator(new MinLenghtValidator(4), ValidationScopes.USER_NAME);
            ChatroomCreationDTO.registerValidator(new MatchValidator("[^A-Za-z0-9]"), ValidationScopes.USER_NAME);
            ChatroomCreationDTO.registerValidator(new NotMatchValidator("^[A-Za-z]"), ValidationScopes.USER_NAME);
            
            ChatroomCreationDTO.registerValidator(new NotEmptyValidatorS(), ValidationScopes.ROOM_NAME);
            ChatroomCreationDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
            ChatroomCreationDTO.registerValidator(new MaxLengthValidator(16), ValidationScopes.ROOM_NAME);
            ChatroomCreationDTO.registerValidator(new MinLenghtValidator(4), ValidationScopes.ROOM_NAME);
            ChatroomCreationDTO.registerValidator(new MatchValidator("[^A-Za-z0-9]"), ValidationScopes.ROOM_NAME);
            ChatroomCreationDTO.registerValidator(new NotMatchValidator("^[A-Za-z]"), ValidationScopes.ROOM_NAME);
            
            ChatroomCreationDTO.registerValidator(new NotEmptyValidatorS(), ValidationScopes.ROOM_PRIVILEGE);
            ChatroomCreationDTO.registerValidator(new MatchValidator("[^A-Za-z0-9]"), ValidationScopes.ROOM_PRIVILEGE);
            ChatroomCreationDTO.registerValidator(new NotMatchValidator("^[A-Za-z]"), ValidationScopes.ROOM_PRIVILEGE);
            ChatroomCreationDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_PRIVILEGE);
            ChatroomCreationDTO.registerValidator(new IncludeInListValidator(privilegeList), ValidationScopes.ROOM_PRIVILEGE);
            
            ChatroomCreationDTO.registerValidator(new NotEmptyValidatorS(), ValidationScopes.ROOM_ACCESS_METHOD);
            ChatroomCreationDTO.registerValidator(new MatchValidator("[^A-Za-z0-9]"), ValidationScopes.ROOM_ACCESS_METHOD);
            ChatroomCreationDTO.registerValidator(new MaxLengthValidator(6), ValidationScopes.ROOM_ACCESS_METHOD);
            ChatroomCreationDTO.registerValidator(new MinLenghtValidator(3), ValidationScopes.ROOM_ACCESS_METHOD);
            ChatroomCreationDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_ACCESS_METHOD);
            ChatroomCreationDTO.registerValidator(new IncludeInListValidator(accessMethodList), ValidationScopes.ROOM_ACCESS_METHOD);
            
            ChatroomCreationDTO.registerValidator(new NotEmptyValidatorN(), ValidationScopes.LONGITUDE);
            ChatroomCreationDTO.registerValidator(new LongitudeValidator(), ValidationScopes.LONGITUDE);
            ChatroomCreationDTO.registerValidator(new FloatNotNaNValidator(), ValidationScopes.LONGITUDE);
            
            ChatroomCreationDTO.registerValidator(new NotEmptyValidatorN(), ValidationScopes.LATITUDE);
            ChatroomCreationDTO.registerValidator(new LongitudeValidator(), ValidationScopes.LATITUDE);
            ChatroomCreationDTO.registerValidator(new FloatNotNaNValidator(), ValidationScopes.LATITUDE);
            
            ChatroomCreationDTO.registerValidator(new MaxLengthValidator(5000), ValidationScopes.RANGE);
            ChatroomCreationDTO.registerValidator(new NotEmptyValidatorN(), ValidationScopes.RANGE);
            
            ChatroomCreationDTO.registerValidator(new NotEmptyValidatorS(), ValidationScopes.PASSWORD);
            ChatroomCreationDTO.registerValidator(new MaxLengthValidator(15), ValidationScopes.PASSWORD);
            ChatroomCreationDTO.registerValidator(new MinLenghtValidator(5), ValidationScopes.PASSWORD);
            ChatroomCreationDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.PASSWORD);
            
            ChatroomDeleteDTO.registerValidator(new NotEmptyValidatorS(), ValidationScopes.USER_NAME);
            ChatroomDeleteDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);
            ChatroomDeleteDTO.registerValidator(new MaxLengthValidator(16), ValidationScopes.USER_NAME);
            ChatroomDeleteDTO.registerValidator(new MinLenghtValidator(4), ValidationScopes.USER_NAME);
            ChatroomDeleteDTO.registerValidator(new MatchValidator("[^A-Za-z0-9]"), ValidationScopes.USER_NAME);
            ChatroomDeleteDTO.registerValidator(new NotMatchValidator("^[A-Za-z]"), ValidationScopes.USER_NAME);
            
            ChatroomDeleteDTO.registerValidator(new NotEmptyValidatorS(), ValidationScopes.ROOM_NAME);
            ChatroomDeleteDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
            ChatroomDeleteDTO.registerValidator(new MaxLengthValidator(16), ValidationScopes.ROOM_NAME);
            ChatroomDeleteDTO.registerValidator(new MinLenghtValidator(4), ValidationScopes.ROOM_NAME);
            ChatroomDeleteDTO.registerValidator(new MatchValidator("[^A-Za-z0-9]"), ValidationScopes.ROOM_NAME);
            ChatroomDeleteDTO.registerValidator(new NotMatchValidator("^[A-Za-z]"), ValidationScopes.ROOM_NAME);
            
            ChatroomDeleteDTO.registerValidator(new NotEmptyValidatorS(), ValidationScopes.PASSWORD);
            ChatroomDeleteDTO.registerValidator(new MaxLengthValidator(15), ValidationScopes.PASSWORD);
            ChatroomDeleteDTO.registerValidator(new MinLenghtValidator(5), ValidationScopes.PASSWORD);
            ChatroomDeleteDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.PASSWORD);
            
            MessageHistoryRequestDTO.registerValidator(new NotEmptyValidatorS(), ValidationScopes.ROOM_NAME);
            MessageHistoryRequestDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
            MessageHistoryRequestDTO.registerValidator(new MaxLengthValidator(16), ValidationScopes.ROOM_NAME);
            MessageHistoryRequestDTO.registerValidator(new MinLenghtValidator(4), ValidationScopes.ROOM_NAME);
            MessageHistoryRequestDTO.registerValidator(new MatchValidator("[^A-Za-z0-9]"), ValidationScopes.ROOM_NAME);
            MessageHistoryRequestDTO.registerValidator(new NotMatchValidator("^[A-Za-z]"), ValidationScopes.ROOM_NAME);
            
            MessageHistoryRequestDTO.registerValidator(new NotEmptyValidatorS(), ValidationScopes.USER_NAME);
            MessageHistoryRequestDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);
            MessageHistoryRequestDTO.registerValidator(new MaxLengthValidator(16), ValidationScopes.USER_NAME);
            MessageHistoryRequestDTO.registerValidator(new MinLenghtValidator(4), ValidationScopes.USER_NAME);
            MessageHistoryRequestDTO.registerValidator(new MatchValidator("[^A-Za-z0-9]"), ValidationScopes.USER_NAME);
            MessageHistoryRequestDTO.registerValidator(new NotMatchValidator("^[A-Za-z]"), ValidationScopes.USER_NAME);
            
            MessageHistoryRequestDTO.registerValidator(new LongitudeValidator(), ValidationScopes.LONGITUDE);
            MessageHistoryRequestDTO.registerValidator(new FloatNotNaNValidator(), ValidationScopes.LONGITUDE);
            
            MessageHistoryRequestDTO.registerValidator(new LongitudeValidator(), ValidationScopes.LATITUDE);
            MessageHistoryRequestDTO.registerValidator(new FloatNotNaNValidator(), ValidationScopes.LATITUDE);
            
            ChatroomLocationDTO.registerValidator(new LongitudeValidator(), ValidationScopes.LONGITUDE);
            ChatroomLocationDTO.registerValidator(new FloatNotNaNValidator(), ValidationScopes.LONGITUDE);
            
            ChatroomLocationDTO.registerValidator(new LongitudeValidator(), ValidationScopes.LATITUDE);
            ChatroomLocationDTO.registerValidator(new FloatNotNaNValidator(), ValidationScopes.LATITUDE);
            
            ChatroomLocationUpdateDTO.registerValidator(new LongitudeValidator(), ValidationScopes.LONGITUDE);
            ChatroomLocationUpdateDTO.registerValidator(new FloatNotNaNValidator(), ValidationScopes.LONGITUDE);
            
            ChatroomLocationUpdateDTO.registerValidator(new LongitudeValidator(), ValidationScopes.LATITUDE);
            ChatroomLocationUpdateDTO.registerValidator(new FloatNotNaNValidator(), ValidationScopes.LATITUDE);
            
            ChatroomLocationUpdateDTO.registerValidator(new NotEmptyValidatorS(), ValidationScopes.ROOM_NAME);
            ChatroomLocationUpdateDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
            ChatroomLocationUpdateDTO.registerValidator(new MaxLengthValidator(16), ValidationScopes.ROOM_NAME);
            ChatroomLocationUpdateDTO.registerValidator(new MinLenghtValidator(4), ValidationScopes.ROOM_NAME);
            ChatroomLocationUpdateDTO.registerValidator(new MatchValidator("[^A-Za-z0-9]"), ValidationScopes.ROOM_NAME);
            ChatroomLocationUpdateDTO.registerValidator(new NotMatchValidator("^[A-Za-z]"), ValidationScopes.ROOM_NAME);
            
            ChatroomMemberDTO.registerValidator(new NotEmptyValidatorS(), ValidationScopes.ROOM_NAME);
            ChatroomMemberDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
            ChatroomMemberDTO.registerValidator(new MaxLengthValidator(16), ValidationScopes.ROOM_NAME);
            ChatroomMemberDTO.registerValidator(new MinLenghtValidator(4), ValidationScopes.ROOM_NAME);
            ChatroomMemberDTO.registerValidator(new MatchValidator("[^A-Za-z0-9]"), ValidationScopes.ROOM_NAME);
            ChatroomMemberDTO.registerValidator(new NotMatchValidator("^[A-Za-z]"), ValidationScopes.ROOM_NAME);
            
            ChatroomMemberDTO.registerValidator(new NotEmptyValidatorS(), ValidationScopes.USER_NAME);
            ChatroomMemberDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);
            ChatroomMemberDTO.registerValidator(new MaxLengthValidator(16), ValidationScopes.USER_NAME);
            ChatroomMemberDTO.registerValidator(new MinLenghtValidator(4), ValidationScopes.USER_NAME);
            ChatroomMemberDTO.registerValidator(new MatchValidator("[^A-Za-z0-9]"), ValidationScopes.USER_NAME);
            ChatroomMemberDTO.registerValidator(new NotMatchValidator("^[A-Za-z]"), ValidationScopes.USER_NAME);
            
            ChatroomMemberDTO.registerValidator(new NotEmptyValidatorS(), ValidationScopes.PASSWORD);
            ChatroomMemberDTO.registerValidator(new MaxLengthValidator(15), ValidationScopes.PASSWORD);
            ChatroomMemberDTO.registerValidator(new MinLenghtValidator(5), ValidationScopes.PASSWORD);
            ChatroomMemberDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.PASSWORD);
            
            ChatroomMemberDTO.registerValidator(new NotEmptyValidatorS(), ValidationScopes.ROOM_ACCESS_METHOD);
            ChatroomMemberDTO.registerValidator(new MatchValidator("[^A-Za-z0-9]"), ValidationScopes.ROOM_ACCESS_METHOD);
            ChatroomMemberDTO.registerValidator(new MaxLengthValidator(6), ValidationScopes.ROOM_ACCESS_METHOD);
            ChatroomMemberDTO.registerValidator(new MinLenghtValidator(3), ValidationScopes.ROOM_ACCESS_METHOD);
            ChatroomMemberDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_ACCESS_METHOD);
            ChatroomMemberDTO.registerValidator(new IncludeInListValidator(accessMethodList), ValidationScopes.ROOM_ACCESS_METHOD);
        } catch (InappropriateValidatorException | ValidatorNotListedException ex) {
            Logger.getLogger(InitializeValidators.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    }
    
    public static void CleanCustomValidators()
    {
        FriendDTO.cleanValidator();
        ChatroomBlacklistDTO.cleanValidator();
        ChatroomCheckInsideDTO.cleanValidator();
        ChatroomConnectionMemberDTO.cleanValidator();
        ChatroomCreationDTO.cleanValidator();
        ChatroomDeleteDTO.cleanValidator();
        ChatroomLocationDTO.cleanValidator();
        ChatroomLocationUpdateDTO.cleanValidator();
        ChatroomMemberDTO.cleanValidator();
        ChatroomWhitelistDTO.cleanValidator();
        ChatroomUpdateDTO.cleanValidator();
        ChatroomQuitMemberDTO.cleanValidator();
        LoginUserDTO.cleanValidator();
        UserDTO.cleanValidator();
        RegisteredUserDTO.cleanValidator();
        MessageDTO.cleanValidator();
        MessageHistoryRequestDTO.cleanValidator();
    }
}
