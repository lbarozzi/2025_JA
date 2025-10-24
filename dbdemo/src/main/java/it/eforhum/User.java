package it.eforhum;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid")
    private int userid;
    
    @Column(name = "firstName")
    private String firstName;
    
    @Column(name = "lastName")
    private String lastName;

    @OneToMany(mappedBy = "userid", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private java.util.Set<PhoneNumber> phoneNumbers;
    
    
    // Costruttori
    public User() {
        this("","");
    }
    
    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    // Getter e setter
    public int getUserid() {
        return userid;
    }
    
    public void setUserid(int userid) {
        this.userid = userid;
    }
    public java.util.Set<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }
    public void setPhoneNumbers(java.util.Set<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }
    public void addPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumbers.add(phoneNumber);
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    @Override
    public String toString() {
        return "User [userid=" + userid + ", firstName=" + firstName + ", lastName=" + lastName + "]";
    }
}