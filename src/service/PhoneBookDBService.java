package service;

import model.Contact;
import model.pair.*;

import java.util.*;

import static main.Loader.ANSI_RED;
import static main.Loader.ANSI_RESET;

public class PhoneBookDBService {
    private static final TreeMap<IdAndContactNamePair, Contact> CONTACTS = new TreeMap<>();

    public static TreeMap<IdAndContactNamePair, Contact> getContacts() {
        return CONTACTS;
    }

    public static void addIdCntNamePairAndContactToContacts(IdAndContactNamePair idAndContactNamePair, Contact contact) {
        CONTACTS.put(idAndContactNamePair, contact);
    }

    public static void replaceEntryInContacts(IdAndContactNamePair oldPair, Contact oldContact,
                                              IdAndContactNamePair newPair, Contact newContact) {
        CONTACTS.remove(oldPair, oldContact);
        CONTACTS.put(newPair, newContact);
    }

    public static TreeMap<IdAndContactNamePair, Contact> searchInContactsByName(String searchValue) {
        searchValue = searchValue.toLowerCase();
        TreeMap<IdAndContactNamePair, Contact> result = new TreeMap<>();
        for (IdAndContactNamePair pair : CONTACTS.navigableKeySet()) {
            if ((pair.getContactName().toLowerCase()).contains(searchValue)) {
                result.put(pair, CONTACTS.get(pair));
            }
        }
        return result;
    }

    public static TreeMap<IdAndContactNamePair, Contact> searchInContactsByPhoneNumber(String searchValue) {
        TreeMap<IdAndContactNamePair, Contact> result = new TreeMap<>();
        for (Map.Entry<IdAndContactNamePair, Contact> set : CONTACTS.entrySet()) {
            for (PhoneNumberTypeAndPhoneNumberPair pair : set.getValue().getPhoneNumbers()) {
                if (pair.getPhoneNumber().contains(searchValue)) {
                    result.put(set.getKey(), set.getValue());
                    break;
                }
            }
        }
        return result;
    }

    public static TreeMap<IdAndContactNamePair, Contact> searchInContactsByEmail(String searchValue) {
        TreeMap<IdAndContactNamePair, Contact> result = new TreeMap<>();
        for (Map.Entry<IdAndContactNamePair, Contact> set : CONTACTS.entrySet()) {
            for (EmailTypeAndEmailPair pair : set.getValue().getEmails()) {
                if (pair.getEmail().contains(searchValue)) {
                    result.put(set.getKey(), set.getValue());
                    break;
                }
            }
        }
        return result;
    }

    public static TreeMap<IdAndContactNamePair, Contact> searchInContactsByCompanyName(String searchValue) {
        TreeMap<IdAndContactNamePair, Contact> result = new TreeMap<>();
        for (Map.Entry<IdAndContactNamePair, Contact> set : CONTACTS.entrySet()) {
            if (set.getValue().getCompanyName().contains(searchValue)) {
                result.put(set.getKey(), set.getValue());
            }
        }
        return result;
    }

    public static void deleteEntryInContacts(IdAndContactNamePair pair) {
        CONTACTS.remove(pair);
    }

    public static void showAllContacts() {
        if (CONTACTS.size() == 0) {
            System.out.println(ANSI_RED + "\nThere is no any contact in your PhoneBook." + ANSI_RESET);
        } else {
            int number = 0;
            System.out.println("\nAll contacts:\n");
            for (Map.Entry<IdAndContactNamePair, Contact> entry : CONTACTS.entrySet()) {
                System.out.println(++number + ". " + entry.getKey().getContactName() + "\n" + entry.getValue().toString());
            }
        }
    }
}
