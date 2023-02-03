package Service;

//import java.util.List;

import DAO.*;
import Model.*;

public class MessageService {
    
     private MessageDAO messageDAO;

     //Constructor for creating new Service() with a new messageDAO()
     public MessageService(){
        messageDAO = new MessageDAO();
     }

     /**
      * Constructor when messageDAO is provided
      * @param messageDAO
      */
     public MessageService(MessageDAO messageDAO){
        this.messageDAO = new MessageDAO();
     }

     public Message getAllMessages(){
        return messageDAO.getAllMesages(getAllMessages());
     }

     public Message addMessage(Message message){
        //The creation of the message will be successful if and only if the message_text is not blank, is under 255 characters, and posted_by refers to a real, existing user. 
         if (message.message_text == "") return null;

        return messageDAO.insertNewMessage(message);
     }

     public Message getMessageById(int messageId){
      // by message Id
      return messageDAO.getMessagebyId(messageId);
     }

     public Message patchMessage(Message message){
        //The login will be successful if and only if the username and password provided in the request body JSON match a real account existing on the database. 
        return messageDAO.patchAMessage(message);
     }
     
     
}
