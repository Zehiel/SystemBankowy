/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systembankowy;

import java.io.Serializable;

/**
 *
 * @author Andrew
 */
public class Client implements Serializable{
    
    private int accountNumber;    
    private String name;    
    private String surname; 
    private String pesel;
    private String address;
    private double funds;
    private String password;
    private boolean admin;

    
    public Client() {}
    public Client(int id,String name, String surname, String pesel, String address, Double funds, String password, Boolean admin) {
      this.accountNumber = id;
      this.name = name;
      this.surname = surname;
      this.pesel = pesel;
      this.address = address;
      this.funds = funds;
      this.password = password;
      this.admin = admin;
   }
    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPesel() {
        return pesel;
    }

    /**
     *
     * @param pesel
     */
    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String addres) {
        this.address = addres;
    }

    public double getFunds() {
        return funds;
    }

    public void setFunds(double funds) {
        this.funds = funds;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    
    
}
