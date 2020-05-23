package dao;

import model.Person;

import java.sql.Date;

public class PersonDaoTest {
    void outputPerson(Person person){
        System.out.println("IDCardNumber="+person.get_id_card_number());
        System.out.println("\nIDCardType="+person.get_class_type());
        System.out.println("\nName="+person.get_name());
        System.out.println("\nGender="+person.get_gender());
        System.out.println("\nBirth="+person.get_birthdate());
        System.out.println("\nNationality="+person.get_nationality());
        System.out.println("\nAddress="+person.get_address());
        System.out.println("\nAddressID="+person.get_address_postal_code());
        System.out.println("\nAddressPhoneNumber="+person.get_address_phone_number());
    }

    @org.junit.jupiter.api.Test
    void PersonDaoTestCase1() {//this case design for insert
    }
}
