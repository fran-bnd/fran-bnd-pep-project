package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Service.*;
import Model.*;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    AccountService accountService;

    public SocialMediaController(){
        this.accountService = new AccountService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        //1. Process registration POST localhost:8080/register
        app.post("/register", this::registerHandler);

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
        /** private void postAuthorHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Author author = mapper.readValue(ctx.body(), Author.class);
        Author addedAuthor = authorService.addAuthor(author);
        if(addedAuthor!=null){
            ctx.json(mapper.writeValueAsString(addedAuthor));
        }else{
            ctx.status(400);
        }
        }
        */
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


}