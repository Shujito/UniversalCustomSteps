window.Logout = function Logout() {
	//console.log('logout part');
	$('button#logout-yes').on('click',function(e) {
		var button = $(this);
		button.button('loading');
		Users.logout(function() {
			button.button('reset');
			delete window['user-data'];
			page('/login');
		});
	});
}
