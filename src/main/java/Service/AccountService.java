package Service;

import DAO.*;
import Model.*;

public class AccountService {
    
     private AccountDAO accountDAO;

     //Constructor for creating new AccountService() with a new AccountDAO()
     public AccountService(){
        accountDAO = new AccountDAO();
     }

     /**
      * Constructor when AccountDAO is provided
      * @param accountDAO
      */
     public AccountService(AccountDAO accountDAO){
        this.accountDAO = new AccountDAO();
     }

     public Account addAccount(Account account){
        //The registration will be successful if and only if the username is not blank, the password is at least 4 characters long, and an Account with that username does not already exist. If all these conditions are met, the response body should contain a JSON of the Account, including its account_id. The response status should be 200 OK, which is the default. The new account should be persisted to the database.
        if (account.username == "") return null;
        if (account.password.length() < 4) return null;

        return accountDAO.insertNewAccount(account);
     }

     public Account loginAccount(Account account){
        //The login will be successful if and only if the username and password provided in the request body JSON match a real account existing on the database. 
        
        return accountDAO.loginIntoAccount(account);
     }
     
     
}
