package pa;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static final int PORT = 3000;
    public static boolean running = true;
    public ServerSocket serverSocket = null;

    private List<ClientThread> clients = new ArrayList<>();

    public Server() throws IOException, InterruptedException {

        try{
            serverSocket = new ServerSocket(PORT);
            while (running){
                System.out.println("Wait for a client");
                Socket socket = serverSocket.accept();
                socket.setSoTimeout(100 * 1000);

                ClientThread client = new ClientThread(socket, this);
                client.start();
                clients.add(client);
            }
        }catch (Exception e){
            System.out.println("Serverul nu mai accepta clienti...");
        }finally {
            serverSocket.close();
            System.out.println("Serversul se opreste....");
            for(ClientThread c : clients){
                c.join();
            }
        }
    }
}
