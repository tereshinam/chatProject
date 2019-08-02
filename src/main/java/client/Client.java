package client;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try (final Socket server = new Socket("localhost", 666)) {
            try (final BufferedReader in =
                         new BufferedReader(
                                 new InputStreamReader(
                                         new BufferedInputStream(
                                                 server.getInputStream())));
                 final BufferedWriter out =
                         new BufferedWriter(
                                 new OutputStreamWriter(
                                         new BufferedOutputStream(
                                                 server.getOutputStream())))) {

                try (BufferedReader console =
                             new BufferedReader(
                                     new InputStreamReader(
                                             new BufferedInputStream(
                                                     System.in)))) {

                    while (true) {
                        out.write(console.readLine());
                        out.newLine();
                        out.flush();
                        String line = in.readLine();
                        while (!line.equals("")) {
                             System.out.println(line);
                              line = in.readLine();
                        }
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