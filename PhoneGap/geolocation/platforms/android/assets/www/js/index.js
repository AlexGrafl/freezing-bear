 //Location content element
  var lc;
  //PhoneGap Ready variable
  var pgr = false;


  function getLocation() {
	//alert("getLocation");
	if(pgr == true) {
	
	  var options = {maximumAge: 0, timeout: 10000, enableHighAccuracy:true};
	  navigator.geolocation.getCurrentPosition(onLocationSuccess, onLocationError, options);
	  lc.innerHTML = "Reading location...";

	} else {
	  alert("Please wait,\nPhoneGap is not ready.");
	}
  }

  function onLocationSuccess(loc) {
	//alert("onLocationSuccess");
	//We received something from the API, so first get the
	// timestamp in a date object so we can work with it
	var d = new Date(loc.timestamp);
	//Then replace the page's content with the current
	// location retrieved from the API
	lc.innerHTML = '<b>Current Location</b><hr /><b>Latitude</b>: ' + loc.coords.latitude + '<br /><b>Longitude</b>: ' + loc.coords.longitude + '<br /><b>Altitude</b>: ' + loc.coords.altitude + '<br /><b>Accuracy</b>: ' + loc.coords.accuracy + '<br /><b>Altitude Accuracy</b>: ' + loc.coords.altitudeAccuracy + '<br /><b>Heading</b>: ' + loc.coords.heading + '<br /><b>Speed</b>: ' + loc.coords.speed + '<br /><b>Timestamp</b>: ' + d.toLocaleString();
  }

  function onLocationError(e) {
	alert("Geolocation error: #" + e.code + "\n" + e.message);
	lc.innerHTML = '<p>Error: couldnt read location!</p>'
  }

 
var app = {
    
	// Application Constructor
    initialize: function() {
        this.bindEvents();
		
    },
	
    // Bind Event Listeners
    //
    // Bind any events that are required on startup. Common events are:
    // 'load', 'deviceready', 'offline', and 'online'.
    bindEvents: function() {
        document.addEventListener('deviceready', this.onDeviceReady, false);
		//document.addEventListener('load', this.onLoadBody, false);
    },
    // deviceready Event Handler
    //
    // The scope of 'this' is the event. In order to call the 'receivedEvent'
    // function, we must explicitly call 'app.receivedEvent(...);'
   onDeviceReady: function() {
        lc = document.getElementById("locationInfo");
		pgr = true;
		
		app.receivedEvent('deviceready');
	},
    // Update DOM on a Received Event
    receivedEvent: function(id) {
        var parentElement = document.getElementById(id);
        var listeningElement = parentElement.querySelector('.listening');
        var receivedElement = parentElement.querySelector('.received');

        listeningElement.setAttribute('style', 'display:none;');
        receivedElement.setAttribute('style', 'display:block;');

		alert('Received Event: ' + id);
        console.log('Received Event: ' + id);
    }	
};
app.initialize();


