package pa;

import socialNetwork.Person;
import socialNetwork.SocialNetwork;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.time.Period;

public class ClientThread extends Thread{
    private Socket socket = null;
    private BufferedReader in;
    private PrintWriter out;
    public Server server;

    private boolean logged = false;
    private Person currentUser = null;

    public ClientThread(Socket socket, Server server){
        this.socket = socket;
        this.server = server;
    }

    public void run(){
        String request = null;
        String response = null;

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            while (!socket.isClosed()) {
                if((request = in.readLine()) == null){
                    System.out.println("Clientul s-a deconectat...");
                    socket.close();
                    break;
                }

                if(request.compareTo("stop") != 0) {
//                    response = "Server recived the request: " + request;
                    response = command(request);
                }
                else {
                    response = "server stopped..";
                    server.serverSocket.close();
                    Server.running = false;
                }
                out.println(response);
                out.flush();
            }

        } catch (SocketTimeoutException e){
            out.println("Timpul a expirat");
            out.flush();
        } catch (IOException e) {
            System.out.println("Communication error..." + e);
        } finally {
            try {
                System.out.println("Inchid Socket-ul");
                socket.close();
            }catch (IOException e){
                System.err.println(e);
            }
        }
    }

    public String command(String command){
        String status = "Comanda Incorecta!!!";
        String[] commandBreak = command.split(" ");
        int size = commandBreak.length;

        if(commandBreak[0].compareTo("register") == 0 && size == 2){
            if(logged){
                status = "Sunteti deja logat";
            }else {
                if(SocialNetwork.register(commandBreak[1]))
                    status = "V-ati inregistrat cu success";
                else
                    status = "Nume de utilizator exista deja";
            }
        }else if(commandBreak[0].compareTo("login") == 0 && size == 2) {
            if(logged){
                status = "Sunteti logat!!";
            }else {
                this.currentUser = SocialNetwork.login(commandBreak[1]);
                if(this.currentUser != null) {
                    status = "V-ati logat cu sucess";
                    logged = true;
                }
                else {
                    status = "Utilizator inexistent";
                }
            }
        }else if(commandBreak[0].compareTo("friend") == 0 && size >= 2 && logged) {
            Person friend;
            status = "";
            for(int i = 1; i< size; i++){
                friend = SocialNetwork.find(commandBreak[i]);
                if(friend != null) {
                    if(SocialNetwork.friendship(this.currentUser, friend))
                        status = status + commandBreak[i] + " a fost adaugat in lista ta de prieteni@#";
                    else
                        status = status + "Esti deja prieten cu: " + commandBreak[i] + "@#";
                }else {
                    status = status + commandBreak[i] + " acest user nu exista@#";
                }
            }
        }else if(commandBreak[0].compareTo("send") == 0 && size == 2 && logged) {
            this.currentUser.sendMessage(commandBreak[1]);
            status = "Mesaj trimis cu suces!";
        }else if(commandBreak[0].compareTo("read") == 0 && size == 1 && logged) {
            status = this.currentUser.messages();
            if(status.compareTo("")==0){
                status = "nu ai primit nici un mesaj";
            }
        }else if(commandBreak[0].compareTo("friendsList") == 0 && size == 1 && logged) {
            status = this.currentUser.friendsList();
            if(status.compareTo("")==0){
                status = "nu ai nici un priente";
            }
        }

        return status;
    }
}
