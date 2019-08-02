package client;

import java.io.*;
import java.net.Socket;

public class ClientReader {
    public static void main(String[] args) {
        try (final Socket server = new Socket("localhost", 666)) {
            try (final BufferedWriter out =
                         new BufferedWriter(
                                 new OutputStreamWriter(
                                         new BufferedOutputStream(
                                                 server.getOutputStream(), 100)));
                 BufferedReader consoleIn =
                         new BufferedReader(
                                 new InputStreamReader(
                                         new BufferedInputStream(
                                                 System.in)))) {
                out.write("/reader " + args[0]);
                out.newLine();
                out.flush();
                while (true) {
                    String line = consoleIn.readLine();
                    out.write(line);
                    out.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
