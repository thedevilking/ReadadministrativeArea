package dtn.readadministrativearea;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Environment;
import android.util.Log;
import dtn.readadministrativearea.PointInPolygon.Point;

/**
 * 
 * @author wwtao
 * 作用：从地图接口数据库中读取数据库的区域分层
 */
public class MapSqllite{

	static String tag="MapSqllite";
	
	Context context=null;
	
	//��ݿ��ļ�
	SQLiteDatabase database=null;
	
	//数据库的名字
//	String dbFileName="beijing_china.osm.db3";
//	String dbFileName="beijing_rectangle.osm.db3";
	String dbFileName="sumoMap.db";
	
	//��ݿ��ļ��������������
	String myDbName="map.db";
	
	//��ݿ��ļ������������ڵ�·��
	String myDbPath="";
	
	//��ݿ��ļ���File
	File dbfile; 
	
	public MapSqllite(Context context) throws Exception
	{
		this.context=context;
		
		//���sd���ϵ���ݿ��ļ����ڣ���ôֱ�Ӵ�sd���ϵ���ݿ��ļ�����
//		File dbfile=context.getExternalFilesDir("osmand-db/beijing_china.osm.db3");
		
		File dbfile=null;
		if(Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)){
			dbfile=Environment.getExternalStorageDirectory();
			dbfile=new File(dbfile,"osmand-db/"+dbFileName);
		}
		else{
			throw new Exception("the external SD card is not exist");
		}
		
		if(dbfile!=null && dbfile.exists()){
			try{
				database=SQLiteDatabase.openDatabase(dbfile.getPath(), null, SQLiteDatabase.OPEN_READONLY | SQLiteDatabase.NO_LOCALIZED_COLLATORS);
				
				Log.i(tag,String.format("open the db file %s", dbfile.getPath()));
			}
			catch(SQLiteException e)
			{
				Log.i(tag,String.format("the db file is not in the sdcard,%s",dbfile.getPath()));
				throw new Exception("the db file is not in the sdcard");
			}
		}
		else{
			throw new Exception(String.format("the db file is not in the sdcard:%s",dbfile.toString()));
			
			/*myDbPath=context.getFilesDir().getPath() +"/database/"+ myDbName;
			this.dbfile= new File(myDbPath); 
			
			Log.i(tag,myDbPath);
			
			//����assets�ļ����µ���ݿ��ļ�������Ŀ¼���棬����Ѿ��������򲻿�����
			copyDataBase();*/
		}
		
		
		
		//������ݿ��Ƿ���õĲ�ѯ���
		/*String s="select b.idRelation,b.idKey,b.idVal,d.Name,c.Text " +
				"from KeyValueRelation b,ValueNode c,KeyNode d " +
				"where b.idVal=c.id and b.idKey=d.id and  b.idRelation=912998";
		
		Cursor c=database.rawQuery(s, null);
		while(c.moveToNext())
		{
			String name = c.getString(c.getColumnIndex("Name"));
	        String text = c.getString(c.getColumnIndex("Text"));
	        Log.i(name,text);
		}*/
		
		/*int daxing=isPointInPolygon(new Point(1163751000,396443000),"2988895");
		Log.i("������",String.valueOf(daxing));
		
		int daxing2=isPointInPolygon(new Point(1163349000,397550000),"2988895");
		Log.i("������",String.valueOf(daxing2));
		
		int daxing3=isPointInPolygon(new Point(1164997000,395239000),"2988895");
		Log.i("������",String.valueOf(daxing3));
		
		List<String> idrelationlist=new ArrayList<String>();
		querySubarea(new Point(1163349000,397550000),"912940",idrelationlist);
		
		for(String id:idrelationlist){
			Log.i(tag, String.format("��p���ڵ�������:%s", id));
		}*/
		
		//��
		/*int chaoyang=isPointInPolygon(new Point(1165107000,400344000),"2988933");
		Log.i("������",String.valueOf(chaoyang));
		
		//����
		int chaoyang1=isPointInPolygon(new Point(1166494000,400344000),"2988933");
		Log.i("������",String.valueOf(chaoyang1));
		
		//��
		int chaoyang2=isPointInPolygon(new Point(1165107000,398512000),"2988933");
		Log.i("������",String.valueOf(chaoyang2));
		
		//����
		int chaoyang3=isPointInPolygon(new Point(1166494000,398512000),"2988933");
		Log.i("������",String.valueOf(chaoyang3));
		
		int beijing=isPointInPolygon(new Point(1164564000,399291000),"912940");
		Log.i("������",String.valueOf(beijing));*/
		
		/*int fengtai=isPointInPolygon(new Point(1164564000,399291000),"2988946");
		Log.i("��̨��",String.valueOf(fengtai));*/
		
		/*int pinggu=isPointInPolygon(new Point(1164564000,399291000),"2988900");
		Log.i("ƽ����",String.valueOf(pinggu));
		
		int mentougou=isPointInPolygon(new Point(1164564000,399291000),"2988898");
		Log.i("��ͷ����",String.valueOf(mentougou));
		
		int changping=isPointInPolygon(new Point(1164564000,399291000),"2988894");
		Log.i("��ƽ��",String.valueOf(changping));
		
		int yanqing=isPointInPolygon(new Point(1164564000,399291000),"2988903");
		Log.i("������",String.valueOf(yanqing));
		
		int shunyi=isPointInPolygon(new Point(1164564000,399291000),"2988901");
		Log.i("˳����",String.valueOf(shunyi));
		
		int huairou=isPointInPolygon(new Point(1164564000,399291000),"2988897");
		Log.i("������",String.valueOf(huairou));
		
		int tongzhou=isPointInPolygon(new Point(1164564000,399291000),"2988902");
		Log.i("ͨ����",String.valueOf(tongzhou));
		
		int fangshan=isPointInPolygon(new Point(1164564000,399291000),"2988896");
		Log.i("��ɽ��",String.valueOf(fangshan));
		
		int daxing=isPointInPolygon(new Point(1164564000,399291000),"2988895");
		Log.i("������",String.valueOf(daxing));
		
		int miyun=isPointInPolygon(new Point(1164564000,399291000),"2988899");
		Log.i("������",String.valueOf(miyun));*/
	}

	/**
	 * �����ݿ��ļ��Ƿ���ڣ��Ƿ�ɱ���
	 * @return
	 */
	private boolean checkDbExist()
	{
		try{
			database=SQLiteDatabase.openDatabase(myDbPath, null, SQLiteDatabase.OPEN_READONLY | SQLiteDatabase.NO_LOCALIZED_COLLATORS);

		}
		catch(SQLiteException e)
		{
//			e.printStackTrace();
			database=null;
			return false;
		}
		return true;
		
	}
	
	/**
	 * ����ݿ��ļ���assets�ļ����¿�����/data��
	 * @throws Exception
	 */
	private void copyDataBase() throws Exception
	{
		if(!checkDbExist())
		{
			Log.i(tag, "database is not exist.we'll copy the database file");
			System.out.println("database is not exist.we'll copy the database file");
		}
		else
		{
			return ;
		}
		
		
		
		try {
			InputStream in=this.context.getResources().getAssets().open(dbFileName);
			if(!dbfile.getParentFile().isDirectory())
				dbfile.getParentFile().mkdir();
			if(!dbfile.exists())
				dbfile.createNewFile();
			FileOutputStream out=new FileOutputStream(dbfile);
			byte[] bswap=new byte[1024];
			int length=0;
			while((length=in.read(bswap))>0){
				out.write(bswap, 0, length);
				
			}
			out.flush();
			out.close();
			in.close();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.i(tag, "no db file in the assets");
			e.printStackTrace();
		}
		
		//����Ƿ񿽱���ݿ��ļ��ɹ�
		if(checkDbExist())
		{
			Log.i(tag, "copy db file successfully");
		}
		else
		{
			Log.i(tag, "copy db file failed");
			throw new Exception("copy db file failed");
		}
	}
	
	/**
	 * Ĭ�����ڱ����У���ѯ�ڵ����ڸ���ε�����
	 * @return �����ظ�����ε�id������
	 */
	public String getBeijingAdminstrativeLevelArea(Point p){
		List<String> idRelationlist=new ArrayList<String>(10);
		//Ĭ�����ڱ����У����뱱���е�id
		idRelationlist.add("912940");
		querySubarea(p, idRelationlist.get(0), idRelationlist);
		
		String str="";
		for(String s:idRelationlist){
			s=queryAreaName(s);
			str+=s+",";
//			Log.i(tag,String.format("idRelation��������%s", s));
		}
//		Log.i(tag,String.format("���ص������ַ�Ϊ��%s", str));
		return str;
	}
	
	/**
	 * ��ȡ��idrelation�¶�Ӧ������������
	 * @param idRelation ��Ҫ��ѯ���ֵ�������id
	 * @return �������ַ���ʽΪ��������id(����������)
	 */
	public String queryAreaName(String idRelation){
		String sql=String.format("select a.idRelation,b.Name,c.Text " +
				"from KeyValueRelation a,KeyNode b,ValueNode c " +
				"where a.idKey=b.id and a.idVal=c.id and b.Name='name' and a.idRelation=%s",idRelation);
		
		Cursor c=database.rawQuery(sql, null);
		if(c.moveToNext()){
			String name=c.getString(c.getColumnIndex("Text"));
			return String.format("%s(%s)", idRelation,name);
		}
		else
			return String.format("%s()", idRelation);
	}
	
	/**
	 * �ݹ��ѯ�������Ƿ���λ��
	 * @param idRelationList
	 */
	public void querySubarea(Point p,String idRelation,List<String> idRelationList){
		String sql="select a.idRef,b.Name from Relation a,RelationRole b " +
				"where a.idRole=b.id and b.Name='subarea' and a.idRelation="+idRelation;
		
		Cursor cursor=database.rawQuery(sql, null);
		while(cursor.moveToNext()){
			String temp_idRelation=cursor.getString(cursor.getColumnIndex("idRef"));
			Log.i(tag, String.format("查询区域%s是否包含点(%f,%f)",temp_idRelation,p.x,p.y));
			if(isPointInPolygon(p, temp_idRelation)==1){
				idRelationList.add(temp_idRelation);
				Log.i(tag,String.format("节点属于该区域%s", temp_idRelation));
				querySubarea(p, temp_idRelation, idRelationList);
				break;//地区的属于都是唯一的，属于某一个子区域后其余的就不用找了
			}
		}
	}
	
/**
 * 记录边
 */
	private List<Line> lineList=new ArrayList<Line>();
	public int isPointInPolygon(Point p,String idRelation){
		//��ѯ�߽���sql��䣬��Ҫ��֤����ÿһ��way���棬way��node�Ķ�Ӧ��ʽ�ǰ���Position��˳�����洢��
		/**
		 * select a.idRelation,b.idWay,b.idNode,c.Name,d.lat,d.lon 
		 * from Relation a,Way b,RelationRole c,Node d 
		 * where b.idNode=d.id and a.idRef=b.idWay and a.idRole=c.id and (c.Name='inner' or c.Name='outer') and a.idRelation=912940;
		 */
		String sql="select a.idRelation,b.idWay,b.Position,b.idNode,c.Name,d.lat,d.lon " +
				"from Relation a,Way b,RelationRole c,Node d " +
				"where b.idNode=d.id and a.idRef=b.idWay and a.idRole=c.id and (c.Name='inner' or c.Name='outer')" +
				" and a.idRelation="+idRelation;
		Cursor c=database.rawQuery(sql, null);
		
		//���Ϊ�գ���˵��û�鵽�߽磬���ؿ�
		if(c==null || c.getCount()<=0)
			return 0;
		
		//������¼��ǰ�ı߽���outer����inner,�ж��Ƿ���Ҫ�л�line
//		String type=null;
		lineList.clear();
		Line line=null; 
		Way way=null;
		
		//��С����η�Χ
		int maxLon=Integer.MIN_VALUE;
		int maxLat=Integer.MIN_VALUE;
		int minLon=Integer.MAX_VALUE;
		int minLat=Integer.MAX_VALUE;
		
		while(c.moveToNext()){
			String temp_idRelation=c.getString(c.getColumnIndex("idRelation"));
			String temp_idWay=c.getString(c.getColumnIndex("idWay"));
			int temp_position=Integer.valueOf(c.getString(c.getColumnIndex("Position")));
			String temp_idNode=c.getString(c.getColumnIndex("idNode"));
			int lat=Integer.valueOf(c.getString(c.getColumnIndex("lat")));
			int lon=Integer.valueOf(c.getString(c.getColumnIndex("lon")));
			
			String temp_type=c.getString(c.getColumnIndex("Name"));
			
			//��ȡ�ĵ�һ���ڵ�
			if(line==null || way==null){
//				type=temp_type;
				line=new Line(temp_idRelation,temp_type);
				lineList.add(line);
				
				way=new Way(temp_idWay, temp_type);
				line.add(way);
			}
			else{
				//读到一个多边形不同的type，由outer转inner，表示一个完整的线段读出来了，而一个点是否属于这个多边形看包含它最里层的多边形是不是outer
				if(!line.type.equals(temp_type)){
//					type=temp_type;
					line=new Line(temp_idRelation,temp_type);
					
					way.sortByPosition();
					way=new Way(temp_idWay, temp_type);
					line.add(way);
				}
				//读到了不同的线段
				else if(!way.idWay.equals(temp_idWay)){
					way.sortByPosition();
					way=new Way(temp_idWay, temp_type);
					line.add(way);
				}
				
			}
			
			//��node���뵽way��
			way.add(new Node(temp_idNode, lon, lat, temp_position));
			
			//�ж���С����ε�
			if(lat<minLat)
				minLat=lat;
			if(lat>maxLat)
				maxLat=lat;
			
			if(lon<minLon)
				minLon=lon;
			if(lon>maxLon)
				maxLon=lon;
		}
		//对最后一个线段排序
		way.sortByPosition();
		
		//���ԣ�������е�way��id
		/*for(Line temp_line:lineList)
		{
			for(Way temp_way:temp_line){
				String temp_idway=temp_way.idWay;
				for(Node temp_node:temp_way){
					Log.i(temp_idway,temp_node.idNode+","+temp_node.position);
				}
			}
		}*/
		/*
		//�жϵ���Ŀ���������󾭶ȷ�Χ��
		if(p.x<minLon || p.x>maxLon)
		{
			Log.i(tag,"Ŀ����������ȷ�Χ֮��");
			return 0;
		}
		//�жϵ���Ŀ����������γ�ȷ�Χ
		if(p.y<minLat || p.y>maxLat)
		{
			Log.i(tag,"Ŀ����������γ�ȷ�Χ֮��");
			return 0;
		}*/
		
		//记录包含点的最有一个边界类型，是inner还是outer
		String inType=null;
		for(Line temp:lineList){
			List<Point> list=temp.getPolygon();
			//���߽粻��ȫ���򷵻�false
			if(list==null)
				return -1;
			
			if(PointInPolygon.pointInPolygon(p, list)){
				inType=temp.type;
			}
			else//����жϵ�һ�����ڵıջ��߽������棬���˳�ѭ��
				break;
		}
		
		//���������ڵ���outer�߽磬��ô�������������������
		if(inType!=null && inType.equals("outer")){
			return 1;
		}
		else{
			Log.i(tag,"Ŀ�겻�����κ�outer����������inner�߽�����;type="+inType);
			return 0;
		}
	}
	
	//sqlite�еĵ�
	public class Node{
		public String idNode;
		public int lon;
		public int lat;
		public int position;//һ��way����ĵ��λ�ã�����������ʹ��
		
		public Node(String idNode,int lon,int lat,int position)
		{
			this.idNode=idNode;
			this.lon=lon;
			this.lat=lat;
			this.position=position;
		}
		
		public Point toPoint()
		{
			return new Point(lon,lat);
		}
		
		@Override
		public boolean equals(Object o) {
			// TODO Auto-generated method stub
			if(!(o instanceof Node))
				return false;
			
			return this.idNode.equals(((Node)o).idNode);
		}
	}

	//������߽��е�һ���߶�
	public class Way extends ArrayList<Node>{
		
		private static final long serialVersionUID = -7116607006510557164L;
		
		public String idWay;
//		public String type;//�����SelectWayNode��outer����inner
		
		public Way(String idWay,String type)
		{
			this.idWay=idWay;
//			this.type=type;
		}
		
		//��way��������е㣬����������ݿ��е�position����������
		public void sortByPosition(){
			Collections.sort(this, new Comparator<Node>() {

				@Override
				public int compare(Node lhs, Node rhs) {
					// TODO Auto-generated method stub
					if(lhs.position<rhs.position)
						return 1;
					else
						return -1;
				}
			});
		}
		
		
		/**
		 * ��ݸ����һway��ĩβ�㣬�ҵ���way����㣬��˳����뵽list�У��ٷ��ر�way��ĩβ�˵�
		 * @param node :��һway��ĩβ�˵�
		 * @param list ����ɱ߽�ĵ��б�
		 * @return ������way��ĩβ�˵㣬���Ϊnull˵����һ��way�ͱ�way�������ŵ�
		 */
		public Node getOrderPoints(Node node,List<Point> list){
//			Collection<Point> c=new ArrayList<Point>();
			int index=this.indexOf(node);
			if(index!=-1){
				//���մ���С��������Point����
				if(index==0){
					for(int i=0;i<this.size();i++){
						list.add(this.get(i).toPoint());
					}
					return this.get(this.size()-1);
				}
				//���մ������С��ʼ���Point����
				else if(index==(this.size()-1)){
					for(int i=index;i>=0;i--){
						list.add(this.get(i).toPoint());
					}
					return this.get(0);
				}
				else{
					return null;
				}
				
					
			}
			return null;
//			return c;
		}
		
		
	}
	
	//������߽���һ���ջ��߽磬������outerҲ������inner
	public class Line extends ArrayList<Way>{

		private static final long serialVersionUID = 8235873185083847058L;
		String idRelation;
		String type;
		public Line(String idRelation,String type)
		{
			this.idRelation=idRelation;
			this.type=type;
		}
		
		/**
		 * 返回多边形点的序列
		 * @return :����������ı߽�������ô������һ��points���б?��������򷵻�null
		 */
		public List<Point> getPolygon(){
			List<Point> polygon=new ArrayList<PointInPolygon.Point>();
			
			Way lastway=this.get(this.size()-1);
			
			//找到一个起点
			/*Node start=lastway.get(lastway.size()-1);
			if(this.get(0).indexOf(start)==-1){
				start=lastway.get(0);
				if(this.get(0).indexOf(start)==-1)
					return null;
			}
			
			//将每个线段的首尾按照顺序串联起来
			for(int i=0;i<this.size()-1;i++){
				Way temp=this.get(i);
				Node t=temp.getOrderPoints(start, polygon);
				if(t==null){
					Log.i(tag,String.format("way(%s)找不到上一个点作为起点way size:%d,node_id:%s,node_position:%d",temp.idWay,temp.size(),start.idNode,start.position));
					return null;
				}
				start=t;
			}*/
			
			//**改进的判断版本，防止两个点时无法挑准首节点
			Node start=lastway.get(lastway.size()-1);//先用最后一个边的最后一个点作为起点
			Way temp=null;
			boolean b=true;
			for(int i=0;i<this.size()-1;i++){
				temp=this.get(i);
				Node t=temp.getOrderPoints(start, polygon);
				if(t==null){
//					Log.i(tag,String.format("way(%s)找不到上一个点作为起点way size:%d,node_id:%s,node_position:%d",temp.idWay,temp.size(),start.idNode,start.position));
					b=false;
					break;
				}
				start=t;
			}
			if(b)
				return polygon;
			
			b=true;//重置标志位
			polygon.clear();
			start=lastway.get(0);//先用最后一个边的第一个点作为起点
			for(int i=0;i<this.size()-1;i++){
				temp=this.get(i);
				Node t=temp.getOrderPoints(start, polygon);
				if(t==null){
//					Log.i(tag,String.format("way(%s)找不到上一个点作为起点way size:%d,node_id:%s,node_position:%d",temp.idWay,temp.size(),start.idNode,start.position));
					b=false;
					break;
				}
				start=t;
			}
			if(b)
				return polygon;
			else{
				Log.i(tag,String.format("way(%s)找不到上一个点作为起点way size:%d,node_id:%s,node_position:%d",temp.idWay,temp.size(),start.idNode,start.position));
				return null;
			}
		}
	}
}
