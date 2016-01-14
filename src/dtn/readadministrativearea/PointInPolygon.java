package dtn.readadministrativearea;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/** 
 * @author wwtao thedevilking@qq.com: 
 * @version 创建时间�?015-8-27 上午10:10:35 
 * 说明 
 */
public class PointInPolygon {
	
	/*public static void main(String[] arg)
	{
		
		//测试点是否在多边形内
		List<Double> x=new ArrayList<Double>();
		List<Double> y=new ArrayList<Double>();
		
		x.add(2d);
		x.add(1d);
		x.add(1d);
		x.add(2d);
		x.add(2d);
		
		x.add(1d);
		x.add(2d);
		x.add(3d);
		x.add(3d);
		x.add(4d);
		
		x.add(4d);
		x.add(3d);
		x.add(3d);
		x.add(5d);
		x.add(5d);
		
		y.add(1d);
		y.add(2d);
		y.add(3d);
		y.add(2d);
		y.add(3d);
		
		y.add(4d);
		y.add(4d);
		y.add(3d);
		y.add(2d);
		y.add(2d);
		
		y.add(3d);
		y.add(4d);
		y.add(5d);
		y.add(5d);
		y.add(1d);
		
		try {
			System.out.println("错误的版�?);
			
			System.out.println(pointInPolygon1(1d,1d,x,y));
			System.out.println(pointInPolygon1(0d,0d,x,y));
			System.out.println(pointInPolygon1(2d,1d,x,y));
			System.out.println(pointInPolygon1(3d,1d,x,y));
			System.out.println(pointInPolygon1(4d,4d,x,y));
			System.out.println(pointInPolygon1(2d,5d,x,y));
			
			System.out.println("---------------------");
			
			System.out.println(pointInPolygon(1d,1d,x,y));
			System.out.println(pointInPolygon(0d,0d,x,y));
			System.out.println(pointInPolygon(2d,1d,x,y));
			System.out.println(pointInPolygon(3d,1d,x,y));
			System.out.println(pointInPolygon(4d,4d,x,y));
			System.out.println(pointInPolygon(2d,5d,x,y));
			
			System.out.println("---------------------");
			
			List<Point> ptPolygon=new ArrayList<Point>();
//			Point[] ptPolygon=new Point[x.size()];
			for(int i=0;i<x.size();i++)
			{
//				ptPolygon[i]=;
				ptPolygon.add(new Point(x.get(i),y.get(i)));
			}
			
			Point p1=new Point(1d,1d);
			Point p2=new Point(0d,0d);
			Point p3=new Point(2d,1d);
			Point p4=new Point(3d,1d);
			Point p5=new Point(4d,4d);
			Point p6=new Point(2d,5d);
			
			System.out.println(pointInPolygon(p1,ptPolygon,x.size()));
			System.out.println(pointInPolygon(p2,ptPolygon,x.size()));
			System.out.println(pointInPolygon(p3,ptPolygon,x.size()));
			System.out.println(pointInPolygon(p4,ptPolygon,x.size()));
			System.out.println(pointInPolygon(p5,ptPolygon,x.size()));
			System.out.println(pointInPolygon(p6,ptPolygon,x.size()));
			
			
//			Point[] p=new Point[listpoint.size()];
//			listpoint.toArrayp(p);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/

	static boolean pointInPolygon1(double x,double y,List<Double> polygonX,List<Double> polygonY) throws Exception
	{
		boolean in=false;
		
		//对多边形周边点的数组的合法�?判断
		if(polygonX==null || polygonY==null || 
				polygonX.size()!=polygonY.size() || polygonX.isEmpty())
		{
			throw (new Exception("多边形的点数组不合法"));
		}
		
		int crossCount=0;
		for(int i=0;i<polygonX.size();i++)
		{
			int p1=i;
			int p2=(i+1)%polygonX.size();
			
			//如果待测节点是多边形的顶点，认为在多边形�?
			if((x==polygonX.get(p1) && y==polygonY.get(p1))||(x==polygonX.get(p2) && y==polygonY.get(p2)))
			{
//				in=true;
				crossCount=1;
				break;
			}
			
			//特殊情况
			//多边形相邻两节点的y坐标�?��
			if(polygonY.get(p1)==polygonY.get(p2))
			{
				continue;
			}
			
			//待测节点不在两节点的y轴之�?
			if(y<polygonY.get(p1) && y<polygonY.get(p2))
				continue;
			if(y>=polygonY.get(p2) && y>=polygonY.get(p2))
				continue;
			
			//求交点的x坐标�?
			double result=(double)(y-polygonY.get(p1))*(double)(polygonX.get(p2)-polygonX.get(p1))
					/((double)(polygonY.get(p2)-polygonY.get(p1))) + polygonX.get(p1);
			
			if(result>x)
				++crossCount;
		}
		
		return crossCount%2==1;
	}
	
	//�������ҵ��жϵ��ڶ���η��������������һ��Բ����ʱ���жϲ�׼ȷ
	public static boolean pointInPolygon(double x,double y,List<Double> polygonX,List<Double> polygonY) throws Exception
	{
		boolean in=false;
		
		//对多边形周边点的数组的合法�?判断
		if(polygonX==null || polygonY==null || 
				polygonX.size()!=polygonY.size() || polygonX.isEmpty())
		{
			throw (new Exception("多边形的点数组不合法"));
		}
		
		
		double minx=Double.MAX_VALUE;
		double maxx=Double.MIN_VALUE;
		double miny=Double.MAX_VALUE;
		double maxy=Double.MIN_VALUE;
		
		Iterator<Double> iter=polygonX.iterator();
		while(iter.hasNext())
		{
			double temp=iter.next();
			if(temp>maxx)
				maxx=temp;
			
			if(temp<minx)
				minx=temp;
		}
		
		iter=polygonY.iterator();
		while(iter.hasNext())
		{
			double temp=iter.next();
			if(temp>maxy)
				maxy=temp;
			
			if(temp<miny)
				miny=temp;
		}
		
		if(x<minx || x>maxx || y<miny || y>maxy)
		{
			return false;
		}
		
		
		int i,j;
		for(i=0,j=polygonX.size()-1;i<polygonX.size();j=i++)
		{
			if(( (polygonY.get(i)>y) != (polygonY.get(j)>y)) 
					&& ( x< (polygonX.get(j)-polygonX.get(i)) * (y-polygonY.get(i)) / (polygonY.get(j)-polygonY.get(i)) + polygonX.get(i)) )
			{
				in=!in;
			}
		}
		
		
		return in;
	}
	
	//ʹ�ô˷�������ȫ�հ������ֹ������㷨
	public static boolean pointInPolygon(Point p,List<Point> ptPolygon)
	{
		int nCount=ptPolygon.size();
		int nCross=0;
		for(int i=0;i<nCount;i++)
		{
			Point p1=ptPolygon.get(i);
			Point p2=ptPolygon.get((i+1)%nCount);
			
			//如果目的点就是多边形顶点，认为目的点在多边形内
			if((p.x==p1.x && p.y==p1.y)||(p.x==p2.x && p.y==p2.y))
			{
				nCross=1;
				break;
			}
			
			//特殊情况，多边形的该边与射线平行
			if(p1.y==p2.y)
				continue;
			//射线与该条边不相交
			if(p.y<p1.y && p.y<p2.y)
				continue;
			if(p.y >= p1.y && p.y >=p2.y)
				continue;
			
			//计算并统计交点个数
			double x=(double)(p.y-p1.y) * (double)(p2.x-p1.x)/(double)(p2.y-p1.y)+p1.x;
			if(x>p.x)
				++nCross;
		}
		
		return (nCross%2==1);
	}
	
	public static Point generatePoint(double x,double y)
	{
		return new Point(x, y);
	}
	
	public static class Point
	{
		/**
		 * lon
		 */
		public double x;
		
		/**
		 * lat
		 */
		public double y;
		
		/**
		 * 
		 * @param x ��lon
		 * @param y :lat
		 */
		Point(double x,double y)
		{
			this.x=x;
			this.y=y;
		}
		
		@Override
		public boolean equals(Object o) {
			if(o instanceof Point)
			{
				Point another=(Point)o;
				if(this.x==another.x && this.y==another.y)
				{
					return true;
				}
				else
					return false;
			}
			return false;
//			return super.equals(o);
		}
	}
}
