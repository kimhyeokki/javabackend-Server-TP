package com.kkj.member.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;

import com.kkj.member.dao.MemberDao;
import com.kkj.member.dto.MemberDto;
import com.kkj.member.dto.ModalState;
import com.kkj.product.util.ScriptWriter;

/**
 * Servlet implementation class MemberInfoModifyProcess
 */
public class MemberInfoModifyProcess extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberInfoModifyProcess() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MemberDao memberDao = new MemberDao();
		HttpSession session = request.getSession();
		
		String userID = (String)session.getAttribute("loggedID");
		String userPW = request.getParameter("userPW");
		
		HashMap<String, String> map = new HashMap();
		map.put("userID", userID);
		map.put("userPW", userPW);
		
	 	MemberDto infoModifyMember = memberDao.infoModify(map);
	 	if(infoModifyMember !=null) {
	 		String telList[] =infoModifyMember.getTel().split("-");
	 		String tel01= telList[0];
	 		String tel02= telList[1];
	 		String tel03= telList[2];
	 		request.setAttribute("tel01", tel01);
	 		request.setAttribute("tel02", tel02);
	 		request.setAttribute("tel03", tel03);
	 		request.setAttribute("infoModifyMember", infoModifyMember);
	 		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/member/infoModify-form.jsp");
	 		dispatcher.forward(request, response);
	 	}else {
	 		ModalState modalState = new ModalState("show", "비밀번호가 틀립니다. 다시입력해주세요");
	 		session.setAttribute("modalState", modalState);
	 		response.sendRedirect("../member/infoModify");
	 		
	 	}
	}

}
