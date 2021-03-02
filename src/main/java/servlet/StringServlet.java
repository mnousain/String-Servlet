// Import Java Libraries
import java.io.*;
import java.util.*;

// Import Servlet Libraries
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;


public class StringServlet extends HttpServlet
{

static String Style = "https://mason.gmu.edu/~mnousain/style.css";


public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
{
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    PrintHead(out);
    PrintBody(out);
    PrintTail(out);
} // End doGet

/** *****************************************************
 *  Prints the <head> of the HTML page, no <body>.
********************************************************* */
private void PrintHead(PrintWriter out)
{
    out.println("<html>");
    out.println("");

    out.println("<head>");
    out.println("<title>String Servlet</title>");
    out.println("<link rel=\"stylesheet\" href=\"" + Style  + "\">");


    out.println("<script>");
    out.println("function addRow()");
    out.println("{");
    
    out.println("var oRow = document.getElementById(\"dyntbl1\").insertRow(document.getElementById(\"dyntbl1\").rows.length);");
    out.println("oRow.onmouseover=function(){document.getElementById(\"dyntbl1\").clickedRowIndex=this.rowIndex;};");
    out.println("var oCell1 = oRow.insertCell(0);");
    out.println("var oCell2 = oRow.insertCell(1);");
    out.println("oCell1.innerHTML = \"<input type=text name=string[]>\";");
    out.println("oCell2.innerHTML = \"<input type=button value=\" X \"  onClick=\"delRow()\">\";");
    out.println("");
    
    out.println("}");

    out.println("function delRow()");
    out.println("{");

    out.println("document.getElementById(\"dyntbl1\").deleteRow(document.getElementById(\"dyntbl1\").clickedRowIndex);");

    out.println("}");

    out.println("</script>");

    out.println("</head>");
} // End PrintHead

private void PrintBody(PrintWriter out)
{
    out.println("<body>");
    out.println("<h1 align=\"center\">SWE-432: Assignment 4</h1>");
    out.println("<h2 align=\"center\">Collaborators: Mason Nousain, Aaron Salenga, Taha Amir</h2>");
    out.println("");

    out.println("<center>");
    out.println("<h2>Create a List of Strings</h2>");
    out.println("<p>Enter a list of strings in the table below. Click the \"+\" key to add a row, and click the \"x\" key to delete a row </p>");
    out.println("<form method=\"post\" action=\"https://cs.gmu.edu:8443/offutt/servlet/formHandler\">");
    out.println("");

    out.println("<table id=dyntbl1 border=1>");
    out.println("<tr>");
    out.println("<th>String</th>");
    out.println("<th><input type=button value=\" + \" onClick=\"addRow()\"></th>");
    out.println("</tr>");

    out.println("<tr onMouseOver=\"dyntbl1.clickedRowIndex=this.rowIndex\">");
    out.println("<td><input type=\"text\" name=\"string[]\"></td>");
    out.println("<td><input type=button name=dyntbl1_delRow value=\" x \" onClick=\"delRow()\"></td>");
    out.println("</tr>");

    out.println("</table>");
    out.println("");

    out.println("<p>Please select and option</p>");

    out.println("<input type=\"radio\" id=\"random\" name=\"option\" value=\"random\">");
    out.println("<label for=\"random\">Random</label><br>");

    out.println("<input type=\"radio\" id=\"replacement\" name=\"option\" value=\"replacement\">");
    out.println("<label for=\"replacement\">With and without replacement</label><br>");
    
    out.println("<input type=\"radio\" id=\"sort\" name=\"option\" value=\"sort\">");
    out.println("<label for=\"sort\">Sort</label><br>");

    out.println("<input type=\"radio\" id=\"reverse\" name=\"option\" value=\"reverse\">");
    out.println("<label for=\"reverse\">Reverse Sort</label><br>");

    out.println("<br>");
    out.println("<input type=\"submit\" name=\"Submit\" value=\"Submit Strings\">");

    out.println("</form>");
    out.println("</center>");
    out.println("");

    out.println("</body>");

} // End PrintBody

private void PrintTail(PrintWriter out)
{
   out.println("");
   out.println("</html>");
} // End PrintTail

} // End StringServlet
