package client;

import java.io.*;
import java.net.Socket;

public class ClientWriter {
    public static void main(String[] args) {
        try (final Socket server = new Socket("localhost", 666)) {
            try (final BufferedReader in =
                         new BufferedReader(
                                 new InputStreamReader(
                                         new BufferedInputStream(
                                                 server.getInputStream(), 100)));
                 BufferedWriter consoleOut =
                         new BufferedWriter(
                                 new OutputStreamWriter(
                                         new BufferedOutputStream(
                                                 System.out)));
                 final BufferedWriter out =
                         new BufferedWriter(
                                 new OutputStreamWriter(
                                         new BufferedOutputStream(
                                                 server.getOutputStream(), 100)))) {
                out.write("/writer " + args[0]);
                while (true) {
                    String line;
                    while ((line = in.readLine()) != null) {
                        consoleOut.write(line);
                        consoleOut.flush();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
