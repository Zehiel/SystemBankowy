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
import java.util.List;

/**
 *
 * @author Andrew
 */
public class Operations {
    public List<Client> clients = new ArrayList<>();
    
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
        Save("C:/Java/clients.txt");
        
    }
    private int generateID(){
        int last=10000,generated;
        for(int i=0;i<clients.size();i++) {
            if(clients.get(i).getAccountNumber()==0){
                last = clients.get(i-1).getAccountNumber();
                break;
            }
            else {
                last = clients.get(i).getAccountNumber();
            }
            
        }
        generated = last+1;
        return generated;
    }
}
