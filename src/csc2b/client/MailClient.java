package csc2b.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MailClient {

    private static PrintWriter pw = null;
    private static BufferedReader br = null;

    public MailClient(){
        try {
            Socket smtp = new Socket("localhost", 25);
            System.out.println("Connected to server.");

            pw = new PrintWriter(smtp.getOutputStream(), true);
            br = new BufferedReader(new InputStreamReader(smtp.getInputStream()));
            readResponse(br);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String readResponse(BufferedReader br) throws IOException {
        return br.readLine();
    }

    private static void writeMessage(PrintWriter out, String message){
        out.println(message);
        out.flush();
    }

    public void sendMail(String fromAddress, String toAddress, String date, String subject, String message) throws IOException {
        writeMessage(pw, "HELO Papercut");
        readResponse(br);
        writeMessage(pw, "MAIL FROM: <" + fromAddress + ">");
        readResponse(br);
        writeMessage(pw, "RCPT TO: <" + toAddress + ">");
        readResponse(br);
        writeMessage(pw, "DATA");
        readResponse(br);
    }
}
