import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    private JChatServer server;
    private Socket clientSocket;
    private DataInputStream Input;
    private DataOutputStream Output;
    int clientID;
    String nickname;



    public ClientHandler(JChatServer server, Socket clientSocket, int clientID) {
        try {
            this.server = server;
            this.clientSocket = clientSocket;
            this.clientID = clientID;
            this.Input = new DataInputStream(clientSocket.getInputStream());
            this.Output = new DataOutputStream(clientSocket.getOutputStream());
            new Thread(() -> {
                try  {
                    while (true){
                        String inputMsg = Input.readUTF();
                        String[] tokens = inputMsg.split("\\s");
                        if(tokens[0].equals("/auth")){
                            String nickFromDB = SQLHandler.getNickByLoginAnPassword(tokens[1],tokens[2]);
                            if(nickFromDB != null){
                                sendMsg("/authOk");
                                server.subscribe(this);
                                nickname = nickFromDB;
                                Output.writeUTF("Authorization successful.");
                                break;
                            }
                        }

                    }
                    while (true) {
                        String inputMsg = Input.readUTF();
                        String[] tokens = inputMsg.split("\\s");
                        if (tokens[0].equals("/changeNickTo")){
                            int res = SQLHandler.changeNick(this.nickname, tokens[1]);
                            switch (res){
                                case 0:
                                    System.out.println("error");
                                case 1:
                                    System.out.println("success");
                                    this.nickname = tokens[1];
                            }
                            System.out.println(this.nickname);
                        }
                        System.out.println(this.nickname + " message :" + inputMsg);
                        if (inputMsg.equals("/end")) {
                            System.out.println("Client aborted the connection.");
//                            Output.writeUTF("Connection has been aborted by client.");
                            break;
                        }
                        server.broadcastMessage(this.nickname + " : " + inputMsg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        Input.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        Output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    server.unsubscribe(ClientHandler.this);
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String inputMsg) {
        try {
            Output.writeUTF(inputMsg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



