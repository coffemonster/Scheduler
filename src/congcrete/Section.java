package congcrete;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import tree.TreeItemData;
import javafx.scene.control.TreeItem;
import database.Connect;

public class Section {
	
	private int section_id ;
	private String section ;
	private Year year ;
	public static final String SECTION_ID = "section_id" ;
	public static final String SECTION = "section" ;
	public static final String YEAR_ID = "year_id" ;
	
	public Section(int section_id, String section, Year year) {
		super();
		this.section_id = section_id;
		this.section = section;
		this.year = year;
	}

	@Override
	public String toString(){
		return section_id + "" ;
	}
	
	public Section() {
	}

	public int getSection_id() {
		return section_id;
	}

	public void setSection_id(int section_id) {
		this.section_id = section_id;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public Year getYear() {
		return year;
	}

	public void setYear(Year year) {
		this.year = year;
	}
	
	public static ArrayList<Section> getList(int year_id){
		ResultSet result = Connect.QUERY("SELECT * FROM sections WHERE year_id = " + year_id) ;
		ArrayList<Section> list = new ArrayList<Section>() ;
		try {
			while(result.next()){
				Section section = new Section() ;
				section.setSection_id(result.getInt(Section.SECTION_ID));
				Year year = Year.getYear(result.getInt(Section.YEAR_ID)) ;
				section.setYear(year);
				section.setSection(result.getString(Section.SECTION));
				list.add(section);
			}
			return list ;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null ;
		
	}
	
	public static Section getSection(int section_id){
		ResultSet result = Connect.QUERY("SELECT * FROM sections WHERE section_id = " + section_id);
		try {
			while(result.next()){
				Section section = new Section() ;
				section.setSection_id(result.getInt(Section.SECTION_ID));
				section.setSection(result.getString(Section.SECTION));
				Year year = Year.getYear(result.getInt(Section.YEAR_ID)) ;
				section.setYear(year);
				return section ;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null ;
	}
	
	public static TreeItem<String> getItem(int section_id){
		Section section = Section.getSection(section_id) ;
		TreeItem<String> year = Year.getItem(section.getYear().getYear_id()) ;
		for(int x = 0 ; x < year.getChildren().size() ; x++){
			TreeItem<String> sectionItem = year.getChildren().get(x) ;
			Section sectionData = TreeItemData.getItemData(sectionItem) ;
			if(sectionData.getSection_id() == section_id){
				return sectionItem ;
			}
		}
		return null ;
	}
	
}
