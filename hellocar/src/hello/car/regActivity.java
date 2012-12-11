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
	private final String SERVER_ADDRESS = "http://kimTHproject.iptime.org/hellocar"; //서버 주소(php파일이 저장되어있는 경로까지, 절대로 127.0.0.1이나 localhost를 쓰면 안된다!!)
	
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
        
        //메인.xml을 화면에 보여주기 위함
        setContentView(R.layout.register);
        
        
        Drawable drawable = getResources().getDrawable(R.drawable.yonsei);
        
        //입력한 회원 정보를 컨트롤 하기위한 변수들
        regId = (EditText )findViewById(R.id.registerId);
        regPassword = (EditText )findViewById(R.id.registerPassword);
        regUniv = (EditText )findViewById(R.id.registerUniv);
        regMajor = (EditText )findViewById(R.id.registerMajor);
    	regName = (EditText )findViewById(R.id.registerName);
    	regCellphone = (EditText )findViewById(R.id.registerCellphone);
        
        //확인버튼과 취소버튼 생성
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
				{ //회원정보중 누락된 것이 있을때
					Toast.makeText(regActivity.this,
							"입력정보를 빠짐없이 입력해주세요", Toast.LENGTH_SHORT).show();
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
							
							URL url = new URL(s); //변수값을 UTF-8로 인코딩하기 위해 URLEncoder를 이용하여 인코딩함
							
							
							//정상적인 오픈 스트림이 되지 않아서 추가되는 구문
							//URLConnection t_connection = url.openConnection();
							//t_connection.setReadTimeout(2000);
							//InputStream t_inputStream = t_connection.getInputStream();
							
							//url.openConnection();
							
							url.openStream(); //서버의 DB에서 해당 ID와 PW를 만족하는 녀석이 있는지
							//확인을 위해 insert.php로 ID와 PW를 넘겨준다
							
							String result = getXmlData("insertresult.xml", "result"); 
							//입력 성공여부 확인
							
							if(result.equals("1")) { //result 태그값이 1일때 성공
								Toast.makeText(regActivity.this,
										"회원가입 성공", Toast.LENGTH_SHORT).show();
								
								//edtID.setText("");
								//edtPW.setText("");
							}
							else //result 태그값이 1이 아닐때 실패
								Toast.makeText(regActivity.this,
										"회원가입 실패", Toast.LENGTH_SHORT).show();
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
			
        
