package six.com.crawler.common.service;

import java.util.List;

import six.com.crawler.common.entity.WorkerSnapshot;

/** 
* @author  作者 
* @E-mail: 359852326@qq.com 
* @date 创建时间：2017年2月24日 下午10:35:07 
*/
public interface ScheduledService {

	/**
	 * 执行job
	 * 
	 * @param jobName
	 * @return
	 */
	public String execute(String jobName);
	
	/**
	 * 协助执行任务
	 * @param jobName
	 * @return
	 */
	public String assistExecute(String jobName);
	
	/**
	 * 暂停执行job
	 * 
	 * @param jobName
	 * @return
	 */
	public String suspend(String jobName);

	/**
	 * 继续执行job
	 * 
	 * @param jobName
	 * @return
	 */
	public String goOn(String jobName);

	/**
	 * 终止执行job
	 * 
	 * @param jobName
	 * @return
	 */
	public String stop(String jobName);
	
	
	public String scheduled(String jobName);
	
	
	public List<WorkerSnapshot> getWorkerInfo(String jobName);
}
