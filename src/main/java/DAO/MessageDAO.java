package DAO;

import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;

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

     public Message getAllMesages(Message allMessages){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM message;" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //preparedStatement.setString(1, allMessages.getUsername());
            //preparedStatement.setString(2, allMessages.getPassword());

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                Message allMessagesQuery = new Message(rs.getInt("message_id"), 
                            rs.getInt("posted_by"), 
                            rs.getString("message_text"),
                            rs.getLong("time_posted_epoch"));
            return allMessagesQuery;
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }



     public Message insertNewMessage(Message newMessage){
        Connection connection = ConnectionUtil.getConnection();
        try {
            // assuming database automatically generate a primary key.
            String sql = "INSERT INTO message(username, password) VALUES (?,?);" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // preparedStatement's setString method 
            preparedStatement.setInt(2, newMessage.getPosted_by());
            preparedStatement.setString(1, newMessage.getMessage_text());

            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                //int generated_newAccount_id = (int) pkeyResultSet.getLong(1);
                //return new Account(generated_newAccount_id, newMessage.getUsername(), newMessage.getPassword());
            }
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message postAMessage(Message message){
        return null;
    }

}
