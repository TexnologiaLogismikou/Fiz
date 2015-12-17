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
        
        accessMethodList.add("blacklist");
        accessMethodList.add("whitelist");
        privilegeList.add("PUBLIC");
        privilegeList.add("PRIVATE");
        
        
        try {
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
            
            MessageHistoryRequestDTO.registerValidator(new NotEmptyValidatorN(), ValidationScopes.LONGITUDE);
            MessageHistoryRequestDTO.registerValidator(new LongitudeValidator(), ValidationScopes.LONGITUDE);
            MessageHistoryRequestDTO.registerValidator(new FloatNotNaNValidator(), ValidationScopes.LONGITUDE);
            
            MessageHistoryRequestDTO.registerValidator(new NotEmptyValidatorN(), ValidationScopes.LATITUDE);
            MessageHistoryRequestDTO.registerValidator(new LongitudeValidator(), ValidationScopes.LATITUDE);
            MessageHistoryRequestDTO.registerValidator(new FloatNotNaNValidator(), ValidationScopes.LATITUDE);
            
            ChatroomLocationDTO.registerValidator(new NotEmptyValidatorN(), ValidationScopes.LONGITUDE);
            ChatroomLocationDTO.registerValidator(new LongitudeValidator(), ValidationScopes.LONGITUDE);
            ChatroomLocationDTO.registerValidator(new FloatNotNaNValidator(), ValidationScopes.LONGITUDE);
            
            ChatroomLocationDTO.registerValidator(new NotEmptyValidatorN(), ValidationScopes.LATITUDE);
            ChatroomLocationDTO.registerValidator(new LongitudeValidator(), ValidationScopes.LATITUDE);
            ChatroomLocationDTO.registerValidator(new FloatNotNaNValidator(), ValidationScopes.LATITUDE);
            
            ChatroomLocationUpdateDTO.registerValidator(new NotEmptyValidatorN(), ValidationScopes.LONGITUDE);
            ChatroomLocationUpdateDTO.registerValidator(new LongitudeValidator(), ValidationScopes.LONGITUDE);
            ChatroomLocationUpdateDTO.registerValidator(new FloatNotNaNValidator(), ValidationScopes.LONGITUDE);
            
            ChatroomLocationUpdateDTO.registerValidator(new NotEmptyValidatorN(), ValidationScopes.LATITUDE);
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
}
