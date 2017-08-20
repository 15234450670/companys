package mr.li.dance.ui.activitys.music;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
/**
 * 
 * @author ��˹ͻ��С�ֶ�
 *	����ӿ��� 
 */
public class ServiceConn implements ServiceConnection{

	private binderCreateFinish bcf;
	
	@Override
	public void onServiceConnected(ComponentName name, IBinder service) {
		MusicService.MyBinder mb = (MusicService.MyBinder) service;
		if(bcf!=null){
			bcf.binderHasCreated(mb);
		}
	}

	@Override
	public void onServiceDisconnected(ComponentName name) {
		
	}

	public interface binderCreateFinish{
		void binderHasCreated(MusicService.MyBinder mb);
	}
	
	public void getMyBinder(binderCreateFinish bcf){
		this.bcf = bcf;
	}
	
}
