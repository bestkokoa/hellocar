package hello.car;

import hello.car.R;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import hello.car.regActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class Android_CarActivity extends Activity 
{
	private final String SERVER_ADDRESS = "http://kimTHproject.iptime.org/hellocar"; //���� �ּ�(php������ ����Ǿ��ִ� ��α���, ����� 127.0.0.1�̳� localhost�� ���� �ȵȴ�!!)
	
	EditText edtID;
	EditText edtPW;
	Button btnCheck;
	Button btnCancel;
	Button btnReg;
	
	ListView list;
	ArrayList<String> data;
	ArrayAdapter<String> adapter;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        
        //����.xml�� ȭ�鿡 �����ֱ� ����
        setContentView(R.layout.main);
        
        
        Drawable drawable = getResources().getDrawable(R.drawable.yonsei);
        
        //�̸��� �н����带 �Է��ϴ� ����
        edtID = (EditText )findViewById(R.id.editText1);
        edtPW = (EditText )findViewById(R.id.editText2);
        //Ȯ�ι�ư�� ��ҹ�ư ����
        btnCheck = (Button )findViewById(R.id.button1);
        btnReg = (Button )findViewById(R.id.button2);
        
       
        
      //Ȯ�� ��ư�� ������ ��
        btnCheck.setOnClickListener(new View.OnClickListener() 
        { 
			
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				
				if( edtID.getText().toString().equals("") ||
						edtPW.getText().toString().equals("") ) { //���̵�� �н������� �Է��� �ȵǾ�����
					Toast.makeText(Android_CarActivity.this,
							"���̵�� �н����带 Ȯ�����ּ���", Toast.LENGTH_SHORT).show();
					return;
				}
					
					runOnUiThread(new Runnable() 
					{
						
						public void run() 
						{
							// TODO Auto-generated method stub
							String ID = edtID.getText().toString();
							String PW = edtPW.getText().toString();
							
							try 
							{
								String s = new String(SERVER_ADDRESS + "/logincheck.php?"
										+ "ID=" + URLEncoder.encode(ID, "UTF-8")
										+ "&PW=" + URLEncoder.encode(PW, "UTF-8"));
								
								URL url = new URL(s); //�������� UTF-8�� ���ڵ��ϱ� ���� URLEncoder�� �̿��Ͽ� ���ڵ���
								
								
								//�������� ���� ��Ʈ���� ���� �ʾƼ� �߰��Ǵ� ����
								//URLConnection t_connection = url.openConnection();
								//t_connection.setReadTimeout(2000);
								//InputStream t_inputStream = t_connection.getInputStream();
								
								//url.openConnection();
								
								url.openStream(); //������ DB���� �ش� ID�� PW�� �����ϴ� �༮�� �ִ���
								//Ȯ���� ���� insert.php�� ID�� PW�� �Ѱ��ش�
								
								String result = getXmlData("loginresult.xml", "result"); 
								//�Է� �������� Ȯ��
								
								if(result.equals("1")) { //result �±װ��� 1�϶� ����
									Toast.makeText(Android_CarActivity.this,
											"�α��� ����", Toast.LENGTH_SHORT).show();
									
									Intent i = new Intent(getApplicationContext(),
											menu.class);
									
									i.putExtra("login", true);
									i.putExtra("ID", ID);
									
									startActivity(i);
									finish();
								}
								else //result �±װ��� 1�� �ƴҶ� ����
									Toast.makeText(Android_CarActivity.this,
											"�α��� ����", Toast.LENGTH_SHORT).show();
							} catch(Exception e) {
								Log.e("Error", e.getMessage());
							}
						}
					});
				}
			});
        
       
        
        btnReg.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				
				Intent i = new Intent(getApplicationContext(),
						regActivity.class);
				startActivity(i);
				finish();
					
			}
		});
    }
    
	private String getXmlData(String filename, String str) { //�±װ� �ϳ��� �޾ƿ������� String�� �Լ�
		String rss = SERVER_ADDRESS + "/";
		String ret = "";
		
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
						ret = xpp.nextText();
					}
				}
				eventType = xpp.next();
			}
		} catch(Exception e) {
			Log.e("Error", e.getMessage());
		}
		
		return ret;
	}
}