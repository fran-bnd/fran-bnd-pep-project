package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Model.*;
import Util.ConnectionUtil;

public class MessageDAO {
    
    /**
     * ### Message table
        
        message_id integer primary key auto_increment,
        posted_by integer,
        message_text varchar(255),
        time_posted_epoch long,
        foreign key (posted_by) references Account(account_id)
     */

     public List<Message> getAllMesages(){
        /**
         *  public List<Book> getAllBooks(){
            Connection connection = ConnectionUtil.getConnection();
            List<Book> books = new ArrayList<>();
            try {
                //Write SQL logic here
                String sql = "select * from Book;";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet rs = preparedStatement.executeQuery();
                while(rs.next()){
                    Book book = new Book(rs.getInt("isbn"),
                            rs.getInt("author_id"),
                            rs.getString("title"),
                            rs.getInt("copies_available"));
                    books.add(book);
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
            return books;
            }
         */
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message aMessageQuery = new Message(rs.getInt("message_id"), 
                            rs.getInt("posted_by"), 
                            rs.getString("message_text"),
                            rs.getLong("time_posted_epoch"));
                messages.add(aMessageQuery);
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }


     public Message insertNewMessage(Message newMessage){
        Connection connection = ConnectionUtil.getConnection();
        try {
            // assuming database automatically generate a primary key.
            String sql = "INSERT INTO message(posted_by, message_text, time_posted_epoch) VALUES (?,?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // preparedStatement's setString method 
            preparedStatement.setInt(1, newMessage.getPosted_by());
            preparedStatement.setString(2, newMessage.getMessage_text());
            preparedStatement.setLong(3, newMessage.getTime_posted_epoch());

            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_newMessage_id = (int) pkeyResultSet.getInt(1);
                return new Message(generated_newMessage_id, newMessage.getPosted_by(), newMessage.getMessage_text(), newMessage.getTime_posted_epoch());
            }
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message getMessagebyId(int messageId) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM message WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, messageId);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message theMessageQuery = new Message(rs.getInt("message_id"), 
                            rs.getInt("posted_by"), 
                            rs.getString("message_text"),
                            rs.getLong("time_posted_epoch"));
            return theMessageQuery;
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message deleteMessageById(int messageId) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "DELETE FROM message WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, messageId);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message theMessageQuery = new Message(rs.getInt("message_id"), 
                            rs.getInt("posted_by"), 
                            rs.getString("message_text"),
                            rs.getLong("time_posted_epoch"));
            return theMessageQuery;
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }


    public List<Message> getMessageByAccId(int accId) {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message WHERE posted_by = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, accId);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message theMessageQuery = new Message(rs.getInt("message_id"), 
                            rs.getInt("posted_by"), 
                            rs.getString("message_text"),
                            rs.getLong("time_posted_epoch"));
                messages.add(theMessageQuery);
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public Message patchMessageById(int messageId) {
        return null;
    }
}
