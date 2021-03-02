function addRow()
{
    var oRow = document.getElementById("dyntbl1").insertRow(document.getElementById("dyntbl1").rows.length);
  	oRow.onmouseover=function(){document.getElementById("dyntbl1").clickedRowIndex=this.rowIndex;};
  	var oCell1 = oRow.insertCell(0);
	var oCell2 = oRow.insertCell(1);
  	oCell1.innerHTML = "<input type=text name=string[]>";
	oCell2.innerHTML = "<input type=button value=\" X \" onClick=\"delRow()\">";
  	document.recalc();
}
		
function delRow()
{
    document.getElementById("dyntbl1").deleteRow(document.getElementById("dyntbl1").clickedRowIndex);
}
