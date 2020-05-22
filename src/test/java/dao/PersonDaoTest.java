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
        PersonDao personDao = new PersonDao();
        String IDCardNumber = "441302";
        Boolean idCardType = true;
        String name = "fuqianshan";
        Boolean gender = true;
        Date birth = new Date(0);
        String nationality = "CN";
        String address = "CNGDHZ";
        String addressID = null;
        String addressPhoneNumber = "0752";

        int flag = personDao.insertPerson(IDCardNumber, idCardType, name, gender,
                birth, nationality, address, addressID, addressPhoneNumber);
        if (flag == 0) {
            System.out.println("Error when inserting");
        }
        System.exit(0);
    }
}
