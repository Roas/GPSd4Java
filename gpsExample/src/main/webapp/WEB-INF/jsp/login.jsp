<%-- 
    Document   : login
    Created on : 26-nov-2015, 13:36:54
    Author     : Ben
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=Edge">
        <meta name="viewport" content="initial-scale=1, maximum-scale=1, width=device-width, height=device-height, user-scalable=no">

        <title>BeaconStrip</title>

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.6/css/bootstrap.min.css">

        <!--[if lt IE 9]>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->
    </head>
    <body>
        <div class="container">
            <div class="col-sm-offset-2 col-sm-10" style="margin-top: 10px;">
                <img class="center-block" src="assets/images/logo_beaconstrip_3x.png">
            </div>
            <c:choose>
            <c:when test="${not empty param.logout}">
                <div class="col-sm-offset-2 col-sm-10">
                    <h4 style="text-align: center;">You are now logged out. We hope to see you again soon
                        <br><br><a href="login">Click here to go to the login page</a>
                    </h4>
                </div>
            </c:when>
            <c:otherwise>
            <form action="" method="post" class="form-horizontal">
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <h4 style="text-align: center;">Welcome to Beaconstrip. Please login</h4>
                        <c:if test="${not empty param.error}">
                            <div class="alert alert-danger" role="alert" style="margin-bottom: 0px;">
                                <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                                <span class="sr-only">Error:</span>
                                Invalid username/password combination.
                            </div>
                        </c:if>  
                    </div>
                </div>

                <div class="form-group center">
                    <label for="username" class="col-sm-offset-3 col-sm-2 control-label">Username: </label>
                    <div class="col-sm-4">
                        <input type="text" id="username" name="username" class="form-control">    
                    </div>
                </div>
                <div class="form-group">
                    <label for="password" class="col-sm-offset-3 col-sm-2 control-label">Password: </label>
                    <div class="col-sm-4">
                        <input type="password" id="password" name="password" class="form-control">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-6 col-sm-4">
                        <button style="width:50%" type="submit" class="btn btn-primary">Login</button>
                    </div>
                </div>
            </form>
            </c:otherwise>
                </c:choose>
        </div>
    </body>
</html>
