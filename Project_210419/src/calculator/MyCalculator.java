package calculator;
import javax.swing.*;
import java.awt.event.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MyCalculator extends JFrame implements ActionListener {

	// 최대한 입력 가능한 길이를 제한
	final int		MAX_INPUT_LENGTH	= 20;

	// 각 모드별로 index를 부여
	final int		INPUT_MODE			= 0;
	final int		RESULT_MODE			= 1;
	final int		ERROR_MODE			= 2;
	int				displayMode;

	boolean			clearOnNextDigit;			// 화면에 표시될 숫자를 지울지 말지 결정

	// 선언부
	double			lastNumber;					// 마지막에 기억될 수
	String			lastOperator;				// 마지막에 누른 연산자를 기억

	private JLabel	jl_output;					// 숫자가 표시되는 레이블
	private JButton	jb_btns[];					// 버튼 배열
	private JPanel	jp_btns;					// 버튼과 레이블을 배치할 공간

	// 생성자
	public MyCalculator() {

		// 배경
		setBackground(Color.gray);

		jp_btns = new JPanel();// 버튼 패널

		// 계산기 숫자 표시 레이블
		jl_output = new JLabel("0", JLabel.RIGHT);
		jl_output.setFont(new Font("Arial", Font.BOLD, 40));
		jl_output.setBackground(Color.WHITE);
		jl_output.setOpaque(true);

		// 버튼 생성
		jb_btns = new JButton[20];

		for (int i = 0; i <= 9; i++) {
			jb_btns[i] = new JButton(String.valueOf(i));
		}

		jb_btns[10] = new JButton("+");
		jb_btns[11] = new JButton("-");
		jb_btns[12] = new JButton("*");
		jb_btns[13] = new JButton("/");

		jb_btns[14] = new JButton(".");
		jb_btns[15] = new JButton("√");
		jb_btns[16] = new JButton("C");
		jb_btns[17] = new JButton("±");
		jb_btns[18] = new JButton("←");
		jb_btns[19] = new JButton("=");

		for (int i = 0; i <= 19; i++) {
			jb_btns[i].setFont(new Font("Arial", Font.BOLD, 35));
		}

		// 버튼 글자 색상지정
		for (int i = 0; i < jb_btns.length; i++) {

			if (i < 10) {
				jb_btns[i].setForeground(Color.black);
			}
			else if (10 <= i && i <= 15) {
				jb_btns[i].setForeground(Color.blue);
			}
			else {
				jb_btns[i].setForeground(Color.red);
			}
		}

		// 버튼 패널들의 레이아웃

		jp_btns.setLayout(new GridLayout(5, 4, 0, 0));

		for (int i = 16; i <= 19; i++) {
			jp_btns.add(jb_btns[i]);
		}

		// 숫자버튼들, 사칙연산 버튼들 배치
		for (int i = 1; i <= 3; i++) {
			jp_btns.add(jb_btns[i]);
		}
		jp_btns.add(jb_btns[10]);

		for (int i = 4; i <= 6; i++) {
			jp_btns.add(jb_btns[i]);
		}
		jp_btns.add(jb_btns[11]);

		for (int i = 7; i <= 9; i++) {
			jp_btns.add(jb_btns[i]);
		}
		jp_btns.add(jb_btns[12]);

		jp_btns.add(jb_btns[14]);
		jp_btns.add(jb_btns[0]);
		jp_btns.add(jb_btns[15]);
		jp_btns.add(jb_btns[13]);

		super.setPreferredSize(new Dimension(500, 500));
		// 레이블과 버튼 패널들 레이아웃

		getContentPane().add(jl_output, BorderLayout.NORTH);
		getContentPane().add(jp_btns, BorderLayout.CENTER);

		requestFocus();// ???무엇일까?

		for (int i = 0; i < jb_btns.length; i++) {
			jb_btns[i].addActionListener(this);
		}
		clearAll();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}// end of MyCalculator(){}

	private void clearAll() {
		setDisplayString("0");
		lastOperator = "0";
		lastNumber = 0;
		displayMode = INPUT_MODE;
		clearOnNextDigit = true;
	}

	// 숫자창에 입력하기
	private void addToDisplay(int i) {
		if (clearOnNextDigit) // clearAll 에서 true로 저장되어 있음
			setDisplayString(""); // jl_output에 저장
		
		String inputString = getDisplayString();// jl_output 에 저장된 값 가져오기 ""

		if (inputString.indexOf("0") == 0) { // 첫번째 입력한 숫자가 0이면
			inputString = inputString.substring(1); // 두번째 숫자부터 저장
		}

		if ((!inputString.contentEquals("0") || i > 0) && inputString.length() < MAX_INPUT_LENGTH) {
			setDisplayString(inputString + i); // 입력한 숫자가 0이 아니면
		}
		displayMode = INPUT_MODE;
		clearOnNextDigit = false;
	}

	// 사칙연산
	private double processLastOperator() throws Exception {
		double	result			= 0;
		double	numberInDisplay	= getNumberInDisplay();

		if (lastOperator.equals("/")) {
			if (numberInDisplay == 0)
				throw (new Exception());

			result = lastNumber / numberInDisplay;
		}

		if (lastOperator.equals("*")) {
			result = lastNumber * numberInDisplay;
		}

		if (lastOperator.equals("-")) {
			result = lastNumber - numberInDisplay;
		}

		if (lastOperator.equals("+")) {
			result = lastNumber + numberInDisplay;
		}

		return result;
	}

	private void setDisplayString(String string) {// setDisplayString(X)
		jl_output.setText(string);
	}

	// 디스플레이에 나올 문자열 가져오기
	private String getDisplayString() {
		return jl_output.getText();
	}

	// 입력된 문자열 double 로 가져오기
	private double getNumberInDisplay() {
		String input = jl_output.getText();
		return Double.parseDouble(input);
	}

	private void processOperator(String string) {

		if (displayMode != ERROR_MODE) {
			double numberInDisplay = getNumberInDisplay();

			if (!lastOperator.equals("0")) {

				try {
					double result = processLastOperator();
					displayResult(result);
					lastNumber = result;
				}
				catch (Exception e) {
				}
			}
			else {
				lastNumber = numberInDisplay;
			}
			clearOnNextDigit = true;
			lastOperator = string;
		}
	}

	// 결과값 출력
	private void displayResult(double result) {
		setDisplayString(Double.toString(result));
		lastNumber = result;
		displayMode = RESULT_MODE;
		clearOnNextDigit = true;
	}

	private void addPoint() {
		displayMode = INPUT_MODE;
		if (clearOnNextDigit)
			setDisplayString("");
		String inputString = getDisplayString();
		if (inputString.indexOf(".") < 0)
			setDisplayString(new String(inputString + "."));
	}

	public void sqrRoot() {
		double result = 0;

		if (displayMode != ERROR_MODE) {

			try {
				if (getDisplayString().indexOf("-") == 0)
					displayError("Invalid input for function");

				result = Math.sqrt(getNumberInDisplay());
				displayResult(result);

			}
			catch (Exception ex) {
				displayError("영으로 나눌 수 없습니다.");
				displayMode = ERROR_MODE;
			}
		}
	}

	private void displayError(String error) {
		setDisplayString(error);
		lastNumber = 0;
		displayMode = ERROR_MODE;
		clearOnNextDigit = true;
	}

	public void processSignChange() {

		if (displayMode == INPUT_MODE) {
			String input = getDisplayString();

			if (input.length() > 0 && !input.equals("0")) {
				if (input.indexOf("-") == 0)
					setDisplayString(input.substring(1));
				else
					setDisplayString("-" + input);
			}
		}
	}

	private void backspace() {

		if (displayMode != ERROR_MODE) {
			setDisplayString(getDisplayString().substring(0,
										getDisplayString().length() - 1));
			if (getDisplayString().length() < 1)
				setDisplayString("0");
		}
	}

	private void processEquals() {
		double result = 0;

		if (displayMode != ERROR_MODE) {

			try {
				result = processLastOperator();
				displayResult(result);
			}
			catch (Exception e) {
				displayError("영으로 나눌 수 없습니다.");
			}
			lastOperator = "0";
		}
	}

	@Override
	public void actionPerformed(ActionEvent ae) {

		double result = 0;

		for (int i = 0; i < jb_btns.length; i++) {

			if (ae.getSource() == jb_btns[i]) {

				if (i < 10) {
					addToDisplay(i);
					break;
				}

				else if (i >= 10 && i <= 13) {
					processOperator(jb_btns[i].getText());
			
					break;
				}
				else {

					switch (i) {
					case 14:
						addPoint();
						break;
					case 15:
						sqrRoot();
						break;
					case 16:
						clearAll();
						break;
					case 17:
						processSignChange();
						break;
					case 18:
						backspace();
						break;
					case 19:
						processEquals();
						break;
					}
				}
			}

		}
	}

	public static void main(String[] args) {

		MyCalculator cal = new MyCalculator();
		cal.setTitle("Calculator");
		cal.pack(); // 무엇일까??
		cal.setLocation(700, 300);
		cal.setVisible(true);
		cal.setResizable(false);

	}

}
