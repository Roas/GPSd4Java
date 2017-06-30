<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<html>
	<head>
		<meta charset="utf-8" />
		<title>DemoFYS</title>

		<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCx1XNc4JHeS-Vl7Gfyv5b1Et940jzToFE"></script>
		<script src="<c:url value="/assets/js/jquery-3.2.1.min.js" />"></script>
	</head>
	<body style="margin:0">
		<div id="googleMap" style="width:100%;height:100%;"></div>
		<div style="position:absolute; right:0; top:0; z-index:9999; margin-right:10px;">
			<h3 id="altitude" style="margin:0;"></h3>
			<h3 id="time" style="margin:0;"></h3>
		</div>
		<script>
			var TIME_BETWEEN_UPDATES = 2000;
			var PLANE_ICON = '<c:url value="/assets/images/plane-icon.png" />';
			var TIME_OFFSET = 0;
			var TIMEZONE_REQUEST_INTERVAL = 10;
			var TIMEZONE_COUNTER = 0;

				function initialize()
				{
					var mapProp = {
						center: new google.maps.LatLng(51.508742, -0.120850),
						zoom: 5
					};
					var map = new google.maps.Map(document.getElementById("googleMap"), mapProp);

					var marker = new google.maps.Marker({
							//position: new google.maps.LatLng(51.508742, -0.120850),
							icon: {
								rotation: 0,
								url: PLANE_ICON
							}
						});

					marker.setMap(map);
					keepUpdatingPlaneLocation(map, marker);
				}

				function keepUpdatingPlaneLocation(map, marker)
				{
					setTimeout(function ()
					{
						var request = $.ajax({
							url: "/gps",
							method: "get",
							dataType: 'json'
						});

						request.done(function (msg)
						{
							moveMarker(map, marker, msg);
							keepUpdatingPlaneLocation(map, marker);

							if(TIMEZONE_COUNTER == 0) {
                                getTimeZone(msg.latitude, msg.longitude);
							} else if(TIMEZONE_COUNTER >= TIMEZONE_REQUEST_INTERVAL) {
                                getTimeZone(msg.latitude, msg.longitude);
                                TIMEZONE_COUNTER =  0;
                            }
                            TIMEZONE_COUNTER++;
						});

						request.fail(function ()
						{
							alert("Map Refresh Failed. Reload the page to try again.");
						});
					}, TIME_BETWEEN_UPDATES);
				}

				function getTimeZone(x, y) {
                    var request = $.ajax({
                        url: "https://maps.googleapis.com/maps/api/timezone/json?location=" + x + "," + y + "&timestamp=1458000000&key= AIzaSyAgPe8wa5rvsBp1XoI_83O2DamD4ks8WBY",
                        method: "get",
                        dataType: 'json'
                    });

                    request.done(function (msg)
                    {
                        TIME_OFFSET = msg.rawOffset;
                    });
				}

				function moveMarker(map, marker, msg)
				{
					var newCoordinate = new google.maps.LatLng(msg.latitude, msg.longitude);
					marker.setPosition(newCoordinate);
                    marker.icon.rotation = msg.course;

                    var time = msg.timestamp + TIME_OFFSET;
                    var hours = Math.floor(time / 3600);
                    time = time - hours * 3600;
                    var minutes = Math.floor(time / 60);

                    $("#altitude").text(Math.round(msg.altitude) + " m");
                    $("#time").text(str_pad_left(hours,'0',2)+':'+str_pad_left(minutes,'0',2));
					//map.panTo(newCoordinate);
				}

            function str_pad_left(string,pad,length)
			{
                return (new Array(length+1).join(pad)+string).slice(-length);
            }

            $(document).ready(function()
			{
                initialize();
            });
		</script>
	</body>
</html>
