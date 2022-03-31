package com.example.demo.common;

import javax.servlet.http.HttpServletRequest;

//  <%=Pager.makeTag(request, 10, 32)%>


public class Pager {
		
	
	//<a href= .....
	public static String makeTag(HttpServletRequest request , int pageSize , int total) {
		
		
		String Tag = "" ; 
		String contextPath = request.getContextPath();
		
		// << < 1 2 3 4 5 6 7 8 9 10 > >> 
		
		int cpage; 
		// 12/10 -> ceiling(1.2) -> 2   
		int pageTotal; 
		int pageGroupSize = 5; 
		
		//1 ~ 5
		//6 ~ 10
		//11 ~ 15
		int pageGroupStart; 
		int pageGroupEnd;  
		
		String path="";
		//System.out.println(path);
		String beginLabel 	= "first";
		String prevLabel 	= "previous"; 
		String nextLabel 	= "next";
		String endLabel 	= "last";	

		
		try {

			StringBuffer sb = new StringBuffer();
			
			//http://localhost:9000/MyHome/freeboard.do?pg=1
			
			String page = request.getParameter("pg"); // /board/list?pg=1
			page = ( page == null ) ? "0" : page;  
			

			cpage = Integer.parseInt(page) ; 

			pageTotal = (int)Math.ceil((total - 1) / pageSize);
            
			// 17 /5 - 3 *5  15    20 
			pageGroupStart = (int) (cpage / pageGroupSize) * pageGroupSize;
			pageGroupEnd = pageGroupStart + pageGroupSize;
			
		
			if (pageGroupEnd > pageTotal) {
				pageGroupEnd = pageTotal + 1;
			}
            //0~4, 5~9, 10~14, 15~19
				 
			boolean hasPreviousPage = cpage - pageGroupSize >= 0;
			boolean hasNextPage = pageGroupStart + pageGroupSize < pageTotal;
			
			sb.append("<ul class='pagination justify-content-center'>\r\n") ;  
			
			//  <<  < 
			sb.append((cpage > 0) ? makeLink(0, beginLabel) : 
				"<li class=\"page-item\"><a class=\"page-link\"  href='#'>"+beginLabel+"</a></li>\r\n");
			sb.append(hasPreviousPage ? makeLink(pageGroupStart - 1, prevLabel) : 
				"<li class=\"page-item\"><a class=\"page-link\"  href='#'>"+prevLabel+"</a></li>\r\n");
			
			for (int i = pageGroupStart; i < pageGroupEnd; i++) {
				if (i == cpage) {
					sb.append(makeActiveLink(i, (i + 1) + ""));
				} else {
					sb.append(makeLink(i, (i + 1) + ""));
				}
			}
			

			sb.append(hasNextPage ? makeLink(pageGroupEnd, nextLabel) : 
				"<li class=\"page-item\"> <a class=\"page-link\" href='#'>"+nextLabel+"</a></li>\r\n");
			sb.append((cpage < pageTotal) ? makeLink(pageTotal, endLabel) : 
				"<li class=\"page-item\"><a class=\"page-link\"  href='#'>"+endLabel+"</a></li>\r\n");
		
			sb.append("</ul>\r\n") ;  		
			Tag = sb.toString() ; 	
		} catch ( Exception e ) {
			e.printStackTrace() ; 
		}
			
		return Tag ; 
	}

	public static String makeLink(int page, String label) 
	{
		StringBuffer tmp = new StringBuffer();
		tmp.append("<li class=\"page-item\"><a class=\"page-link\"  href=\"javascript:goPage('" + page + "')\">").append(label).append("</a></li>\r\n");
		return tmp.toString();
	}
	
	
	public static String makeActiveLink(int page, String label) 
	{
		StringBuffer tmp = new StringBuffer();
		tmp.append("<li class=\"page-item  active\"><a class=\"page-link\"  href=\"javascript:goPage('" + page + "')\">").append(label).append("</a></li>\r\n");
		return tmp.toString();
	}

	
}
