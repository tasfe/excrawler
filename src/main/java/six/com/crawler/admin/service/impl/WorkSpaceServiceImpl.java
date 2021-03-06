package six.com.crawler.admin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import six.com.crawler.admin.api.ResponseMsg;
import six.com.crawler.admin.service.BaseService;
import six.com.crawler.admin.service.WorkSpaceService;
import six.com.crawler.admin.vo.PageVo;
import six.com.crawler.entity.Page;
import six.com.crawler.entity.WorkSpaceInfo;
import six.com.crawler.work.space.WorkSpace;
import six.com.crawler.work.space.WorkSpaceData;
import six.com.crawler.work.space.WorkSpaceManager;

/**
 * @author 作者
 * @E-mail: 359852326@qq.com
 * @date 创建时间：2017年3月8日 下午3:27:27
 */

@Service
public class WorkSpaceServiceImpl extends BaseService implements WorkSpaceService {

	final static Logger log = LoggerFactory.getLogger(WorkSpaceServiceImpl.class);

	@Autowired
	private WorkSpaceManager workSpaceManager;

	public ResponseMsg<List<WorkSpaceInfo>> getWorkSpaces() {
		ResponseMsg<List<WorkSpaceInfo>> responseMsg = createResponseMsg();
		List<WorkSpaceInfo> workSpaceInfos = new ArrayList<>();
		List<WorkSpace<WorkSpaceData>> allWorkSpaces = workSpaceManager.getAllWorkSpaces();
		WorkSpaceInfo workSpaceInfo = null;
		for (WorkSpace<WorkSpaceData> workSpace : allWorkSpaces) {
			workSpaceInfo = new WorkSpaceInfo();
			workSpaceInfo.setWorkSpaceName(workSpace.getName());
			workSpaceInfo.setDoingSize(workSpace.doingSize());
			workSpaceInfo.setErrSize(workSpace.errSize());
			workSpaceInfo.setDoneSize(workSpace.doneSize());
			workSpaceInfos.add(workSpaceInfo);
		}
		responseMsg.setIsOk(1);
		responseMsg.setData(workSpaceInfos);
		return responseMsg;
	}

	public WorkSpaceInfo getWorkSpaceInfo(String workSpaceName) {
		WorkSpaceInfo workSpaceInfo = new WorkSpaceInfo();
		if (StringUtils.isNotBlank(workSpaceName)) {
			WorkSpace<WorkSpaceData> workSpace = workSpaceManager.newWorkSpace(workSpaceName, WorkSpaceData.class);
			workSpaceInfo.setWorkSpaceName(workSpaceName);
			workSpaceInfo.setDoingSize(workSpace.doingSize());
			workSpaceInfo.setErrSize(workSpace.errSize());
			workSpaceInfo.setDoneSize(workSpace.doneSize());
		}
		return workSpaceInfo;
	}

	public ResponseMsg<Map<String, Object>> getWorkSpaceDoingData(String workSpaceName, String cursor) {
		ResponseMsg<Map<String, Object>> responseMsg = createResponseMsg();
		Map<String, Object> resultMap = new HashMap<>();
		List<WorkSpaceData> list = new ArrayList<>();
		cursor = workSpaceManager.newWorkSpace(workSpaceName, WorkSpaceData.class).batchGetDoingData(list, 0, cursor);
		resultMap.put("cursor", cursor);
		resultMap.put("list", list);
		responseMsg.setIsOk(1);
		responseMsg.setData(resultMap);
		return responseMsg;
	}

	public ResponseMsg<Map<String, Object>> getWorkSpaceErrData(String workSpaceName, String cursor) {
		ResponseMsg<Map<String, Object>> responseMsg = createResponseMsg();
		Map<String, Object> resultMap = new HashMap<>();
		List<WorkSpaceData> list = new ArrayList<>();
		cursor = workSpaceManager.newWorkSpace(workSpaceName, WorkSpaceData.class).batchGetErrData(list, cursor);
		List<PageVo> pageVolist = new ArrayList<>();
		PageVo pageVo = null;
		for (WorkSpaceData workSpaceData : list) {
			pageVo = new PageVo();
			pageVo.setPageKey(workSpaceData.getKey());
			pageVo.setUrl(workSpaceData.toString());
			pageVolist.add(pageVo);
		}
		resultMap.put("cursor", cursor);
		resultMap.put("list", pageVolist);
		responseMsg.setIsOk(1);
		responseMsg.setData(resultMap);
		return responseMsg;
	}

	public ResponseMsg<String> clearDoing(String workSpaceName) {
		ResponseMsg<String> responseMsg = createResponseMsg();
		workSpaceManager.newWorkSpace(workSpaceName, WorkSpaceData.class).clearDoing();
		String msg = "clean workSpace[" + workSpaceName + "]  doing data succeed";
		responseMsg.setMsg(msg);
		responseMsg.setIsOk(1);
		log.info(msg);
		return responseMsg;
	}

	public ResponseMsg<String> clearErr(String workSpaceName) {
		ResponseMsg<String> responseMsg = createResponseMsg();
		workSpaceManager.newWorkSpace(workSpaceName, WorkSpaceData.class).clearErr();
		String msg = "clean workSpace[" + workSpaceName + "]  err data succeed";
		responseMsg.setMsg(msg);
		responseMsg.setIsOk(1);
		log.info(msg);
		return responseMsg;
	}

	public ResponseMsg<String> clearDone(String workSpaceName) {
		ResponseMsg<String> responseMsg = createResponseMsg();
		workSpaceManager.newWorkSpace(workSpaceName, WorkSpaceData.class).clearDone();
		String msg = "clean workSpace[" + workSpaceName + "]  done data succeed";
		responseMsg.setMsg(msg);
		responseMsg.setIsOk(1);
		log.info(msg);
		return responseMsg;
	}

	@Override
	public ResponseMsg<String> againDoErrQueue(String workSpaceName) {
		ResponseMsg<String> responseMsg = createResponseMsg();
		workSpaceManager.newWorkSpace(workSpaceName, WorkSpaceData.class).againDoErrQueue();
		responseMsg.setIsOk(1);
		responseMsg.setMsg("again do errQueue[" + workSpaceName + "] succeed");
		return responseMsg;
	}

	public WorkSpaceManager getWorkSpaceManager() {
		return workSpaceManager;
	}

	public void setWorkSpaceManager(WorkSpaceManager workSpaceManager) {
		this.workSpaceManager = workSpaceManager;
	}

	@Override
	public ResponseMsg<String> AddDoing(String workSpaceName, Page page) {
		ResponseMsg<String> responseMsg = createResponseMsg();
		workSpaceManager.newWorkSpace(workSpaceName, WorkSpaceData.class).push(page);
		String msg = "add workSpace[" + workSpaceName + "]  doing data succeed";
		responseMsg.setMsg(msg);
		responseMsg.setIsOk(1);
		log.info(msg);
		return null;
	}
}
