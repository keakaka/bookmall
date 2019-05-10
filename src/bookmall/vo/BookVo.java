package bookmall.vo;

public class BookVo {
	private Long no;
	private Long cate_no;
	private String category;
	private String title;
	private Long price;
	private String status;
	
	@Override
	public String toString() {
		return "BookVo [no=" + no + ", cate_no=" + cate_no + ", category=" + category + ", title=" + title + ", price="
				+ price + ", status=" + status + "]";
	}
	public Long getCate_no() {
		return cate_no;
	}
	public void setCate_no(Long cate_no) {
		this.cate_no = cate_no;
	}
	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	
}
