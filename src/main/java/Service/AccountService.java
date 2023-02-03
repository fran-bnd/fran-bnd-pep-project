package Service;

//import java.util.List;

import DAO.*;
import Model.*;

public class AccountService {
    /**
     * private AuthorDAO authorDAO;
     * 
     * no-args constructor for creating a new AuthorService with a new AuthorDAO.
     * There is no need to change this constructor.
     
    public AuthorService(){
        authorDAO = new AuthorDAO();
    }
    /**
     * Constructor for a AuthorService when a AuthorDAO is provided.
     * This is used for when a mock AuthorDAO that exhibits mock behavior is used in the test cases.
     * This would allow the testing of AuthorService independently of AuthorDAO.
     * There is no need to modify this constructor.
     * @param authorDAO
     
    public AuthorService(AuthorDAO authorDAO){
        this.authorDAO = authorDAO;
    }
    /**
     * TODO: Use the AuthorDAO to retrieve all authors.
     *
     * @return all authors
     *
    public List<Author> getAllAuthors() {
        return authorDAO.getAllAuthors();
    }
    /**
     * TODO: Use the AuthorDAO to persist an author. The given Author will not have an id provided.
     *
     * @param author an author object.
     * @return The persisted author if the persistence is successful.
     *
    public Author addAuthor(Author author) {
        
        return authorDAO.insertAuthor(author);
    }
     */
    
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
