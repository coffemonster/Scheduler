package congcrete;

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
	
	
	
}
