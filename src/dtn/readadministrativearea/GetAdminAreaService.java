package dtn.readadministrativearea;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import dtn.readadministrativearea.PointInPolygon.Point;

/**
 * 
 * @author wwtao
 * 说明：等待客户端请求，并提供分区分层的服务
 */
public class GetAdminAreaService extends Service{

	private static String tag="GetAdminAreaService";
	
	//监听请求的端口
	private static int receivePort=63301;
	
	//是否关闭线程
	private static boolean shutdown=false;
	
	//请求的ip和所在区域层次的缓存
	HashMap<String,AreaLayerInfo> areaLayerlist=new HashMap<String,AreaLayerInfo>();
	
	//数据库访问
	MapSqllite mysql=null;
	
	DatagramSocket server=null;
	
	@Override
	public void onCreate() {
		Log.i(tag,"启动分层信息提供服务");
		try {
			mysql=new MapSqllite(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		start();
		super.onCreate();
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		shutdown();
		Log.d(tag,"service onDestroy");
		super.onDestroy();
	}
	
	private void start(){
		waitRequestThread.start();
	}
	
	private void shutdown(){
		shutdown=true;
		if(server!=null)
			server.close();
	}
	
	/**
	 * 相应请请求线程
	 */
	private Thread waitRequestThread=new Thread(new Runnable() {
		
		
		@Override
		public void run() {
			
			try {
				server=new DatagramSocket(receivePort);
				byte[] buff = new byte[1024];
				DatagramPacket dpacket=new DatagramPacket(buff, buff.length);
				ByteArrayOutputStream byteout=new ByteArrayOutputStream(1024);
				DataInputStream din;
				DataOutputStream dout;
				while(true){
					if(shutdown){
						break;
					}
					
					try {
						Log.i(tag,"wait for request");
						server.receive(dpacket);
						
						int port=dpacket.getPort();
						InetAddress addr=dpacket.getAddress();
						din=new DataInputStream(new ByteArrayInputStream(dpacket.getData()));
						String ip=din.readUTF();
						double longitude=din.readDouble();
						double latitude=din.readDouble();
						din.close();
						
						
						if(longitude<-180d || longitude >180d || latitude<-90d || latitude>90d){
							throw new Exception("非法的经纬度");
						}
						
						AreaLayerInfo info=getLayerInfoByGps(ip, longitude, latitude);
						if(info!=null){
							byteout.reset();
							dout=new DataOutputStream(byteout);
							dout.writeInt(info.size());
							for(int i=0;i<info.size();i++){
								dout.writeInt(info.get(i));
							}
							byte[] temp=byteout.toByteArray();
							DatagramPacket sendpacket=new DatagramPacket(temp,temp.length,addr,port);
							server.send(sendpacket);
							dout.close();
						}
						
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Log.e(tag,"��ͼ�ӿڳ������gps����ʱ����");
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				server.close();
				server=null;
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				if(server!=null)
					server.close();
				
			}
		}
	});
	
	
	
	private AreaLayerInfo getLayerInfoByGps(String ip,double longitude,double latitude){
		AreaLayerInfo layerInfo=areaLayerlist.get(ip);
		Point p=new Point(longitude*10000000, latitude*10000000);
		if(layerInfo!=null){
			int layerid=layerInfo.get(layerInfo.size()-1);
//			mysql.getBeijingAdminstrativeLevelArea(new Point(longitude, latitude));
			if(mysql.isPointInPolygon(p, String.valueOf(layerid))==1){
				Log.i(tag,String.format("ip(%s)所在的位置和上次所在的位置一样",ip));
				return layerInfo;
			}
			else{
				//和上次最底层区域不一样，由于只有三层，所以不再递归找到同样的一层了，直接重新计算
			}
		}
		List<String> idRelationlist=new ArrayList<String>(10);
		idRelationlist.add("270056");
		idRelationlist.add("912940");
		mysql.querySubarea(p, idRelationlist.get(1), idRelationlist);
		AreaLayerInfo areaLayerInfo=new AreaLayerInfo();
		for(int i=0;i<idRelationlist.size();i++){
			areaLayerInfo.add(Integer.valueOf(idRelationlist.get(i)));
		}
		areaLayerlist.put(ip, areaLayerInfo);
		
		//查找对应层次的名字
		String str="";
		for(String s:idRelationlist){
			s=mysql.queryAreaName(s);
			str+=s+",";
		}
		Log.i(tag,"所在区域分层："+str);
		return areaLayerInfo;
	}
}
