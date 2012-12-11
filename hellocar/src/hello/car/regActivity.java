package hello.car;

import hello.car.R;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import hello.car.Android_CarActivity;

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


public class regActivity  extends Activity 
{
	private final String SERVER_ADDRESS = "http://kimTHproject.iptime.org/hellocar"; //���� �ּ�(php������ ����Ǿ��ִ� ��α���, ����� 127.0.0.1�̳� localhost�� ���� �ȵȴ�!!)
	
	EditText regId;
	EditText regPassword;
	EditText regMajor;
	EditText regName;
	EditText regCellphone;
	EditText regUniv;
	
	Button btnReg;
	Button btnLinkToLogin;
	
	ListView list;
	ArrayList<String> data;
	ArrayAdapter<String> adapter;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        
        //����.xml�� ȭ�鿡 �����ֱ� ����
        setContentView(R.layout.register);
        
        
        Drawable drawable = getResources().getDrawable(R.drawable.yonsei);
        
        //�Է��� ȸ�� ������ ��Ʈ�� �ϱ����� ������
        regId = (EditText )findViewById(R.id.registerId);
        regPassword = (EditText )findViewById(R.id.registerPassword);
        regUniv = (EditText )findViewById(R.id.registerUniv);
        regMajor = (EditText )findViewById(R.id.registerMajor);
    	regName = (EditText )findViewById(R.id.registerName);
    	regCellphone = (EditText )findViewById(R.id.registerCellphone);
        
        //Ȯ�ι�ư�� ��ҹ�ư ����
        btnReg = (Button )findViewById(R.id.btnRegister);
        btnLinkToLogin = (Button )findViewById(R.id.btnLinkToLoginScreen);
        
        
        
        btnReg.setOnClickListener(new View.OnClickListener() 
        {
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				if( regId.getText().toString().equals("") ||
						regPassword.getText().toString().equals("") ||
						regMajor.getText().toString().equals("") ||
						regName.getText().toString().equals("") ||
						regCellphone.getText().toString().equals(""))
				{ //ȸ�������� ������ ���� ������
					Toast.makeText(regActivity.this,
							"�Է������� �������� �Է����ּ���", Toast.LENGTH_SHORT).show();
					return;
				}
				
				runOnUiThread(new Runnable() 
				{
					
					public void run() 
					{
						// TODO Auto-generated method stub
						String ID = regId.getText().toString();
						String PW = regPassword.getText().toString();
						String UNIV = regUniv.getText().toString();
						String MAJOR = regMajor.getText().toString();
						String NAME = regName.getText().toString();
						String CELL = regCellphone.getText().toString();
						
						
						
						try 
						{
							String s = new String(SERVER_ADDRESS + "/insertMember.php?"
									+ "ID=" + URLEncoder.encode(ID, "UTF-8")
									+ "&PW=" + URLEncoder.encode(PW, "UTF-8")
									+ "&UNIV=" + URLEncoder.encode(UNIV, "UTF-8")
									+ "&MAJOR=" + URLEncoder.encode(MAJOR, "UTF-8")
									+ "&NAME=" + URLEncoder.encode(NAME, "UTF-8")
									+ "&CELL=" + URLEncoder.encode(CELL, "UTF-8")
									);
							
							URL url = new URL(s); //�������� UTF-8�� ���ڵ��ϱ� ���� URLEncoder�� �̿��Ͽ� ���ڵ���
							
							
							//�������� ���� ��Ʈ���� ���� �ʾƼ� �߰��Ǵ� ����
							//URLConnection t_connection = url.openConnection();
							//t_connection.setReadTimeout(2000);
							//InputStream t_inputStream = t_connection.getInputStream();
							
							//url.openConnection();
							
							url.openStream(); //������ DB���� �ش� ID�� PW�� �����ϴ� �༮�� �ִ���
							//Ȯ���� ���� insert.php�� ID�� PW�� �Ѱ��ش�
							
							String result = getXmlData("insertresult.xml", "result"); 
							//�Է� �������� Ȯ��
							
							if(result.equals("1")) { //result �±װ��� 1�϶� ����
								Toast.makeText(regActivity.this,
										"ȸ������ ����", Toast.LENGTH_SHORT).show();
								
								//edtID.setText("");
								//edtPW.setText("");
							}
							else //result �±װ��� 1�� �ƴҶ� ����
								Toast.makeText(regActivity.this,
										"ȸ������ ����", Toast.LENGTH_SHORT).show();
						} 
						catch(Exception e) 
						{
							Log.e("Error", e.getMessage());
						}
					}
				});
			}
        });
    
        
        
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				Intent i = new Intent(getApplicationContext(),
						Android_CarActivity.class);
				startActivity(i);
				// Close Registration View
				finish();
			}
		});
        
        
    }
    
    private String getXmlData(String filename, String str) 
	{
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
			
        
