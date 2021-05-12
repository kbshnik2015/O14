package filters;

import model.dto.AbstractUserDTO;
import model.dto.CustomerDTO;
import model.dto.EmployeeDTO;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class EmployeeFilter implements Filter
{
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws
            IOException, ServletException
    {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);
        AbstractUserDTO curretnUser = null;
        try
        {
            curretnUser = (AbstractUserDTO) session.getAttribute("currentUser");
        }
        catch (NullPointerException e){
            RequestDispatcher dd = request.getRequestDispatcher("/index.jsp");
            dd.forward(request, response);
        }
        if(curretnUser instanceof CustomerDTO || session == null ){
            response.sendRedirect("/index.jsp");
        }else if (curretnUser instanceof EmployeeDTO) {
            filterChain.doFilter(request, response);
        }
    }

}
