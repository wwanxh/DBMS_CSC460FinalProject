<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page import="java.util.*" %>
<%@ page import="java.lang.*" %>
<%@ page import="dbController.DatabaseController" %>



<html>
<head>
<title>Membership Sign Up</title>
<style>
body {
    font-family:"Helvetica Neue";
    background-image: url("./images/customer.png");
    background-size: cover;
    background-repeat: no-repeat;
    background-position: center center;
}

table {
    table-layout: fixed;
    color: white;
}
td {
    width: 35%;
}
tr {
    width: 33%;
}

button {
    background-color: #4CAF50;
    color: white;
    padding: 14px 20px;
    margin: 8px 0;
    border: none;
    cursor: pointer;
    width: 120px;
}

#top-right
{
    position: fixed;

    top: 15%;
    left: 50%;
    margin: -100px 0 0 -150px;
    
}

</style>
</head>

<body>
<br/><br/><br/>
<br/><br/><br/>
<br/><br/><br/>
<br/><br/><br/>

<div id="middle-left">
<h1 style="color: white" font-family:"Helvetica Neue">Welcome Back Bro!</h1></br>

<button type="button" onclick="window.location.href='index.jsp'">Sign Out</button>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<button type="button" onclick="window.location.href='model.jsp'">Order New Ship</button>
</div>


<div id= "top-right" class="Connect">
<h1 style="color: white" font-family:"Helvetica Neue">Your current orders</h1>


<%


//Purpose: This method is to connect jsp to database controller.
//In this page customer can check his/her curretn orders
//or order a new ship. There will be and option to show
//all current orders so customer can see contracts. Current
//contracts are send by database so we receive the value
//from database and create a current contract view to let
//customer use.                                               
request.setCharacterEncoding("utf-8");
response.setContentType("text/html;charset=utf-8");

//get the current username from current session
String user = (String) session.getAttribute("current_user");

//build connection to database
DatabaseController db = new DatabaseController();
db.EstablishConnection();

//create current contracts and build a view for customer
out.write("<form action = '#' name='form1'>");
out.write("<select id = 'tables' name='tables' onchange='submit();'>");
out.write("<option selected disabled>Select Contract</option>");
List<String[]> list = new ArrayList<String[]>();
list = db.getContractsByUsername(user);
String[] arr = new String[3];
String ConID;
for(int i=0; i<list.size();i++){
    arr = list.get(i);
    ConID = arr[0];
    out.write("<option value = '");
    out.print(ConID);
    out.write("'>");
    out.print(ConID);
    out.write("</option>");
}

out.write("</select>");
out.write("</form>");

String result = (String)request.getParameter("tables");

List<String[]> process = new ArrayList<String[]>();
String pid, name, status;
//if select an option
if(result != null){
   //save current customer id in current seesion
    session.setAttribute("",result);
    for(int i=0; i<list.size(); i++){
        arr = list.get(i);
        if(result.equals(arr[0])){
            out.write("<table border='1'>");
            out.write("<tr>");
            out.write("<th>Contract ID</th>");
            out.write("<th>Model Name</th>");
            out.write("<th>Time Start</th>");
            out.write("<th>Total Price</th></tr>");
            out.write("<tr>");
            out.write("<td>");
            out.print(arr[0]);
            out.write("</td><td>");
            out.print(arr[1]);
            out.write("</td><td>");
            out.print(arr[2]);
            out.write("</td><td>");
            out.print(arr[3]);
            out.write("</td>");
            out.write("</tr></table>");
            //get current customer id from current session
            String cur_cid = (String) session.getAttribute("");
            process = db.getPartStatusByContID(cur_cid);
            out.write("<table border='1'>");
            out.write("<tr>");
            out.write("<th>Part ID</th>");
            out.write("<th>Part Name</th>");
            out.write("<th>Status</th></tr>");
            for(int j=0; j<process.size(); j++){
                String[] arr2 = process.get(j);
                pid = arr2[0];
                name = arr2[1];
                status = arr2[2];
                //if function getPartStatusByContID(String) returns 0
                //means this part is to be built
                if(status.equals("0")){
                    out.write("<tr>");
                    out.write("<td>");
                    out.print(pid);
                    out.write("</td><td>");
                    out.print(name);
                    out.write("</td><td>To be assigned </td>");
                    out.write("</tr>");
                    //if function getPartStatusByContID(String) returns 1
                    //means this part is in prograss
                }else if(status.equals("1")){
                    out.write("<tr>");
                    out.write("<td>");
                    out.print(pid);
                    out.write("</td><td>");
                    out.print(name);
                    out.write("</td><td>In Prograss</td>");
                    out.write("</tr>");
                   //if function getPartStatusByContID(String) returns 2
                   //means this part is completed
                }else{
                    out.write("<tr>");
                    out.write("<td>");
                    out.print(pid);
                    out.write("</td><td>");
                    out.print(name);
                    out.write("</td><td>Completed</td>");
                    out.write("</tr>");
                }
            }
            out.write("</table>");
            
        }else{
            continue;
        }
    }
}


db.termination();

%>

</div>




</body>
</html>