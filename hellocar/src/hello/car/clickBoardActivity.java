package hello.car;

import java.io.InputStream;
import java.net.URL;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class clickBoardActivity extends Activity {

	private final String SERVER_ADDRESS = "http://kimTHproject.iptime.org/hellocar";

	Button btnRes; // 탑승신청버튼
	Button btnGotoBoard;
	
	TextView nono;
	TextView titletitle;
	TextView regregtime;
	
	
	TextView clickName;
	TextView clickCellphone;
	TextView clickStartTime;
	TextView clickStartLoc;
	TextView clickRouteLoc;
	TextView clickGoalLoc;
	TextView clickMaxRide;
	TextView clickUniv;
	TextView clickMajor;
	TextView clickCarRely;
	TextView clickText;
	
	

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.click_board);
		
		btnRes = (Button)findViewById(R.id.btnRes);
		btnGotoBoard = (Button)findViewById(R.id.btnGoboard);
		
		clickName = (TextView )findViewById(R.id.clickName);
		clickCellphone = (TextView )findViewById(R.id.clickCellphone);
		clickStartTime = (TextView )findViewById(R.id.clickStartTime);
		clickStartLoc = (TextView )findViewById(R.id.clickStartLoc);
		clickRouteLoc = (TextView )findViewById(R.id.clickRouteLoc);
		clickGoalLoc = (TextView )findViewById(R.id.clickGoalLoc);
		clickMaxRide = (TextView )findViewById(R.id.clickMaxRide);
		clickUniv = (TextView )findViewById(R.id.clickUniv);
		clickMajor = (TextView )findViewById(R.id.clickMajor);
		clickCarRely = (TextView )findViewById(R.id.clickCarRely);
		clickText = (TextView )findViewById(R.id.clickText);
		
		nono = (TextView)findViewById(R.id.nono);
		titletitle = (TextView)findViewById(R.id.titletitle);
		regregtime = (TextView)findViewById(R.id.regregtime);
		
		// TODO Auto-generated method stub

		Intent intent = getIntent();
		final String id = intent.getStringExtra("ID");
		int no = intent.getIntExtra("no", -1);
		
		
		//게시자 정보 호출 
		try {
			String s = new String(SERVER_ADDRESS
					+ "/loadBoarderInform.php?"
					+ "NO=" + no);

			URL url = new URL(s); // 변수값을 UTF-8로 인코딩하기 위해
									// URLEncoder를 이용하여 인코딩함

			url.openStream(); // 서버의 DB에서 해당 ID와 PW를 만족하는 녀석이
								// 있는지
			// 확인을 위해 insert.php로 ID와 PW를 넘겨준다


			String goalLoc = getXmlData("loadBoarderInformresult.xml", "goalLoc");
			String routeLoc = getXmlData("loadBoarderInformresult.xml", "routeLoc");
			//no는 기존에 있는 no를 사용합니다.
			String title = getXmlData("loadBoarderInformresult.xml", "title");
			String startTime = getXmlData("loadBoarderInformresult.xml", "startTime");
			String regTime = getXmlData("loadBoarderInformresult.xml", "regTime");
			String owner = getXmlData("loadBoarderInformresult.xml", "owner");
			String name = getXmlData("loadBoarderInformresult.xml", "name");
			String cellphone = getXmlData("loadBoarderInformresult.xml", "cellphone");
			
			String startLoc = getXmlData("loadBoarderInformresult.xml", "startLoc");
			String MaxRide = getXmlData("loadBoarderInformresult.xml", "MaxRide");
			String Univ = getXmlData("loadBoarderInformresult.xml", "Univ");
			String Major = getXmlData("loadBoarderInformresult.xml", "Major");
			String Text = getXmlData("loadBoarderInformresult.xml", "Text");
			
			
			// 입력 성공여부 확인
			if (no != 0) { // result 태그값이 1일때 성공
				Toast.makeText(clickBoardActivity.this, "정보로딩 성공" + owner,
						Toast.LENGTH_SHORT).show();

				clickName.setText(name);
				clickCellphone.setText(cellphone); 
				clickStartTime.setText(startTime) ;
				clickStartLoc.setText(startLoc);
				clickRouteLoc.setText(routeLoc) ;
				clickGoalLoc.setText(goalLoc) ;
				clickMaxRide.setText(MaxRide) ;
				clickUniv.setText(Univ) ;
				clickMajor.setText(Major) ;
				clickText.setText(Text) ;
				
				nono.setText(no);
				titletitle.setText(title);
				regregtime.setText(regTime);
				
				// Intent i = new Intent(this,
				// Android_CarActivity.class);
				// this.startActivity(i);
				// // Close Registration View
				// finish();

				// edtID.setText("");
				// edtPW.setText("");
			} else
				// result 태그값이 1이 아닐때 실패
				Toast.makeText(clickBoardActivity.this, "정보로딩 실패",
						Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			Log.e("Error", e.getMessage());
		}
		
		
		
		
		
		///////////////////////////탑승신청 하는부분//////////////////////////다시볼것
		btnRes.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {

				Intent i = new Intent(getApplicationContext(),
						BoardActivity.class);//이을부분
				startActivity(i);
				finish();

			}
		});
		///////////////////////////////////////////////////////////////
		
		// '게시판으로'버튼 클릭시 게시판으로 이동
		btnGotoBoard.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {

				Intent i = new Intent(getApplicationContext(),
						BoardActivity.class);
				startActivity(i);
				finish();

			}
		});
	}
	private String getXmlData(String filename, String str) { //태그값 하나를 받아오기위한 String형 함수
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
