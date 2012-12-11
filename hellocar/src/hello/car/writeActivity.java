package hello.car;

import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class writeActivity extends Activity {
	private final String SERVER_ADDRESS = "http://kimTHproject.iptime.org/hellocar"; 

	EditText wrtStartime;
	EditText wrtStartLoc;
	EditText wrtRouteLoc;
	EditText wrtGoalLoc;
	EditText wrtRely;// 신뢰도제한
	EditText wrtText;
	EditText wrtTitle;

	Button btnReg;
	Button btnLinkToBoard;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.write);

		Intent intent = getIntent();
		boolean loggedIn = intent.getBooleanExtra("login", false);
		final String id = intent.getStringExtra("ID");
		
		Toast.makeText(writeActivity.this, id + "님 접속중입니다.", Toast.LENGTH_SHORT).show();
		
		// 입력한 회원 정보를 컨트롤 하기위한 변수들
		wrtStartime = (EditText) findViewById(R.id.wrtStartime);
		wrtStartLoc = (EditText) findViewById(R.id.wrtStartLoc);
		wrtRouteLoc = (EditText) findViewById(R.id.wrtRouteLoc);
		wrtGoalLoc = (EditText) findViewById(R.id.wrtGoalLoc);
		wrtText = (EditText) findViewById(R.id.wrtText);
		wrtTitle = (EditText) findViewById(R.id.wrtTitle);
		
		// 확인버튼과 취소버튼 생성
		btnReg = (Button) findViewById(R.id.btnReg);
		btnLinkToBoard = (Button) findViewById(R.id.btnLinkToBoard);

		// TODO Auto-generated method stub
		btnReg.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (wrtStartime.getText().toString().equals("")
						|| wrtStartLoc.getText().toString().equals("")
						|| wrtRouteLoc.getText().toString().equals("")
						|| wrtGoalLoc.getText().toString().equals("")
						|| wrtText.getText().toString().equals("")
						|| wrtTitle.getText().toString().equals("")) { 
					Toast.makeText(writeActivity.this, "입력정보를 빠짐없이 입력해주세요",
							Toast.LENGTH_SHORT).show();
					return;
				}

				runOnUiThread(new Runnable() {

					public void run() {
						// TODO Auto-generated method stub
						String STARTTIME = wrtStartime.getText().toString();
						String STARTLOC = wrtStartLoc.getText().toString();
						String ROUTELOC = wrtRouteLoc.getText().toString();
						String GOALLOC = wrtGoalLoc.getText().toString();
						String TEXT = wrtText.getText().toString();
						String TITLE = wrtTitle.getText().toString();

						try {
							String s = new String(SERVER_ADDRESS
									+ "/writeBoard.php?"
									+ "ID=" + URLEncoder.encode(id,"UTF-8")
									+ "&TITLE=" + URLEncoder.encode(TITLE, "UTF-8")
									+ "&STARTTIME=" + URLEncoder.encode(STARTTIME, "UTF-8") 
									+ "&STARTLOC=" + URLEncoder.encode(STARTLOC, "UTF-8") 
									+ "&ROUTELOC=" + URLEncoder.encode(ROUTELOC, "UTF-8")
									+ "&GOALLOC=" + URLEncoder.encode(GOALLOC, "UTF-8") 
									+ "&TEXT=" + URLEncoder.encode(TEXT, "UTF-8"));

							URL url = new URL(s); // 변수값을 UTF-8로 인코딩하기 위해
													// URLEncoder를 이용하여 인코딩함

							// 정상적인 오픈 스트림이 되지 않아서 추가되는 구문
							// URLConnection t_connection =
							// url.openConnection();
							// t_connection.setReadTimeout(2000);
							// InputStream t_inputStream =
							// t_connection.getInputStream();

							// url.openConnection();

							url.openStream(); // 서버의 DB에서 해당 ID와 PW를 만족하는 녀석이
												// 있는지
							// 확인을 위해 insert.php로 ID와 PW를 넘겨준다

							String result = getXmlData("writeresult.xml", "result");
							// 입력 성공여부 확인

							if (result.equals("1")) { // result 태그값이 1일때 성공
								Toast.makeText(writeActivity.this, "성공적으로 등록하였습니다.",
										Toast.LENGTH_SHORT).show();
								
								Intent i = new Intent(getApplicationContext(),
										writeActivity.class);
								
								i.putExtra("login", true);
								i.putExtra("ID", id);
								
								startActivity(i);
								finish();
								
								// /
								// Intent i = new Intent(this,
								// Android_CarActivity.class);
								// this.startActivity(i);
								// // Close Registration View
								// finish();

								// /

								// edtID.setText("");
								// edtPW.setText("");
							} else
								// result 태그값이 1이 아닐때 실패
								Toast.makeText(writeActivity.this, "글쓰기 실패",
										Toast.LENGTH_SHORT).show();
						} catch (Exception e) {
							Log.e("Error", e.getMessage());
						}
					}
				});
			}
		});

		btnLinkToBoard.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				Intent i = new Intent(getApplicationContext(),
						BoardActivity.class);
				startActivity(i);
				// Close Registration View
				finish();
			}
		});
	}

	private String getXmlData(String filename, String str) {
		String rss = SERVER_ADDRESS + "/";
		String ret = "";

		try { // XML 파싱을 위한 과정
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser xpp = factory.newPullParser();
			URL server = new URL(rss + filename);
			InputStream is = server.openStream();
			xpp.setInput(is, "UTF-8");

			int eventType = xpp.getEventType();

			while (eventType != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_TAG) {
					if (xpp.getName().equals(str)) { // 태그 이름이 str 인자값과 같은 경우
						ret = xpp.nextText();
					}
				}
				eventType = xpp.next();
			}
		} catch (Exception e) {
			Log.e("Error", e.getMessage());
		}

		return ret;

	}

}
