var xmlHttp;

function createXmlHttpRequest()
{
    if(window.ActiveXObject)
    {
        xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
    }
    else if(window.XMLHttpRequest)
    {
        xmlHttp=new XMLHttpRequest();
    }
}
function startRequest()
{

    createXmlHttpRequest();
    xmlHttp.open("GET", "/", true); // Submit GET request to proper page using relative path
    xmlHttp.onreadystatechange=handleSubmit;
    xmlHttp.send(null);

}
function handleSubmit()
{
//    console.log("xmlHttp.readyState: " + xmlHttp.readyState);
    if(xmlHttp.readyState == 4 && xmlHttp.status == 200)
    {
        var result = xmlHttp.responseText;
        var list = result.split(', ');
        if (list.length == 1) { // Singular result
            document.getElementById("result").value = list[0];
        }
        else if (list.length > 1) { // List of results, from when you sort
            var inputs = document.getElementsByName("string[]");
            for (var i = 0; i < list.length; i++) {
                inputs[i].value = list[i];
            }
            if (inputs.length > list.length) { // Remove extra inputs in the html in the case of removing duplicates
                var extra = list.length;
                for(var i = extra; i < inputs.length; i++) {
                    inputs[i].parentElement.parentElement.remove(); // Remove the whole table row
                }
            }
        }
    }
    else
    {
        //alert("Error loading page  "+ xmlHttp.status + ": "+xmlHttp.statusText);
    }
}