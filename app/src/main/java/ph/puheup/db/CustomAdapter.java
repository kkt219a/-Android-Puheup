package ph.puheup.db;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ph.puheup.R;

public class CustomAdapter extends BaseAdapter{
	private static final String Log__ = "CustomAdapter";
	private LayoutInflater inflater;
	private ArrayList<ph.puheup.db.InfoClass> infoList;
	private ViewHolder viewHolder;
	
	public CustomAdapter(Context c , ArrayList<ph.puheup.db.InfoClass> array){
		inflater = LayoutInflater.from(c);
		infoList = array;
	}

	@Override
	public int getCount() {
		return infoList.size();
	} // 몇개넘어옴

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	@Override
	public View getView(int position, View convertview, ViewGroup parent) {

		View v = convertview;
		if(v == null){
			viewHolder = new ViewHolder();
			v = inflater.inflate(R.layout.list_row, null);
			viewHolder.name = (TextView)v.findViewById(R.id.tv_name);
			viewHolder.money = (TextView)v.findViewById(R.id.tv_money);
			viewHolder.content = (TextView)v.findViewById(R.id.tv_content);
			viewHolder.day = (TextView)v.findViewById(R.id.tv_day);
			//viewHolder.lis = (LinearLayout)v.findViewById(R.id.list_linear);
			v.setTag(viewHolder);
			
		}else {
			viewHolder = (ViewHolder)v.getTag();
		}


		/*
		if(Objects.equals(checking.state, "1")) {
				viewHolder.lis.setBackgroundColor(Color.rgb(27, 146, 241));
				String strColor = "#FFFFFF"; // 글자 색깔을 스트링으로 우선넣어두고
				viewHolder.content.setTextColor(Color.parseColor(strColor)); // 이걸로 활성화
				viewHolder.day.setTextColor(Color.parseColor(strColor)); // 이걸로 활성화
				viewHolder.money.setTextColor(Color.parseColor(strColor)); // 이걸로 활성화
				viewHolder.name.setTextColor(Color.parseColor(strColor)); // 이걸로 활성화
		}

		else if(Objects.equals(checking.state, "2")) {
				String strColor1 = "#000000"; // 글자 색깔을 스트링으로 우선넣어두고
				viewHolder.lis.setBackgroundColor(Color.rgb(255, 255, 255));
				viewHolder.content.setTextColor(Color.parseColor(strColor1)); // 이걸로 활성화
				viewHolder.day.setTextColor(Color.parseColor(strColor1)); // 이걸로 활성화
				viewHolder.money.setTextColor(Color.parseColor(strColor1)); // 이걸로 활성화
				viewHolder.name.setTextColor(Color.parseColor(strColor1)); // 이걸로 활성화
		}

*/
		viewHolder.name.setText(infoList.get(position).name);
		viewHolder.money.setText(infoList.get(position).money);
		viewHolder.content.setText(infoList.get(position).content);
		viewHolder.day.setText(infoList.get(position).day);
		
		return v;
	}
	
	public void setArrayList(ArrayList<ph.puheup.db.InfoClass> arrays){
		this.infoList = arrays;
	}
	
	public ArrayList<ph.puheup.db.InfoClass> getArrayList(){
		return infoList;
	}
	
	
	/*
	 * ViewHolder
	 */
	class ViewHolder{
		TextView name = null;
		TextView money=null;
		TextView content=null;
		TextView day=null;
		//LinearLayout lis;
	}
	

}







