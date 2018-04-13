<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page import="java.util.*" %>
<%@ page import="java.lang.*" %>
<%@ page import="java.lang.StringBuffer" %>
<%@ page import="dbController.DatabaseController" %>

<html>
<head>
<title>Model Select Page</title>
<style>
body {
    font-family:"Helvetica Neue";
    background-image: url("./images/order.jpg");
    background-size: cover;
    background-repeat: no-repeat;
    background-position: center center;
}


input[type=submit] {
    background-color: #4CAF50;
    color: white;
    padding: 14px 20px;
    margin: 8px 0;
    border: none;
    cursor: pointer;
    width: 100px;
}

#left
{
    position: fixed;
    height: 300px;
    top: 20%;
    left: 20%;
    margin: -100px 0 0 -150px;
    
}

</style>
</head>

<body>
<br/><br/><br/>
<br/><br/><br/>
<br/><br/><br/>
<br/><br/><br/>

<div id="left">
<h1 style="color: white" font-family:"Helvetica Neue">Hey bro!</h1>
<h2 style="color: white" font-family:"Helvetica Neue">Choose your favourite model</h2>
<form id="myform">
<input type="submit" id="purchase" name="purchase" value="Purchase" />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input type="submit" id="back" name="back" value="Back" /></form>


<%

/*---------------------------------------------------------------------*
 * Purpose: This method is to connect jsp to database controller.      |
 *          Customer can get current models we have in a selected      |
 *          option. After selecting an option, it will shows the       |
 *          corresponding features that selected ship has.             |
 *          After click Purchase button, customer will have a new      |
 *          order of current showed ship. Then the page will jump      |
 *          back to customer page. Customer can check new order in     |
 *          customer page.                                             |
 *---------------------------------------------------------------------*
 */

request.setCharacterEncoding("utf-8");
response.setContentType("text/html;charset=utf-8");

//build connection to database
DatabaseController db = new DatabaseController();
db.EstablishConnection();

//get current username from current session
String cur_user = (String) session.getAttribute("current_user");

List<String[]> model = new ArrayList<String[]>();
model = db.getModels();

//get current model list from database and create an view for it
out.write("<form action = '#' name='form1'>");
out.write("<select id = 'tables' name='tables' onchange='submit();'>");
out.write("<option selected disabled>Select Model</option>");
String ModelID, ModelName;
for(int i=0; i<model.size();i++){
    String[] arr = model.get(i);
    ModelID = arr[0];
    ModelName = arr[1];
    out.write("<option value = '");
    out.print(ModelID);
    out.write("'>");
    out.print(ModelName);
    out.write("</option>");
}

out.write("/<select>");
out.write("/<form>");


String result = (String)request.getParameter("tables");

String current_MID = null;
//if select an option from the model list it will show what
//features this ship has
if(result != null){
    session.setAttribute("current_mid",result);
    List<String> list = new ArrayList<String>();
    list = db.getModelFeatures(result);
    out.write("<h3 style='color:white'>");
    out.write("Included Features");
    out.write("</h3>");
    out.write("<h5 style='color:white'>");
    for(int i=0; i<list.size();i++){
        out.print(list.get(i));
        out.write("</br></br>");
    }
    out.write("</h5>");
}
String cur_mid = (String) session.getAttribute("current_mid");

//click purchase button and build a new order for this customer
if(request.getParameter("purchase")!=null){
    out.write("<h1 style='color:white'> MID after purchasing is ");
    out.print(cur_mid);
    out.write("</h1>");
    out.write("<h1 style='color:white'> User after purchasing is ");
    out.print(cur_user);
    out.write("</h1>");
    if(db.makeNewContract(cur_user,cur_mid)){
       response.sendRedirect("./customer.jsp");
    }else{
        out.write("<h3 style='color:white'>");
        out.write("Currently no department builds this model ship, please choose the other one");
        out.write("</h3>");
    }
}


if(request.getParameter("back")!=null){
    response.sendRedirect("./customer.jsp");
}
//close database
db.termination();

%>
</br》

</div>



</body>
</html>