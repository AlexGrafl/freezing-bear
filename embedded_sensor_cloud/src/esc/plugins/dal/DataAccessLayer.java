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
            String connectionUrl = "jdbc:sqlserver://PHIPS-THINK\\SQLEXPRESS;databaseName=ErpData";
            connection = DriverManager.getConnection(connectionUrl, "Remote", "1q2w3e");
        } catch (Exception e) {
           log.error(e);
        }
    }

    public List<Contact> searchContacts(String text, boolean onlyActive) {
        sql = "SELECT * FROM contact WHERE name LIKE ? AND isActive = ?;";
        try {
            resultSet = null;
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%"+text+"%");
            preparedStatement.setBoolean(2, onlyActive);

            resultSet = preparedStatement.executeQuery();
            if (resultSet != null) {
                ResultSetMapper<Contact> resultSetMapper = new ResultSetMapper<>();
                List<Contact> contactList = resultSetMapper.mapToList(resultSet, Contact.class);
                log.info("Found " + contactList.size() + " contacts for string '" + text + "'.");
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
            preparedStatement.setDate(7, new Date(newContact.getBirthDate().getTime()));
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
}

