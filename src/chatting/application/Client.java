
package chatting.application;


import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.*;
import java.text.*;
import java.io.*;

public class Client  implements ActionListener{
    static JFrame f=new JFrame();
    JTextField text;
    static JPanel p2;
    static Box vertical=Box.createVerticalBox();//align messages in vertical manner
    static DataOutputStream dout;
    Client(){
        f.setLayout(null);
        JPanel p1=new JPanel();//header panel
         p1.setBackground(new Color(7,94,84));
         p1.setBounds(0,0,450,70);//use to set the location of panel
         p1.setLayout(null);
         f.add(p1);//add panel on frame
         
//         this is for back-arrow image         
         ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
         Image i2=i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
         ImageIcon i3=new ImageIcon(i2);
         JLabel back=new JLabel(i3);
         back.setBounds(5,20,25,25);
         p1.add(back);
         
         back.addMouseListener(new MouseAdapter(){
        public void mouseClicked(MouseEvent e){
            System.exit(0);
        }
    });
         //         this is for profile image  
         ImageIcon i4=new ImageIcon(ClassLoader.getSystemResource("icons/2.png"));
         Image i5=i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
         ImageIcon i6=new ImageIcon(i5);
         JLabel profile=new JLabel(i6);
         profile.setBounds(40,10,50,50);
         p1.add(profile);
         
         
//         this is for video icon image
         ImageIcon i7=new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
         Image i8=i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
         ImageIcon i9=new ImageIcon(i8);
         JLabel video=new JLabel(i9);
         video.setBounds(300,20,30,30);
         p1.add(video);
         
//         this is for phone icon image
         
         ImageIcon i10=new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
         Image i11=i10.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
         ImageIcon i12=new ImageIcon(i11);
         JLabel phone=new JLabel(i12);
         phone.setBounds(360,20,35,30);
         p1.add(phone);
         
         
//         this is for more-button image

 ImageIcon i13=new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
         Image i14=i13.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
         ImageIcon i15=new ImageIcon(i14);
         JLabel more=new JLabel(i15);
         more.setBounds(410,20,10,25);
         p1.add(more);
         
//         setting the name 
         JLabel name=new JLabel("Bunty");
         name.setBounds(110,15,100,18);
         name.setForeground(Color.WHITE);
         name.setFont(new Font("SAM_SERIF",Font.BOLD,18));
         p1.add(name);
         
         //         setting person status (active or not)
         JLabel status=new JLabel("Active Now");
         status.setBounds(110,35,100,18);
         status.setForeground(Color.WHITE);
         status.setFont(new Font("SAM_SERIF",Font.BOLD,14));
         p1.add(status);
         
         
         p2=new JPanel();//body(chat-area) panel
         p2.setBounds(5,75,440,570);
         f.add(p2);
         
//         At footor
         text=new JTextField();
         text.setBounds(5,655,310,40);
         text.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
         f.add(text);
         
         JButton btn=new JButton("SEND");
         btn.setBounds(320,655,123,40);
         btn.setBackground(new Color(7,94,84));
         btn.setForeground(Color.WHITE);
         btn.addActionListener(this);//this refer to actionPerformed method
         btn.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
         f.add(btn);
         
         
         
         
         f.setSize(450,700);//use to set the size of frame
         f.setLocation(800,50);//use to set the location of frame
         f.setUndecorated(true);//used to remove the top header of frame
         f.getContentPane().setBackground(Color.WHITE);
         f.setVisible(true);//use to show the frame
    }
    
    public void actionPerformed(ActionEvent ae){
        try{
          String value=text.getText();
          
          JPanel c1=formatLabel(value);

                  
          p2.setLayout(new BorderLayout());
//          algin our msg at right side
          JPanel right=new JPanel(new BorderLayout());//while sending msg, our msg should be at right side
          right.add(c1,BorderLayout.LINE_END);
//          placed all our msg in vertical box
          vertical.add(right);
          vertical.add(Box.createVerticalStrut(15));//gap between the msg
          
          p2.add(vertical,BorderLayout.PAGE_START);
          
          dout.writeUTF(value);
          
          text.setText("");
//          all these functions help to repaint or recall the server function again
          f.repaint();
          f.invalidate();
          f.validate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static JPanel formatLabel(String value){
        JPanel panel=new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        
        JLabel output=new JLabel("<html><p style=\"width: 150px\">"+value+"</p></html>");
        output.setFont(new Font("Tahoma",Font.PLAIN,16));
        output.setBackground(new Color(37,211,102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15,15,15,50));//use or padding
        panel.add(output); 
        
        Calendar cal=Calendar.getInstance();//get the current date and time
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
        
        JLabel time=new JLabel();
        time.setText(sdf.format(cal.getTime()));
        
        panel.add(time);
        
        return panel;
    }
    
    public static void main(String[] args){
        new Client();
        
        try{
            //port number of server and localhost of client on which it will run
            Socket s=new Socket("127.0.0.1",6000);
             //incoming msg and input details from s
                DataInputStream din=new DataInputStream(s.getInputStream());
                //outgoing msg and output details from s
                dout=new DataOutputStream(s.getOutputStream());
                
                while(true){
                    p2.setLayout(new BorderLayout());
                    String msg=din.readUTF();//read msg
                    JPanel c2=formatLabel(msg);
                    
                    JPanel left=new JPanel(new BorderLayout());
                    left.add(c2,BorderLayout.LINE_START);
                    vertical.add(left);
                    vertical.add(Box.createVerticalStrut(15));
                    
                    p2.add(vertical,BorderLayout.PAGE_START);
                    
                    f.validate();
                }
                
                
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

