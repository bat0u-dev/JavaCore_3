import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    VBox mainBox;

    @FXML
    TextArea textArea;

    @FXML
    TextField textField, loginField;

    @FXML
    PasswordField passwordField;

    @FXML
    Button connectBtn;

    @FXML
    HBox authPanel, msgPanel;

    private Socket clientSocket;
    private DataInputStream clientInput;
    private DataOutputStream clientOutput;

    public TextArea getTextArea() {
        return textArea;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setAuthorized(false);
    }

    public void setAuthorized(boolean authorized) {
        if (authorized) {
            authPanel.setVisible(false);
            authPanel.setManaged(false);
            msgPanel.setVisible(true);
            msgPanel.setManaged(true);
        } else {
            authPanel.setVisible(true);
            authPanel.setManaged(true);
            msgPanel.setVisible(false);
            msgPanel.setManaged(false);
        }
    }

    public void sendMessage() {
        try {
            String message = textField.getText().trim();
            if (message.equals("")) {
                System.out.println("\n");
                return;
            }
            clientOutput.writeUTF(message);
            textField.clear();
            textField.requestFocus();
        } catch (IOException e) {
            textField.clear();
            System.out.println("Not connected to server. Socket has been closed. Restart the application!");
            textArea.appendText("Not connected to server. Socket has been closed. Restart the application!");
            textArea.appendText("\n");
        }
    }

    public void sendMessage(String message) {
        try {
            clientOutput.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendAuthData() {
        connect();
        sendMessage("/auth " + loginField.getText() + " " + passwordField.getText());
        loginField.clear();
        passwordField.clear();
    }

    public void clearTextAreaBtnAction() {
        textArea.clear();
        textField.requestFocus();
    }

    public void connect() {

        try {
            if (clientSocket == null || clientSocket.isClosed()) {
                clientSocket = new Socket("127.0.0.1", 8189);
                System.out.println("Connection to server successful:)");
                clientInput = new DataInputStream(clientSocket.getInputStream());
                clientOutput = new DataOutputStream(clientSocket.getOutputStream());
                HistoryLog historyLog = new HistoryLog();
                String[] historyLogLines = historyLog.getHistory();

                for (int i = 0; i < 100; i++) {
                    if (historyLog.getHistory()[i] != null) {
                        if(historyLogLines[i].equals("Authorization successful.")) continue;
                        textArea.appendText(historyLog.getHistory()[i] + "\n");
                    } else {
                        break;
                    }
                }
                historyLog.getReadHistory().close();

                new Thread(() -> {
                    try {
                        while (true) {
                            String inputMsg = clientInput.readUTF();
                            if (inputMsg.equals("/authOk")) {
                                setAuthorized(true);
                                break;
                            }
                        }
                        while (true) {
                            String inputMsg = clientInput.readUTF();
                            if (inputMsg.equals("")) continue;
//                            if (inputMsg.equals("Authorization successful.")) {
//                                try {
//                            textArea.appendText(inputMsg);
//                                    Thread.sleep(2000);
//                                    textArea.clear();
//                                    continue;
//                                } catch (InterruptedException e) {
//                                    e.printStackTrace();
//                                }
//                            }
                            textArea.appendText(inputMsg);
                            textArea.appendText("\n");
                            historyLog.writeHistoryToLogFile(inputMsg);
                            if (inputMsg.equals("Echo : /end")) {
                                textArea.appendText("Connection has been closed by your command.");
                                textArea.appendText("\n");
                                clientSocket.close();
                                historyLog.getWriteToHistoryLog().close();
                                textArea.clear();
                                break;
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            historyLog.getReadHistory().close();
                            historyLog.getWriteToHistoryLog().close();
                            clientSocket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            clientInput.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            clientOutput.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        setAuthorized(false);
                    }
                }).start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
