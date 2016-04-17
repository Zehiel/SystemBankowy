/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systembankowy;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Andrew
 */
public class Operations {
    public List<Client> clients = new ArrayList<>();    
    public Client itemToRemove = new Client();
    
    public class IncorrectTransaction extends Exception {

    public IncorrectTransaction(String message) {
        super(message);
    }

    public IncorrectTransaction(String message, Throwable throwable) {
        super(message, throwable);
    }

}
    
    public void Save(String path){
        try {
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(clients);
            out.close();
            fileOut.close();            
            } catch (FileNotFoundException e) {
                    e.printStackTrace();
            } catch (IOException e) {
                    e.printStackTrace();
            }            
        }
    public void Load(String path) throws ClassNotFoundException {
        
        try {
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            clients = (ArrayList<Client>) in.readObject();
            in.close();
            fileIn.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }       
     }
    public void AddClient(String name, String surname, String pesel, String address, String password) throws ClassNotFoundException {
        Load("C:/Java/clients.txt");
        int id = generateID();
        Client client = new Client(generateID(),name,surname,pesel,address,0.0,password,false);
        clients.add(client);
        Collections.sort(clients,(o1, o2) -> o1.getAccountNumber()-o2.getAccountNumber());
        Save("C:/Java/clients.txt");
        
    }
    
    public void DeleteClient(int ID) throws ClassNotFoundException{
        Load("C:/Java/clients.txt");
        List<Client> deleteCandidates = new ArrayList<>();
        for (Client current : clients){
            if(current.getAccountNumber()==ID){
                deleteCandidates.add(current);                 
            }           
        }
        clients.removeAll(deleteCandidates);
        
        Save("C:/Java/clients.txt");
    }
    
    public void Admin(int ID) throws ClassNotFoundException{
        Load("C:/Java/clients.txt");
        for (Client current : clients){
            if(current.getAccountNumber()==ID){
                if(current.isAdmin()) {
                    current.setAdmin(false);
                }
                else {
                    current.setAdmin(true);
                }
            }           
        }
        Save("C:/Java/clients.txt");
    }
    
    public void Transaction(int fromID,int toID,double amount) throws ClassNotFoundException, IncorrectTransaction{
        Load("C:/Java/clients.txt");
        Client from=null,to=null;
        for (Client current : clients){
            if(current.getAccountNumber()==fromID){
                from=current;                
            }
            if(current.getAccountNumber()==toID){
                to=current;
            }            
        }
        if(from==null || to==null || amount<=0){
            throw new IncorrectTransaction("Zle parametry");
        }
        if(from.getFunds()>=amount){
            from.setFunds(from.getFunds()-amount);
            to.setFunds(to.getFunds()+amount);
        }
        Save("C:/Java/clients.txt");
    }
    
    private int generateID(){
        int last=10000,generated;
        if(clients.size()==1){
                last = 10001;
        }
        
        for(int i=1;i<clients.size();i++) {
            last = clients.get(i).getAccountNumber();
                
            if(clients.get(i).getAccountNumber()-clients.get(i-1).getAccountNumber()!=1){
                last = clients.get(i-1).getAccountNumber();
                break;
            }
        }
        generated = last+1;
        return generated;
    }
}
