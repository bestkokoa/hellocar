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

public class myInfoActivity extends Activity implements OnItemClickListener {
	private final String SERVER_ADDRESS = "http://kimTHproject.iptime.org/hellocar"; //서버 주소(php파일이 저장되어있는 경로까지, 절대로 127.0.0.1이나 localhost를 쓰면 안된다!!)
	
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
	    
		Toast.makeText(myInfoActivity.this, id + "님 접속중입니다.", Toast.LENGTH_SHORT).show();
	    //php를 호출하여 xml파일 생성(게시판)
	    //xml파일은 초기 호출 시 전체데이터를 가지고 있는 xml파일에서 가져온다
	    //Search기능을 가지고 호출 시에는 각 user의 이름의 xml로 /user에 저장
	    
	    arMemberList = new ArrayList<Member>();
	    //xml파일로부터 데이터 가져오기
	    //arMemberList로 데이터를 저장함
	    //getxmldatalist로 해당 xml파일에서 태그를 가진 것들을 전부 가져옴
	    
	    try 
	    {
	    	arMemberList.clear(); //반복적으로 누를경우 똑같은 값이 나오는 것을 방지하기 위해 data를 클리어함
	    	URL url = new URL(SERVER_ADDRESS + "/loadMyInfo.php?" + "ID=" +id);
	    	url.openStream(); //서버의 serarch.php파일을 실행함
	    	arMemberList = new ArrayList<Member>();
	    	
	    	ArrayList<String> goallocList = getXmlDataList("searchresult.xml", "goalLoc");
	    	ArrayList<String> routelocList =getXmlDataList("searchresult.xml", "routeLoc");
	    	ArrayList<String> noList =getXmlDataList("searchresult.xml", "no") ;
	    	ArrayList<String> nameList =getXmlDataList("searchresult.xml", "title");
	    	ArrayList<String> starttimeList =getXmlDataList("searchresult.xml", "startTime");
	    	ArrayList<String> regtimeList =getXmlDataList("searchresult.xml", "regTime")  ;
	    	
	
	    	//ArrayList<string> namelist = getXmlDataList("searchresult.xml", "name");//name 태그값을 읽어 namelist 리스트에 저장                                    
	    	//ArrayList<string> pricelist = getXmlDataList("searchresult.xml", "price"); 	//price 태그값을 읽어 prica 리스트에 저장
	    	if(noList.isEmpty())
	    		arMemberList.add(new Member("","",3,"아무것도 검색되지 않았습니다.","",""));
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
	    //게시판에 들어갈 내용 list에 담기
	    //arMemberList = new ArrayList<Member>();
	    //arMemberList.add(new Member("asdf", "asdf", 1, "asdf", "12:34", "23:45"));
	    //adapter 준비
	    MemberAdapter myMemberAdapter = new MemberAdapter(this, arMemberList);
	    ListView myMemberList;
	    
	    //adapter 연결
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
	
	private ArrayList<String> getXmlDataList(String filename, String str) { //태그값 여러개를 받아오기위한 ArrayList<String>형 변수
		String rss = SERVER_ADDRESS + "/";
		ArrayList<String> ret = new ArrayList<String>();
		
		try { //XML 파싱을 위한 과정
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser xpp = factory.newPullParser();
			URL server = new URL(rss + filename);
			InputStream is = server.openStream();
			xpp.setInput(is, "UTF-8");
			
			int eventType = xpp.getEventType();
			
			while(eventType != XmlPullParser.END_DOCUMENT) {
				if(eventType == XmlPullParser.START_TAG) {
					if(xpp.getName().equals(str)) { //태그 이름이 str 인자값과 같은 경우
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
