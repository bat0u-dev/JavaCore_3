import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class JChatServer {
    private Vector<ClientHandler> clientsList;
    int clientCounter = 1;

    public JChatServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(8189);
            clientsList = new Vector<>();
            SQLHandler.connect();

            while (true) {
                System.out.println("Waiting for client's to connect...");
                Socket socket = serverSocket.accept();
                ClientHandler processedClient = new ClientHandler(this,socket,clientCounter);
                System.out.println("Client " + clientCounter + " has been connected...");
                clientCounter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            SQLHandler.disconnect();
        }
    }

    public void subscribe(ClientHandler processedClient){
        clientsList.add(processedClient);
    }

    public void unsubscribe(ClientHandler processedClient){
        clientsList.remove(processedClient);
    }

    public void broadcastMessage(String message){
        for (ClientHandler c: clientsList) {
            c.sendMsg(message);
        }
    }

    public void serviceMessage(String message){

    }
}

