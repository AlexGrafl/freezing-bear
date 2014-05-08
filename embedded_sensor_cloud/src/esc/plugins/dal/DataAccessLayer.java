package esc.plugins.dal;

import esc.plugins.Contact;
import esc.plugins.ResultSetMapper;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.List;

/**
 * @author Alex
 */
public class DataAccessLayer implements IDataAccessLayer{
    private static final Logger log = Logger.getLogger(DataAccessLayer.class);

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private String sql;

    public DataAccessLayer() {
        try {
            if (connection != null) connection.close();

            // Establish the connection.
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            //String connectionUrl = "jdbc:sqlserver://PHIPS-THINK\\SQLEXPRESS;databaseName=ErpData";
            String connectionUrl = "jdbc:sqlserver://ALEX-NOTEBOOK;databaseName=ErpData";
          //  connection = DriverManager.getConnection(connectionUrl, "Remote", "1q2w3e");
            connection = DriverManager.getConnection(connectionUrl, "test_user", "password_yo");
        } catch (Exception e) {
           log.error(e);
        }
    }

    public List<Contact> searchContacts(String key, String value) {
        sql = "SELECT * FROM contact WHERE "+ key +" LIKE ? ;";
        try {
            resultSet = null;
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + value + "%");

            resultSet = preparedStatement.executeQuery();
            if (resultSet != null) {
                ResultSetMapper<Contact> resultSetMapper = new ResultSetMapper<>();
                List<Contact> contactList = resultSetMapper.mapToList(resultSet, Contact.class);
                log.info("Found " + contactList.size() + " contacts for string '" + value + "'.");
                return  contactList;
            }

        } catch (SQLException | ResultSetMapper.ResultSetMapperException e) {
            log.error(e);
        }
        return null;
    }

    @Override
    public boolean insertNewContact(Contact newContact) {
        sql = "INSERT INTO contact VALUES (? , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newContact.getName());
            if(newContact.getUid() != null) {
                preparedStatement.setInt(2, newContact.getUid());
            }
            else{
                preparedStatement.setNull(2, Types.INTEGER);
            }
            preparedStatement.setString(3, newContact.getTitle());
            preparedStatement.setString(4, newContact.getFirstName());
            preparedStatement.setString(5, newContact.getLastName());
            preparedStatement.setString(6, newContact.getSuffix());
            if(newContact.getBirthDate() != null) {
                preparedStatement.setDate(7, new Date(newContact.getBirthDate().getTime()));
            }
            else{
                preparedStatement.setNull(7, Types.DATE);
            }
            preparedStatement.setString(8, newContact.getAddress());
            preparedStatement.setString(9, newContact.getInvoiceAddress());
            preparedStatement.setString(10, newContact.getShippingAddress());
            preparedStatement.setBoolean(11, true);
            int result = preparedStatement.executeUpdate();
            if(result == 1) return true;
        }
        catch (SQLException e){
            log.error("Insert failed - ", e);
        }
        return false;
    }

    @Override
    public boolean editContact(Contact contact) {
        sql = "UPDATE contact SET name = ?, uid = ?, title = ?, firstName = ?, lastName = ?, suffix = ?, birthDate =" +
                " ?, address = ?, invoiceAddress = ?, shippingAddress = ?, isActive = ? WHERE contactID = ?";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, contact.getName());
            if(contact.getUid() != null) {
                preparedStatement.setInt(2, contact.getUid());
            }
            else{
                preparedStatement.setNull(2, Types.INTEGER);
            }
            preparedStatement.setString(3, contact.getTitle());
            preparedStatement.setString(4, contact.getFirstName());
            preparedStatement.setString(5, contact.getLastName());
            preparedStatement.setString(6, contact.getSuffix());
            if(contact.getBirthDate() != null) {
                preparedStatement.setDate(7, new Date(contact.getBirthDate().getTime()));
            }
            else{
                preparedStatement.setNull(7, Types.DATE);
            }
            preparedStatement.setString(8, contact.getAddress());
            preparedStatement.setString(9, contact.getInvoiceAddress());
            preparedStatement.setString(10, contact.getShippingAddress());
            preparedStatement.setBoolean(11, contact.isActive());
            preparedStatement.setInt(12, contact.getContactID());
            int result = preparedStatement.executeUpdate();
            if(result == 1) return true;
        } catch (SQLException e) {
            log.error("Update failed - ", e);
        }
        return false;
    }
}

