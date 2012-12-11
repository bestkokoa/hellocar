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
	    
		Toast.makeText(myInfoActivity.this, id + "�� �������Դϴ�.", Toast.LENGTH_SHORT).show();
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
	    	URL url = new URL(SERVER_ADDRESS + "/loadMyInfo.php?" + "ID=" +id);
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
	    		arMemberList.add(new Member("","",3,"�ƹ��͵� �˻����� �ʾҽ��ϴ�.","",""));
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
