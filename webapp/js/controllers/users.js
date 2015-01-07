window.Users = {};

window.Users.me = function(callback) {
	// get data from lawn
	lawn.get('login-data', function (data) {
		if (!(data && data.value && data.value['access_token'])) {
			callback('no user data');
			return;
		}
		var accessToken = data.value['access_token'];
		Utils.get('/api/users/me',{'access-token':accessToken},function (err,data){
			callback(err,data);
		});
	});
}

window.Users.register = function(data,callback) {
	Utils.post('/api/users/register', null, data, function(err,data) {
		callback(err,data);
	});
}

window.Users.login = function(data,callback) {
	Utils.post('/api/users/login', null, data, function(err,data) {
		callback(err,data);
	});
}

window.Users.logout = function(callback) {
	lawn.get('login-data',function(data){
		if (!data) return;
		var accessToken = data.value['access_token'];
		var headers = {'access-token':accessToken};
		Utils.delete('/api/users/logout',headers,function(err) {
			if (err) {
				console.error(err);
			}
			// whatever the previous result was
			lawn.remove('login-data',function(){
				callback();
			});
		});
	});
}