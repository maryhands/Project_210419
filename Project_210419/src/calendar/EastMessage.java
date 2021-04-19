package calendar;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import dao.CreateAccountVO;

public class EastMessage extends JFrame {
	Main80East m80e = null;
	CreateAccountVO caVO = null;
	JLabel jlb_sen = new JLabel("보내는 사람");
	JLabel jlb_rec = new JLabel("받는 사람");
	JLabel jlb_title = new JLabel("제목");
	JTextField jtf_sen_mail = new JTextField(20);
	JTextField jtf_rec_mail = new JTextField(20);
	JTextField jtf_title = new JTextField(20);
	JTextArea jta = new JTextArea();
	JScrollPane jsp = new JScrollPane(jta);
	JButton send = new JButton("보내기");

	Font font = new Font("맑은 고딕", Font.BOLD, 15);
	
	String imgPath = "project_210404\\src\\calendar\\";
	ImageIcon imgIcon = new ImageIcon(imgPath + "blossom.jpg");
	
	//보내는 사람
	String smtpServer = "smtp.naver.com";
	final String sendId = "";//보내는 사람 네이버 아이디 입력
	final String sendPass = "";//보내는 사람 네이버 비밀번호 입력
	String sendEmailAddress = "";//메일입력 ex)jks5117@naver.com



	class BackGroundPanel extends JPanel { // 배경화면을 위해서 내부에 클래스 지정
		public void paintComponent(Graphics g) {
			g.drawImage(imgIcon.getImage(), 0, 0, null);
			setOpaque(false);
			super.paintComponent(g);

		}
	}
	
	public EastMessage() {
		initDisplay();
	}
	
	public EastMessage(Main80East m80e) {
		this.m80e=m80e;
	}
	public void initDisplay() {

		this.setContentPane(new BackGroundPanel());
		jta.setLineWrap(true);
		jsp.setBounds(70, 150, 440, 330);
		jlb_sen.setBounds(25, 20, 100, 50);
		jlb_sen.setFont(font);
		jlb_rec.setBounds(25, 50, 100, 50);
		jlb_rec.setFont(font);
		jlb_title.setBounds(25, 80, 100, 50);
		jlb_title.setFont(font);
		jtf_sen_mail.setBounds(120, 38, 300, 20);
		jtf_rec_mail.setBounds(120, 68, 300, 20);
		jtf_title.setBounds(120, 98, 300, 20);
		send.setBounds(440, 40, 100, 60);
		send.setFont(font);
		jlb_sen.setHorizontalAlignment(JLabel.CENTER);
		jlb_rec.setHorizontalAlignment(JLabel.CENTER);
		jlb_title.setHorizontalAlignment(JLabel.CENTER);
		jtf_sen_mail.setEditable(false);
		jtf_sen_mail.setText(sendEmailAddress);
		
		send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendMail();
			}
			
		});
		this.add(jsp);
		this.add(send);
		this.add(jlb_sen);
		this.add(jlb_rec);
		this.add(jlb_title);
		this.add(jtf_sen_mail);
		this.add(jtf_rec_mail);
		this.add(jtf_title);
		this.setLayout(null);
		this.setTitle("메일쓰기");
		this.setResizable(false);
		this.setSize(600, 600);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
	public void sendMail() {
		int smtpPort=465;
		String receiveEmailAddress = jtf_rec_mail.getText();
		
		String subject = jtf_title.getText();
		//내용
		String content = jta.getText();
		
		
		Properties props = System.getProperties();
		props.put("mail.smtp.host", smtpServer);
		props.put("mail.smtp.port", smtpPort);
		props.put("mail.smtp.auth", true);
		props.put("mail.smtp.ssl.enable", true);
		props.put("mail.smtp.ssl.trust", smtpServer);
		
		Session session5 = Session.getDefaultInstance(props, new Authenticator(){
			protected PasswordAuthentication getPasswordAuthentication(){
				return new PasswordAuthentication(sendId, sendPass);
			}
		});
		//session2.setDebug(true);
		try{
			Message mimeMessage = new MimeMessage(session5);
			mimeMessage.setFrom(new InternetAddress(sendEmailAddress));
			mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(receiveEmailAddress));
			mimeMessage.setSubject(subject);
			mimeMessage.setText(content);
			Transport.send(mimeMessage);
			System.out.print("message sent successfully..."); 
			JOptionPane.showMessageDialog(null, "메일 전송에 성공했습니다.");
		} catch (AddressException e) { 
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "메일 전송에 실패했습니다.");
			e.printStackTrace(); 
		} catch (MessagingException e) { 
			// TODO Auto-generated catch block 
			e.printStackTrace(); 
			JOptionPane.showMessageDialog(null, "메일 전송에 실패했습니다.");
		} 			
	
	}
}