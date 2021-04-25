package calendar;

import java.awt.GridLayout;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dao.CreateAccountVO;
import dao.LoginDao;
import login.LoginView;

public class Main80 extends JFrame {
   Main80East me = null;
   Main80Center mc = null;
   CreateAccountVO caVO = null;
   LoginDao ld = null;
   Map<String, String> callData = null;
   LoginView lv = null;
   
   public Main80(LoginView lv) {
	  this.lv = lv;
      me = new Main80East();
      mc = new Main80Center(me);
      callData = lv.check_ok;
      initDisplay();
   }
   private void initDisplay() {
      this.add("Center", mc);
      this.add("East", me);
      this.setTitle(callData.get("name")+"님의 일정관리");
      this.setSize(1200, 700);
      this.setVisible(true);
      this.setResizable(false);// 크기조정 불가
      this.setLocationRelativeTo(null);// 가운데 띄우게하기
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }

   public static void main(String[] args) {
      new Main80(null);

   }


}