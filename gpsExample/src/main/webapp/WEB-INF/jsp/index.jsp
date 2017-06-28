<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=Edge">
        <meta name="viewport" content="initial-scale=1, maximum-scale=1, width=device-width, height=device-height, user-scalable=no">

        <title>BeaconStrip</title>

        <link rel="stylesheet" href="//bootswatch.com/yeti/bootstrap.min.css">
        <link rel="stylesheet" href="<c:url value="/assets/styles/bs-app.css" />">

        <!--[if lt IE 9]>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->
    </head>
    <body ng-app="bsApp">
        <nav class="navbar navbar-default navbar-static-top">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bsNav" aria-expanded="false">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="#/dashboard">BeaconStrip</a>
                </div>
                <div class="collapse navbar-collapse" id="bsNav">
                    <ul class="nav navbar-nav">
                        <li><a href="#/actions">Actions</a></li>
                        <li><a href="#/beacons">Beacons</a></li>
                        <li><a href="#/groups">Groups</a></li>
                        <li><a href="#/roles">Roles</a></li>
                        <li><a href="#/rules">Rules</a></li>
                        <li><a href="#/users">Users</a></li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="logout">Logout</a></li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="#/settings">Settings</a></li>
                    </ul>
                </div>
            </div>
        </nav>
        <noscript>
        <div class="container">
            <div class="alert alert-warning">
                <p>JavaScript is disabled! Please turn on JavaScript.</p>
            </div>
        </div>
        </noscript>
        <div class="container">
            <div ng-view=""></div>
        </div>
        <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="//cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.6/js/bootstrap.min.js"></script>
        <script src="//cdnjs.cloudflare.com/ajax/libs/angular.js/1.4.8/angular.min.js"></script>
        <script src="//cdnjs.cloudflare.com/ajax/libs/angular.js/1.4.8/angular-route.min.js"></script>
        <script src="//cdnjs.cloudflare.com/ajax/libs/angular.js/1.4.8/angular-resource.min.js"></script>
        <script src="//cdnjs.cloudflare.com/ajax/libs/angular-filter/0.5.8/angular-filter.min.js"></script>
        <script src="<c:url value="/assets/scripts/dragular.min.js" />"></script>
        <script src="//maps.googleapis.com/maps/api/js"></script>
        <script src="<c:url value="/assets/scripts/bs-app.js" />"></script>
        <script src="<c:url value="/assets/scripts/bs-controllers.js" />"></script>
        <script src="<c:url value="/assets/scripts/bs-services.js" />"></script>
        <script src="<c:url value="/assets/scripts/searchFilter.js" />"></script>
        <script src="<c:url value="/assets/scripts/directives/beacon-form.js" />"></script>
        <script src="<c:url value="/assets/scripts/directives/beacon-map.js" />"></script>
        <script src="<c:url value="/assets/scripts/directives/ripple-loader.js" />"></script>
    </body>
</html>
