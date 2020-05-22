import java.io.*;

public class HistoryLog {
    private FileWriter writeToHistoryLog;
    private FileReader readHistory;
    private BufferedReader historyBuffer;
    private static final String FILE_PATH = "src/Lesson2_&3_&4_jchat/client/src/main/resources/history.txt";

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

    public String[] getHistory() throws IOException {

        String[] historyArr = new String[100];

        try {
            readHistory = new FileReader(FILE_PATH);
            this.historyBuffer = new BufferedReader(readHistory);

            for (int i = 0; i < 100; i++) {
                historyArr[i] = this.historyBuffer.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return historyArr;
    }
}


