package hello.car;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BoardActivity extends Activity implements OnItemClickListener {
	private final String SERVER_ADDRESS = "http://kimTHproject.iptime.org/hellocar"; //���� �ּ�(php������ ����Ǿ��ִ� ��α���, ����� 127.0.0.1�̳� localhost�� ���� �ȵȴ�!!)
	
	ArrayList<Member> arMemberList;
	Button write;
	/*
	ListView list;
	ArrayList<String> data;
	ArrayAdapter<String> adapter;
	*/
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
	    super.onCreate(savedInstanceState);
	        
	    setContentView(R.layout.board);
	    // TODO Auto-generated method stub
	     
	    write = (Button )findViewById(R.id.write);
	    
	    Intent intent = getIntent();
		boolean loggedIn = intent.getBooleanExtra("login", false);
		final String id = intent.getStringExtra("ID");
	    
		Toast.makeText(BoardActivity.this, id + "�� �������Դϴ�.", Toast.LENGTH_SHORT).show();
	    //php�� ȣ���Ͽ� xml���� ����(�Խ���)
	    //xml������ �ʱ� ȣ�� �� ��ü�����͸� ������ �ִ� xml���Ͽ��� �����´�
	    //Search����� ������ ȣ�� �ÿ��� �� user�� �̸��� xml�� /user�� ����
	    
	    arMemberList = new ArrayList<Member>();
	    //xml���Ϸκ��� ������ ��������
	    //arMemberList�� �����͸� ������
	    //getxmldatalist�� �ش� xml���Ͽ��� �±׸� ���� �͵��� ���� ������
	    
	    try 
	    {
	    	arMemberList.clear(); //�ݺ������� ������� �Ȱ��� ���� ������ ���� �����ϱ� ���� data�� Ŭ������
	    	URL url = new URL(SERVER_ADDRESS + "/search.php");
	    	url.openStream(); //������ serarch.php������ ������
	    	arMemberList = new ArrayList<Member>();
	    	
	    	ArrayList<String> goallocList = getXmlDataList("searchresult.xml", "goalLoc");
	    	ArrayList<String> routelocList =getXmlDataList("searchresult.xml", "routeLoc");
	    	ArrayList<String> noList =getXmlDataList("searchresult.xml", "no") ;
	    	ArrayList<String> nameList =getXmlDataList("searchresult.xml", "title");
	    	ArrayList<String> starttimeList =getXmlDataList("searchresult.xml", "startTime");
	    	ArrayList<String> regtimeList =getXmlDataList("searchresult.xml", "regTime")  ;
	    	
	
	    	//ArrayList<string> namelist = getXmlDataList("searchresult.xml", "name");//name �±װ��� �о� namelist ����Ʈ�� ����                                    
	    	//ArrayList<string> pricelist = getXmlDataList("searchresult.xml", "price"); 	//price �±װ��� �о� prica ����Ʈ�� ����
	    	if(noList.isEmpty())
	    		arMemberList.add(new Member("","", 0,"�ƹ��͵� �˻����� �ʾҽ��ϴ�.","",""));
	    	else
	    	{
	    		for (int i =0; i< noList.size(); i++)
		    	{
		    		arMemberList.add( new Member(
		    				goallocList.get(i),
		    				routelocList.get(i),
		    				Integer.parseInt(noList.get(i)),
		    				nameList.get(i),
		    				starttimeList.get(i),
		    				regtimeList.get(i))
		    		);
		    	}
	    	}
	    } 
	    catch(Exception e)
	    {                                    
	    	Log.e("Error", e.getMessage());
	 	} 
	 	finally
	 	{
	 		//dialog.dismiss();
	 		//myMemberAdapter.notifyDataSetChanged();
	 	}
	    /*
        list = (ListView )findViewById(R.id.listView1);
        data = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this,
        		android.R.layout.simple_list_item_1, data);
        list.setAdapter(adapter);
        */
	    //�Խ��ǿ� �� ���� list�� ���
	    //arMemberList = new ArrayList<Member>();
	    //arMemberList.add(new Member("asdf", "asdf", 1, "asdf", "12:34", "23:45"));
	    //adapter �غ�
	    MemberAdapter myMemberAdapter = new MemberAdapter(this, arMemberList);
	    ListView myMemberList;
	    
	    //adapter ����
	    myMemberList = (ListView)findViewById(R.id.listView);
	    myMemberList.setAdapter(myMemberAdapter);
	    
	    myMemberList.setOnItemClickListener(this);
	    
	    write.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				
				Intent i = new Intent(getApplicationContext(),
						writeActivity.class);
				
				i.putExtra("login", true);
				i.putExtra("ID", id);
				
				startActivity(i);
				finish();
					
			}
		});
	    
	    
	}
	
	private ArrayList<String> getXmlDataList(String filename, String str) { //�±װ� �������� �޾ƿ������� ArrayList<String>�� ����
		String rss = SERVER_ADDRESS + "/";
		ArrayList<String> ret = new ArrayList<String>();
		
		try { //XML �Ľ��� ���� ����
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser xpp = factory.newPullParser();
			URL server = new URL(rss + filename);
			InputStream is = server.openStream();
			xpp.setInput(is, "UTF-8");
			
			int eventType = xpp.getEventType();
			
			while(eventType != XmlPullParser.END_DOCUMENT) {
				if(eventType == XmlPullParser.START_TAG) {
					if(xpp.getName().equals(str)) { //�±� �̸��� str ���ڰ��� ���� ���
						ret.add(xpp.nextText());
					}
				}
				eventType = xpp.next();
			}
		} catch(Exception e) {
			Log.e("Error", e.getMessage());
		}
		
		return ret;
	}

	public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub
		Member one = (Member) parent.getItemAtPosition(position);
		
		
		Intent i = new Intent(getApplicationContext(),
				clickBoardActivity.class);
		Intent intent = getIntent();
		
		i.putExtra("no", one.no);
		i.putExtra("ID", intent.getStringExtra("ID"));
		
		startActivity(i);
		finish();
		//arMemberList[position]; 
	}
}

class Member{
	String goalLoc;
	String routeLoc;
	int no;
	String title;
	String startTime;
	String regTime;
	
	public Member(String _goalLoc, String _routeLoc, 
			int _no, String _title, String _startTime, String _regTime){
		goalLoc = _goalLoc;
		routeLoc = _routeLoc;
		no = _no;
		title = _title;
		startTime = _startTime;
		regTime = _regTime;
	}
}

class MemberAdapter extends BaseAdapter
{

	LayoutInflater mInflater;
	ArrayList<Member> arSrc;
	Context _context;
	
	public MemberAdapter(Context context, ArrayList<Member>personListItem){
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		arSrc = personListItem;
		_context = context;
		
	}
	
	public int getCount() {
		// TODO Auto-generated method stub
		return arSrc.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return arSrc.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		int res = R.layout.board_list;
		convertView = mInflater.inflate(res, parent, false);
		
		TextView txt1 = (TextView)convertView.findViewById(R.id.text1);
		txt1.setText(arSrc.get(position).goalLoc);
		
		TextView txt2 = (TextView)convertView.findViewById(R.id.text2);
		txt2.setText("" + arSrc.get(position).no);
		
		TextView txt3 = (TextView)convertView.findViewById(R.id.text3);
		txt3.setText(arSrc.get(position).title);
		
		TextView txt4 = (TextView)convertView.findViewById(R.id.text4);
		txt4.setText(arSrc.get(position).startTime);
		
		TextView txt5 = (TextView)convertView.findViewById(R.id.text5);
		txt5.setText(arSrc.get(position).regTime);
			
		return convertView;
	}
	
	
	
	
	
}