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

		// 이전인텐트를 불러와서
		Intent intent = getIntent();
		boolean loggedIn = intent.getBooleanExtra("login", false);
		String id2 =  intent.getStringExtra("ID");
		final String id = id2;
		// 로그인되었다는 정보를 넘겨준다. 정상적으로 안넘겨줬다면 false;

		// 로그인 정보값이 제대로 넘어 왔는지 확인
		Toast.makeText(menu.this, id + "님 접속중입니다.", Toast.LENGTH_SHORT).show();

		
		// Check login status in database
		if (loggedIn) 
		{
			
			setContentView(R.layout.menu);

			IDinform = (TextView) findViewById(R.id.IDinform);
			btnLogout = (Button) findViewById(R.id.btnLogout);
			btnBoard = (Button) findViewById(R.id.btnBoard);
			btnMyInform = (Button) findViewById(R.id.btnMyInform);
			btnMyModify = (Button) findViewById(R.id.btnMyModify);

			
			// 인텐트로부터 받아온 id정보를 넣어준당
			IDinform.setText(id + "님 접속중입니다.");
			// 로그아웃 버튼 눌렀을때
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
			// 게시판 버튼을 눌렀을때
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
			// 내정보 버튼을 눌렀을때
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

			// 내정보 버튼을 눌렀을때
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
