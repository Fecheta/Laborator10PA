package pa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Client {
    private String serverAddress;
    private int PORT;

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public Client(String serverAddress, int PORT){
        this.serverAddress = serverAddress;
        this.PORT = PORT;
    }

    public void request(){
        String response = null;
        String request = null;
        Scanner scanner = new Scanner(System.in);
        try{
            socket = new Socket(serverAddress, PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (!socket.isClosed()) {
                System.out.print("Dati un request: ");
                request = scanner.nextLine();
                if(request.compareTo("exit") == 0){
                    System.out.println("V-ati deconectat!");
                    socket.close();
                    break;
                }
                out.println(request);

                if((response = in.readLine()) != null)
                    System.out.println(response.replaceAll("@#","\n"));
                else {
                    System.out.println("Serverul nu mai raspunde...");
                    socket.close();
                }
            }

        }catch (SocketTimeoutException e){
            System.out.println("timpul a expirat");
        } catch (IOException e){
//            System.err.println("No server listening..." + e);
            System.out.println("Nu ma pot conecta la server...");
            System.exit(-10);
        }finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
