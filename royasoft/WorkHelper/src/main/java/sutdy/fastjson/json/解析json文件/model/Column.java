package sutdy.fastjson.json.解析json文件.model;


//https://www.cnblogs.com/dmego/p/9033080.html
public class Column {
	
	//这五个字段是是从query.json中提取出来的,是column中出现的所有字段名
	private String key;
	private String header;
	private String width;
	private boolean allowSort;
	private boolean hidden;


	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public boolean isAllowSort() {
		return allowSort;
	}

	public void setAllowSort(boolean allowSort) {
		this.allowSort = allowSort;
	}
	
	@Override
	public String toString() {
		return "Column [key=" + key + ", header=" + header + ", width=" + width + ", allowSort=" + allowSort
				+ ", hidden=" + hidden + "]";
	}

}