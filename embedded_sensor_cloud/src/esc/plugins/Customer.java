package esc.plugins;

import java.util.Date;
import java.util.HashSet;

/**
 * @author Alex
 */
public class Customer {
    private int customerID;
    private String name;
    private int uid;
    private String title;
    private String firstName;
    private String lastName;
    private String suffix;
    private Date birthDate;
    private String address;
    private String invoiceAddress;
    private String shippingAddress;
    private HashSet<Invoice> invoiceList;

}
