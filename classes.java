package user;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class classes extends user{
	
	private ArrayList<Integer> classYear = new ArrayList<Integer>();
	private ArrayList<Integer> classNum = new ArrayList<Integer>();
	private ArrayList<String> className = new ArrayList<String>();
	private ArrayList<Integer> classPoint = new ArrayList<Integer>();
	private ArrayList<String> classTeacher = new ArrayList<String>();
	private ArrayList<String> classType = new ArrayList<String>();
	private int[][] classStudent = new int[30][20];
	private File classes = new File("classes.txt");
	private Scanner sc;
	
	classes(){
		openClassesFile();
	}
	
	public String chaClassStudent(int classYear, int classNum, 
				int oldStudentNum, int newStudentNum) {
		int x=0;
		while (this.classYear.get(x)!=classYear || this.classNum.get(x)!=classNum) x++;
		int y=0;
		while (classStudent[x][y] != oldStudentNum) y++;
		if (this.classYear.get(x)==classYear && this.classNum.get(x)==classNum && classStudent[x][y] == oldStudentNum) {
			classStudent[x][y] = newStudentNum;
			studentSort(x);
			writeClassesFile();
			return "更改成功";
		}
		return "沒有此課程或人";
	}
	
	public String delClassStudent(int classYear, int classNum, int studentNum) {
		int x=0;
		while (this.classYear.get(x)!=classYear || this.classNum.get(x)!=classNum) x++;
		int y=0;
		while (classStudent[x][y] != studentNum) y++;
		if (this.classYear.get(x)==classYear && this.classNum.get(x)==classNum && classStudent[x][y] == studentNum) {
			for (int k=y; k<classStudent.length-1; k++) {
				classStudent[x][k] = classStudent[x][k+1];
			}
			classStudent[x][classStudent.length-1] = 0;
			writeClassesFile();
			return "刪除成功";
		}
		return "沒有此課程或人";
	}
	
	public String addClassStudent(int classYear, int classNum, int studentNum) {
		if (checkStudent(studentNum+"")=="沒有這個人") {
			return "沒有此學生";
		}
		int x=0;
		while (this.classYear.get(x)!=classYear || this.classNum.get(x)!=classNum) x++;
		if (this.classYear.get(x)==classYear && this.classNum.get(x)==classNum) {
			int y=0;
			while (classStudent[x][y] != 0) y++;
			classStudent[x][y] = studentNum;
			studentSort(x);
			writeClassesFile();
			return "新增成功";
		}
		return "沒有此課程";
	}
	
	public void studentSort(int i) {
		int y=0;
		for (int j=0; j<19; j++) {
			for (int k=0; k<19; k++) {
				if (classStudent[i][k]>classStudent[i][k+1] && classStudent[i][k+1]!=0) {
					y = classStudent[i][k];
					classStudent[i][k] = classStudent[i][k+1];
					classStudent[i][k+1] = y; 
				}
			}
		}
	}
	
	public String[] printClassStudent(int classYear, int classNum) {
		int x=0;
		String[] students = new String[classStudent.length*2];
 		for (int i=0; i<this.classNum.size()-1; i++) {
			if (this.classYear.get(i)==classYear && this.classNum.get(i)==classNum) {
				for (int j=0; j<classStudent[i].length-1; j++) {
					if (classStudent[i][j] != 0) {
						students[x] = classStudent[i][j]+"";
						students[x+1] = checkStudent(classStudent[i][j]+"");
						x += 2;
					}
				}
				return students;
			}
		}
 		return students;
	}
	
	public String addClasses(int newClassYear, int newClassNum, String newClassName,  
			int newClassPoint, String newClassTeacher, String newClassType) throws IOException {
		for (int i=0; i<classNum.size(); i++) {
			if (classNum.get(i)==newClassNum && classYear.get(i)==newClassYear) {
				return "已有此課程";
			}
		}
		int x=0;
		while (x<classYear.size() && classYear.get(x) <= newClassYear) x++;
		this.classYear.add(x, newClassYear);
		this.classNum.add(x, newClassNum);
		this.className.add(x, newClassName);
		this.classPoint.add(x, newClassPoint);
		this.classTeacher.add(x, newClassTeacher);
		this.classType.add(x, newClassType);
		
		for (int i=classNum.size()-1; i>x; i--) {
			classStudent[i] = classStudent[i-1];
		}
		classStudent[x][0] = 0;
		writeClassesFile();
		return "新增成功";
	}
	
	public String delClasses(int delClassYear, int delClassNum) throws IOException {
		int x=0;
		while (classNum.get(x)!=delClassNum || classYear.get(x)!=delClassYear) x++;
		if (classNum.get(x)==delClassNum && classYear.get(x)==delClassYear) {
			classYear.remove(x);
			classNum.remove(x);
			className.remove(x);
			classPoint.remove(x);
			classTeacher.remove(x);
			classType.remove(x);
			for (int i=x; i<classStudent.length-1; i++) {
				classStudent[i] = classStudent[i+1];
			}
			classStudent[classNum.size()+1][0] = 0;
			writeClassesFile();
			return "刪除成功";
		}
		return "沒有此課程";
		
	}
	
	public String changeClasses(int oldClassNum, int chaClassNum, int chaClassYear, String chaClassName,
			int chaClassPoint, String chaClassTeacher, String chaClassType) throws IOException {
		if (chaClassNum!=oldClassNum && classNum.indexOf(chaClassNum)!=-1) {
			return "已有此編號";
		}
		if (classTeacher.indexOf(chaClassTeacher) == -1) {
			return "沒有這個教授";
		}
		int a = classNum.indexOf(oldClassNum);
		classYear.set(a, chaClassYear);
		classNum.set(a,  chaClassNum);
		className.set(a, chaClassName);
		classPoint.set(a, chaClassPoint);
		classTeacher.set(a, chaClassTeacher);
		classType.set(a, chaClassType);
		
		writeClassesFile();
		
		return "修改成功";
	}
	
	public String[] printClass(int classYear) {
		ArrayList<String> classes = new ArrayList<String>();
		for (int i=0; i<classNum.size(); i++) {
			if (this.classYear.get(i) == classYear) {
				String str = String.format("%d %06d %s %d %s %s\n", this.classYear.get(i), classNum.get(i), 
					className.get(i), classPoint.get(i), classTeacher.get(i), classType.get(i));
				classes.add(str);
			}
		}
		String[] classList = new String[classes.size()];
		for (int i=0; i<classes.size(); i++) {
			classList[i] = classes.get(i);
		}
		return classList;
	}
	
	public boolean openClassesFile() {
		try {
			sc = new Scanner(classes);
			while (sc.hasNextLine()) {
				String[] str = sc.nextLine().split(" ");
				classYear.add(Integer.parseInt(str[0]));
				classNum.add(Integer.parseInt(str[1]));
				className.add(str[2]);
				classPoint.add(Integer.parseInt(str[3]));
				classTeacher.add(str[4]);
				classType.add(str[5]);
				for (int i=6; i<str.length; i++) {
					classStudent[classYear.size()-1][i-6] = Integer.parseInt(str[i]);
				}
			}
			sc.close();
			return true;
		}
		catch (FileNotFoundException e){
			return false;
		}
	}
	
	public void writeClassesFile() {
		try {
			FileWriter f = new FileWriter("classes.txt");
			for (int i=0; i<classNum.size(); i++) {
				String str = String.format("%d %06d %s %d %s %s ", classYear.get(i), classNum.get(i), 
						className.get(i), classPoint.get(i), classTeacher.get(i), classType.get(i));
				f.write(str);
				for (int  j=0; j<classStudent[i].length; j++) {
					if (classStudent[i][j] != 0) {
						f.write(classStudent[i][j]+" ");
					}
					else break;
				}
				f.write("\n");
			}
			f.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
