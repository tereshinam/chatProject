package client;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Client {
    public static void main(String[] args) {
        try (final Socket server = new Socket("localhost", 666)) {
            try (final BufferedReader in =
                         new BufferedReader(
                                 new InputStreamReader(
                                         new BufferedInputStream(
                                                 server.getInputStream(), 100)));
                 final BufferedWriter out =
                         new BufferedWriter(
                                 new OutputStreamWriter(
                                         new BufferedOutputStream(
                                                 server.getOutputStream(), 10)))) {

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
                        //String line1 = in.readLine();
                        //Stream<String> lines = in.lines();
                        //Object[] linesArray = lines.toArray();
                        while (!line.equals("")) {
                        //for(Object line: linesArray) {
                             System.out.println(line);
                        //}
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