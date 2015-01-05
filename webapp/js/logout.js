window.Logout = function Logout() {
	//console.log('logout part');
	$('button#logout-yes').on('click',function(e){
		var button = $(this);
		//console.log('logout yes');
		lawn.get('login-data',function(data){
			if (!data) return;
			var accessToken = data.value['access_token'];
			var headers = {'access-token':accessToken}
			button.button('loading');
			Utils.delete('/api/users/logout',headers,function(err,data) {
				button.button('reset');
				if (err) {
					console.log(err);
					alert(err.message);
				}
				// whatever the result...
				lawn.remove('login-data',function(data){
					page('/login');
				});
			});
		});
	});
	$('button#logout-no').on('click',function(e){
		window.history.back();
	});
}