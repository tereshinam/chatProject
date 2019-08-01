package server;

import java.io.*;
import java.security.PublicKey;

/**
 * Created by Java_1 on 01.08.2019.
 */
public class HistoryLog {
    private File path;
    private BufferedWriter out;
    private BufferedReader in;
    public HistoryLog() throws IOException {
        path = new File("target", "history.txt");
        path.createNewFile();
    }
    public void log(String message){
        try {
            out = new BufferedWriter(
                    new OutputStreamWriter(
                            new BufferedOutputStream(
                                    new FileOutputStream(
                                            path), 450)));
            out.write(message);
            out.newLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getHistory(){
        try {
            in = new BufferedReader(
                new InputStreamReader(
                        new BufferedInputStream(
                                new FileInputStream(
                                        path))));
            StringBuffer history = new StringBuffer("");
            String line = in.readLine();
            while(line != null){
               history.append(line);
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

}