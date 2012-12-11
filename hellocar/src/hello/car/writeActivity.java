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
	EditText wrtRely;// �ŷڵ�����
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
		
		Toast.makeText(writeActivity.this, id + "�� �������Դϴ�.", Toast.LENGTH_SHORT).show();
		
		// �Է��� ȸ�� ������ ��Ʈ�� �ϱ����� ������
		wrtStartime = (EditText) findViewById(R.id.wrtStartime);
		wrtStartLoc = (EditText) findViewById(R.id.wrtStartLoc);
		wrtRouteLoc = (EditText) findViewById(R.id.wrtRouteLoc);
		wrtGoalLoc = (EditText) findViewById(R.id.wrtGoalLoc);
		wrtText = (EditText) findViewById(R.id.wrtText);
		wrtTitle = (EditText) findViewById(R.id.wrtTitle);
		
		// Ȯ�ι�ư�� ��ҹ�ư ����
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
					Toast.makeText(writeActivity.this, "�Է������� �������� �Է����ּ���",
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

							URL url = new URL(s); // �������� UTF-8�� ���ڵ��ϱ� ����
													// URLEncoder�� �̿��Ͽ� ���ڵ���

							// �������� ���� ��Ʈ���� ���� �ʾƼ� �߰��Ǵ� ����
							// URLConnection t_connection =
							// url.openConnection();
							// t_connection.setReadTimeout(2000);
							// InputStream t_inputStream =
							// t_connection.getInputStream();

							// url.openConnection();

							url.openStream(); // ������ DB���� �ش� ID�� PW�� �����ϴ� �༮��
												// �ִ���
							// Ȯ���� ���� insert.php�� ID�� PW�� �Ѱ��ش�

							String result = getXmlData("writeresult.xml", "result");
							// �Է� �������� Ȯ��

							if (result.equals("1")) { // result �±װ��� 1�϶� ����
								Toast.makeText(writeActivity.this, "���������� ����Ͽ����ϴ�.",
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
								// result �±װ��� 1�� �ƴҶ� ����
								Toast.makeText(writeActivity.this, "�۾��� ����",
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

		try { // XML �Ľ��� ���� ����
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser xpp = factory.newPullParser();
			URL server = new URL(rss + filename);
			InputStream is = server.openStream();
			xpp.setInput(is, "UTF-8");

			int eventType = xpp.getEventType();

			while (eventType != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_TAG) {
					if (xpp.getName().equals(str)) { // �±� �̸��� str ���ڰ��� ���� ���
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
