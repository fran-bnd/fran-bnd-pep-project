package Service;

import java.util.List;

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
        return accountDAO.insertNewAccount(account);
     }
     
     
}
