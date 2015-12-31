package my.ly.release.filters ;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
 
// 实现 Filter 类
public class LogFilter implements Filter  {
	
	
//   public void  init(FilterConfig config) throws ServletException{
//      // 获取初始化参数
//      String testParam = config.getInitParameter("test-param"); 
// 
//      //打印初始化参数
//      System.out.println("Test Param: " + testParam); 
//   }
   
   
   
   public void  doFilter(ServletRequest request, 
                 ServletResponse response,
                 FilterChain chain) 
                 throws java.io.IOException, ServletException {
 
      // 获取客户端ip地址  
      String ipAddress = request.getRemoteAddr(); 
      HttpServletRequest request2 = (HttpServletRequest) request;
		 
      String url = request2.getScheme()+"://"+ request2.getServerName()+request2.getRequestURI()+"?"+request2.getQueryString(); 
      System.out.println(new Date() +"  ---url:"+ url);
      // 输出ip地址及当前时间
      //System.out.println("IP "+ ipAddress + ", Time "
                                      // + new Date().toString());
 
      // 传递请求道过滤器链
      chain.doFilter(request,response);
   }
   
   
   public void destroy( ){
      /* 在Filter实例在服务器上被移除前调用。*/
   }
   
   
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
}