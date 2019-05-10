package bookmall.vo;

public class OrdersDetailVo {
	private Long no;
	private String status;
	private String status_date;
	private Long orders_no;
	@Override
	public String toString() {
		return "OrdersDetailVo [no=" + no + ", status=" + status + ", status_date=" + status_date + ", orders_no="
				+ orders_no + "]";
	}
	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatus_date() {
		return status_date;
	}
	public void setStatus_date(String status_date) {
		this.status_date = status_date;
	}
	public Long getOrders_no() {
		return orders_no;
	}
	public void setOrders_no(Long orders_no) {
		this.orders_no = orders_no;
	}
	
	
}
