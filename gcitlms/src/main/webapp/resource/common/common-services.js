libraryApp.factory("commonService", function($http) {
	var getData = {};
	var postData = {};
	
	return {
		getData : function(url) {
			return $http({
				url: url,
				method: "GET",
			}).success(function(data){
				getData = data;
			}).then(function(){
				return getData;
			});
		},
		postData : function(url, obj) {
			return $http({
				url: url,
				method: "POST",
				data: obj
			}).success(function(data){
				postData = data;
			}).then(function(){
				return postData;
			});
		},
		putData : function(url, obj) {
			return $http({
				url: url,
				method: "PUT",
				data: obj
			}).success(function(data){
				postData = data;
			}).then(function(){
				return postData;
			});
		},
		deleteData : function(url, obj) {
			return $http({
				url: url,
				method: "DELETE",
				data: obj
			}).success(function(data){
				postData = data;
			}).then(function(){
				return postData;
			});
		}
	}
});