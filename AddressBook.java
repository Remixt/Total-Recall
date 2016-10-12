import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
public class AddressBook implements ActionListener, Runnable {

 static boolean programOpen = true;

 JFrame frame;

 JMenuBar menubar = new JMenuBar();
    JMenu menu = new JMenu();
 JMenuItem menuItem;

 Toolkit kit = Toolkit.getDefaultToolkit();
 Dimension screenSize = kit.getScreenSize();
 int screenHeight = screenSize.height;
 int screenWidth = screenSize.width;

 Image img = kit.getImage("images/icon.JPG");
 public static JTextField customMessage = new JTextField(40);

 public static String getMessageText() {
  return customMessage.getText();

 }

 String recallText = "Enter your message here";

 AddressBook() {

  frame = new JFrame("Recall Roster");
  frame.setSize(680, 200);

  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  frame.setLocation(screenWidth / 4, screenHeight / 4);
  frame.setIconImage(img);
  addWidgets();
  frame.setVisible(true);

 }

 public void addWidgets()

 {
  menubar.add(menu);

  menu = new JMenu("Options");
  menuItem = new JMenuItem("Add New Member");
  menu.add(menuItem);
  menuItem.addActionListener(this);

  menuItem = new JMenuItem("Delete Member");
  menu.add(menuItem);
  menuItem.addActionListener(this);

  menuItem = new JMenuItem("Search Members");
  menu.add(menuItem);
  menuItem.addActionListener(this);

  menuItem = new JMenuItem("Sort Members");
  menu.add(menuItem);
  menuItem.addActionListener(this);

  menuItem = new JMenuItem("View All Members");
  menu.add(menuItem);
  menuItem.addActionListener(this);

  menuItem = new JMenuItem("Backup Members");
  menu.add(menuItem);
  menuItem.addActionListener(this);

  menubar.add(menu);

  menu = new JMenu("Help");

  menuItem = new JMenuItem("Help Contents");
  menu.add(menuItem);
  menuItem.addActionListener(this);

  menuItem = new JMenuItem("About");
  menu.add(menuItem);
  menuItem.addActionListener(this);

  menubar.add(menu);

  frame.setJMenuBar(menubar);

  JPanel topPanel = new JPanel();
  JPanel bottomPanel = new JPanel();
  JPanel centerPanel = new JPanel();

  // Add Buttons To Bottom Panel
  JButton AddNew = new JButton("Add New Member");
  JButton DeleteMember = new JButton("Delete Member");
  JButton SearchMembers = new JButton("Search Roster");
  JButton SortMembers = new JButton("Sort Roster");
  JButton ViewMemberList = new JButton("View All Members");
  JButton doRecall = new JButton("Initiate Recall");

  JLabel label = new JLabel(
    "<HTML><FONT FACE = ARIAL SIZE = 2><B>Use The options below and In The Menu To Manage Members");

  customMessage.setText(recallText);

  // Add Action Listeners
  AddNew.addActionListener(this);
  DeleteMember.addActionListener(this);
  SearchMembers.addActionListener(this);
  SortMembers.addActionListener(this);
  ViewMemberList.addActionListener(this);
  doRecall.addActionListener(this);

  topPanel.add(label);

  centerPanel.add(customMessage);
  centerPanel.add(doRecall);

  bottomPanel.add(AddNew);
  bottomPanel.add(DeleteMember);
  bottomPanel.add(SearchMembers);
  bottomPanel.add(SortMembers);
  bottomPanel.add(ViewMemberList);

  frame.getContentPane().add(topPanel, BorderLayout.NORTH);
  frame.getContentPane().add(centerPanel, BorderLayout.CENTER);
  frame.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
  frame.setResizable(false);

 }

 public static void main(String args[]) throws Exception {
  new AddressBook();

  while (programOpen = true) {

  }
 }

 static OperationHandler oh = new OperationHandler();

 public void actionPerformed(ActionEvent ae) {
  String path = System.getProperty("user.dir");

  if (ae.getActionCommand() == "Add New Member") {
   oh.AddNew();

  }

  else if (ae.getActionCommand() == "Search Roster") {
   oh.SearchMembers();

  }

  else if (ae.getActionCommand() == "Sort Roster") {
   oh.SortMembers();

  }

  else if (ae.getActionCommand() == "Delete Member") {
   oh.DeleteMember();

  }

  else if (ae.getActionCommand() == "View All Members") {

   oh.viewAllMembers();

  }

  else if (ae.getActionCommand() == "Initiate Recall") {

   SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
    @Override
    protected Void doInBackground() throws Exception {
     oh.doRecall();
     return null;
    }

    @Override
    protected void done() {
     try {
      get();
     } catch (InterruptedException | ExecutionException e) {

      e.printStackTrace();
     }
    }
   };
   worker.execute();
  } else if (ae.getActionCommand() == "Help Contents") {
   try {
    oh.showHelp();
   } catch (IOException e) {
   }

  } else if (ae.getActionCommand() == "Backup Members") {
   JFileChooser chooser = new JFileChooser();
   chooser.setCurrentDirectory(new File("."));
   chooser.setMultiSelectionEnabled(false);

   chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
   chooser.showSaveDialog(frame);
   FileOutputStream bfout = null;
   FileInputStream bfin = null;
   String filename = null;

   int p;

   try {
    filename = chooser.getSelectedFile().getPath();
   } catch (Exception e) {
   }

   try {
    bfout = new FileOutputStream(
      path + "\\data.dat");
   } catch (Exception e) {

   }
   try {
    bfin = new FileInputStream(
      path + "\\data.dat");
   } catch (Exception e) {

   }

   try {
    do {
     p = bfin.read();
     if (p != -1)
      bfout.write(p);
    } while (p != -1);
   } catch (Exception e) {

   }

  }

 }

 @Override
 public void run() {
  // TODO Auto-generated method stub

 }

}

//Member Class
class Member implements Serializable {
 /**
     * Class for storing contact information of each member in the data.dat file.
  * 
  */
 private static final long serialVersionUID = 1L;
 private String FName;
 private String LName;
 private String Nname;
 private String EMail;
 private String Address;
 private String PhoneNo;
 private String response;
 private String Bday;

 public void setDetails(String fname, String lname, String nname,
   String email, String address, String phone, String web, String bday) {
  FName = fname;
  LName = lname;
  Nname = nname;
  EMail = email;
  Address = address;
  PhoneNo = phone;
  response = web;
  Bday = bday;
 }

 public String getFName() {
  return FName;
 }

 public String getLName() {
  return LName;
 }

 public String getNname() {
  return Nname;
 }

 public String getEMail() {
  return EMail;
 }

 public String getAddress() {
  return Address;
 }

 public String getPhoneNo() {
  return PhoneNo;
 }

 public String getResponse() {
  return response;
 }

 public void setResponse(String message) {
  response = message;

 }

 public String getBday() {
  return Bday;
 }

}

//Member Class

//Operation Handler
class OperationHandler implements ListSelectionListener, ActionListener,
  Runnable {
/*
* This is the meat behind the Recall operation for Total Recall. It uses fowiz calls to send mass texts from the desktop application to an "unlimited" amount of users at one time, and handle their replies by using an email forwarding protocol.
* see www.fowiz.com for more details.
* */
 public String getMessage() {
  return AddressBook.getMessageText();

 }

 JTable abtable = new JTable();

 JFrame newFrame;
 JTextField txtFirstName;
 JTextField txtLastName;
 JTextField txtNickname;
 JTextField txtEMail;
 JTextField txtAddress;
 JTextField txtPhoneNo;
 JTextField txtresponse;
 JTextField txtBDay;
 JTextArea messageField = new JTextArea("Enter Message here");

 JButton BttnSaveAdded;

 Vector v = new Vector(10, 3);
 int i = 0, k = 0;

 Toolkit kit = Toolkit.getDefaultToolkit();
 Dimension screenSize = kit.getScreenSize();
 int screenHeight = screenSize.height;
 int screenWidth = screenSize.width;

 Image img = kit.getImage("images/icon.JPG");

 FileInputStream fis;
 ObjectInputStream ois;

 JList<?> list;
 DefaultListModel listModel;
 ListSelectionModel listSelectionModel;

 JRadioButton byfname, bylname;

 Thread t;

 JTable searchTable;

 JTextField txtSearch;

 String columnNames[] = { "First Name", "Last Name", "Nickname",
   "E Mail Address", "Address", "Phone No.", "response", "B'day" };

 Object data[][] = new Object[5][8];
 private MailReader mR;
 private MailReader mR2;

 OperationHandler() {
        String path = System.getProperty("user.dir");
  try {
   fis = new FileInputStream(
     path + "\\data.dat");
   ois = new ObjectInputStream(fis);
   v = (Vector) ois.readObject();
   ois.close();
  }

  catch (Exception e) {

  }

 }

 public void run() {
        String path = System.getProperty("user.dir");
  try {
   FileOutputStream fos = new FileOutputStream(
     path + "\\data.dat");
   ObjectOutputStream oos = new ObjectOutputStream(fos);
   oos.writeObject(v);
   oos.flush();
   oos.close();

  } catch (Exception e) {
   JOptionPane.showMessageDialog(newFrame,
     "Error Opening Data File: Could Not Save Contents.",
     "Error Opening Data File", JOptionPane.INFORMATION_MESSAGE);
  }

 }

 public void AddNew() {
  newFrame = new JFrame("Add New");
  newFrame.setSize(220, 250);
  newFrame.setResizable(false);
  newFrame.setIconImage(img);

  JLabel lblFirstName = new JLabel("First Name: ");
  JLabel lblLastName = new JLabel("Last Name: ");

  JLabel lblPhoneNo = new JLabel("Phone No: ");


  txtFirstName = new JTextField(10);
  txtLastName = new JTextField(10);
  txtNickname = new JTextField(10);
  txtEMail = new JTextField(10);
  txtAddress = new JTextField(10);
  txtPhoneNo = new JTextField(10);
  txtresponse = new JTextField(10);
  txtBDay = new JTextField(10);

  JButton BttnAdd = new JButton("Add New!");
  BttnSaveAdded = new JButton("Save Added!");

  BttnAdd.addActionListener(this);
  BttnSaveAdded.addActionListener(this);
  BttnSaveAdded.setEnabled(false);

  JPanel centerPane = new JPanel();
  JPanel bottomPane = new JPanel();

  centerPane.add(lblFirstName);
  centerPane.add(txtFirstName);
  centerPane.add(lblLastName);
  centerPane.add(txtLastName);

  centerPane.add(lblPhoneNo);
  centerPane.add(txtPhoneNo);

  bottomPane.add(BttnAdd);
  bottomPane.add(BttnSaveAdded);

  centerPane.setLayout(new GridLayout(0, 1));

  newFrame.getContentPane().add(centerPane, BorderLayout.CENTER);

  newFrame.getContentPane().add(bottomPane, BorderLayout.SOUTH);
  newFrame.setLocation(screenWidth / 4, screenHeight / 4);
  newFrame.setVisible(true);

 }

 public void SearchMembers() {
  newFrame = new JFrame("Search Members");
  newFrame.setSize(700, 220);
  newFrame.setLocation(screenWidth / 4, screenHeight / 4);
  newFrame.setIconImage(img);
  newFrame.setResizable(false);

  JPanel topPane = new JPanel();
  JLabel label1 = new JLabel(
    "Search Members by First Name or Last Name or Both Spaced Via a Single Space:");
  topPane.add(label1);

  JPanel centerPane = new JPanel();
  JLabel label2 = new JLabel("Search String:");
  txtSearch = new JTextField(20);
  JButton bttnSearch = new JButton("Search!");
  bttnSearch.addActionListener(this);
  JButton bttnCancel = new JButton("Cancel");
  bttnCancel.addActionListener(this);
  centerPane.add(label2);
  centerPane.add(txtSearch);
  centerPane.add(bttnSearch);
  centerPane.add(bttnCancel);

  searchTable = new JTable(data, columnNames);
  JScrollPane scrollPane = new JScrollPane(searchTable);
  searchTable.setPreferredScrollableViewportSize(new Dimension(500, 90));

  newFrame.getContentPane().add(scrollPane, BorderLayout.SOUTH);

  newFrame.getContentPane().add(topPane, BorderLayout.NORTH);
  newFrame.getContentPane().add(centerPane, BorderLayout.CENTER);
  newFrame.setVisible(true);
 }

 public void SortMembers() {
  newFrame = new JFrame("Sort Members");
  newFrame.setSize(250, 160);
  newFrame.setLocation(screenWidth / 4, screenHeight / 4);
  newFrame.setIconImage(img);
  newFrame.setResizable(false);

  byfname = new JRadioButton("By First Name");
  byfname.setActionCommand("By First Name");
  byfname.setSelected(true);

  bylname = new JRadioButton("By Last Name");
  bylname.setActionCommand("By Last Name");

  ButtonGroup group = new ButtonGroup();
  group.add(byfname);
  group.add(bylname);

  JPanel topPane = new JPanel();
  JLabel label = new JLabel("Sort Members By:");
  topPane.add(label);

  JPanel pane = new JPanel(new GridLayout(0, 1));
  pane.add(byfname);
  pane.add(bylname);

  JPanel bottomPane = new JPanel();
  JButton sortBttn = new JButton("Sort Members");
  JButton cancelBttn = new JButton("Cancel");
  bottomPane.add(sortBttn);
  bottomPane.add(cancelBttn);
  sortBttn.addActionListener(this);
  cancelBttn.addActionListener(this);

  newFrame.getContentPane().add(topPane, BorderLayout.NORTH);
  newFrame.getContentPane().add(pane, BorderLayout.CENTER);
  newFrame.getContentPane().add(bottomPane, BorderLayout.SOUTH);

  newFrame.setVisible(true);

 }

 public void DeleteMember() {
  String fname, lname;
  newFrame = new JFrame("Delete Member");
  newFrame.setSize(300, 300);
  newFrame.setLocation(screenWidth / 4, screenHeight / 4);
  newFrame.setIconImage(img);

  JPanel centerPane = new JPanel();
  listModel = new DefaultListModel();

  Member Member = new Member();

  for (int l = 0; l < v.size(); l++) {
   Member = (Member) v.elementAt(l);

   fname = Member.getFName();
   lname = Member.getLName();
   listModel.addElement(fname + " " + lname);

  }

  list = new JList(listModel);

  list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
  listSelectionModel = list.getSelectionModel();
  listSelectionModel.addListSelectionListener(this);

  JScrollPane listScrollPane = new JScrollPane(list);

  JPanel topPane = new JPanel();
  JLabel label = new JLabel("Current Members:");
  topPane.add(label);

  JPanel bottomPane = new JPanel();

  JButton bttnDelete = new JButton("Delete Selected");
  bottomPane.add(bttnDelete);
  bttnDelete.addActionListener(this);

  JButton bttnCancel = new JButton("Cancel");
  bottomPane.add(bttnCancel);
  bttnCancel.addActionListener(this);

  newFrame.getContentPane().add(topPane, BorderLayout.NORTH);
  newFrame.getContentPane().add(listScrollPane, BorderLayout.CENTER);
  newFrame.getContentPane().add(bottomPane, BorderLayout.SOUTH);

  newFrame.setVisible(true);

 }

 public void viewAllMembers() {

  newFrame = new JFrame("All Members In The Roster");
  newFrame.setSize(600, 300);
  newFrame.setIconImage(img);

  Member con = new Member();

  String columnNames[] = { "First Name", "Last Name", "Phone No." };

  Object[][] data = new Object[v.size()][4];

  for (int j = 0; j < v.size(); j++) {

   con = (Member) v.elementAt(k);

   data[j][0] = con.getFName();
   data[j][1] = con.getLName();
   data[j][2] = con.getPhoneNo();
   data[j][3] = con.getResponse();

   k++;

  }
  k = 0;

  abtable = new JTable(data, columnNames);

  JScrollPane scrollPane = new JScrollPane(abtable);
  abtable.setPreferredScrollableViewportSize(new Dimension(500, 370));

  JPanel pane = new JPanel();
  JLabel label = new JLabel("Members Currently In The Recall Roster");
  pane.add(label);

  newFrame.getContentPane().add(pane, BorderLayout.SOUTH);
  newFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);

  newFrame.setLocation(screenWidth / 4, screenHeight / 4);
  newFrame.setVisible(true);

 }

 public void doRecall() throws Exception {

  newFrame = new JFrame("Recall Initiated Awaiting Replies");
  newFrame.setSize(600, 300);
  newFrame.setIconImage(img);

  Member con = new Member();

  String columnNames[] = { "First Name", "Last Name", "Phone No.",
    "Response" };

  Object[][] data = new Object[v.size()][4];

  for (int j = 0; j < v.size(); j++) {

   con = (Member) v.elementAt(k);

   data[j][0] = con.getFName();
   data[j][1] = con.getLName();
   data[j][2] = con.getPhoneNo();
   data[j][3] = "No reply yet";

   k++;

  }
  k = 0;
  DefaultTableModel model = new DefaultTableModel(data, columnNames);
  abtable = new JTable(model);

  JScrollPane scrollPane = new JScrollPane(abtable);
  abtable.setPreferredScrollableViewportSize(new Dimension(500, 370));

  JPanel pane = new JPanel();
  JLabel label = new JLabel("Members Currently In The Recall Roster");
  pane.add(label);

  newFrame.getContentPane().add(pane, BorderLayout.SOUTH);
  newFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);

  newFrame.setLocation(screenWidth / 4, screenHeight / 4);
  newFrame.setVisible(true);

  String customMessage = getMessage();

  k = 0;

  
  for (int j = 0; j < v.size(); j++) {

   con = (Member) v.elementAt(k);

   try {
    String toPhoneNumber = con.getPhoneNo();

    customMessage = customMessage.replaceAll("\\s+", "+");
    System.out.println(customMessage);
                // need to change this information in order for the application to function properly.
    String requestUrl = ("http://cloud.fowiz.com/api/message_http_api.php?username=Remixt&phonenumber=+"
      + toPhoneNumber + "&message=" + customMessage + "&passcode=EQUINE2449");

    URL url = new URL(requestUrl);

    HttpURLConnection uc = (HttpURLConnection) url.openConnection();
    System.out.println(uc.getResponseMessage());
    String reply = uc.getResponseMessage();

    if (reply.equalsIgnoreCase("ok")) {

    }

   } catch (Exception ex) {
    System.out.println(ex.getMessage());

   }
   k++;

  }
  k = 0;
  
  Map<String, Integer> replies = new HashMap<String, Integer>();
  for(int i = 0; i < model.getRowCount(); i++){
      replies.put((String)model.getValueAt(i,3), i); //Assuming 3 is phone number column
  }

  // need to change this information for the app to work correctly.
  String host = "pop.gmail.com";
  String mailStoreType = "pop3";
  String username = "totalrecallaf@gmail.com";
  String password = "equine2449";
  String[] phoneToCheck = new String[v.size()];
  int phoneCheck = phoneToCheck.length;
  for(int i1 = 0; i1 <model.getRowCount(); i1++) {
  while(model.getValueAt(i1,3).equals("No reply yet")){
      MailReader.check(host, mailStoreType, username, password); 
      if(MailReader.checkHowMany()>0) {
      for(int i = 0; i<v.size(); i++) {
       con = (Member) v.elementAt(i);
       phoneToCheck[i] = con.getPhoneNo();
      
      if (MailReader.searchForPhone(con.getPhoneNo())==true) {
          
          System.out.println("IT WORKED");
          
          
          model.setValueAt(MailReader.getReply(con.getPhoneNo()), i, 3);
          
          model.fireTableDataChanged();
          replies.remove(con.getPhoneNo());
          
          phoneCheck --;
      }
      }
      
  }
  }
 }
 }
 public void checkMail() throws Exception {
 }
 public void showHelp() throws IOException {

  String title = "Help Contents";
  String data = "";
  FileInputStream fishelp = null;
  int i;

  newFrame = new JFrame(title);
  newFrame.setSize(401, 400);
  newFrame.setResizable(false);
  newFrame.setLocation(screenWidth / 4, screenHeight / 4);
  newFrame.setIconImage(img);

  JTextArea textArea = new JTextArea(5, 20);
  textArea.setLineWrap(true);
  textArea.setEditable(false);

  try {
            String path = System.getProperty("user.dir");
   fishelp = new FileInputStream(
     path + "\\data.dat\\help.txt");
  } catch (Exception e) {
   JOptionPane.showMessageDialog(newFrame, "Help File Not Found.",
     "Help File Not Found", JOptionPane.INFORMATION_MESSAGE);
  }

  do {
   i = fishelp.read();
   if (i != 1)
    data = data + (char) i;
  } while (i != -1);

  fishelp.close();

  textArea.setText(data);

  JPanel bottomPane = new JPanel();
  JButton button = new JButton("Ok");
  bottomPane.add(button);
  button.addActionListener(this);

  JPanel topPane = new JPanel();
  JLabel label = new JLabel(title);
  topPane.add(label);

  JScrollPane scrollPane = new JScrollPane(textArea);

  newFrame.getContentPane().add(topPane, BorderLayout.NORTH);
  newFrame.getContentPane().add(scrollPane);

  newFrame.getContentPane().add(bottomPane, BorderLayout.SOUTH);

  newFrame.setVisible(true);

 }

 @SuppressWarnings("unchecked")
 public void actionPerformed(ActionEvent ae) {

  if (ae.getActionCommand() == "Add New!") {

   if (txtFirstName.getText().equals("")
     && txtLastName.getText().equals("")
     && txtNickname.getText().equals("")
     && txtEMail.getText().equals("")
     && txtAddress.getText().equals("")
     && txtPhoneNo.getText().equals("")
     && txtresponse.getText().equals("")
     && txtBDay.getText().equals("")) {

    JOptionPane
      .showMessageDialog(
        newFrame,
        "Entries Empty! Fill in the required entries to save Member.",
        "Entries Empty",
        JOptionPane.INFORMATION_MESSAGE);

   }

   else {
    Member Member = new Member();

    Member.setDetails(txtFirstName.getText(),
      txtLastName.getText(), txtNickname.getText(),
      txtEMail.getText(), txtAddress.getText(),
      txtPhoneNo.getText(), txtresponse.getText(),
      txtBDay.getText());
    v.addElement(Member);
    txtFirstName.setText("");
    txtLastName.setText("");
    txtNickname.setText("");
    txtEMail.setText("");
    txtAddress.setText("");
    txtPhoneNo.setText("");
    txtresponse.setText("");
    txtBDay.setText("");

    if (BttnSaveAdded.isEnabled() == false)
     BttnSaveAdded.setEnabled(true);
   }

  } else if (ae.getActionCommand() == "Save Added!") {

   saveVector();
   newFrame.setVisible(false);

  } else if (ae.getActionCommand() == "Ok") {
   newFrame.setVisible(false);

  } else if (ae.getActionCommand() == "Delete Selected") {

   int index;
   try {

    index = list.getSelectedIndex();

    if (index == -1) {

     JOptionPane.showMessageDialog(newFrame,
       "Select a Member first to delete it.",
       "Select a Member First",
       JOptionPane.INFORMATION_MESSAGE);
    }

    else {

     int n = JOptionPane
       .showConfirmDialog(
         newFrame,
         "Are you sure you want to delete the selected Member?",
         "Are you sure?", JOptionPane.YES_NO_OPTION);

     if (n == JOptionPane.YES_OPTION) {
      listModel.remove(index);
      v.removeElementAt(index);
      saveVector();
      newFrame.setVisible(true);

     } else if (n == JOptionPane.NO_OPTION) {

     }

    }

   } catch (Exception e) {

   }

  } else if (ae.getActionCommand() == "Cancel") {

   newFrame.setVisible(false);
  } else if (ae.getActionCommand() == "Search!") {
   String SearchStr;
   SearchStr = txtSearch.getText();
   boolean flag = false;
   Member con = new Member();
   int c = 0;

   for (int t = 0; t < 5; t++) {
    data[t][0] = "";
    data[t][1] = "";
    data[t][2] = "";
    data[t][3] = "";
    data[t][4] = "";
    data[t][5] = "";
    data[t][6] = "";
    data[t][7] = "";
   }

   for (int t = 0; t < v.size(); t++) {

    con = (Member) v.elementAt(t);

    if (SearchStr.equalsIgnoreCase(con.getFName())
      || SearchStr.equalsIgnoreCase(con.getLName())
      || SearchStr.equalsIgnoreCase(con.getFName() + " "
        + con.getLName())) {
     flag = true;

     data[c][0] = con.getFName();
     data[c][1] = con.getLName();
     data[c][2] = con.getNname();
     data[c][3] = con.getEMail();
     data[c][4] = con.getAddress();
     data[c][5] = con.getPhoneNo();
     data[c][6] = con.getResponse();
     data[c][7] = con.getBday();
     searchTable = new JTable(data, columnNames);
     newFrame.setSize(700, 221);
     newFrame.setSize(700, 220);

     if (c < 5)
      c++;
    }

   }

   if (flag) {
    JOptionPane.showMessageDialog(newFrame, "Member Found!",
      "Search Result!", JOptionPane.INFORMATION_MESSAGE);
   }

   else {
    JOptionPane.showMessageDialog(newFrame, "NoSuch Member Found!",
      "Search Result!", JOptionPane.INFORMATION_MESSAGE);
   }

  } else if (ae.getActionCommand() == "Sort Members") {

   if (byfname.isSelected()) {
    Member Member1 = new Member();
    Member Member2 = new Member();
    Member temp = new Member();
    int l, m;

    for (l = 0; l < v.size() - 1; l++) {
     for (m = l + 1; m < v.size(); m++) {
      Member1 = (Member) v.elementAt(l);
      Member2 = (Member) v.elementAt(m);

      if (Member1.getFName().compareTo(Member2.getFName()) > 0) {
       temp = (Member) v.elementAt(m);

       v.setElementAt(v.elementAt(l), m);
       v.setElementAt(temp, l);
      }

     }
    }

    saveVector();
   } else {

    Member Member1 = new Member();
    Member Member2 = new Member();
    Member temp = new Member();
    int l, m;

    for (l = 0; l < v.size() - 1; l++) {
     for (m = l + 1; m < v.size(); m++) {
      Member1 = (Member) v.elementAt(l);
      Member2 = (Member) v.elementAt(m);

      if (Member1.getLName().compareTo(Member2.getLName()) > 0) {
       temp = (Member) v.elementAt(m);

       v.setElementAt(v.elementAt(l), m);
       v.setElementAt(temp, l);
      }

     }
    }

    saveVector();
   }

   newFrame.setVisible(false);
  }

 }

 public void saveVector() {
  t = new Thread(this, "Save Vector Thread");
  t.start();

 }

 public void valueChanged(ListSelectionEvent lse) {

 }

}
//Operation Handler
