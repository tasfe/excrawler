package six.com.crawler.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import six.com.crawler.cluster.ClusterManager;
import six.com.crawler.common.entity.Node;

/**
 * @author 作者
 * @E-mail: 359852326@qq.com
 * @date 创建时间：2017年3月17日 下午4:05:07
 */
@Component
public class MasterSchedulerManagerClient extends RemotingClient {

	@Autowired
	private ClusterManager clusterManager;

	public ClusterManager getClusterManager() {
		return clusterManager;
	}

	public void startWorker(String jobName) {
		Node masterNode = clusterManager.getMasterNode();
		String path = "/crawler/master/scheduled/startWorker/" + jobName;
		doExecute(masterNode, path);
	}

	public void endWorker(String jobName) {
		Node masterNode = clusterManager.getMasterNode();
		String path = "/crawler/master/scheduled/endWorker/" + jobName;
		doExecute(masterNode, path);
	}

}
