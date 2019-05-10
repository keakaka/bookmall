package bookmall.vo;

public class OrdersVo {
	private Long no;
	private Long member_no;
	private String deli_add;
	private Long pay_price;
	private String pay_date;
	private Long book_no;
	private Long count;
	private String pay_no;
	private String title;
	
	@Override
	public String toString() {
		return "OrdersVo [no=" + no + ", member_no=" + member_no + ", deli_add=" + deli_add + ", pay_price=" + pay_price
				+ ", pay_date=" + pay_date + ", book_no=" + book_no + ", count=" + count + ", pay_no=" + pay_no
				+ ", title=" + title + "]";
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPay_no() {
		return pay_no;
	}
	public void setPay_no(String pay_no) {
		this.pay_no = pay_no;
	}
	public Long getBook_no() {
		return book_no;
	}
	public void setBook_no(Long book_no) {
		this.book_no = book_no;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	public Long getMember_no() {
		return member_no;
	}
	public void setMember_no(Long member_no) {
		this.member_no = member_no;
	}
	public String getDeli_add() {
		return deli_add;
	}
	public void setDeli_add(String deli_add) {
		this.deli_add = deli_add;
	}
	public Long getPay_price() {
		return pay_price;
	}
	public void setPay_price(Long pay_price) {
		this.pay_price = pay_price;
	}
	public String getPay_date() {
		return pay_date;
	}
	public void setPay_date(String pay_date) {
		this.pay_date = pay_date;
	}
	
	
}
