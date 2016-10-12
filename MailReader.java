import java.util.ArrayList;
import java.util.Properties;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;


/*
 Class that will check a specified email box for replies to the total recall system.
 */
public class MailReader {
 static int howManyMessages = 0;
 static ArrayList<String> myMessages = new ArrayList<String>();
 static String reply = null;
 
 public static boolean searchForPhone(String phone){
 CharSequence phoneN = phone;
 for(int i=0;i<myMessages.size();i++){
        return myMessages.get(i).contains(phone);
        }
 return false;
}
 
 public static String getReply(String phone){
  
  for(int i=0;i<myMessages.size();i++){
         if(myMessages.get(i).contains(phone)){
          reply = myMessages.get(i).toString();
          reply = reply.substring(26);
          System.out.println(reply);
          return reply;
         }
         else{ 
          return "no reply yet!";
         }
         }
  return "no reply yet!";
  
  
 }
 
 public static int checkHowMany() {
  return howManyMessages;
 }


 

   public static void check(String host, String storeType, String user,
      String password) 
   {
      try {

      // create properties field
      Properties properties = new Properties();

      properties.put("mail.pop3s.host", host);
      properties.put("mail.pop3s.port", "995");
      properties.put("mail.pop3s.starttls.enable", "true");

      // Setup authentication, get session
      Session emailSession = Session.getInstance(properties,
         new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication(
                  "totalrecallaf@gmail.com", "equine2449");
            }
         });
      // emailSession.setDebug(true);

      // create the POP3 store object and connect with the pop server
      Store store = emailSession.getStore("pop3s");

      store.connect();

      // create the folder object and open it
      Folder emailFolder = store.getFolder("INBOX");
      emailFolder.open(Folder.READ_WRITE);

      // retrieve the messages from the folder in an array and print it
      Message[] messages = emailFolder.getMessages();
      System.out.println("messages.length---" + messages.length);
      howManyMessages  = messages.length;
      for (int i = 0, n = messages.length; i < n; i++) {
         Message message = messages[i];

        
         myMessages.add(message.getContent().toString());
         System.err.println(myMessages.get(i));
      
         
      }

      // close the store and folder objects
      emailFolder.close(false);
      store.close();

      } catch (NoSuchProviderException e) {
         e.printStackTrace();
      } catch (MessagingException e) {
         e.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public static void main(String[] args) {

      String host = "pop.gmail.com";// change accordingly
      String mailStoreType = "pop3";
      String username = "totalrecallaf@gmail.com";// change accordingly
      String password = "equine2449";// change accordingly

      check(host, mailStoreType, username, password);

   }

}
