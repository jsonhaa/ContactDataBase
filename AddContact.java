package cs3220.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AddContact")
public class AddContact extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public AddContact()
    {
        super();
    }

    protected void doGet( HttpServletRequest request,
        HttpServletResponse response ) throws ServletException, IOException
    {
    	request.getRequestDispatcher("/WEB-INF/ContactJSP/AddContact.jsp").forward(request, response);
    }

    @SuppressWarnings("unchecked")
    protected void doPost( HttpServletRequest request,
        HttpServletResponse response ) throws ServletException, IOException
    {
        String name = request.getParameter( "name" );
        String phone = request.getParameter( "phone" );
        
        //insert data into database
        Connection c = null;
        try
        {
            String url = "jdbc:mysql://cs3.calstatela.edu/cs3220stu79";
            String username = "cs3220stu79";
            String password = "4VmDDSf5Y2Ar";

            c = DriverManager.getConnection( url, username, password );
            Statement stmt = c.createStatement();
            String sql = "insert into guestbook (name, message) values ('" + name + "', + '" + phone + "')";
            stmt.executeUpdate( sql, Statement.RETURN_GENERATED_KEYS );
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            
        }
        catch( SQLException e )
        {
            throw new ServletException( e );
        }
        finally
        {
            try
            {
                if( c != null ) c.close();
            }
            catch( SQLException e )
            {
                throw new ServletException( e );
            }
        }
        
        //Returning a View - redirect is considered a special view
        response.sendRedirect( "GuestBook" );
    }

}