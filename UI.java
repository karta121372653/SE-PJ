package user;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

class baseUI extends  JFrame{
	private JButton btn_myData, btn_class, btn_Info, btn_close, btn_logout;

	public baseUI(user mainUser){
		int id = Integer.parseInt(mainUser.printUserInf()[1]);
		super.setTitle("資訊");
		super.setLayout(null);

		btn_myData = new JButton("我的資料");
		btn_myData.setBounds(10,5,90,20);
		super.add(btn_myData);
		btn_myData.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				myDataUI stData = new myDataUI(mainUser);
				stData.setVisible(true);
				dispose();
			}
		});

		btn_class = new JButton("課程");
		btn_class.setBounds(105,5,90,20);
		super.add(btn_class);
		btn_class.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				classUI classUI = new classUI(mainUser, new int[]{1071});
				classUI.setVisible(true);
				dispose();
			}
		});

		if(id == 0)
			btn_Info = new JButton("人員資訊");
		else if(id == 1)
			btn_Info = new JButton("成績系統");
		else
			btn_Info = new JButton("我的成績");
		btn_Info.setBounds(200,5,90,20);
		super.add(btn_Info);
		btn_Info.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(id==0) {
					administratorUI adUI = new administratorUI(mainUser);
					adUI.setVisible(true);
				}
				else if(id==1) {
					professorUI proUI = new professorUI(mainUser);
					proUI.setVisible(true);
				}
				else {
					studentUI stUI = new studentUI(mainUser);
					stUI.setVisible(true);
				}
				dispose();
			}
		});

		btn_close = new JButton("X");
		btn_close.setBounds(430,5,45,20);
		super.add(btn_close);
		btn_close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		btn_logout = new JButton("登出");
		btn_logout.setBounds(355,5,65,20);
		super.add(btn_logout);
		btn_logout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loginUI loginUI = new loginUI();
				loginUI.setVisible(true);
				dispose();
			}
		});

		super.setSize(500,400);
		super.setVisible(true);
	}

}

class loginUI extends JFrame{
	private JLabel lb_account, lb_password;
	private JTextField tf_account, tf_password;
	private JButton btn_login, btn_close;
	
	public loginUI() {
		user mainUser = new classes();
		super.setTitle("登入");
		super.setLayout(null);
		
		lb_account = new JLabel("帳號");
		lb_account.setBounds(20,20,150,20);
		super.add(lb_account);
		tf_account = new JTextField("");
		tf_account.setBounds(20,45,150,20);
		super.add(tf_account);

		lb_password = new JLabel("密碼");
		lb_password.setBounds(20,70,150,20);
		super.add(lb_password);
		tf_password = new JTextField("");
		tf_password.setBounds(20,95,150,20);
		super.add(tf_password);

		btn_login = new JButton("登入");
		btn_login.setBounds(100,120,70,20);
		super.add(btn_login);
		btn_close = new JButton("X");
		btn_close.setBounds(430,5,45,20);
		super.add(btn_close);
		
		super.setSize(500,400);
		super.setVisible(true);

		btn_login.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String acc = tf_account.getText();
				String pass = tf_password.getText();
				String check = mainUser.checkAccount(acc,pass);
				int id = Integer.parseInt(mainUser.printUserInf()[1]);
				if(check.equals("密碼錯誤") || check.equals("帳號錯誤") || check.equals("無法找到檔案")){
					JOptionPane.showMessageDialog(null, check, "錯誤", JOptionPane.ERROR_MESSAGE);
				}
				else {
					myDataUI stData = new myDataUI(mainUser);
					stData.setVisible(true);
					dispose();
				}
			}
		});

		btn_close.addActionListener((new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		}));
	}
}

class myDataUI extends baseUI{
	private JLabel lb_stID, lb_name, lb_year;
	private JButton btn_changePassword;

	public myDataUI(user mainUser){
		super(mainUser);
		super.setTitle("我的資料");
		super.setLayout(null);

		if(Integer.parseInt(mainUser.printUserInf()[1]) == 0)
			lb_stID = new JLabel("身分 : 管理員");
		else if(Integer.parseInt(mainUser.printUserInf()[1]) == 1)
			lb_stID = new JLabel("身分 : 教授");
		else
			lb_stID = new JLabel("學號 : " + mainUser.printUserInf()[0]);
		lb_stID.setBounds(10,40,140,20);
		super.add(lb_stID);

		lb_name = new JLabel("姓名 : " + mainUser.printUserInf()[2]);
		lb_name.setBounds(10,65,140,20);
		super.add(lb_name);

		if(Integer.parseInt(mainUser.printUserInf()[1])>=107) {
			lb_year = new JLabel("入學年 :" + mainUser.printUserInf()[1]);
			lb_year.setBounds(10,90,140,20);
			super.add(lb_year);
		}


		btn_changePassword = new JButton("變更密碼");
		btn_changePassword.setBounds(10,115,120,20);
		super.add(btn_changePassword);
		btn_changePassword.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changePassword change = new changePassword(mainUser);
				change.setVisible(true);
				dispose();
			}
		});
	}
}

class classUI extends baseUI{

	classes cla = new classes();
	JTable tbl_classList = new JTable();
	String getYears;
	String getClass;


	public classUI(user mainUser, int[] year){
		super(mainUser);

		super.setTitle("課程");
		super.setLayout(null);
		String[][] str = showTable(mainUser,year);

		tbl_classList.setBounds(10,80,350,120);
		super.add(tbl_classList);

		JComboBox cb_class = new JComboBox();
		cb_class.setBounds(100,35,80,20);
		super.add(cb_class);

		cb_class.addItem("請選擇");
		for(int i =1; i<cla.printClass(year[0]).length+1; i++) {
			cb_class.addItem(str[i][2]);
		}

		cb_class.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getClass = (String) cb_class.getSelectedItem();//get the selected item
			}
		});

		JComboBox cb_years = new JComboBox();
		cb_years.setBounds(10,35,80,20);
		super.add(cb_years);
		cb_years.addItem("請選擇");
		cb_years.addItem("107-1");
		cb_years.addItem("107-2");
		cb_years.addItem("108-1");
		cb_years.addItem("108-2");
		cb_years.addItem("109-1");
		cb_years.addItem("109-2");

		cb_years.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getYears = (String) cb_years.getSelectedItem();//get the selected item

				switch (getYears) {
					case "107-1":
						year[0] = 1071;
						showTable(mainUser,year);

						cb_class.removeAllItems();
						cb_class.addItem("請選擇");
						for(int i =1; i<cla.printClass(year[0]).length+1; i++) {
							cb_class.addItem(showTable(mainUser,year)[i][2]);
						}
						break;
					case "107-2":
						year[0] = 1072;
						showTable(mainUser,year);

						cb_class.removeAllItems();
						cb_class.addItem("請選擇");
						for(int i =1; i<cla.printClass(year[0]).length+1; i++) {
							cb_class.addItem(showTable(mainUser,year)[i][2]);
						}
						break;
					case "108-1":
						year[0] = 1081;
						showTable(mainUser,year);

						cb_class.removeAllItems();
						cb_class.addItem("請選擇");
						for(int i =1; i<cla.printClass(year[0]).length+1; i++) {
							cb_class.addItem(showTable(mainUser,year)[i][2]);
						}
						break;
					case "108-2":
						year[0] = 1082;
						showTable(mainUser,year);

						cb_class.removeAllItems();
						cb_class.addItem("請選擇");
						for(int i =1; i<cla.printClass(year[0]).length+1; i++) {
							cb_class.addItem(showTable(mainUser,year)[i][2]);
						}
						break;
					case "109-1":
						year[0] = 1091;
						showTable(mainUser,year);

						cb_class.removeAllItems();
						cb_class.addItem("請選擇");
						for(int i =1; i<cla.printClass(year[0]).length+1; i++) {
							cb_class.addItem(showTable(mainUser,year)[i][2]);
						}
						break;
					case "109-2":
						year[0] = 1092;
						showTable(mainUser,year);

						cb_class.removeAllItems();
						cb_class.addItem("請選擇");
						for(int i =1; i<cla.printClass(year[0]).length+1; i++) {
							cb_class.addItem(showTable(mainUser,year)[i][2]);
						}
						break;
				}
			}
		});

		JButton btn_search = new JButton("查詢");
		btn_search.setBounds(220,35,60,20);
		super.add(btn_search);
		btn_search.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(getYears != null && getClass != null){
					if(getYears == "請選擇")
						JOptionPane.showMessageDialog(null, "請選擇學年", "錯誤", JOptionPane.ERROR_MESSAGE);
					else if(getClass == "請選擇")
						JOptionPane.showMessageDialog(null, "請選擇課程", "錯誤", JOptionPane.ERROR_MESSAGE);
					else{
						classDetailUI CD = new classDetailUI(mainUser, getYears, getClass);
						CD.setVisible(true);
						dispose();
					}

				}

			}
		});
	}

	private String[][] showTable(user mainUser, int[] year) {

		String[][] str = new String[cla.printClass(year[0]).length+1][];

		str[0] = new String[]{"學年", "課程代碼", "課程名稱", "學分", "授課教授", "課別"};
		for(int i = 1; i<cla.printClass(year[0]).length+1; i++)
			str[i] = cla.printClass(year[0])[i-1].split(" ");

		Object[] columnNames = {"學年", "課程代碼", "課程名稱", "學分", "授課教授", "課別"};
		Object[][] rowData = str;

		tbl_classList.setEnabled(false);


		TableModel dataModel = new DefaultTableModel(str, columnNames);
		tbl_classList.setModel(dataModel);

		return str;

	}
}
class classDetailUI extends baseUI{
	private JLabel lb_year, lb_classNumber, lb_className,lb_credit, lb_teacher, lb_type, lb_classPeople;
	private JButton btn_editClass;

	public classDetailUI(user mainUser, String year, String Class) {
		super(mainUser);
		super.setTitle("課程資訊");

		lb_year = new JLabel("學年 : " + year);
		lb_year.setBounds(5,40,110,20);
		super.add(lb_year);
		lb_classNumber = new JLabel("課程代碼 : ");
		lb_classNumber.setBounds(5,65,110,20);
		super.add(lb_classNumber);
		lb_className = new JLabel("課程名稱 : " + Class);
		lb_className.setBounds(5,90,110,20);
		super.add(lb_className);
		lb_credit = new JLabel("學分 : ");
		lb_credit.setBounds(5,115,110,20);
		super.add(lb_credit);
		lb_teacher = new JLabel("授課教授 : ");
		lb_teacher.setBounds(5,140,110,20);
		super.add(lb_teacher);
		lb_type = new JLabel("課別 : ");
		lb_type.setBounds(5,165,110,20);
		super.add(lb_type);
		lb_classPeople = new JLabel("修課學生 : ");
		lb_classPeople.setBounds(5,190,110,20);
		super.add(lb_classPeople);

		if(Integer.parseInt(mainUser.printUserInf()[1]) == 0) {

			JLabel lb_inputYear = new JLabel("學年");
			lb_inputYear.setBounds(5, 240, 45, 22);
			super.add(lb_inputYear);
			JTextField tf_inputYear = new JTextField(year);
			tf_inputYear.setBounds(5, 265, 45, 22);
			super.add(tf_inputYear);

			JLabel lb_inputClaNum = new JLabel("課程代碼");
			lb_inputClaNum.setBounds(60, 240, 55, 22);
			super.add(lb_inputClaNum);
			JTextField tf_inputClaNum = new JTextField("000000");
			tf_inputClaNum.setBounds(60, 265, 55, 22);
			super.add(tf_inputClaNum);

			JLabel lb_inputClaName = new JLabel("課程名稱");
			lb_inputClaName.setBounds(125, 240, 55, 22);
			super.add(lb_inputClaName);
			JTextField tf_inputClaName = new JTextField(Class);
			tf_inputClaName.setBounds(125, 265, 55, 22);
			super.add(tf_inputClaName);

			JLabel lb_inputCredit = new JLabel("學分");
			lb_inputCredit.setBounds(190, 240, 35, 22);
			super.add(lb_inputCredit);
			JTextField tf_inputCredit = new JTextField("0");
			tf_inputCredit.setBounds(190, 265, 35, 22);
			super.add(tf_inputCredit);

			JLabel lb_inputTeacher = new JLabel("授課教授");
			lb_inputTeacher.setBounds(235, 240, 55, 22);
			super.add(lb_inputTeacher);
			JTextField tf_inputTeacher = new JTextField("0000");
			tf_inputTeacher.setBounds(235, 265, 55, 22);
			super.add(tf_inputTeacher);

			JLabel lb_inputType = new JLabel("課別");
			lb_inputType.setBounds(300, 240, 35, 22);
			super.add(lb_inputType);
			JTextField tf_inputType = new JTextField("別修");
			tf_inputType.setBounds(300, 265, 35, 22);
			super.add(tf_inputType);

			btn_editClass = new JButton("確認修改");
			btn_editClass.setBounds(245, 290, 90, 25);
			super.add(btn_editClass);
			btn_editClass.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String newYear = tf_inputYear.getText();
					lb_year.setText("學年 : " + newYear);
					String newClaNum = tf_inputClaNum.getText();
					lb_classNumber.setText("課程代碼 : " + newClaNum);
					String newClaName = tf_inputClaName.getText();
					lb_className.setText("課程名稱 : " + newClaName);
					String newCredit = tf_inputCredit.getText();
					lb_credit.setText("學分 : " + newCredit);
					String newTeacher = tf_inputTeacher.getText();
					lb_teacher.setText("授課教授 : " + newTeacher);
					String newType = tf_inputType.getText();
					lb_type.setText("課別 : " + newType);

					JOptionPane.showMessageDialog(null, "修改完成", "完成", JOptionPane.INFORMATION_MESSAGE);
				}
			});
		}
	}
}

class administratorUI extends baseUI{
	private JLabel lb_manInfo;

	public administratorUI(user mainUser){
		super(mainUser);
		super.setTitle("人員資訊");
		super.setLayout(null);

		lb_manInfo = new JLabel("人員資訊");
		lb_manInfo.setBounds(5,55,110,40);
		super.add(lb_manInfo);
	}
}

class professorUI extends baseUI{
	private JComboBox cb_classes;

	public professorUI(user mainUser){
		super(mainUser);
		super.setTitle("成績系統");
		super.setLayout(null);
		final String[] Class = new String[1];

		cb_classes = new JComboBox();
		cb_classes.setBounds(5,35,100,20);
		cb_classes.addItem("請選擇課程");		// 下拉選單顯示此教授所有的課程


		super.add(cb_classes);

		cb_classes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Class[0] = (String) cb_classes.getSelectedItem();
				showClassStudent(mainUser, Class[0]);
			}
		});


	}

	private void showClassStudent(user mainUser,String CLass){
		ArrayList<JLabel> lb_studentArr = new ArrayList<JLabel>();
		ArrayList<JLabel> lb_scoreArr = new ArrayList<JLabel>();
		ArrayList<JTextField> btn_scoreArr = new ArrayList<JTextField>();
	}
}


class studentUI extends baseUI{
	private JLabel lb_show ;

	public studentUI(user mainUser){
		super(mainUser);
		lb_show = new JLabel("這邊放成績");
		lb_show.setBounds(10,55,200,20);
		super.add(lb_show);

		super.setTitle("我的成績");
		super.setLayout(null);

	}
}

class changePassword extends baseUI{
	private JLabel lb_oldPassword, lb_newPassword, lb_check;
	private JTextField tf_oldPassword, tf_newPassword, tf_check;
	private JButton btn_update;

	public changePassword(user mainUser) {
		super(mainUser);

		super.setTitle("變更密碼");
		super.setLayout(null);

		lb_oldPassword = new JLabel("密碼");
		lb_oldPassword.setBounds(5,25,200,20);
		super.add(lb_oldPassword);
		tf_oldPassword = new JTextField("");
		tf_oldPassword.setBounds(5,50,200,20);
		super.add(tf_oldPassword);
		lb_newPassword = new JLabel("新密碼");
		lb_newPassword.setBounds(5,75,200,20);
		super.add(lb_newPassword);
		tf_newPassword = new JTextField("");
		tf_newPassword.setBounds(5,100,200,20);
		super.add(tf_newPassword);
		lb_check = new JLabel("確認密碼");
		lb_check.setBounds(5,125,200,20);
		super.add(lb_check);
		tf_check = new JTextField("");
		tf_check.setBounds(5,150,200,20);
		super.add(tf_check);

		btn_update = new JButton("變更密碼");
		btn_update.setBounds(120,175,85,20);
		super.add(btn_update);

		super.setSize(500,400);
		super.setVisible(true);

		btn_update.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String oldPass = tf_oldPassword.getText();
				String newPass = tf_newPassword.getText();
				String check = tf_check.getText();

				if(mainUser.checkPass(oldPass)) {
					if(newPass.equals(check)) {
						String msg = null;
						try {
							msg = mainUser.changePassword(newPass);
						} catch (IOException ioException) {
							ioException.printStackTrace();
						}
						JOptionPane.showMessageDialog(null, msg, "完成", JOptionPane.INFORMATION_MESSAGE);
						myDataUI stData = new myDataUI(mainUser);
						stData.setVisible(true);
						dispose();
					}
					else {
						JOptionPane.showMessageDialog(null, "密碼錯誤", "錯誤", JOptionPane.ERROR_MESSAGE);
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "密碼錯誤", "錯誤", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
}

public class UI {
	public static void main(String[] args) {
		loginUI mainWindow = new loginUI();

		mainWindow.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
	}

}
