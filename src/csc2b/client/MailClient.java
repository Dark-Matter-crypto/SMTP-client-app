package csc2b.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    private static String readResponse(BufferedReader br){
        String res = null;
        try {
            res = br.readLine();
            System.out.println(res);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            return res;
        }
    }

    private static void writeMessage(PrintWriter out, String message){
        out.println(message);
        out.flush();
    }

    public void sendMail(String fromAddress, String toAddress, String ccAddress, String subject, String message){
        DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        writeMessage(pw, "HELO Papercut");
        readResponse(br);
        writeMessage(pw, "MAIL FROM: <" + fromAddress + ">");
        readResponse(br);
        writeMessage(pw, "RCPT TO: <" + toAddress + ">");
        readResponse(br);
        writeMessage(pw, "RCPT TO: <" + ccAddress + ">");
        readResponse(br);
        writeMessage(pw, "DATA");
        readResponse(br);
        writeMessage(pw, "From: <" + fromAddress + ">");
        writeMessage(pw, "To: <" + toAddress + ">");
        writeMessage(pw, "Cc: <" + toAddress + ">");
        writeMessage(pw, "Date: " + date.format(now));
        writeMessage(pw, "Subject: " + subject);
        writeMessage(pw, " \r\n");
        writeMessage(pw, message);
        writeMessage(pw, "\r\n.\r\n");
        readResponse(br);
        writeMessage(pw, "QUIT");
        readResponse(br);
    }
}
