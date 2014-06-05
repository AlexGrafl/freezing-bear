package esc.plugins.dal;

import esc.plugins.Contact;
import esc.plugins.Invoice;
import esc.plugins.InvoiceItem;
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

    public ArrayList<Contact> searchContacts(HashMap<String, String> parameters) {
        if(parameters != null && !parameters.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("SELECT * FROM contact WHERE ");
            int i;
            boolean valid = false;
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                String key = entry.getKey();
                if (key.equals("name") || key.equals("firstName") || key.equals("lastName") || key.equals("uid")) {
                    stringBuilder.append(key + " LIKE ? AND ");
                    valid = true;
                }
            }
            stringBuilder.append(" isActive = 1;");
            if(valid) {
                sql = stringBuilder.toString();
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
                        ArrayList<Contact> contactList = (ArrayList<Contact>)
                                resultSetMapper.mapToList(resultSet, Contact.class);
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
    public ArrayList<Contact> findCompany(String company) {
        sql = "SELECT * FROM contact WHERE name LIKE ? AND isActive = 1;";
        try {
            resultSet = null;
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + company + "%");

            resultSet = preparedStatement.executeQuery();
            if (resultSet != null) {
                ResultSetMapper<Contact> resultSetMapper = new ResultSetMapper<>();
                ArrayList<Contact> contactList = (ArrayList<Contact>)
                        resultSetMapper.mapToList(resultSet, Contact.class);
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
        sql = "INSERT INTO invoice VALUES (?,?,?,?,?,?);";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, new Date(new java.util.Date().getTime()));
            preparedStatement.setDate(2, new Date(newInvoice.getDueDate().getTime()));
            preparedStatement.setString(3, newInvoice.getComment());
            preparedStatement.setString(4, newInvoice.getMessage());
            preparedStatement.setInt(5, newInvoice.getContactID());
            preparedStatement.setNull(6, Types.DOUBLE);
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

    @Override
    public boolean addInvoiceItem(InvoiceItem invoiceItem) {
        sql = "INSERT INTO invoiceItem VALUES (?, ?, ?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, invoiceItem.getInvoiceID());
            preparedStatement.setInt(2, invoiceItem.getQuantity());
            preparedStatement.setDouble(3, invoiceItem.getNettoPrice());
            preparedStatement.setDouble(4, invoiceItem.getPricePerUnit());
            preparedStatement.setInt(5, invoiceItem.getTax());
            preparedStatement.setString(6, invoiceItem.getDescription());
            int success = preparedStatement.executeUpdate();
            if(success == 1){
                log.info("InvoiceItem added.");
                return true;
            }
        }catch (SQLException e){
            log.error("Cannot add InvoiceItem - ", e);
        }
        return false;
    }

    @Override
    public boolean setTotalInInvoice(double total, int invoiceID) {
        sql = "UPDATE invoice SET total = ? WHERE invoiceID = ?;";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, total);
            preparedStatement.setInt(2, invoiceID);
            int success = preparedStatement.executeUpdate();
            if(success == 1){
                log.info("Set total to '"+total+"' in invoice '"+invoiceID+"'");
                return true;
            }
        }catch (SQLException e){
            log.error("Failed to set total!");
        }
        return false;
    }

    @Override
    public ArrayList<InvoiceItem> getInvoiceItems(String invoiceId) {
        ArrayList<InvoiceItem> invoiceItems = new ArrayList<>();
        sql = "SELECT * FROM invoiceItem WHERE invoiceID = ?;";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(invoiceId));
            resultSet = preparedStatement.executeQuery();
            if(resultSet != null){
                ResultSetMapper<InvoiceItem> resultSetMapper = new ResultSetMapper<>();
                invoiceItems = (ArrayList<InvoiceItem>) resultSetMapper.mapToList(resultSet, InvoiceItem.class);
                log.info("Found "+ invoiceItems.size() + " invoiceitems for invoice "+invoiceId);
            }
        } catch(SQLException | ResultSetMapper.ResultSetMapperException e){
            log .error("Error fetching invoice items for id '" + invoiceId + "'", e);
        }
        return invoiceItems;
    }

    @Override
    public ArrayList<Invoice> searchInvoices(HashMap<String, String> parameters) {
        ArrayList<Invoice> invoices = new ArrayList<>();
        boolean valid = false;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT * FROM invoice WHERE ");
        if(parameters != null && parameters.size() > 0 && !parameters.isEmpty()){
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                String key = entry.getKey();
                if (key.equals("contactId")){
                    stringBuilder.append(key).append(" LIKE :").append(key).append(" AND ");
                    valid = true;
                }
                if(key.equals("dateMin")){
                    stringBuilder.append("issueDate > :").append(key).append(" AND ");
                    valid = true;
                }
                if(key.equals("dateMax")){
                    stringBuilder.append("issueDate < :").append(key).append(" AND ");
                    valid = true;
                }
                if(key.equals("totalMin")){
                    stringBuilder.append("total > :").append(key).append(" AND ");
                    valid = true;
                }
                if(key.equals("totalMax")){
                    stringBuilder.append("total < :").append(key).append(" AND ");
                    valid = true;
                }
            }
            stringBuilder.append("1=1");
        }
        if(valid){
            try{
                sql = stringBuilder.toString();
                log.debug("invoice sql: "+ sql);
                NamedParameterStatement namedParameterStatement = new NamedParameterStatement(connection, sql);
                for (Map.Entry<String, String> entry : parameters.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    if (key.equals("contactId")){
                        namedParameterStatement.setInt(key, Integer.parseInt(value));
                    }
                    if(key.equals("dateMin") || key.equals("dateMax")){
                        //TODO: SimpleDateFormat und umwandeln!!!
                        //namedParameterStatement.setDate(key, new Date());
                    }
                    if(key.equals("totalMin") || key.equals("totalMax")){
                        namedParameterStatement.setDouble(key, Double.parseDouble(value));
                    }
                }
                resultSet = namedParameterStatement.executeQuery();
                if(resultSet != null){
                    ResultSetMapper<Invoice> resultSetMapper = new ResultSetMapper<>();
                    invoices = (ArrayList<Invoice>) resultSetMapper.mapToList(resultSet, Invoice.class);
                    log.info("Found "+ invoices.size() +" invoices!");
                }
            } catch (SQLException | ResultSetMapper.ResultSetMapperException | NumberFormatException e) {
                log.error("Error searching for eierlegende Wollmilchsau", e);
            }
        }
        return invoices;
    }
}

