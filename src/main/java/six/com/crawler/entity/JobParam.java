package six.com.crawler.entity;


/**
 * @author 作者
 * @E-mail: 359852326@qq.com
 * @date 创建时间：2016年9月9日 下午2:43:39
 */
public class JobParam extends BasePo{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8041698028299021489L;
	
	private String jobName;// jobName
	private String name;// 参数名字
	private String value;// 参数值
	
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}

}
