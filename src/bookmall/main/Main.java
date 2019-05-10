package bookmall.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import bookmall.dao.BookDao;
import bookmall.dao.CartDao;
import bookmall.dao.CategoryDao;
import bookmall.dao.MemberDao;
import bookmall.dao.OrdersDao;
import bookmall.vo.BookVo;
import bookmall.vo.CartVo;
import bookmall.vo.CategoryVo;
import bookmall.vo.MemberVo;
import bookmall.vo.OrdersVo;

public class Main {
	static Scanner sc = new Scanner(System.in);
	static Long member_no = 0L;
	public static void main(String[] args) {
		new InsertClass().firstInsert();
		int num = 0;
		while(true) {
			System.out.println("온라인 서점입니다.");
			System.out.println("1. 회원 조회");
			System.out.println("2. 카테고리 조회");
			System.out.println("3. 상품 조회");
			System.out.println("4. 로그인");
			System.out.println("0. 종료");
			
			System.out.print("메뉴 선택 : ");
			num = sc.nextInt();
			switch(num) {
			case 1 : displayMember(); break;
			case 2 : displayCategory(); break;
			case 3 : displayBook(); break;
			case 4 : login(); break;
			case 0 : return;
			default : System.out.println("잘못 선택하셨습니다."); continue;
			}
		}
	}
	
	
	public static void login() {
		while(true) {
			System.out.print("접속 할 회원번호 선택  : ");
			Long no = sc.nextLong();
			
			member_no = new MemberDao().login(no);
			if(member_no > 0) {
				int num = 0;
				while(true) {
					System.out.println("회원 메뉴");
					System.out.println("1. 상품 조회");
					System.out.println("2. 장바구니에 담기");
					System.out.println("3. 장바구니 조회");
					System.out.println("4. 책 구입");
					System.out.println("5. 주문 리스트 조회");
					System.out.println("6. 주문도서 리스트 조회");
					System.out.println("0. 로그아웃");
					
					System.out.print("메뉴 선택 : ");
					num = sc.nextInt();
					switch(num) {
					case 1 : displayBook(); break;
					case 2 : insertCart(); break;
					case 3 : displayCart(); break;
					case 4 : insertOrders(); break;
					case 5 : displayOrders(); break;
					case 6 : displayOrdersBook(); break;
					case 0 : return;
					default : System.out.println("잘못 선택하셨습니다."); continue;
					}
				}
			}else {
				System.out.println("회원번호가 없습니다.");
				return;
			}
		}
	}
	
	public static void displayMember() {
		List<MemberVo> list = new MemberDao().getList();
		
		for(MemberVo vo : list) {
			System.out.println("회원번호 : " + vo.getNo() + "\n" +
								"아이디 = " + vo.getId() + "\n" +
								"이름 = " + vo.getName() + "\n" +
								"이메일 = " + vo.getEmail() + "\n" +
								"전화번호 = " + vo.getPhone());
		}
	}
	
	public static void displayCategory() {
		List<String> list = new CategoryDao().getList();
		
		for(String st : list) {
			System.out.println("현재 카테고리 목록 : " + st);
		}
	}
	
	public static void displayBook() {
		List<BookVo> list = new BookDao().getList();
		for(BookVo vo : list) {
			System.out.println("책 번호 : " + vo.getNo() + " / " +
								"카테고리  : " + vo.getCategory() + " / " +
								"제목 : " + vo.getTitle() + " / " + 
								"가격 : " + vo.getPrice() + " / " +
								"판매상태 : " + vo.getStatus());
		}
	}
	
	public static void insertCart() {
		CartVo vo = new CartVo();
		vo.setMember_no(member_no);
		System.out.print("책 번호 입력 : ");
		Long book_no = sc.nextLong();
		System.out.print("수량 입력 : ");
		Long count = sc.nextLong();
		
		vo.setBook_no(book_no);
		vo.setCount(count);
		Boolean result = new CartDao().insert(vo);
		if(result) {
			System.out.println("장바구니에 담겼습니다.");
		}else {
			System.out.println("잘못 입력하셨습니다.");
		}
	}
	
	public static void displayCart() {
		List<CartVo> list = new CartDao().getList(member_no);
		for(CartVo vo : list) {
			System.out.println("장바구니 번호 : " + vo.getNo() + " / " +
					"책 제목 : " + vo.getTitle() + " / " +
					"갯수  : " + vo.getCount() + " / " +
					"총 가격 : " + vo.getPrice());
		}
		System.out.print("구입  (장바구니 번호 입력) \n0 (장바구니 나가기)\n입력 : ");
		int cart_no = sc.nextInt(); 
		sc.nextLine();
		if(cart_no > 0) {
			System.out.print("배송지 입력 : ");
			String deli_add = sc.nextLine();
			insertOrders(cart_no, deli_add);
		}
	}
	public static void insertOrders(int cart_no, String deli_add) {
		
		Boolean result = new OrdersDao().insert(member_no, cart_no, deli_add);
		
		if(!result) {
			System.out.println("Error!");
		}
	}
	public static void insertOrders() {
		List<OrdersVo> list = new ArrayList<OrdersVo>();
		while(true) {
			System.out.print("책 번호 입력 : ");
			Long book_no = sc.nextLong();
			System.out.print("수량 입력 : "); 
			Long count = sc.nextLong();
			sc.nextLine();
			OrdersVo vo = new OrdersVo();
			vo.setMember_no(member_no);
			vo.setBook_no(book_no);
			vo.setCount(count);
			list.add(vo);
			
			System.out.print("다른 책을 더 구입하시겠습니까? (Y/N) ");
			String check = sc.nextLine();
			
			if("Y".equals(check) || "y".equals(check)) {
				continue;
			}else {
				break;
			}
		}
		System.out.print("배송지 입력 : ");
		String deli_add = sc.nextLine();
		list.get(0).setDeli_add(deli_add);
		new OrdersDao().insert(list);
		
	}
	
	public static void displayOrders() {
		List<OrdersVo> list = new OrdersDao().getOrdersList(member_no);
		if(list != null) {
			for(OrdersVo vo : list) {
				System.out.println("구매번호 : " + vo.getPay_no() + " / " +
						"가격  : " + vo.getPay_price() + " / " +
						"날짜 : " + vo.getPay_date() + " / " + 
						"배송지 : " + vo.getDeli_add());
			}
		}else {
			System.out.println("구매 목록이 없습니다.");
		}
	}
	
	public static void displayOrdersBook() {
		sc.nextLine();
		System.out.print("조회 할 구매 번호 입력 : ");
		String pay_no = sc.nextLine();
		
		List<OrdersVo> list = new OrdersDao().getOrdersBookList(member_no, pay_no);
		if(list != null) {
			for(OrdersVo vo : list) {
				System.out.println("책 제목 : " + vo.getTitle() + " / " +
									"수량  : " + vo.getCount());
			}
		}else {
			System.out.println("잘못 입력하셨습니다.");
		}
		
	}
	
	
}
