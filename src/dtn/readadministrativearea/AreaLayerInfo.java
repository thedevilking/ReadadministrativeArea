package dtn.readadministrativearea;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * ������¼���GPS���ڵĸ����������ֲ����Ϣ
 * ��Ϊ�յ�����ֲ���Ϣ�󷵻طֲ����Ϣ
 * @author wwtao
 *
 */
public class AreaLayerInfo extends ArrayList<Integer> implements Serializable{
	@Override
	public boolean equals(Object o) {
		if(this == o)
			return true;
		
		if(! (o instanceof AreaLayerInfo)){
			return false;
		}
		
		AreaLayerInfo other=(AreaLayerInfo)o;
		if(this.size()!=other.size()){
			return false;
		}
		
		for(int i=0;i<this.size();i++){
			if(this.get(i)!=other.get(i))
				return false;
		}
		return true;
	}
}
