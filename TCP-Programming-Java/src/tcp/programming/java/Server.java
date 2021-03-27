/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcp.programming.java;
import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author root
 */
public class Server {
    //initialize socket and input stream 
    private Socket socket  = null; 
    private ServerSocket server = null;
    private DataInputStream in = null;
    public void startServer(int port){
        try {
            InetAddress ip = InetAddress.getByName("localhost");
            server = new ServerSocket(port, 5, ip);
            System.out.println("Server sedang menjalankan pada port: "+port);
            //untuk membuat sever bisa menjalankan beberapa port dan menangani banyak client: menggunakan Thread
            Runnable r = new ServerSocketHandler(server, port);
            new Thread(r).start(); 
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    public static void main(String[] args) {
        int [] port ={1234, 2345, 3456, 4567, 5678}; // membuat 5 buah port untuk dibinding
        Server serv = new Server();
        for(int p : port){
            serv.startServer(p);
        }
    }
}

class ClientHandlerThread extends Thread{
    //Untuk menangani beberapa client dalam satu port
    private Socket socket;
    private DataInputStream in = null; 
    public ClientHandlerThread(Socket socket){
        this.socket = socket;
    }
    @Override
    public void run(){
        try {
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            String req ="";
            while(!req.equalsIgnoreCase("udah")){
                try {
                    req = in.readUTF();
                    System.out.println("Klien "+socket.getPort()+" mengirimkan: "+req);
                    out.writeUTF("Kamu mengirimkan pesan ke pada saya: "+req);
                    
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
            System.out.println("Client "+socket.getPort()+"telah memutuskan koneksi");
        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    
    }
}

class ServerSocketHandler implements Runnable{
    //Untuk menagani beberapa port terbuka secara bersamaan
    private ServerSocket serv;
    int port;
    public ServerSocketHandler(ServerSocket server, int port){
        this.port = port;
        this.serv = server;
    }
    @Override
    public void run(){
        while (true) {            
            try {
                Socket s = serv.accept();
                System.out.println("Pengguna baru terhubung pada port "+port);
                Runnable r = new ClientHandlerThread(s);
                new Thread(r).start();
            } catch (IOException ex) {
                Logger.getLogger(ServerSocketHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}