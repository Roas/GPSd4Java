// Used to display all beacons on a google map and interact with them
bsApp.directive('beaconMap', ['Beacon', '$compile', '$timeout', function (Beacon, $compile, $timeout) {
        return {
            restrict: 'E',
            templateUrl: 'assets/scripts/directives/beacon-map.html',
            link: function (scope) {
                scope.loading = true; // Used to specify whether the map/page is loading
                // Query the database for all available beacons
                scope.beacons = Beacon.query(function () {
                    var totalLat = 0,
                            totalLng = 0,
                            beaconMarkers = [],
                            i,
                            center,
                            map,
                            infoWindow,
                            addBeaconMarker,
                            addNewBeacon,
                            deleteBeacon,
                            validLatAndLng,
                            image;
                            
                    // Check if latitude and longitude are valid values.
                    // TODO: check range
                    validLatAndLng = function (lat, lng) {
                        if (lat !== "" && lng !== "") {
                            return true;
                        }
                        return false;
                    };
                    
                    // Add a beacon marker to the map by specifying the location
                    addBeaconMarker = function (location) {
                        // Create the google maps marker
                        var marker = new google.maps.Marker({
                            icon: image,
                            position: location,
                            draggable: true,
                            animation: google.maps.Animation.DROP,
                            title: scope.beacon.name,
                            map: map
                        });
                        
                        // Give the beacon the id of the earlier created beacon
                        marker.id = scope.beacon.id;

                        // Event for gaining information about the beacon of the marker
                        marker.addListener('click', function () {
                            for (i = 0; i < scope.beacons.length; i++) {
                                if (scope.beacons[i].id === this.id) {
                                    break;
                                }
                            }

                            // Open the popup/card/window of the marker
                            infoWindow.open(map, marker);
                            // Empty the hook and fill it with the (new) beacon form
                            $('#infoWindowHook').html('');
                            $('#infoWindowHook').append($compile("<beacon-form beacon='beacons[" + i + "]'></beacon-form>")(scope));
                        });

                        // Event for deleting a marker and it's attached beacon
                        marker.addListener("rightclick", function () {
                            $('#modal-title').html('Delete beacon');
                            $('#modal-body').html('<p>Are you sure you want to delete this beacon?</p>');
                            // Show bootstrap modal/dialog
                            $('#bsDialog').modal();
                            // Delete beacon and marker on accept
                            $('#bsDialogAccept').off('click').on('click', function () {
                                deleteBeacon(marker.id);
                            });
                        });

                        // Event for moving the beacon on the map
                        marker.addListener('dragend', function () {
                            // Find the beacon with the marker id
                            for (i = 0; i < scope.beacons.length; i++) {
                                if (scope.beacons[i].id === this.id) {
                                    break;
                                }
                            }
                            // Change beacon latitude and longitude
                            scope.beacons[i].latitude = this.position.lat();
                            scope.beacons[i].longitude = this.position.lng();
                            Beacon.update({id: scope.beacons[i].id}, scope.beacons[i], function (data) {
                                console.log(data);
                            });
                        });

                        // Save/store the created marker
                        beaconMarkers.push(marker);
                    };

                    // Adds new beacon to map
                    addNewBeacon = function (location, beacon) {
                        // For new beacons
                        if (beacon === null) {
                            // CRUD: Create beacon
                            scope.beacon = new Beacon();
                            scope.beacon.latitude = location.lat;
                            scope.beacon.longitude = location.lng;
                            scope.beacon.name = "Unnamed";
                            Beacon.save(scope.beacon, function (data) {
                                scope.beacon.id = data.id;
                                // data.id bevat het id van het aangemaakte beacon!
                                console.log(data);
                                addBeaconMarker(location);
                            });
                            scope.beacons.push(scope.beacon);
                        } else {
                            scope.beacon = beacon;
                            addBeaconMarker(location);
                        }
                    };
                    // Delete beacon by specifying it's id (marker)
                    deleteBeacon = function (id) {
                        for (i = 0; i < beaconMarkers.length; i++) {
                            if (beaconMarkers[i].id === id) {
                                // CRUD: Delete beacon
                                Beacon.remove({id: id}, function (data) {
                                    console.log(data);
                                    // remove from google maps
                                    beaconMarkers[i].setMap(null);
                                    // remove from stored markers
                                    beaconMarkers.splice(i, 1);
                                }, function () {
                                    // Error
                                    scope.removeError = true;
                                    $timeout(function () {
                                        scope.removeError = false;
                                        scope.$apply();
                                    }, 5000);
                                });
                                break;
                            }
                        }
                    };

                    // Calculate center by getting the average latitude and longitude
                    // of all beacons.
                    for (i = 0; i < scope.beacons.length; i++) {
                        if (validLatAndLng(scope.beacons[i].latitud, scope.beacons[i].longitude)) {
                            totalLat += parseFloat(scope.beacons[i].latitude);
                            totalLng += parseFloat(scope.beacons[i].longitude);
                        }
                    }
                    center = {lat: totalLat / scope.beacons.length, lng: totalLng / scope.beacons.length};
                    
                    // Initialize map
                    map = new google.maps.Map(document.getElementById("map"), mapOptions = {
                        zoom: 14,
                        center: center
                    });
                    
                    // Image that's used as the marker (beacon) icon
                    image = {
                        url: 'assets/images/marker-icon.png',
                        size: new google.maps.Size(27, 30),
                        origin: new google.maps.Point(0, 0),
                        // Anchor of image
                        anchor: new google.maps.Point(13, 30)
                    };
                    
                    // Initialize marker (beacon) window
                    infoWindow = new google.maps.InfoWindow({
                        maxWidth: 600,
                        content: '<div style="overflow: hidden; white-space: nowrap;" id="infoWindowHook"></div>'
                    });
                    
                    // Add all the beacons that the query has returned to the map.
                    for (i = 0; i < scope.beacons.length; i++) {
                        if (validLatAndLng(scope.beacons[i].latitud, scope.beacons[i].longitude)) {
                            addNewBeacon({lat: parseFloat(scope.beacons[i].latitude), lng: parseFloat(scope.beacons[i].longitude)}, scope.beacons[i]);
                        }
                    }

                    // Events triggers when map is loaded
                    google.maps.event.addListenerOnce(map, 'idle', function () {
                        scope.loading = false;
                        scope.$apply();
                    });

                    // Event triggers when the user left clicks on the map
                    google.maps.event.addListener(map, 'click', function (event) {
                        // Edit the content of the modal/dialog 
                        $('#modal-title').html('Add new beacon');
                        $('#modal-body').html('<p>Are you sure you want to create a new beacon?</p>');
                        // Show modal/dialog to the user
                        $('#bsDialog').modal();
                        // Add beacon when accepted
                        $('#bsDialogAccept').off('click').on('click', function () {
                            addNewBeacon({lat: event.latLng.lat(), lng: event.latLng.lng()}, null);
                        });
                    });
                });
            }
        };
    }]);