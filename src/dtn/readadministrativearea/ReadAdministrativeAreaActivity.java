package dtn.readadministrativearea;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import dtn.readadministrativearea.PointInPolygon.Point;

public class ReadAdministrativeAreaActivity extends Activity {

	static String tag="dtn.readadministrativearea.ReadAdministrativeAreaActivity";
	
	//���水��
	Button queryButton=null;
	EditText latEditText=null;
	EditText lonEditText=null;
	EditText resultEditText=null;
	
	private ProgressDialog progressDialog=null;
	
	//����
	public static final int CANCLE_PROGRESS_DIALOG=23;
	public static final int DISPLAY_RESULT=234;
	
	//��ѯ��ݿ�
	MapSqllite mysql=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_read_administrative_area);
		
		//���Դ���,�����ṩ�ֲ���Ϣ�ķ���
		Intent intent=new Intent(this, GetAdminAreaService.class);
		startService(intent);
		
		try {
			mysql=new MapSqllite(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		latEditText=(EditText)findViewById(R.id.editText_lat);
		lonEditText=(EditText)findViewById(R.id.editText_lon);
		resultEditText=(EditText)findViewById(R.id.editText_result);
		queryButton=(Button)findViewById(R.id.button);
		
		queryButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(v.getId()==R.id.button){
					progressDialog=ProgressDialog.show(ReadAdministrativeAreaActivity.this, "��ѯ��ݿ���", "���ڲ�ѯ�ڵ����������������Ժ�...");
					
					(new Thread(new Runnable() {
						
						@Override
						public void run() {
							/*try {
//								Thread.sleep(3000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}*/
							try{
								double lon=Double.valueOf(lonEditText.getText().toString());
								double lat=Double.valueOf(latEditText.getText().toString());
								
								Log.i(tag,String.format("查询点(%s,%s)属于哪些区域",
										lonEditText.getText().toString(),latEditText.getText().toString()));
								
								if(lon<-180d || lon >180d || lat<-90d || lat>90d){
									throw new Exception("非法的经纬度");
								}
								
								/*int x=(int)lon*10000000;
								int y=(int)lat*10000000;*/
								
								String str=mysql.getBeijingAdminstrativeLevelArea(new Point(lon*10000000, lat*10000000));
								Message m=new Message();
								m.what=DISPLAY_RESULT;
								Bundle b=new Bundle();
								b.putString("result", str);
								m.setData(b);
								handler.sendMessage(m);
								
							}
							catch(NumberFormatException e){
								e.printStackTrace();
							}
							catch(Exception e){
								e.printStackTrace();
							}
							
							Message message=new Message();
							message.what=CANCLE_PROGRESS_DIALOG;
							handler.sendMessage(message);
						}
					})).start();
				}
			}
		});
		
	}
	
	protected void onDestroy() {
		//���Դ���,�����ṩ�ֲ���Ϣ�ķ���
				Intent intent=new Intent(this, GetAdminAreaService.class);
				stopService(intent);
		super.onDestroy();
	};
	
	
	
	Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case CANCLE_PROGRESS_DIALOG:
				progressDialog.dismiss();
				break;
				
			case DISPLAY_RESULT:
				String s=msg.getData().getString("result");
				if(s!=null && !s.equals(""))
					resultEditText.setText(s);
				else
					resultEditText.setText("Ŀ��㲻�ڵ�ͼ��Χ��");
				break;
			}
		};
	};

	
}
