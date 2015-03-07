<%@ page language="java" contentType="text/html; charset=ISO-8859-1" session="true"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html>

<head>

  <meta charset="UTF-8">

  <title>CodePen - Login - Animation - NoEdit </title>

  <link href="<c:url value="/resources/font-awesome/css/font-awesome.css" />" rel="stylesheet">

 <!--  <link rel='stylesheet prefetch' href='http://fonts.googleapis.com/css?family=Open+Sans:600,700,300'> -->

    <style>
html,
body {
  width: 100%;
  height: 100%;
}
body {
  margin: 0 auto;
  display: table;
  text-align: center;
  font-family: 'Open Sans', sans-serif;
  background: #81b5d6;
  max-width: 33em;
}
.wrap {
  margin-top:50px;
}

.flip-container {
  perspective: 1000;
  border-radius: 50%;
  margin: 0 auto 10px auto;
}

.logged-in {
	transform: rotateY(180deg);
}

.flip-container, .front, .back, .back-logo {
	width: 130px;
	height: 130px;
}

.flipper {
	transition-duration: 0.6s;
	transform-style: preserve-3d;
}

.front
 {
  background-size: cover;
}

.front {
	background: url('resources/img/Flip_Img.png') 0 0 no-repeat;
}


h1 {
  font-size: 22px;
  color: #FFF;
}
h1 span {
  font-weight: 300;
}
input[type=text],
input[type=password] {
  color:#FFF;
  background: #68add8; /* Old browsers */
  background: linear-gradient(45deg,  #68add8 0%,#8cbede 100%); /* W3C */
  width:250px;
  height:40px;
  margin: 0 auto 10px auto;
  font-size:14px;
  padding-left:15px;
  border:none;
  box-shadow: -3px 3px #679acb ;
  -webkit-appearance:none;
  border-radius:0;
  border-top: 1px solid #92c5e2;
  border-right: 1px solid #92c5e2;
}
input::-webkit-input-placeholder { 
  color: #FFF;
}
input:focus {
  outline:none;
}
input[type=submit] {
    color: #fff;
    background-color:#3f88b8;
    font-size: 14px;
    height: 40px;
    border: none;
    margin: 0 auto 0 17px;
    padding: 0 20px 0 20px;
    -webkit-appearance:none;
    border-radius:0;
    cursor: pointer;
}
input[type=submit]:hover {
  background-color:#3f7ba2;
}
a {
  color:#1c70a7;
  font-weight:600;
  font-size:12px;
  text-decoration:none;
}
a:hover {
  color:#3f7ba2;
}

.hint
{
  width:250px;
  dislay:block;
  margin:80px auto 0 auto;
  text-align:left;
}

.hint p
{
  padding: 5px 0 5px 0;
  color:#FFF;
  font-weight:600;
  font-size:20px;
}

.hint p span
{
  font-weight:300;
  font-size:16px;
}

</style>

    <script src="<c:url value="/resources/js/prefixfree.min.js" />"></script>
	<script>
	$('.error').empty();
	</script>
</head>

<body>

  <!--    
        JS Credits to bartveneman
        Comments? Feel free!
 -->

<div class="wrap">

  <div class="flip-container" id='flippr'>
    <div class="flipper">
      <div class="front"></div>
    </div>
  </div>

  <h1 class="text" id="welcome">Welcome. <span>please login.</span></h1>
	<c:if test="${not empty error}">
			<h4 class="error" style="color:red">${error}</h4>
		</c:if>
		<c:if test="${not empty msg}">
			<h4 class="msg" style="color:red">${msg}</h4>
		</c:if>
  <form name='loginForm'
	action="<c:url value='/auth/login_check?targetUrl=${targetUrl}' />"
	method='POST'>
    <input type='text' id="username" name='username' placeholder='Username'>
    <input type='password' id='password' name='password' placeholder='Password'>

    <div class='login'>
      <input type='submit' value='Login' name="submit">
    </div><!-- /login -->
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
  </form>
</div><!-- /wrap -->


  <script src="<spring:url value="/resources/loginjs/jquery.js" />"></script>

  <script src="<c:url value="/resources/js/index.js" />"></script>

</body>

</html>