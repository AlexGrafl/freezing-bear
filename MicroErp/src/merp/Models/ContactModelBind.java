package merp.Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class ContactModelBind {
     private ArrayList<Contact> contactList;

        public ContactModelBind(ArrayList<Contact> i) {
            this.contactList = i;
        }

        public ObservableList<ContactTableModel> getItems() {
            if (contactList == null) {
                return null;
            }

            Integer uid = 0;
            String birthDate;

            ObservableList<ContactTableModel> elements = FXCollections
                    .observableArrayList();

            for (int i = 0; i < contactList.size(); i++) {
                Contact contact = contactList.get(i);

                try{
                    uid = contact.getUid();
                }
                catch(NullPointerException e){
                    uid = 0;
                }

                try{
                    birthDate = contact.getBirthDate().toString();

                }catch(NullPointerException e){
                    birthDate="";
                }

                elements.add(new ContactTableModel(contact.getContactID(),contact.getName(),0,contact.getTitle(),
                        contact.getFirstName(),contact.getLastName(),contact.getSuffix(),birthDate,
                        contact.getAddress(),contact.getInvoiceAddress(),contact.getShippingAddress(),contact.getIsActive()));
            }
            return elements;
        }

}