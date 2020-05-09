import java.io.*;

public class HistoryLog {
    private FileWriter writeToHistoryLog;
    private FileReader readHistory;
    private BufferedReader historyBuffer;
    private static final String FILE_PATH = "src/Lesson2_and_lesson3/client/src/main/resources/history.txt";

    public FileWriter getWriteToHistoryLog() {
        return writeToHistoryLog;
    }

    public FileReader getReadHistory() {
        return readHistory;
    }

    public HistoryLog() {
        try {
            writeToHistoryLog = new FileWriter(FILE_PATH,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeHistoryToLogFile(String inputMsg) {
        try {
            this.writeToHistoryLog.write(inputMsg + "\n");
            this.writeToHistoryLog.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getHistory() throws IOException {
        try {

            Controller controller = new Controller();/* не придумал, как обойтись без создания данного объекта,
            по сути имеющего чисто "служебное назнаение, чтобы получить поле textArea" */

            readHistory = new FileReader(FILE_PATH);
            this.historyBuffer = new BufferedReader(readHistory);

            for (int i = 0; i < 100; i++) {
                if(this.historyBuffer != null){
                    controller.textArea.appendText(this.historyBuffer.readLine());
                } else {
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}


