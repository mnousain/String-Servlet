// Import Java Libraries
import java.io.*;
import java.util.*;

// Import Servlet Libraries
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;


public class StringServlet extends HttpServlet
{

// Location of servlet.
static String Domain  = "string-servlet.herokuapp.com";
static String Path    = "";
static String Servlet = "";

// Location of servlet... when running it locally (make sure to uncomment the debug version of the "action=" out.println line below and comment out the regular version)
static String debugDomain  = "localhost:5000";
static String debugPath    = "";
static String debugServlet = "";

static String Style = "https://mason.gmu.edu/~mnousain/style.css";

public void doPost (HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
{

    // Grab the values of all oCell1 cells (the cells on the left-hand side) from the table. All of them share the same name: "string[]"
    String[] allVals = request.getParameterValues("string[]");

    // Convert to ArrayList for easier manipluation
    ArrayList<String> allVals_AL = new ArrayList<>(Arrays.asList(allVals));

    // Check what radio button option was selected and process the allVals_AL as appropriate
    String selectedOption = request.getParameter("option");
    if (selectedOption != null) {
        if (selectedOption.equals("random")) { // Return one random string (eg, “Grace”), with replacement
            // TODO: implement this

        } else if (selectedOption.equals("replacement")) { // Return one random string (eg, “Grace”), without replacement 
            // TODO: implement this

        } else if (selectedOption.equals("sort")) { // Return the strings in sorted order (eg, “Anita,” “Grace,” “Julia,” “Kent,” “Tim”)
            // TODO: implement this

        } else if (selectedOption.equals("reverse")) { // Return the strings in reverse-sorted order (eg, “Tim,” “Kent,” “Julia,” “Grace,” “Anita”)
            // TODO: implement this
            
        }
    }

    // After processing allVals_AL according to the selected radio button option,
    // construct the result as a string that will be displayed on the page
    StringBuffer sb = new StringBuffer();
    sb.append("<b>Result:</b><br><br>");
    for (String s : allVals_AL) {
        sb.append(s);
        sb.append("<br>");
    }
    String processedResult = sb.toString();

    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    PrintHead(out, request, response);
    PrintBody(out, processedResult); // pass in the processed result so it can be printed
    PrintTail(out);
}  // End doPost

public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
{
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    PrintHead(out, request, response);
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

    out.println("</head>");
    out.println(""):
} // End PrintHead

private void PrintBody(PrintWriter out, String displayedResult)
{
    out.println("<body>");
    out.println("<h1 align=\"center\">SWE-432: Assignment 4</h1>");
    out.println("<h2 align=\"center\">Collaborators: Mason Nousain, Aaron Salenga, Taha Amir</h2>");
    out.println("");

    out.println("<center>");
    out.println("<h2>Create a List of Strings</h2>");
    out.println("<p>Enter a list of strings in the table below. Click the \"+\" key to add a row, and click the \"x\" key to delete a row </p>");
    out.println("<form method=\"post\"");
    out.println(" action=\"https://" + Domain + Path + Servlet + "\">");                        // NOTE: This line is for publishing. Uncomment this before you push & deploy.
    // out.println(" action=\"http://" + debugDomain + debugPath + debugServlet + "\">");       // NOTE: This line is for local debugging. Uncomment this when doing local tests.
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

    out.println("<p>Please select an option</p>");

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

    // Print the result
    // out.println("<h3>Result:</h3>");
    // out.println("");
    out.println("<p>" + displayedResult + "</p>");

    out.println("</center>");
    out.println("");

    out.println("</body>");

} // End PrintBody

/** *****************************************************
 *  Overloads PrintBody (out,displayedResult) to print a page
 *  with blanks in the form fields.
********************************************************* */
private void PrintBody (PrintWriter out)
{
   PrintBody(out, "");
}

private void PrintTail(PrintWriter out)
{
   out.println("");
   out.println("</html>");
} // End PrintTail

} // End StringServlet
