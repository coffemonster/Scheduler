package congcrete;

import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;

public class TableCellList {
	
	private SimpleStringProperty time ;
	private SimpleStringProperty day_1 ;
	private SimpleStringProperty day_2 ;
	private SimpleStringProperty day_3 ;
	private SimpleStringProperty day_4 ;
	private SimpleStringProperty day_5 ;
	
	public TableCellList(String time , String day1 , String day2 , String day3 , String day4 , String day5){
		this.time = new SimpleStringProperty(time) ;
		day_1 = new SimpleStringProperty(day1) ;
		day_2 = new SimpleStringProperty(day2) ;
		day_3 = new SimpleStringProperty(day3) ;
		day_4 = new SimpleStringProperty(day4) ;
		day_5 = new SimpleStringProperty(day5) ;
	}

	
	
	public String getTime() {
		return time.get();
	}



	public void setTime(String time) {
		this.time.set(time);
	}



	public void setDay_1(String day_1) {
		this.day_1.set(day_1);
	}



	public void setDay_2(String day_2) {
		this.day_2.set(day_2) ;
	}



	public void setDay_3(String day_3) {
		this.day_3.set(day_3);
	}



	public void setDay_4(String day_4) {
		this.day_4.set(day_4);
	}



	public void setDay_5(String day_5) {
		this.day_5.set(day_5) ;
	}


	public String getDay_1() {
		return day_1.get();
	}

	public String getDay_2() {
		return day_2.get();
	}

	public String getDay_3() {
		return day_3.get();
	}

	public String getDay_4() {
		return day_4.get();
	}

	public String getDay_5() {
		return day_5.get();
	}
	
	
}
