package cs3220.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/EditEntry")
public class EditEntry extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public EditEntry()
    {
        super();
    }

    protected void doGet( HttpServletRequest request,
        HttpServletResponse response ) throws ServletException, IOException
    {
        int id = Integer.parseInt( request.getParameter( "id" ) );

        DbService dbService = new DbService();
        request.setAttribute( "entry", dbService.getEntry( id ) );
        dbService.close();

        request.getRequestDispatcher( "/WEB-INF/ContactJSP/EditEntry.jsp" )
            .forward( request, response );
    }

    protected void doPost( HttpServletRequest request,
        HttpServletResponse response ) throws ServletException, IOException
    {
        int id = Integer.parseInt( request.getParameter( "id" ) );
        String name = request.getParameter( "name" );
        String phone = request.getParameter( "phone" );
        DbService dbService = new DbService();
        dbService.updateEntry( id, name, phone );
        dbService.close();

        response.sendRedirect( "ContactList" );
    }
}