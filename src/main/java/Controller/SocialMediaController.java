package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

// Defining endpoints and handlers for controller
public class SocialMediaController {

    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * Endpoints in the startAPI() method, as the test suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        //1. Process registration - POST localhost:8080/register
        app.post("/register", this::registerHandler);

        //2. Process logins- POST localhost:8080/login 
        app.post("/login", this::loginHandler);

        //3. Creation of new messages - POST localhost:8080/messages
        app.post("/messages", this::messagesHandler);

        //4. Retrieve all messages - GET localhost:8080/messages
        app.get("/messages", this::getAllMessagesHandler);

        //5. Get a message by its Id - GET localhost:8080/messages/{message_id}
        app.get("/messages/{message_id}", this::getMessageByIdHandler);

        //6. Delete a message - DELETE localhost:8080/messages/{message_id} 
        app.delete("/messages/{message_id}", this::deleteMessageHandler); 

        //7. Patch message by Id - PATCH localhost:8080/messages/{message_id}
        app.patch("/messages/{message_id}", this::patchMessageByIdHandler);

        //8. Get a message by its account Id - GET localhost:8080/accounts/{account_id}/messages. 
        app.get("/accounts/{account_id}/messages", this::getMessageByAccIdHandler);

        return app;
    }


    /**
     * 1. Handler to POST and new registration endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     * 
     * The body will contain a representation of a JSON Account, but will not contain an account_id.
     *  - The registration will be successful if and only if the username is not blank, the password is at least 4 characters long, and an Account with that username does not already exist. If all these conditions are met, the response body should contain a JSON of the Account, including its account_id. The response status should be 200 OK, which is the default. The new account should be persisted to the database.
     *  - If the registration is not successful, the response status should be 400. (Client error)
     * 
     */
    private void registerHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);

        // if new unique account return JSON Account
        if (addedAccount != null){
            context.json(mapper.writeValueAsString(addedAccount));
            context.status(200);
        } else { 
            // else not successful registration
            context.status(400);
        }
    }

    /**
     * 2: API should be able to process User logins.
     * Verify login on the endpoint POST localhost:8080/login. 
     * The request body will contain a JSON representation of an Account, not containing an account_id. In the future, this action may generate a Session token to allow the user to securely use the site. We will not worry about this for now.
     *   - The login will be successful if and only if the username and password provided in the request body JSON match a real account existing on the database. 
     *     If successful, the response body should contain a JSON of the account in the response body, including its account_id. 
     *     The response status should be 200 OK, which is the default.
     *   - If the login is not successful, the response status should be 401. (Unauthorized)
     * 
     * @param context
     * @throws JsonProcessingException
     */
    private void loginHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account loginAccount = accountService.loginAccount(account);

        // if unique account return JSON Account
        if (loginAccount != null){
            context.json(mapper.writeValueAsString(loginAccount));
            context.status(200);
        } else { 
            // unauthorized login
            context.status(401);
        }
    }

    /**
     * 3: API should be able to process the creation of new messages.
        Submit a new post on the endpoint POST localhost:8080/messages. 
        The request body will contain a JSON representation of a message, which should be persisted to the database, but will not contain a message_id.

        - The creation of the message will be successful if and only if the message_text is not blank, is under 255 characters, and posted_by refers to a real, existing user. 
          If successful, the response body should contain a JSON of the message, including its message_id. The response status should be 200, which is the default. 
          The new message should be persisted to the database.
        - If the creation of the message is not successful, the response status should be 400. (Client error)
    */
    private void messagesHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message addedMessage = messageService.addMessage(message);

        // if new unique message return JSON message
        if (addedMessage != null){
            context.json(mapper.writeValueAsString(addedMessage));
            context.status(200);
        } else { 
            // else not successful registration
            context.status(400);
        }
    }

    /**
     * 4: Our API should be able to retrieve all messages.
        GET request on the endpoint GET localhost:8080/messages.
        - The response body should contain a JSON representation of a list containing all messages retrieved from the database. 
          It is expected for the list to simply be empty if there are no messages. The response status should always be 200, which is the default.
     */
    private void getAllMessagesHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<Message> allMessages = messageService.getAllMessages();

        context.json(mapper.writeValueAsString(allMessages));

        //always respond 200, which is the default response
        context.status(200);
    }

    /**
     * 5: API should be able to retrieve a message by its ID.
        Submit a GET request on the endpoint GET localhost:8080/messages/{message_id}.
        - The response body should contain a JSON representation of the message identified by the message_id. 
        It is expected for the response body to simply be empty if there is no such message. 
        The response status should always be 200, which is the default.
     */
    private void getMessageByIdHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        //int for the Id and pathParam to use the value from the API call
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message theMessage = messageService.getMessageById(messageId);

        // if new unique return JSON messages
        if (theMessage != null){
            context.json(mapper.writeValueAsString(theMessage));
            context.status(200);
        } else { 
            // else not successful
            context.status(400);
        }
    }

    /**
     * 6: API should be able to delete a message identified by a message ID.
        Submit a DELETE request on the endpoint DELETE localhost:8080/messages/{message_id}. 
        - The deletion of an existing message should remove an existing message from the database. 
        If the message existed, the response body should contain the now-deleted message. 
        The response status should be 200, which is the default.
        - If the message did not exist, the response status should be 200, but the response body should be empty. 
        This is because the DELETE verb is intended to be idempotent, ie, multiple calls to the DELETE endpoint should respond with the same type of response.
     */
    private void deleteMessageHandler(Context context) throws JsonProcessingException {
        
        ObjectMapper mapper = new ObjectMapper();
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message deletedMessage = messageService.deleteMessageById(messageId);
           
        // checking if deletedMessage is not null
        if (deletedMessage != null) {
            context.json(mapper.writeValueAsString(deletedMessage));
        }

        //always respond 200, which is the default response
        context.status(200);
    }

    /**
     * 7: API should be able to update a message text identified by a message ID.
        Submit a PATCH request on the endpoint PATCH localhost:8080/messages/{message_id}. 
        The request body should contain a new message_text values to replace the message identified by message_id. 
        The request body can not be guaranteed to contain any other information.
        - The update of a message should be successful if and only if the message id already exists and the new message_text is not blank and is not over 255 characters. 
        If the update is successful, the response body should contain the full updated message (including message_id, posted_by, message_text, and time_posted_epoch), and the response status should be 200, which is the default. 
        The message existing on the database should have the updated message_text.
        - If the update of the message is not successful for any reason, the response status should be 400. (Client error)
     */
    private void patchMessageByIdHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        //int for the Id and pathParam to use the value from the API call
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        //get the new message_text from the body value from the API call
        Message message = mapper.readValue(context.body(), Message.class);
        Message theMessage = messageService.patchMessageById(messageId, message.getMessage_text());

        // if new unique return JSON messages
        if (theMessage != null){
            context.json(mapper.writeValueAsString(theMessage));
            context.status(200);
        } else { 
            // else not successful
            context.status(400);
        }
    }

    /**
     * 8: API should be able to retrieve all messages written by a particular user.
        Submit a GET request on the endpoint GET localhost:8080/accounts/{account_id}/messages. 
        - The response body should contain a JSON representation of a list containing all messages posted by a particular user, which is retrieved from the database. 
        It is expected for the list to simply be empty if there are no messages. 
        The response status should always be 200, which is the default.
     */
    private void getMessageByAccIdHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        //int for the Id and pathParam to use the value from the API call
        int accId = Integer.parseInt(context.pathParam("account_id"));
        List<Message> theMessages = messageService.getMessageByAccId(accId);

        // if new unique return JSON messages
        if (theMessages != null){
            context.json(mapper.writeValueAsString(theMessages));
            context.status(200);
        } else { 
            // else not successful
            context.status(400);
        }
    }
    

}