package esc.plugins.dal;

import esc.plugins.Contact;
import esc.plugins.Invoice;
import esc.plugins.ResultSetMapper;
import org.apache.log4j.Logger;

import java.sql.*;
import java.sql.Date;
import java.util.*;

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

    public List<Contact> searchContacts(HashMap<String, String> parameters) {
        if(parameters != null && !parameters.isEmpty()) {
            sql = "SELECT * FROM contact WHERE ";
            int i = 0;
            boolean valid = false;
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                String key = entry.getKey();
                if (key.equals("name") || key.equals("firstName") || key.equals("lastName") || key.equals("uid")) {
                    sql += key + " LIKE ? AND";
                    valid = true;
                }
            }
            sql += " isActive = 1;";
            if(valid) {
                log.debug("SQL string: " + sql);
                try {
                    resultSet = null;
                    preparedStatement = connection.prepareStatement(sql);
                    Collection<String> values = parameters.values();
                    String[] valueArray = values.toArray(new String[values.size()]);
                    for (i = 0; i < valueArray.length; i++) {
                        preparedStatement.setString(i + 1, "%" + valueArray[i] + "%");
                    }
                    resultSet = preparedStatement.executeQuery();
                    if (resultSet != null) {
                        ResultSetMapper<Contact> resultSetMapper = new ResultSetMapper<>();
                        List<Contact> contactList = resultSetMapper.mapToList(resultSet, Contact.class);
                        log.info("Found " + contactList.size() + " contacts.");
                        return contactList;
                    }

                } catch (SQLException | ResultSetMapper.ResultSetMapperException e) {
                    log.error(e);
                }
            }

        }
        return new ArrayList<>();
    }

    @Override
    public boolean insertNewContact(Contact newContact) {
        sql = "INSERT INTO contact VALUES (? , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
            if(newContact.getCompanyID() != null) {
                preparedStatement.setInt(6, newContact.getCompanyID());
            }
            else{
                preparedStatement.setNull(6, Types.INTEGER);
            }
            preparedStatement.setString(7, newContact.getSuffix());
            if(newContact.getBirthDate() != null) {
                preparedStatement.setDate(8, new Date(newContact.getBirthDate().getTime()));
            }
            else{
                preparedStatement.setNull(8, Types.DATE);
            }
            preparedStatement.setString(9, newContact.getAddress());
            preparedStatement.setString(10, newContact.getInvoiceAddress());
            preparedStatement.setString(11, newContact.getShippingAddress());
            preparedStatement.setBoolean(12, true);

            int result = preparedStatement.executeUpdate();
            if(result == 1){
                log.info("Contact added");
                return true;
            }
        }
        catch (SQLException e){
            log.error("Insert failed - ", e);
        }
        return false;
    }

    @Override
    public boolean editContact(Contact contact) {
        sql = "UPDATE contact SET name = ?, uid = ?, title = ?, firstName = ?, lastName = ?, suffix = ?, birthDate =" +
                " ?, address = ?, invoiceAddress = ?, shippingAddress = ?, isActive = ?, companyID = ? WHERE contactID = ?";
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
            if(contact.getCompanyID() != null) {
                preparedStatement.setInt(12, contact.getCompanyID());
            }
            else{
                preparedStatement.setNull(12, Types.INTEGER);
            }
            preparedStatement.setInt(13, contact.getContactID());
            int result = preparedStatement.executeUpdate();
            if(result == 1) return true;
        } catch (SQLException e) {
            log.error("Update failed - ", e);
        }
        return false;
    }

    @Override
    public List<Contact> findCompany(String company) {
        sql = "SELECT * FROM contact WHERE name LIKE ? AND isActive = 1;";
        try {
            resultSet = null;
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + company + "%");

            resultSet = preparedStatement.executeQuery();
            if (resultSet != null) {
                ResultSetMapper<Contact> resultSetMapper = new ResultSetMapper<>();
                List<Contact> contactList = resultSetMapper.mapToList(resultSet, Contact.class);
                log.info("Found " + contactList.size() + " companies for name '" + company + "'.");
                return  contactList;
            }

        } catch (SQLException | ResultSetMapper.ResultSetMapperException e) {
            log.error(e);
        }
        return new ArrayList<>();
    }

    @Override
    public boolean createInvoice(Invoice newInvoice) {
        sql = "INSERT INTO invoice VALUES (?,?,?,?,?);";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, new Date(new java.util.Date().getTime()));
            preparedStatement.setDate(2, new Date(newInvoice.getDueDate().getTime()));
            preparedStatement.setString(3, newInvoice.getComment());
            preparedStatement.setString(4, newInvoice.getMessage());
            preparedStatement.setInt(5, newInvoice.getContactID());
            int result = preparedStatement.executeUpdate();
            if(result == 1){
                log.info("Invoice added.");
                return true;
            }
        }
        catch(SQLException e){
            log.error("Cannot create new invoice - ", e);
        }
        return false;
    }
}

