<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<html>
	<head>
		<meta charset="utf-8" />
		<title>DemoFYS</title>
	</head>
	<body>

		<h1>My First Google Map</h1>

		<div id="googleMap" style="width:100%;height:100%;"></div>


		<script>
			const TIME_BETWEEN_UPDATES = 5000;

			function initialize() {
				function myMap() {
					var mapProp = {
						center: new google.maps.LatLng(51.508742, -0.120850),
						zoom: 5,
					};
					var map = new google.maps.Map(document.getElementById("googleMap"), mapProp);

					var marker = new google.maps.Marker({
							position: new google.maps.LatLng(51.508742, -0.120850),
							icon: '<c:url value="/assets/images/plane-icon.png" />'
						})
						;

					marker.setMap(map);
					keepUpdatingPlaneLocation(map, marker);
				}

				function keepUpdatingPlaneLocation(map, marker) {
					setTimeout(function () {
						var request = $.ajax({
							url: "/gps",
							method: "get",
							dataType: 'json'
						});

						request.done(function (msg) {
							moveMarker(map, marker, msg);
							keepUpdatingPlaneLocation(map, marker);
						});

						request.fail(function () {
							alert("Map Refresh Failed. Reload the page to try again.");
						});
					}, TIME_BETWEEN_UPDATES);
				}

				function moveMarker(map, marker, msg) {
					location = new google.maps.LatLng(0, 0);
					marker.setPosition(location);
					map.panTo(location);
				}
			}

			initialize();

		</script>

		<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCx1XNc4JHeS-Vl7Gfyv5b1Et940jzToFE&callback=myMap"></script>
		<script src="<c:url value="/assets/js/jquery-3.2.1.min.js" />"></script>
	</body>
</html>
