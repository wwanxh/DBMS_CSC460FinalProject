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
    background-image: url("./images/rick.png");
    background-size: cover;
    background-repeat: no-repeat;
    background-position: center center;
}

input[type=text], input[type=password] {
    padding: 12px 20px;
    margin: 8px 0;
    display: inline-block;
    border: 1px solid #ccc;
    box-sizing: border-box;
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

#middle-left
{
    position: fixed;
    width: 300px;
    height: 300px;
    top: 30%;
    left: 40%;
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
<h1 style="color: white" font-family:"Helvetica Neue">Member Sign Up</h1></br>

<form id="myform">
<label style="color: white"><b>Username</b></label>&nbsp;
<input type="text" name="user" name="userID" id="userID" placeholder="Please Enter Username" required></br></br>
<label style="color: white"><b>Password </b></label>&nbsp;
<input type="password" name="pass" id="Password" placeholder="Please Enter Password" required></br></br>
<label style="color: white"><b>First Name</b></label>
<input type="text" name="fname" id="fname" placeholder="Please Enter First name" required></br></br>
<label style="color: white"><b>Last Name</b></label>
<input type="text" name="lname" id="lname" placeholder="Please Enter Last name" required></br></br>
<label style="color: white"><b>Address  </b></label>&nbsp;&nbsp;&nbsp;&nbsp;
<input type="text" name="address" id="address" placeholder="Please Enter Address" required></br></br>
<input type="submit" name="register" value="Register" />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input type="submit" onclick="window.location.href='index.jsp'" value="Back"/>
</form>
<br/><br/>

</div>



<div class="Connect">
<%
/*---------------------------------------------------------------------*
 * Purpose: This method is to connect jsp to database controller.      |
 *          The sign up page is to get input from user and send        |
 *          info such as firstname and lastname to database. If sign   |
 *          up successfully, it will jump to login page. Or it will    |
 *          return an error message that the username has already      |
 *          existed.(Return false if duplicate username)               |
 *---------------------------------------------------------------------*
 */
request.setCharacterEncoding("utf-8");
response.setContentType("text/html;charset=utf-8");

//get info from input
String password = (String) request.getParameter("pass");
String username = (String) request.getParameter("user");
String fname = (String) request.getParameter("fname");
String lname = (String) request.getParameter("lname");
String address = (String) request.getParameter("address");

//build connection to database
DatabaseController db = new DatabaseController();
db.EstablishConnection();

//click register button, if sign up successfully
//wiil jump to login page or it will show an error
//message.
if(request.getParameter("register")!= null){
    boolean check = db.insertNewMember(new String[]{fname,lname,address,username,password});
    if(check){
        response.sendRedirect("./index.jsp");
    }else{
        out.write("<h3 style='color:#4CAF50' align='top'>Something gets wrong! Please sign up again</h2>");
    }
    
}
//close database
db.termination();
%>

</div>





</body>
</html>