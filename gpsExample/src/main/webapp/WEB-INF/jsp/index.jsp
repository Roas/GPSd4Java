<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <title>DemoFYS</title>
    </head>
    <body>

        <h1>My First Google Map</h1>

        <div id="googleMap" style="width:100%;height:100%;"></div>

        <script>
			function myMap() {
				var mapProp= {
					center:new google.maps.LatLng(51.508742,-0.120850),
					zoom:5,
				};
				var map = new google.maps.Map(document.getElementById("googleMap"),mapProp);

				var marker = new google.maps.Marker({
					position: new google.maps.LatLng(51.508742,-0.120850),
					icon:'<c:url value="/assets/images/plane-icon.png" />'
				});

				marker.setMap(map);
			}
        </script>

        <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCx1XNc4JHeS-Vl7Gfyv5b1Et940jzToFE&callback=myMap"></script>

    </body>
</html>