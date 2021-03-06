package six.com.crawler.work.plugs;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import six.com.crawler.entity.Page;
import six.com.crawler.entity.PageType;
import six.com.crawler.entity.ResultContext;
import six.com.crawler.utils.WebDriverUtils;
import six.com.crawler.work.AbstractCrawlWorker;
import six.com.crawler.work.WorkerLifecycleState;
import six.com.crawler.work.space.WorkSpace;

/**
 * @author 作者
 * @E-mail: 359852326@qq.com
 * @date 创建时间：2016年10月28日 上午10:20:51
 */
public class SzplGovProjectWorker extends AbstractCrawlWorker {

	final static Logger LOG = LoggerFactory.getLogger(SzplGovProjectWorker.class);

	String nextXpath = "//div[@id='AspNetPager1']/div[@class='mypaper']/a";
	WorkSpace<Page> preSaleQueue;
	WorkSpace<Page> projectQueue;

	@Override
	protected void insideInit() {
		preSaleQueue = getManager().getWorkSpaceManager().newWorkSpace("szpl_gov_pre_sale", Page.class);
		projectQueue = getManager().getWorkSpaceManager().newWorkSpace("szpl_gov_project_detail", Page.class);
	}

	@Override
	protected void beforeDown(Page doingPage) {

	}

	@Override
	protected void beforeExtract(Page doingPage) {
		String 预售证号_url = "预售证号_url";
		String 项目名称_url = "项目名称_url";
		ResultContext resultContext = getExtracter().extract(doingPage);
		getStore().store(resultContext);
		// 获取预售许可详细信息url
		List<String> 预售证号_urls = resultContext.getExtractResult(预售证号_url);
		if (null != 预售证号_urls) {
			Page page = null;
			for (String url : 预售证号_urls) {
				page = new Page(doingPage.getSiteCode(), 1, url, url);
				page.setType(PageType.DATA.value());
				page.setReferer(doingPage.getFinalUrl());
				if (!preSaleQueue.isDone(page.getPageKey())) {
					preSaleQueue.push(page);
				}
			}
		}
		// 获取项目详细信息url
		List<String> 项目名称_urls = resultContext.getExtractResult(项目名称_url);
		if (null != 项目名称_urls) {
			Page page = null;
			for (String url : 项目名称_urls) {
				page = new Page(doingPage.getSiteCode(), 1, url, url);
				page.setType(PageType.DATA.value());
				page.setReferer(doingPage.getFinalUrl());
				projectQueue.push(page);
			}
		}
		WebDriver webDriver = getDowner().getWebDriver();
		WebElement nextPageElement = findNextWebElement(webDriver);
		if (null != nextPageElement) {
			WebDriverUtils.click(webDriver, nextPageElement, null, findElementTimeout);
		} else {
			// 没有处理数据时 设置 state == WorkerLifecycleState.SUSPEND
			compareAndSetState(WorkerLifecycleState.STARTED, WorkerLifecycleState.FINISHED);
		}

	}

	@Override
	protected void afterExtract(Page doingPage, ResultContext result) {

	}

	@Override
	public boolean insideOnError(Exception t, Page doingPage) {
		return false;
	}

	@Override
	public void onComplete(Page p, ResultContext resultContext) {

	}

	private WebElement findNextWebElement(WebDriver webDriver) {
		List<WebElement> elements = WebDriverUtils.findElements(webDriver, nextXpath, findElementTimeout);
		WebElement nextWebElement = null;
		for (WebElement element : elements) {
			String text = element.getText();
			if (text.contains("下一页")) {
				String disabled = element.getAttribute("disabled");
				if (!"true".equalsIgnoreCase(disabled)) {
					nextWebElement = element;
					break;
				}
			}
		}
		return nextWebElement;
	}

}
