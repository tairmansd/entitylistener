(function() {
	var module = angular.module('autoGridModule', ['ui.grid']);
	
	var controller = module.controller('MainCtrl', ['$scope', '$http', 'uiGridConstants', function($scope, $http, uiGridConstants) {
		
		function employeeHandler(activity) {
			if(activity.operation=="INSERT") {
				$scope.message = "detected insert... with id"+JSON.stringify(activity);
				
				$http.get('api/employee/search').then(function(response) {
					$scope.gridOptions.data = response.data;
				}, function(error) {
					console.error(error);
				});
				
				/*$http.get('api/employee/search?id='+activity.tableRefId).then(function(response) {
					$scope.gridOptions.data.push(response.data);
					$scope.gridOptions.data = angular.copy($scope.gridOptions.data);
				}, function(error) {
					console.error(error);
				});*/
				
			} else if(activity.operation=="UPDATE") {
				$scope.message = "detected update... with id"+JSON.stringify(activity);
				
				$http.get('api/employee/search').then(function(response) {
					$scope.gridOptions.data = response.data;
				}, function(error) {
					console.error(error);
				});
				
				/*$http.get('api/employee/search?id='+activity.tableRefId).then(function(response) {
					for (var i = 0; i < $scope.gridOptions.data.length; i++) {
						var obj = $scope.gridOptions.data[i];
						if(obj.emp_no == activity.tableRefId) {
							$scope.gridOptions.data[i] = response.data;
							break;
						}
					}
					$scope.gridOptions.data = angular.copy($scope.gridOptions.data);
				}, function(error) {
					console.error(error);
				});*/
				
			} else if(activity.operation=="DELETE"){
				$scope.message = "detected deleted... with id"+JSON.stringify(activity);
				
				$http.get('api/employee/search').then(function(response) {
					$scope.gridOptions.data = response.data;
				}, function(error) {
					console.error(error);
				});
				
				/*var i = 0;
				for (var i = 0; i < $scope.gridOptions.data.length; i++) {
					var obj = $scope.gridOptions.data[i];
					if(obj.emp_no == activity.tableRefId) {
						break;
					}
					i++;
				}
				$scope.gridOptions.data.splice(i,1);
				$scope.gridOptions.data = angular.copy($scope.gridOptions.data);;*/
			}
		}
		
		var socket = new WebSocket("ws://localhost:8090/entitylistener/ws/entitylistener");
		
		var handlers = {
			"EMPLOYEES" : employeeHandler
		};
		
		socket.onmessage = function (event) {
			var json = JSON.parse(event.data);
			for(var index = 0; index < json.data.length; index++) {
				handlers[json.data[index].tableName](json.data[index]);
			}
		};
		
		$http.get('api/employee/search').then(function(response) {
			$scope.gridOptions.data = response.data;
		}, function(error) {
			console.error(error);
		});
		
		$scope.gridOptions = {
			data: [],
			onRegisterApi: function(gridApi){ $scope.gridApi = gridApi;}
		};
	}]);
	
})();