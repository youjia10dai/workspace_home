package sutdy.fastjson.json.����json�ļ�.model;


//https://www.cnblogs.com/dmego/p/9033080.html
public class Column {
	
	//������ֶ����Ǵ�query.json����ȡ������,��column�г��ֵ������ֶ���
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