import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JChatServer {
    private Vector<ClientHandler> clientsList;
    private static ExecutorService executorService = Executors.newCachedThreadPool();/*может сделать данное поле
    final?! В целом не совсем понятно каким образом модификатор final влияет на ссылочные типы данных? Не сможем в
    дальнейшем поменять значение, на которое сылается переменная объявленная как final?*/
    int clientCounter = 1;

    public static ExecutorService getExecutorService() {
        return executorService;
    }

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

//    public void serviceMessage(String message){
//    }
}

