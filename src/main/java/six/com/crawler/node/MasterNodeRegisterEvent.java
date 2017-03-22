package six.com.crawler.node;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import six.com.crawler.entity.Node;
import six.com.crawler.utils.JavaSerializeUtils;

/**
 * @author 作者
 * @E-mail: 359852326@qq.com
 * @date 创建时间：2017年3月16日 上午11:56:39
 */
public class MasterNodeRegisterEvent extends NodeRegisterEvent {

	final static Logger log = LoggerFactory.getLogger(MasterNodeRegisterEvent.class);

	public MasterNodeRegisterEvent(Node currentNode) {
		super(currentNode);
	}

	@Override
	public boolean doRegister(NodeManager clusterManager, CuratorFramework zKClient) {
		try {
			Node masterNode = clusterManager.getMasterNode();
			if (null == masterNode || masterNode.equals(getCurrentNode())) {
				byte[] data = JavaSerializeUtils.serialize(getCurrentNode());
				zKClient.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL)
						.forPath(ZooKeeperPathUtils.getMasterNodePath(getCurrentNode().getName()), data);
				return true;
			}
		} catch (Exception e) {
			log.error("", e);
		}
		return false;
	}

	@Override
	public void process(WatchedEvent arg0) {

	}
}