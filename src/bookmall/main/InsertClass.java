package bookmall.main;

import java.util.ArrayList;
import java.util.List;

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

public class InsertClass {
	
	public static void firstInsert() {
		MemberVo mvo = new MemberVo();
		mvo.setId("user01");
		mvo.setPwd("pwd01");
		mvo.setName("name01");
		mvo.setEmail("email01");
		mvo.setPhone("phone01");
		new MemberDao().insert(mvo);
		
		mvo.setId("user02");
		mvo.setPwd("pwd02");
		mvo.setName("name02");
		mvo.setEmail("email02");
		mvo.setPhone("phone02");
		new MemberDao().insert(mvo);
		
		CategoryVo cvo = new CategoryVo();
		String[] sar = {"소설", "수필", "컴퓨터/IT", "인문", "경제", "예술"};
		for(int i = 0; i < sar.length; i++) {
			cvo.setName(sar[i]);
			new CategoryDao().insert(cvo);
		}
		
		BookVo bvo = new BookVo(); 
		bvo.setCate_no(1L);
		bvo.setTitle("해리포터");
		bvo.setPrice(35000L);
		new BookDao().insert(bvo);
		
		bvo.setCate_no(3L);
		bvo.setTitle("자바의 정석");
		bvo.setPrice(30000L);
		new BookDao().insert(bvo);
		
		bvo.setCate_no(5L);
		bvo.setTitle("경제상식 알아보기");
		bvo.setPrice(40000L);
		new BookDao().insert(bvo);
		
		CartVo ctvo = new CartVo();
		ctvo.setMember_no(1L);
		ctvo.setBook_no(1L);
		ctvo.setCount(2L);
		new CartDao().insert(ctvo);
		ctvo.setMember_no(1L);
		ctvo.setBook_no(2L);
		ctvo.setCount(5L);
		new CartDao().insert(ctvo);
		
		
		List<OrdersVo> list = new ArrayList<OrdersVo>();
		OrdersVo ovo = new OrdersVo();
		ovo.setDeli_add("서울시");
		ovo.setMember_no(1L);
		ovo.setBook_no(1L);
		ovo.setCount(3L);
		list.add(ovo);
		ovo.setDeli_add("경기도");
		ovo.setMember_no(1L);
		ovo.setBook_no(3L);
		ovo.setCount(5L);
		list.add(ovo);
		new OrdersDao().insert(list);
	}
}
