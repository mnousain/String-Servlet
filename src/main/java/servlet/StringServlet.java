// Import Java Libraries
import java.io.*;
import java.util.*;

// Import Servlet Libraries
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import com.google.gson.Gson;

public class StringServlet extends HttpServlet
{

// Location of servlet.
// static String Domain  = "string-servlet.herokuapp.com";
// static String Path    = "";
// static String Servlet = "";

// Location of servlet... when running it locally (make sure to uncomment the debug version of the "action=" out.println line below and comment out the regular version)
// static String debugDomain  = "localhost:5000";
// static String debugPath    = "";
// static String debugServlet = "";

static String Style = "https://mason.gmu.edu/~mnousain/style.css";
static String Script = "https://mason.gmu.edu/~mnousain/table.js";

static enum Data {AGE, NAME};
static String RESOURCE_FILE = "entries.json";

// Button labels
static String OperationAdd = "Add";

public class Entries {
    List<String> entries;
}

public class EntryManager {
    private String filePath = null;

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
    public Entries save(List<String> entriesToSave){
        
        Entries entries = new Entries();
        entries.entries = entriesToSave;
        
        try 
        {
            FileWriter fileWriter = new FileWriter(filePath);
            new Gson().toJson(entries, fileWriter);
            
            fileWriter.flush();
            fileWriter.close();
        } 
        catch (IOException ioException) 
        {
            return null;
        }
        
        return entries;
    }

    private Entries getAll(){
        Entries entries = new Entries();
        entries.entries = new ArrayList<String>();

        try {
            File file = new File(filePath);
            if(!file.exists()){
                return entries;
            }

            BufferedReader bufferedReader =
                new BufferedReader(new FileReader(file));
            Entries readEntries =
                new Gson().fromJson(bufferedReader, Entries.class);

            if(readEntries != null && readEntries.entries != null){
                entries = readEntries;
            }
            bufferedReader.close();

        } catch(IOException ioException){
        }

        return entries;
    }

}

public void doPost (HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
{

    String error = "";

    // Grab the values of all oCell1 cells (the cells on the left-hand side) from the table. All of them share the same name: "string[]"
    String[] allVals = request.getParameterValues("string[]");

    // Convert to ArrayList for easier manipluation
    ArrayList<String> allVals_AL = new ArrayList<>(Arrays.asList(allVals));

    // Check what radio button option was selected and process the allVals_AL as appropriate
    String selectedOption = request.getParameter("option");
    String returnString = "";
    // Check if the checkbox is toggled on
    String selectedCheckbox = request.getParameter("check");
    
    if (selectedOption != null) 
    {
        if (selectedOption.equals("random") || selectedOption.equals("with")) 
        { // Return one random string (eg, “Grace”), with replacement
            returnString = allVals_AL.get((int)(Math.random()*allVals.length));
        } 
        else if (selectedOption.equals("without")) 
        { // Return one random string (eg, “Grace”), without replacement
            returnString = allVals_AL.remove((int)(Math.random()*allVals.length));
        } 
        else if (selectedOption.equals("sort")) 
        { // Return the strings in sorted order (eg, “Anita,” “Grace,” “Julia,” “Kent,” “Tim”)
            Collections.sort(allVals_AL);
            
            if (selectedCheckbox != null) 
            { // If not null, this means the checkbox is enabled, so we should remove duplicate entries
                ArrayList<String> allVals_AL_temp = new ArrayList<>();
                
                for (String currVal : allVals_AL) 
                {
                    if (!allVals_AL_temp.contains(currVal)) 
                    { // Create a new array and add first occurrence of each string
                        allVals_AL_temp.add(currVal);
                    }
                }
                
                allVals_AL = allVals_AL_temp; // Set original array equal to new array
            }
        } else if (selectedOption.equals("reverse")) 
        { // Return the strings in reverse-sorted order (eg, “Tim,” “Kent,” “Julia,” “Grace,” “Anita”)
            Collections.sort(allVals_AL);
            Collections.reverse(allVals_AL);
            
            if (selectedCheckbox != null) 
            { // If not null, this means the checkbox is enabled, so we should remove duplicate entries
                ArrayList<String> allVals_AL_temp = new ArrayList<>();
                
                for (String currVal : allVals_AL) 
                {
                    if (!allVals_AL_temp.contains(currVal)) 
                    { // Create a new array and add first occurrence of each string
                        allVals_AL_temp.add(currVal);
                    }
                }
                
                allVals_AL = allVals_AL_temp; // Set original array equal to new array
            }
        }
    }

    EntryManager entryManager = new EntryManager();
    entryManager.setFilePath(RESOURCE_FILE);
    Entries newEntries = entryManager.save((List<String>)allVals_AL);

    // After processing allVals_AL according to the selected radio button option,
    // construct the result as a string that will be displayed on the page
    String processedResult = "";
    if (!returnString.equals("")) {
        StringBuffer sb = new StringBuffer();
        sb.append("<b>Result:</b><br><br>");
        sb.append(returnString);
        processedResult = sb.toString();
    }

    // out.println(getAllAsHTMLTable);

    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    PrintHead(out);
    if(newEntries == null) 
    {
        error += "<li>Could not save entries.</li>";
        PrintBody(out, processedResult, allVals_AL.toArray(new String[0]), error);
    
    } else 
    {
        PrintBody(out, processedResult, newEntries.entries.toArray(new String[0]), null); // pass in the processed result so it can be printed
    }

    PrintTail(out);

}  // End doPost

public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
{
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    PrintHead(out);

    EntryManager entryManager = new EntryManager();
    entryManager.setFilePath(RESOURCE_FILE);
    Entries currEntries = entryManager.getAll();
    if (currEntries == null) {
        PrintBody(out);
    } else {
        PrintBody(out, "", currEntries.entries.toArray(new String[0]), null);
    }

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
    out.println("<script type=\"text/javascript\" src=\"" + Script + "\"></script>");
    out.println("</head>");
    out.println("");
} // End PrintHead

private void PrintBody(PrintWriter out, String displayedResult, String[] inputs, String error)
{
    out.println("<body>");
    out.println("<h1 align=\"center\">SWE-432: Assignment 6</h1>");
    out.println("<h2 align=\"center\">Collaborators: Mason Nousain, Aaron Salenga, Taha Amir</h2>");
    out.println("");

    out.println("<center>");

    if(error != null && error.length() > 0){
      out.println(
      "<p style=\"color:red;\">Please correct the following and resubmit.</p>"
      );
      out.println("<ol>");
      out.println(error);
      out.println("</ol>");
    }

    out.println("<h2>Create a List of Strings</h2>");
    out.println("<p>Enter a list of strings in the table below. Click the \"+\" key to add a row, and click the \"x\" key to delete a row </p>");
    out.println("<input type=button name=clear value=\"Clear Strings\">");

    out.println("<form method=\"post\"");

    String url = "/"; // Relative path, so we don't need to change the url when switching between debug and deploy modes
    out.println(" action=\"" + url + "\" onsubmit=\"return validateString()\">");
    out.println("");

    out.println("<table id=dyntbl1 border=1>");
    out.println("<tr>");
    out.println("<th>String</th>");
    out.println("<th><input type=button value=\" + \" onClick=\"addRow()\"></th>");
    out.println("</tr>");

    if (inputs == null || inputs.length == 0) 
    {
        out.println("<tr onMouseOver=\"dyntbl1.clickedRowIndex=this.rowIndex\">");
        out.println("<td><input type=\"text\" name=\"string[]\"></td>");
        out.println("<td><input type=button name=dyntbl1_delRow value=\" x \" onClick=\"delRow()\"></td>");
        out.println("</tr>");
    }
    else 
    {
        for (int i = 0; i < inputs.length; i++) 
        {
            out.println("<tr onMouseOver=\"dyntbl1.clickedRowIndex=this.rowIndex\">");
            out.println(String.format("<td><input type=\"text\" name=\"string[]\" value=\"%s\"></td>", inputs[i]));
            out.println("<td><input type=button name=dyntbl1_delRow value=\" x \" onClick=\"delRow()\"></td>");
            out.println("</tr>");

        }
    }
    out.println("<tr>");
    out.println("<td><input type=button name=clear value=\"Clear Strings\"></td>");
    out.println("</tr>");

    out.println("</table>");
    out.println("");

    out.println("<h3>Please select an option</h3>");

    out.println("<p><b>Return a string:</b></p>");

    out.println("<input type=\"radio\" id=\"random\" name=\"option\" value=\"random\">");
    out.println("<label for=\"random\">Random</label><br>");

    out.println("<input type=\"radio\" id=\"replacement\" name=\"option\" value=\"with\">");
    out.println("<label for=\"replacement\">With replacement</label><br>");

    out.println("<input type=\"radio\" id=\"replacement\" name=\"option\" value=\"without\">");
    out.println("<label for=\"replacement\">Without replacement</label><br>");

    out.println("<p><b>Sort the list:</b></p>");

    out.println("<input type=\"radio\" id=\"sort\" name=\"option\" value=\"sort\">");
    out.println("<label for=\"sort\">Sort</label><br>");

    out.println("<input type=\"radio\" id=\"reverse\" name=\"option\" value=\"reverse\">");
    out.println("<label for=\"reverse\">Reverse Sort</label><br>");

    out.println("<br>");
    out.println("<input type=\"checkbox\" id=\"noDuplicates\" name=\"check\" value=\"noDuplicates\">");
    out.println("<label for=\"noDuplicates\">Remove Duplicate Entries (when sorting)</label><br>");

    out.println("<br>");
    out.println("<input type=\"submit\" name=\"Submit\" value=\"Submit Strings\">");

    // Print the result
    // out.println("<h3>Result:</h3>");
    // out.println("");
    out.println("<p>" + displayedResult + "</p>");
    out.println("</form>");


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
   PrintBody(out, "", null, null);
}

private void PrintTail(PrintWriter out)
{
   out.println("");
   out.println("</html>");
} // End PrintTail

} // End StringServlet
