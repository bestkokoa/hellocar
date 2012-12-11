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

	Button btnRes; // ž�½�û��ư
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
		
		
		//�Խ��� ���� ȣ�� 
		try {
			String s = new String(SERVER_ADDRESS
					+ "/loadBoarderInform.php?"
					+ "NO=" + no);

			URL url = new URL(s); // �������� UTF-8�� ���ڵ��ϱ� ����
									// URLEncoder�� �̿��Ͽ� ���ڵ���

			url.openStream(); // ������ DB���� �ش� ID�� PW�� �����ϴ� �༮��
								// �ִ���
			// Ȯ���� ���� insert.php�� ID�� PW�� �Ѱ��ش�


			String goalLoc = getXmlData("loadBoarderInformresult.xml", "goalLoc");
			String routeLoc = getXmlData("loadBoarderInformresult.xml", "routeLoc");
			//no�� ������ �ִ� no�� ����մϴ�.
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
			
			
			// �Է� �������� Ȯ��
			if (no != 0) { // result �±װ��� 1�϶� ����
				Toast.makeText(clickBoardActivity.this, "�����ε� ����" + owner,
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
				// result �±װ��� 1�� �ƴҶ� ����
				Toast.makeText(clickBoardActivity.this, "�����ε� ����",
						Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			Log.e("Error", e.getMessage());
		}
		
		
		
		
		
		///////////////////////////ž�½�û �ϴºκ�//////////////////////////�ٽú���
		btnRes.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {

				Intent i = new Intent(getApplicationContext(),
						BoardActivity.class);//�����κ�
				startActivity(i);
				finish();

			}
		});
		///////////////////////////////////////////////////////////////
		
		// '�Խ�������'��ư Ŭ���� �Խ������� �̵�
		btnGotoBoard.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {

				Intent i = new Intent(getApplicationContext(),
						BoardActivity.class);
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
