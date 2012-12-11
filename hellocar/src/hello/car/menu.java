package hello.car;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class menu extends Activity {

	TextView IDinform;
	Button btnLogout;
	Button btnBoard;
	Button btnMyInform;
	Button btnMyModify;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.menu);

		// ��������Ʈ�� �ҷ��ͼ�
		Intent intent = getIntent();
		boolean loggedIn = intent.getBooleanExtra("login", false);
		String id2 =  intent.getStringExtra("ID");
		final String id = id2;
		// �α��εǾ��ٴ� ������ �Ѱ��ش�. ���������� �ȳѰ���ٸ� false;

		// �α��� �������� ����� �Ѿ� �Դ��� Ȯ��
		Toast.makeText(menu.this, id + "�� �������Դϴ�.", Toast.LENGTH_SHORT).show();

		
		// Check login status in database
		if (loggedIn) 
		{
			
			setContentView(R.layout.menu);

			IDinform = (TextView) findViewById(R.id.IDinform);
			btnLogout = (Button) findViewById(R.id.btnLogout);
			btnBoard = (Button) findViewById(R.id.btnBoard);
			btnMyInform = (Button) findViewById(R.id.btnMyInform);
			btnMyModify = (Button) findViewById(R.id.btnMyModify);

			
			// ����Ʈ�κ��� �޾ƿ� id������ �־��ش�
			IDinform.setText(id + "�� �������Դϴ�.");
			// �α׾ƿ� ��ư ��������
			btnLogout.setOnClickListener(new View.OnClickListener() {
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent login = new Intent(getApplicationContext(),
							Android_CarActivity.class);
					// login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(login);
					// Closing dashboard screen
					finish();
				}
			});
			// �Խ��� ��ư�� ��������
			btnBoard.setOnClickListener(new View.OnClickListener() {
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent login = new Intent(getApplicationContext(),
							BoardActivity.class);
					// login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					login.putExtra("login", true);
					login.putExtra("ID", id);

					startActivity(login);
					// Closing dashboard screen
					finish();
				}
			});
			// ������ ��ư�� ��������
			btnMyInform.setOnClickListener(new View.OnClickListener() {
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent login = new Intent(getApplicationContext(),
							myInfoActivity.class);
					// login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					login.putExtra("login", true);
					login.putExtra("ID", id);
					startActivity(login);
					// Closing dashboard screen
					finish();
				}
			});

			// ������ ��ư�� ��������
			btnMyModify.setOnClickListener(new View.OnClickListener() {
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent login = new Intent(getApplicationContext(),
							BoardActivity.class);
					// login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					
					Intent i = new Intent(getApplicationContext(),
							menu.class);
					
					
					startActivity(login);
					// Closing dashboard screen
					finish();
				}
			});

		} else {
			// user is not logged in show login screen
			Intent login = new Intent(getApplicationContext(),
					Android_CarActivity.class);
			startActivity(login);
			// Closing dashboard screen
			finish();
		}
	}
}
