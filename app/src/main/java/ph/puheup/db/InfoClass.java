package ph.puheup.db;

public class InfoClass {
	public int _id;
	public String name;
	public String money;
	public String content;
	public String day;
	
	public InfoClass(){}
	
	public InfoClass(int _id , String name , String money , String content, String day){
		this._id = _id;
		this.name = name;
		this.money = money;
		this.content = content;
		this.day = day;

	}
	
}
