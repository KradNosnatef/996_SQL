package model;

import java.io.Serializable;

// Define Person Class
// all the followings elments are basic information of a person
// I will only mention two important rules:
// card_type: 0 - ID Card in PRC, 1 - passport (Default is 0)
// TODO we can make check id_card_number is leagal or not when all other parts finish
// gender: 0 - Male, 1 - Female
// TODO We do not consider about LGBT in our database in this version
public class Person implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id_card_number;
    private boolean card_type;
    private String name;
    private boolean gender;
    private String birthdate;
    private String nationality;
    private String address;
    private String address_postal_code;
    private String address_phone_number;

    public String get_id_card_number() { return id_card_number; }

    public boolean get_class_type() { return card_type; }

    public String get_name() { return name; }

    public boolean get_gender() { return gender; }

    public String get_birthdate() { return birthdate; }

    public String get_nationality() { return nationality; }

    public String get_address() { return address; }

    public String get_address_postal_code() { return address_postal_code; }

    public String get_address_phone_number() { return address_phone_number; }

    public void set_id_card_number(String new_id_card_number) { id_card_number = new_id_card_number; }

    public void set_card_type(boolean new_card_type) { card_type = new_card_type; }

    public void set_name(String new_name) { name = new_name; }

    public void set_gender(boolean new_gender) { gender = new_gender; }

    public void set_birthdate(String new_birthdate) { birthdate = new_birthdate; }

    public void set_nationnality(String new_nationality) { nationality = new_nationality; }

    public void set_address(String new_address) { address = new_address; }

    public void set_address_postal_code(String new_address_postal_code) {
        address_postal_code = new_address_postal_code;
    }
}
