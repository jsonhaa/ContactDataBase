package cs3220.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/ContactList", loadOnStartup = 1)
public class ContactList extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public ContactList()
    {
        super();
    }
    
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
    	
    	//get data from database
    	Connection c = null;
        try
        {
            String url = "jdbc:mysql://cs3.calstatela.edu/cs3220stu79";
            String username = "cs3220stu79";
            String password = "4VmDDSf5Y2Ar";

            c = DriverManager.getConnection( url, username, password );
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "select * from contacts" );
            
            //Create a List<GuestBookEntry>
            List<ContactEntry> entries = new ArrayList<ContactEntry>();
            while( rs.next() )
            {
            	ContactEntry entry = new ContactEntry();
            	entry.setId(rs.getInt("id"));
            	entry.setName(rs.getString("name"));
            	entry.setPhone(rs.getString("phone"));
            	entries.add(entry);
            }
            
            //Pass the list to view
            request.setAttribute("entries", entries);
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
    	
    	request.getRequestDispatcher("/WEB-INF/ContactJSP/ContactList.jsp").forward(request, response);
    }
}