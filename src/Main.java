import csc2b.client.MailClient;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        MailClient smtpClient = new MailClient();
        String toAddress = "recipient@csc2b.uj.ac.za";
        String fromAddress = "sendername@csc2b.uj.ac.za";
        String subject = "Test sub";
        String message = "Hello World.";

        smtpClient.sendMail(fromAddress, toAddress, subject, message);
    }
}
