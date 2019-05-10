package bookmall.vo;

public class CartVo {
	private String title;
	private Long no;
	private Long count;
	private Long book_no;
	private Long member_no;
	private Long price;
	
	
	
	@Override
	public String toString() {
		return "CartVo [title=" + title + ", no=" + no + ", count=" + count + ", book_no=" + book_no + ", member_no="
				+ member_no + ", price=" + price + "]";
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public Long getBook_no() {
		return book_no;
	}
	public void setBook_no(Long book_no) {
		this.book_no = book_no;
	}
	public Long getMember_no() {
		return member_no;
	}
	public void setMember_no(Long member_no) {
		this.member_no = member_no;
	}
	
	
}
