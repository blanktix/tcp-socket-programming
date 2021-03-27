/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcp.programming.java;
import java.net.*;
import java.util.Scanner;
import java.io.*;
/**
 *
 * @author root
 */
public class Client {
    private Socket socket = null; 
    private Scanner input = null; 
    private DataOutputStream out = null;
    private DataInputStream in = null;
    // constructor to put ip address and port 
    public Client(String address, int port) 
    { 
            // establish a connection 
            try
            { 
                socket = new Socket(address, port); 
                System.out.println("Connected"); 

                // takes input from terminal 
                input = new Scanner(System.in); 

                // sends output to the socket 
                out = new DataOutputStream(socket.getOutputStream()); 

                in = new DataInputStream(socket.getInputStream());
            } 
            catch(UnknownHostException u) 
            { 
                System.out.println(u); 
            } 
            catch(IOException i) 
            { 
                System.out.println(i); 
            } 

            // string to read message from input 
            String line = ""; 

            // keep reading until "Over" is input 
            while (!line.equals("udah")) 
            {  
                try
                { 
                    String res="";
                    line = input.nextLine(); 
                    out.writeUTF(line);
                    res = in.readUTF();
                    System.out.println(res);
                } 
                catch(IOException i) 
                { 
                        System.out.println(i); 
                } 
            } 

            // close the connection 
            try
            { 
                input.close(); 
                out.close(); 
                socket.close(); 
            } 
            catch(IOException i) 
            { 
                System.out.println(i); 
            }
            System.out.println("Koneksi ke server telah dihentikan");
    } 

    public static void main(String args[]) 
    { 
        Scanner n = new Scanner(System.in);
        System.out.println("Pilih port yang akan digunakan");
        System.out.println("Ketikkan salah satu [1234, 2345, 3456, 4567, 5678]: ");
        Client client = new Client("localhost", n.nextInt());
    } 
}
