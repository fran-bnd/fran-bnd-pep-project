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

     public Message addMessage(Message message){
        //The registration will be successful if and only if the username is not blank, the password is at least 4 characters long, and an Account with that username does not already exist. If all these conditions are met, the response body should contain a JSON of the Account, including its account_id. The response status should be 200 OK, which is the default. The new account should be persisted to the database.
        if (message.message_text == "") return null;

        return messageDAO.insertNewMessage(message);
     }

     public Message postMessage(Message message){
        //The login will be successful if and only if the username and password provided in the request body JSON match a real account existing on the database. 
        return messageDAO.postAMessage(message);
     }
     
     
}
