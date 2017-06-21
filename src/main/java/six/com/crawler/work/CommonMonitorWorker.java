package six.com.crawler.work;

import java.util.List;

import six.com.crawler.entity.JobSnapshot;
import six.com.crawler.entity.JobSnapshotStatus;
import six.com.crawler.entity.WorkerErrMsg;
import six.com.crawler.schedule.TriggerType;
import six.com.crawler.work.exception.WorkerException;
import six.com.crawler.work.exception.WorkerMonitorException;
import six.com.crawler.work.space.WorkSpaceData;

/**
 * @author 作者
 * @E-mail: 359852326@qq.com
 * @date 创建时间：2017年6月6日 上午10:56:46
 * 
 *       监控任务worker
 * 
 */
public class CommonMonitorWorker extends AbstractMonitorWorker{

	/**
	 * 实现监控逻辑,需要循环监控的话，返回true,否则返回false监控任务线程将会结束
	 * 
	 * @return
	 * @throws WorkerException
	 */
	protected boolean doMonitor() throws WorkerException{
		JobSnapshot jobSnapshot=getManager().getScheduleCache().getJobSnapshot(getTriggerJobName());
		 //当null == jobSnapshot时表明被监控的任务结束了
	    if (null == jobSnapshot) {
	      jobSnapshot = getManager().getJobSnapshotDao().query(getTriggerJobSnapshotId(), getTriggerJobName());
	      if (null == jobSnapshot) {
	        throw new WorkerMonitorException("Job info exception!");
	      }
	      if (jobSnapshot.getStatus() == JobSnapshotStatus.STOP.value()) {
	        // 非正常结束
	        List<WorkerErrMsg> msgs = getManager().getWorkerErrMsgDao().queryByJob(getTriggerJobSnapshotId(),
	            getTriggerJobName());
	        if (msgs != null) {
	          for (int i = 0; i < msgs.size(); i++) {
	            if (msgs.get(i).getType().equals("worker_init")) {
	              finish();
	              frequencyControl();
	              // 重新调度任务
	              getManager().getMasterSchedulerManager(result->{}).execute(TriggerType.newDispatchTypeByMaster(),
	                  getTriggerJobName());
	            }
	          }
	        }
	      }
	      //返回true
	      return false;
	    } else {
	      //否则返回true，继续监控
	      return true;
	    }
	};

	@Override
	protected void onError(Exception t, WorkSpaceData workerData) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void insideDestroy() {
		// TODO Auto-generated method stub
		
	}
}
