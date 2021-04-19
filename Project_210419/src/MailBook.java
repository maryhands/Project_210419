package calender;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class MailBook extends JFrame {
	String cols[] = { "보낸 시각", "수신자", "메일 주소" };
	String data[][] = null;
	DefaultTableModel dtm = new DefaultTableModel(data, cols) {
		public boolean isCellEditable(int d, int c) {
			return false;
		}
	};
	JPanel jpl = new JPanel();
	JTable jtb = new JTable(dtm);
	JScrollPane jsp = new JScrollPane(jtb);

	public MailBook() {
		initDisplay();
	}

	public void initDisplay() {
		jsp.setBounds(50, 30, 380, 300);
		jtb.getColumnModel().getColumn(0).setPreferredWidth(100);  //JTable 의 컬럼 길이 조절
		jtb.getColumnModel().getColumn(1).setPreferredWidth(40);
		jtb.getColumnModel().getColumn(2).setPreferredWidth(80);
		jtb.getTableHeader().setReorderingAllowed(false);
		jtb.getTableHeader().setResizingAllowed(false); 
		this.setLayout(null);
		this.add(jsp);
		this.setSize(500, 400);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setTitle("발신 기록");
	}

	public static void main(String[] args) {
		new MailBook();
	}
}
