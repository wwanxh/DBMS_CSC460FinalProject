<!DOCTYPE html>
<%@ page import="java.util.*" %>
<%@ page import="java.lang.*" %>
<%@ page import="dbController.DatabaseController" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<html>
<head>
	<title>Administration</title>
</head>
<style>
table {
    font-family: arial, sans-serif;
    border-collapse: collapse;
    width: 100%;
}

td, th {
    border: 1px solid #dddddd;
    text-align: left;
    padding: 8px;
}

tr:nth-child(even) {
    background-color: #dddddd;
}
</style>
<body>
<%
DatabaseController db = new DatabaseController();
db.EstablishConnection();
%>
<div name="query1">
	<p>
		1. Choosing one from a list of ship types, report the cost of all the parts.
	</p>
	<form>
		<select name="part">
			<%
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			ArrayList query1options = (ArrayList) db.getModels();
			for(int i = 0; i<query1options.size(); i++){
				String[] query1optionsTemp = (String[]) query1options.get(i);
				String MID = query1optionsTemp[0];
				String name = query1optionsTemp[1];
				out.write("<option value=\"" + MID + "\">" + name + "</option>");
			}
			%>
		</select>
		<input type="submit" id="query1" name="runquery1" value="Run Query 1" />
	</form>
	<div name="query1result">
		<table>
		<%
		if(request.getParameter("runquery1") != null){
			String query1MID = request.getParameter("part");
			String query1result = db.modelCostQuery(query1MID);
			out.write("<tr><th>Total Cost</th></tr><tr><th>"+query1result+"</th></tr>");
		}
		%>
		</table>
	</div>
</div>

<div name="query2">
	<p>
		2. List all ships that are partially built but incomplete.
	</p>
	<form>
		<input type="submit" id="query2" name="runquery2" value="Run Query 2" />
	</form>
	<div name="query2result">
		<table>
			<%
			if(request.getParameter("runquery2") != null){
				out.write("<tr><th>Contract ID</th><th>Model ID</th><th>Model Name</th><th>Developed Part</th><th>In Progress Part</th></tr>");
				ArrayList query2result = (ArrayList) db.getBuildingShips();
				for(int i = 0; i < query2result.size(); i++){
					out.write("<tr>");
					String[] query2temp = (String[]) query2result.get(i);
					String contid = query2temp[0];
					out.write("<th>" + contid + "</th>");
					String mid = query2temp[1];
					out.write("<th>" + mid + "</th>");
					String name = query2temp[2];
					out.write("<th>" + name + "</th>");
					String developedPart = query2temp[3];
					out.write("<th>" + developedPart + "</th>");
					String inProgressPart = query2temp[4];
					out.write("<th>" + inProgressPart + "</th>");
					out.write("</tr>");
				}
			}
			%>
		</table>
	</div>
</div>

<div name="query3">
	<p>
		3. Find the customer that has paid the most for ships from I4, and how much they have spent. <br>
		&nbsp;&nbsp;&nbsp;&nbsp;This includes the markup applied to the cost of parts. <br>
		&nbsp;&nbsp;&nbsp;&nbsp;Include both ships that have been completed for this customer and ships that are currently contracted by this customer.
	</p>
	<form>
		<input type="submit" id="query3" name="runquery3" value="Run Query 3" />
	</form>
	<div name="query3result">
		<%
		if(request.getParameter("runquery3") != null){
			out.write("<p>This is money spent of each customer:</p>");
			out.write("<table>");
			out.write("<tr><th>Customer ID</th><th>First Name</th><th>Last Name</th><th>Money Spent</th></tr>");
			ArrayList query31result = (ArrayList) db.getMoneySpentPerCustomer();
			for(int i = 0; i < query31result.size(); i++){
				out.write("<tr>");
				String[] query31temp = (String[]) query31result.get(i);
				String custid = query31temp[0];
				out.write("<th>" + custid + "</th>");
				String firstName = query31temp[1];
				out.write("<th>" + firstName + "</th>");
				String lastName = query31temp[2];
				out.write("<th>" + lastName + "</th>");
				String moneySpent = query31temp[3];
				out.write("<th>" + moneySpent + "</th>");
				out.write("</tr>");
			}
			out.write("</table>");
			// The following is only the customer spent the most
			out.write("<p>This is the customer who spent the most amount of money:</p>");
			out.write("<table>");
			out.write("<tr><th>Customer ID</th><th>First Name</th><th>Last Name</th><th>Address</th><th>Money Spent</th></tr>");
			String[] query32result = (String[]) db.getMoneySpentHighestCustomer();
			out.write("<tr>");
			out.write("<th>" + query32result[0] + "</th>");
			out.write("<th>" + query32result[1] + "</th>");
			out.write("<th>" + query32result[2] + "</th>");
			out.write("<th>" + query32result[3] + "</th>");
			out.write("<th>" + query32result[4] + "</th>");
			out.write("</tr>");
			out.write("</table>");
		}
		%>
	</div>
</div>

<div name="query4.1">
	<p>
		4.1 List all total revenue of each department.
	</p>
	<form>
		<input type="submit" id="query4.1" name="runquery4.1" value="Run Query 4.1" />
	</form>
	<div name="query4.1result">
	<br>
		<%
		if(request.getParameter("runquery4.1") != null){
			out.write("<fieldset>");
			out.write("<legend>Query Result</legend>");
			out.write("<table>");
			out.write("<tr><th>Department Name</th><th>Department ID</th><th>Revenue</th></tr>");
			ArrayList query41Result = (ArrayList) db.getDepartmentRevenue();
			for(int i  = 0; i < query41Result.size(); i++){
				String[] one41Result = (String[]) query41Result.get(i);
				String name = one41Result[0];
				String did = one41Result[1];
				String revenue = one41Result[2];
				out.write("<tr>");
				out.write("<th>" + name + "</th>");
				out.write("<th>" + did + "</th>");
				out.write("<th>" + revenue + "</th>");
				out.write("</tr>");
			}
			out.write("</table>");
			out.write("</fieldset>");
		}
		%>
	</div>
</div>

<div name="query4.2">
	<p>
		4.2 Given a status, display the parts which match the status, including name quantity and price. 
	</p>
	<form>
		<select name="query42status">
			<option value="0">To Be Added</option>
			<option value="1">In Progress</option>
			<option value="2">Completed</option>
		</select>
		<input type="submit" id="query4.2" name="runquery4.2" value="Run Query 4.2" />
	</form>
	<br>
	<div name="query4.2result">
		<%
		if(request.getParameter("runquery4.2") != null){
			String query42status = request.getParameter("query42status");
			out.write("<fieldset>");
			out.write("<legend>Query Result</legend>");
			out.write("<table>");
			out.write("<tr><th>Part ID</th><th>Part Name</th><th>Status</th><th>Quantity</th><th>Price Per Part</th></tr>");
			ArrayList query42result = (ArrayList) db.getPartByStatus(Integer.parseInt(query42status));
			for(int i = 0 ; i < query42result.size();i++){
				String[] one42Result = (String[]) query42result.get(i);
				String pid = one42Result[0];
				String name = one42Result[1];
				String status = one42Result[2];
				String quantity = one42Result[3];
				String price = one42Result[4];
				out.write("<tr>");
				out.write("<th>" + pid + "</th>");
				out.write("<th>" + name + "</th>");
				out.write("<th>" + status + "</th>");
				out.write("<th>" + quantity + "</th>");
				out.write("<th>" + price + "</th>");
				out.write("</tr>");
			}
		out.write("</table>");
			out.write("</fieldset>");
		}
		%>
	</div>
</div>

<hr>
<div name="dml1.1">
	<p>
		Welcome to DML 1.1, ADD SHIPS(Model to be specific) to the database.
	</p>
	<p>
		We need Model name, How much money need for this model, and the market markup.
	</p>
	<form>
		<label>Model Name</label>
		<input type="text" id="dml1shipname" name="addshipname" required />
		<label>Labor $</label>
		<input type="text" id="dml1shiplabor" name="addshiplabor" required />
		<label>Markup</label>
		<input type="text" id="dml1shipmarkup" name="addshipmarkup" required />
		<br>
		<br>
		<fieldset>
			<legend>Extra Parts/Features</legend>
			<table>
				<%
				out.write("<tr><th>Add This</th><th>Part ID</th><th>Name</th><th>Price</th></tr>");
				ArrayList addShipTemp = (ArrayList) db.getTable("part");
				for(int i = 0; i < addShipTemp.size();i++){
					String[] oneShip = (String[]) addShipTemp.get(i);
					String pid = oneShip[0];
					String name = oneShip[1];
					String price = oneShip[2];
					if(pid.equals("10001") || pid.equals("20001") || pid.equals("80001")){
						continue;
					}
					out.write("<tr>");
					out.write("<th>");
					out.write("<input type=\"checkbox\" name=\"addshippid\" value=\"" + pid + "\">");
					out.write("</th>");
					out.write("<th>" + pid + "</th>");
					out.write("<th>" + name + "</th>");
					out.write("<th>" + price + "</th>");
					out.write("</tr>");
				}
				%>
			</table>
		</fieldset>
		<br>
		<input type="submit" name="dml1ship" value="Add This Ship" />
	</form>
	<%
	if(request.getParameter("dml1ship") != null){
		boolean departmentAvailability = db.checkAvailableDepartment();
		if(departmentAvailability){
			String toBeAddedName1 = request.getParameter("addshipname");
			String toBeAddedLabor1 = request.getParameter("addshiplabor");
			String toBeAddedMarkup1 = request.getParameter("addshipmarkup");
			String[] toBeAddedParts1 = request.getParameterValues("addshippid");
			boolean status11 = db.insertModel(toBeAddedName1, toBeAddedLabor1, toBeAddedMarkup1, toBeAddedParts1);
			if(!status11){
				out.write("<p>Something is wrong, please reenter your input.</p>");
			}
		}
		else{
			out.write("<p>Sorry, there is no department available yet, please add a department first. </p>");
		}
	}
	%>
</div>
<hr>
<div name="dml1.2">
	<p>
		Welcome to DML 1.2, ADD Department to the database.
	</p>
	<p>
		We need the model ID assigned to the department and the department name.
	</p>
	<form>
		<label>Department Name</label>
		<input type="text" name="adddepartmentname" required />
		<br>
		<input type="submit" name="dml1department" value="Add This Department" />
	</form>
	<%
	if(request.getParameter("dml1department") != null){
		String toBeAddedName2 = request.getParameter("adddepartmentname");
		boolean status12 = db.insertDepartment(toBeAddedName2);
		if(!status12){
			out.write("Something is wrong, please reenter your input.");
		}
	}
	%>
</div>
<hr>
<div name="dml1.3">
	<p>
		Welcome to DML 1.3, ADD Part to the database.
	</p>
	<p>
		We need the name of part and the price.
	</p>
	<form>
		<label>Part Name</label>
		<input type="text" name="addpartname" required />
		<label>Price</label>
		<input type="text" name="addpartprice" required />
		<br>
		<input type="submit" name="dml1part" value="Add This Part" />
	</form>
	<%
	if(request.getParameter("dml1part") != null){
		String toBeAddedName3 = request.getParameter("addpartname");
		String toBeAddedPrice3 = request.getParameter("addpartprice");
		boolean status13 = db.insertPart(toBeAddedName3, toBeAddedPrice3);
		if(!status13){
			out.write("Something is wrong, please reenter your input.");
		}
	}
	%>
</div>
<hr>
<div name="dml3">
	<p>
		Welcome to DML 3, Update the cost of an item.
	</p>
	<p>
		Please choose the part item you want to update and provide a new price for it. 
	</p>
	<form>
		<fieldset>
			<legend>Extra Parts/Features(Including Basic Parts)</legend>
			<table>
				<%
				out.write("<tr><th>Update This</th><th>Part ID</th><th>Name</th><th>Price</th></tr>");
				for(int i = 0; i < addShipTemp.size();i++){
					String[] oneShip = (String[]) addShipTemp.get(i);
					String pid = oneShip[0];
					String name = oneShip[1];
					String price = oneShip[2];
					out.write("<tr>");
					out.write("<th>");
					out.write("<input type=\"radio\" name=\"updatepriceid\" value=\"" + pid + "\">");
					out.write("</th>");
					out.write("<th>" + pid + "</th>");
					out.write("<th>" + name + "</th>");
					out.write("<th>" + price + "</th>");
					out.write("</tr>");
				}
				%>
			</table>
		</fieldset>
		<br>
		<input type="text" name="updatepartprice" required />
		<input type="submit" name="dml3" value="Update This Price" />
	</form>
	<%
	if(request.getParameter("dml3") != null){
		String toBeUpdatedPid = request.getParameter("updatepriceid");
		String priceToBeUpdated = request.getParameter("updatepartprice");
		boolean status3 = db.updatePartPrice(priceToBeUpdated, toBeUpdatedPid);
		if(!status3){
			out.write("Something is wrong, please reenter your input.");
		}
	}
	%>
</div>
<hr>
<div name="dml4">
	<p>
		Welcome to DML 4, Scrap a ship(contract) under construction.
	</p>
	<p>
		Please choose the ship(contract) under construction, which you want to scrap. 
	</p>
	<form>
		<fieldset>
			<legend>Ship/Contract Under Construction</legend>
			<table>
				<%
				out.write("<tr><th>Delete This</th><th>Contract ID</th><th>First Name</th><th>Last Name</th><th>Model Name</th></tr>");
				ArrayList dml4constructedContract = (ArrayList) db.getContractUnderConstruction();
				for(int i = 0; i < dml4constructedContract.size();i++){
					String[] oneContract = (String[]) dml4constructedContract.get(i);
					String contid = oneContract[0];
					String firstName = oneContract[1];
					String lastName = oneContract[2];
					String modelName = oneContract[3];
					out.write("<tr>");
					out.write("<th>");
					out.write("<input type=\"radio\" name=\"deletecontractid\" value=\"" + contid + "\">");
					out.write("</th>");
					out.write("<th>" + contid + "</th>");
					out.write("<th>" + firstName + "</th>");
					out.write("<th>" + lastName + "</th>");
					out.write("<th>" + modelName + "</th>");
					out.write("</tr>");
				}
				%>
			</table>
		</fieldset>
		<br>
		<input type="submit" name="dml4" value="Delete This Contract" />
	</form>
	<%
	if(request.getParameter("dml4") != null){
		String toBeDeletedContid = request.getParameter("deletecontractid");
		boolean status4 = db.deleteContract(toBeDeletedContid);
		if(!status4){
			out.write("Something is wrong, please reenter your input.");
		}
	}
	%>
</div>
<hr>
<div name="dml5">
	<p>Welcome to DML 5, Add to a ship progress by updating which parts of the ship have been built, </p>
	<p>and the current cost of construction.</p>
	<p>Please first choose the ship(contract) you want to edit.</p>
	<form>
		<fieldset>
			<legend>Ship/Contract</legend>
			<table>
				<%
				out.write("<tr><th>Choose one</th><th>Contract ID</th><th>First Name</th><th>Last Name</th><th>Model Name</th><th>Department Name</th><th>Invoice</th><th>Timestamp</th></tr>");
				ArrayList dml5refinedContracts = (ArrayList) db.getRefinedContractData();
				for(int i = 0; i < dml5refinedContracts.size();i++){
					String[] oneContract = (String[]) dml5refinedContracts.get(i);
					String contid = oneContract[0];
					String firstName = oneContract[1];
					String lastName = oneContract[2];
					String modelName = oneContract[3];
					String departmentName = oneContract[4];
					String invoice = oneContract[5];
					String timestamp = oneContract[6];
					out.write("<tr>");
					out.write("<th>");
					out.write("<input type=\"radio\" name=\"toBeEditedContract\" value=\"" + contid + "\">");
					out.write("</th>");
					out.write("<th>" + contid + "</th>");
					out.write("<th>" + firstName + "</th>");
					out.write("<th>" + lastName + "</th>");
					out.write("<th>" + modelName + "</th>");
					out.write("<th>" + departmentName + "</th>");
					out.write("<th>" + invoice + "</th>");
					out.write("<th>" + timestamp + "</th>");
					out.write("</tr>");
				}
				%>
			</table>
		</fieldset>
		<br>
		<input type="submit" name="dml5ChooseContract" value="Choose This Contract To Proceed" />
	</form>
	<%
	if(request.getParameter("dml5ChooseContract") != null){
		out.write("<p>Please Choose a Part to Edit the progress.</p>");
		// the following part displays the part information to edit.
		String theOneContractId = request.getParameter("toBeEditedContract");
		ArrayList thePartsInTheContract = (ArrayList) db.getPartStatusByContID(theOneContractId);
		out.write("<form>");

		out.write("<fieldset>");
		out.write("<legend>Current Cost of Construction</legend>");
		String currentCost = db.getCurrentCostByContID(theOneContractId);
		out.write("<p>" + currentCost + "</p>");
		out.write("</fieldset>");


		out.write("<fieldset>");
		out.write("<legend>Part</legend>");
		out.write("<table><tr><th>Choose one</th><th>Part ID</th><th>Part Name</th><th>Status</th><th>Change This Status To</th></tr>");
		for(int i = 0;i<thePartsInTheContract.size();i++){
			String[] onePart = (String[]) thePartsInTheContract.get(i);
			String pid = onePart[0];
			String name = onePart[1];
			String status = onePart[2];
			out.write("<tr>");
			out.write("<th>");
			out.write("<input type=\"radio\" name=\"toBeEditedPart\" value=\"" + pid + "\">");
			out.write("</th>");
			out.write("<th>" + pid + "</th>");
			out.write("<th>" + name + "</th>");
			if(status.equals("0")){
				out.write("<th>To Be Added</th>");
			}
			else if(status.equals("1")){
				out.write("<th>In Progress</th>");
			}
			else if(status.equals("2")){
				out.write("<th>Completed</th>");
			}
			else{
				out.write("<th>Undefined</th>");
			}
			out.write("<th>");
			out.write("<select name=\"dml5Status"+ pid + "\">");
			out.write("<option value=\"-1\"></option>");
			out.write("<option value=\"0\">To Be Added</option>");
			out.write("<option value=\"1\">In Progress</option>");
			out.write("<option value=\"2\">Completed</option>");
			out.write("</th>");
			out.write("</tr>");
		}
		out.write("</table>");
		out.write("</fieldset><br>");
		out.write("<input type=\"hidden\" name=\"passedContractId\" value=\"" + theOneContractId + "\" />");
		out.write("<input type=\"submit\" name=\"dml5ChoosePart\" value=\"Change This Status\" />");
		out.write("</form>");
	}

	if(request.getParameter("dml5ChoosePart") != null){
			String passedContractId = request.getParameter("passedContractId");
			String pidToBeEdited = request.getParameter("toBeEditedPart");
			String statusToBeEdited = request.getParameter("dml5Status" + pidToBeEdited);
			if(statusToBeEdited.equals("-1")){
				out.write("Please choose a part.");
			}
			else{
				boolean status5 = db.updatePartStatusByContID(passedContractId, pidToBeEdited, statusToBeEdited);
				if(status5 == false){
					out.write("Something is WRONG, please reenter your input.");
				}
			}
		}
	%>
</div>

<%
db.termination();
%>

</body>
</html>
