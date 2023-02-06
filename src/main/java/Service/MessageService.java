package Service;

import java.util.List;

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

     public List<Message> getAllMessages(){
        return messageDAO.getAllMesages();
     }

     public Message addMessage(Message message){
        //The creation of the message will be successful if and only if the message_text is not blank, is under 255 characters, and posted_by refers to a real, existing user. 
         if (message.message_text == "") return null;

        return messageDAO.insertNewMessage(message);
     }

     public Message getMessageById(int messageId){
         return messageDAO.getMessagebyId(messageId);
     }

    public Message deleteMessageById(int messageId) {
         //The deletion of an existing message should remove an existing message from the database. 
         //If the message existed, the response body should contain the now-deleted message. 
         //The response status should be 200, which is the default.
         //If the message did not exist, the response status should be 200, but the response body should be empty. 

         Message messageToDelete = messageDAO.getMessagebyId(messageId);
         if (messageToDelete != null){ 
            messageDAO.deleteMessageById(messageId);
            return messageToDelete;
         } else {
            return null;
         }
     }

    public List<Message> getMessageByAccId(int accId) {
        return messageDAO.getMessageByAccId(accId);
    }

   public Message patchMessageById(int messageId, String messageText) {
      
      //blank message_text should result in a response status 400
      if (!messageText.isBlank()){
         return messageDAO.patchMessageById(messageId, messageText);
      } else {
         return null;
      }
   }
     
}
