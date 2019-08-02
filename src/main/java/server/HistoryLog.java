package server;

import java.io.*;
import java.security.PublicKey;

/**
 * Created by Java_1 on 01.08.2019.
 */
public class HistoryLog {
    private File path;
    private FileWriter fileWriter;
    private FileReader fileReader;
    private BufferedWriter out;
    private BufferedReader in;
    public HistoryLog() throws IOException {
        path = new File("history.txt");
        path.createNewFile();
        //fileWriter = new FileWriter("history.txt", true );
        //fileReader = new FileReader("history.txt");
        out = new BufferedWriter(
                new OutputStreamWriter(
                        new BufferedOutputStream(
                                new FileOutputStream(
                                        path, true), 450)));
        in = new BufferedReader(
                new InputStreamReader(
                        new BufferedInputStream(
                                new FileInputStream(
                                        path))));
    }
    public void log(String message){
        try {
            out.write(message);
            out.newLine();
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getHistory(){
        try {
            StringBuffer history = new StringBuffer("");
            String line = in.readLine();
            while(line != null){
               history.append(line).append("\n");
               line = in.readLine();
            }
            return history.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public void closeFile() throws IOException {
        out.close();
        in.close();

    }

    public File getPath(){
        return path;
    }

}