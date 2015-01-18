window.Login = function Login() {
	$('#btn-login').on('click', function (ev) {
		var button = $(this);
		ev.preventDefault();
		var stuff = Utils.serializeInputs('#form-login');
		//console.log('stuff:',stuff);
		$('input').parent().removeClass('has-error');
		var inputUsername = $('input[name=username]');
		var inputPassword = $('input[name=password]');
		if (stuff.username.length === 0 || stuff.password.length === 0) {
			if (stuff.username.length === 0)
				inputUsername.parent().addClass('has-error');
			if (stuff.password.length === 0)
				inputPassword.parent().addClass('has-error');
			alert.warning('Fill in your credentials!');
			return;
		}
		button.button('loading');
		Users.login(stuff, function (err,data) {
			button.button('reset');
			if (err) {
				console.log(err);
				if (err.status === 404) {
					// username is bad
					inputUsername.parent().addClass('has-error');
				}
				if (err.status === 406) {
					// password is bad
					inputPassword.parent().addClass('has-error');
				}
				alert.warning(err.message);
				return;
			}
			// save it!
			lawn.save({
				key:'login-data',
				value: data
			},function (data) {
				page('/me');
			});
		});
	});
}
