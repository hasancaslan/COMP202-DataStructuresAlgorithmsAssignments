package code;

import given.ContactInfo;

import java.util.ArrayList;
import java.util.List;

/*
 * A phonebook class that should:
 * - Be efficiently (O(log n)) searchable by contact name
 * - Be efficiently (O(log n)) searchable by contact number
 * - Be searchable by e-mail (can be O(n))
 *
 * The phonebook assumes that names and numbers will be unique
 * Extra exercise (not to be submitted): Think about how to remove this assumption
 *
 * You need to use your own data structures to implement this
 *
 * Hint: You need to implement a multi-map!
 *
 */
public class PhoneBook {

    private BinarySearchTree<String, ContactInfo> contactName;
    private BinarySearchTree<String, ContactInfo> contactNumber;

    public PhoneBook() {
        contactName = new BinarySearchTree<>();
        contactNumber = new BinarySearchTree<>();
        contactName.setComparator(String::compareTo);
        contactNumber.setComparator(String::compareTo);
    }

    // Returns the number of contacts in the phone book
    public int size() {
        return contactName.size();
    }

    // Returns true if the phone book is empty, false otherwise
    public boolean isEmpty() {
        return contactName.isEmpty();
    }

    //Adds a new contact or overwrites an existing contact with the given info
    // Args should be given in the order of e-mail and address which is handled for
    // you
    // Note that it can also be empty. If you do not want to update a field pass
    // null
    public void addContact(String name, String number, String... args) {
        int numArgs = args.length;
        String email = null;
        String address = null;

        if (numArgs > 0)
            if (args[0] != null)
                email = args[0];
        if (numArgs > 1)
            if (args[1] != null)
                address = args[1];
        if (numArgs > 2)
            System.out.println("Ignoring extra arguments");

        ContactInfo contact = new ContactInfo(name, number);
        contact.setAddress(address);
        contact.setEmail(email);
        contactName.put(name, contact);
        contactNumber.put(number, contact);
    }

    // Return the contact info with the given name
    // Return null if it does not exists
    // Should be O(log n)!
    public ContactInfo searchByName(String name) {
        return contactName.get(name);
    }

    // Return the contact info with the given phone number
    // Return null if it does not exists
    // Should be O(log n)!
    public ContactInfo searchByPhone(String phoneNumber) {
        return contactNumber.get(phoneNumber);
    }

    // Return the contact info with the given e-mail
    // Return null if it does not exists
    // Can be O(n)
    public ContactInfo searchByEmail(String email) {
        for (BinaryTreeNode<String, ContactInfo> node : contactName.getNodesInOrder()) {
            if (email.equals(node.getValue().getEmail()))
                return node.getValue();
        }
        return null;
    }

    // Removes the contact with the given name
    // Returns true if there is a contact with the given name to delete, false otherwise
    public boolean removeByName(String name) {
        ContactInfo contact = contactName.remove(name);
        if (contact == null) return false;
        contactNumber.remove(contact.getNumber());
        return true;
    }

    // Removes the contact with the given name
    // Returns true if there is a contact with the given number to delete, false otherwise
    public boolean removeByNumber(String phoneNumber) {
        ContactInfo contact = contactNumber.remove(phoneNumber);
        if (contact == null) return false;
        contactName.remove(contact.getName());
        return true;
    }

    // Returns the number associated with the name
    public String getNumber(String name) {
        ContactInfo contact = searchByName(name);
        if (contact == null) return null;
        return contact.getNumber();
    }

    // Returns the name associated with the number
    public String getName(String number) {
        ContactInfo contact = searchByPhone(number);
        if (contact == null) return null;
        return contact.getName();
    }

    // Update the email of the contact with the given name
    // Returns true if there is a contact with the given name to update, false otherwise
    public boolean updateEmail(String name, String email) {
        ContactInfo contact = searchByName(name);
        if (contact == null) return false;
        contact.setEmail(email);
        return true;
    }

    // Update the address of the contact with the given name
    // Returns true if there is a contact with the given name to update, false otherwise
    public boolean updateAddress(String name, String address) {
        ContactInfo contact = searchByName(name);
        if (contact == null) return false;
        contact.setAddress(address);
        return true;
    }

    // Returns a list containing the contacts in sorted order by name
    public List<ContactInfo> getContacts() {
        List<ContactInfo> contacts = new ArrayList<>();
        contactName.getNodesInOrder().forEach((node) -> contacts.add(node.getValue()));
        return contacts;
    }

    // Prints the contacts in sorted order by name
    public void printContacts() {
        List<ContactInfo> contacts = getContacts();
        contacts.forEach((contact) -> System.out.printf("%s %s\n", contact.getName(), contact.getNumber()));
    }
}
