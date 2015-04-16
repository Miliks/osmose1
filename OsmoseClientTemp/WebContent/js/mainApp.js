'use strict';
var pollingApp = angular.module('pollingApp', [ 'ngResource', 'ngRoute',
		'xeditable' ]);

pollingApp.run(function($rootScope) {
	$rootScope.displayError = false;
});

pollingApp.config(function($routeProvider, $locationProvider, $sceProvider) {
	if ($('html').is('.ie7')) {
		$sceProvider.enabled(false);
	}
	$routeProvider.when('/home', {
		templateUrl : 'partials/homeTestPolling.html',
		controller : 'ShowController'
	}).otherwise({
		redirectTo : '/home'
	});
});

pollingApp.controller('ShowController', function($scope, $filter, $http,
		$location, $rootScope) {
	var x2js = new X2JS();
	$scope.showPollingButton = true;

	$scope.pollingTest = function() {

		$rootScope.callsMaxNumber = $('#maxCallsInput').val();
		$rootScope.temperature = $('#temperatureInput').val();

		console.log($rootScope.callsMaxNumber);
		console.log($rootScope.temperature);

		var pollingCalls = 0;
		var callsNum = 0; // max callsMaxNumber times

		$scope.showPollingButton = false;
		pollingCalls = setInterval(function() {
			callsNum = callsNum + 1;
			$http.get(
					'/JAXRS-RESTEasy/rest/hello/temp?queryParameter='
							+ $rootScope.temperature).success(
					function(data, status) {
						if (callsNum == $rootScope.callsMaxNumber) {
							clearInterval(pollingCalls);
							console.log(data);
							$scope.showPollingButton = true;
						}
					}).error(function(data, status) {
						console.log(data);
						$location.path("/home");
					});
		}, 5000);

	};

});