<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page import="java.util.*" %>
<%@ page import="java.lang.*" %>
<%@ page import="dbController.DatabaseController" %>

<html>
<head>
<title>Member Login Page</title>
<style>
body {
    font-family:"Helvetica Neue";
    background-image: url("./images/morty.png");
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

#bottom-right
{
    position: fixed;
    bottom: 0;
    margin-left:15%;
    overflow: auto;
}


</style>
</head>

<body>
<br/><br/><br/>
<br/><br/><br/>
<br/><br/><br/>
<br/><br/><br/>

<div id="bottom-right">
<h1 style="color: #4CAF50" font-family:"Helvetica Neue">Member Login</h1></br>

<form id="myform">
<label style="color: white"><b>Username</b></label>
<input type="text" name="userID" id="userID" placeholder="Please Enter Username" required>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

<label style="color: white"><b>Password </b></label>
<input type="password" name="Password" id="Password" placeholder="Please Enter Password" required>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input type="submit" id="login" name="logbtn" value="Log In" />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input type="submit" id="signup" onclick="window.location.href='register.jsp'" value="Sign Up" /></form>
<br/><br/>
</div>



<div class="Connect">
<%
//Purpose: This method is to connect jsp to database controller.
//         Send input username and password to database so we can
//        check database that whether we have this customer. If
//         return -1, it means no such customer. Return 0 means
//         it is cutomer and skip to customer page. Return 1 means
//         it is administration and skip to administration page.
request.setCharacterEncoding("utf-8");
response.setContentType("text/html;charset=utf-8");

//get username and password from input
String password = (String) request.getParameter("Password");
String username = (String) request.getParameter("userID");

//build connection to database
DatabaseController db = new DatabaseController();
db.EstablishConnection();

if(request.getParameter("logbtn")!= null){
    int result = db.verifyingMember(username,password);
    if(result < 0){
        out.write("<h3 style='color:white'>Opps! You are not our member! Sign up now</h2>");
    }else if(result == 0){
        session.setAttribute("current_user",username);
        session.setAttribute("current_password",password);
        response.sendRedirect("./customer.jsp");
    }else{
        session.setAttribute("current_user",username);
        session.setAttribute("current_password",password);
        response.sendRedirect("./administration.jsp");
    }
}

//close database
db.termination();
%>

</div>




</body>
</html>