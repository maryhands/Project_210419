package calendar;

import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

import calendar.EastWork.BackGroundPanel;

public class EastWork extends JFrame implements ActionListener {
	String cols[] = { "출근 시간", "퇴근 시간" };
	String data[][] = new String[17][2];
	Font font = new Font("맑은 고딕", Font.BOLD, 15);
	Font font_time = new Font("맑은 고딕", Font.PLAIN, 11
			);
	DefaultTableModel dtm = new DefaultTableModel(data, cols) {
		public boolean isCellEditable(int d, int c) {
			return false;

		}
	};
	
	JTable jtb = new JTable(dtm);
	JScrollPane jsp = new JScrollPane(jtb);
	JButton work = new JButton("출근");
	JButton leave = new JButton("퇴근");
	
	int workrow = 0;
	int leaverow = 0;

	String imgPath = "project_210404\\src\\calendar\\";
	ImageIcon imgIcon = new ImageIcon(imgPath + "watercolor.jpg");

	class BackGroundPanel extends JPanel { // 배경화면을 위해서 내부에 클래스 지정
		public void paintComponent(Graphics g) {
			g.drawImage(imgIcon.getImage(), 0, 0, null);
			setOpaque(false);
			super.paintComponent(g);

		}
	}

	public EastWork() {
		initDisplay();
	}

	public void initDisplay() {

		this.setContentPane(new BackGroundPanel());
		jsp.setBounds(50, 20, 300, 300);
		work.setBounds(50, 340, 140, 50);
		work.setFont(font);
		leave.setBounds(210, 340, 140, 50);
		leave.setFont(font);
		jtb.getTableHeader().setReorderingAllowed(false); // 이동 불가
		jtb.getTableHeader().setResizingAllowed(false); // 크기 조절 불가
		jtb.setFont(font_time);//?? 테이블은 폰트 적용이 안되고 있음
		this.add(jsp);
		this.add(work);
		this.add(leave);
		this.setLayout(null);// 패널에 레이아웃 위치 설정 및 배치
		this.setTitle("근태관리");
		this.setResizable(false);// 크기조정 불가
		this.setSize(420, 450);
		this.setVisible(true);
		this.setLocationRelativeTo(null);// 가운데 띄우게하기
		work.addActionListener(this);
		leave.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy / MM / dd / HH:mm:ss");
		Object obj = ae.getSource();
		if (work == obj) {
			String time_work = sdf.format(cal.getTime());
			dtm.setValueAt(time_work, workrow, 0);
			workrow++;
		} else if (leave == obj) {
			String time_leave = sdf.format(cal.getTime());
			dtm.setValueAt(time_leave, leaverow, 1);
			leaverow++;
			
		}		
	}

}