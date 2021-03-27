/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jajal;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author root
 */
public class Serv {
    private Socket socket  []= new Socket[5]; 
    private ServerSocket server [] = new ServerSocket[5];
    private DataInputStream in = null;
    public void startServer(){
        int [] ports ={1234, 2345, 3456, 4567, 5678};
        try {
            InetAddress ip = InetAddress.getByName("localhost");
            for (int i=0; i<ports.length; i++){
                server[i] = new ServerSocket(ports[i], 5, ip);
                System.out.println("Server sedang menjalankan pada port: "+ports[i]);
                socket[i] = server[i].accept();
                System.out.println("Pengguna terhubung");
                in = new DataInputStream(new BufferedInputStream(socket[i].getInputStream()));
                DataOutputStream out = new DataOutputStream(socket[i].getOutputStream());
                String req ="";
                while(!req.equalsIgnoreCase("udah")){
                    try {
                        req = in.readUTF();
                        System.out.println("Klien "+socket[i].getPort()+" mengirimkan: "+req);
                        out.writeUTF("Kamu mengirimkan pesan ke pada saya: "+req);

                    } catch (IOException e) {
                        System.out.println(e);
                    }
            }
            socket[i].close();
            System.out.println("Layanan pada port "+ports[i]+" dihentikan");
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    public static void main(String[] args) {
        Serv serv = new Serv();
        serv.startServer();
    }
}
