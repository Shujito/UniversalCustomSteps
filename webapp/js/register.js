window.Register = function Login() {
	$('#btn-register').on('click', function(ev){
		var button = $(this);
		ev.preventDefault();
		var stuff = Utils.serializeInputs('#form-login');
		//console.log(stuff);
		$('input').parent().removeClass('has-error');
		var inputUsername = $('input[name=username]');
		var inputEmail = $('input[name=email]');
		var inputPassword = $('input[name=password]');
		var inputConfirm = $('input[name=confirm]');
		if (stuff.username.length === 0 || stuff.email.length === 0 ||
			stuff.password.length === 0 || stuff.confirm.length === 0) {
			if (stuff.username.length === 0) {
				inputUsername.parent().addClass('has-error');
			}
			if (stuff.email.length === 0) {
				inputEmail.parent().addClass('has-error');
			}
			if (stuff.password.length === 0) {
				inputPassword.parent().addClass('has-error');
			}
			if (stuff.confirm.length === 0) {
				inputConfirm.parent().addClass('has-error');
			}
			alert('Fill in your login information!');
			return;
		}
		if (!rfc822validate(stuff.email)) {
			inputEmail.parent().addClass('has-error');
			alert('Invalid email address');
			return;
		}
		if (stuff.password !== stuff.confirm) {
			inputPassword.parent().addClass('has-error');
			inputConfirm.parent().addClass('has-error');
			alert('Passwords do not match!');
			return;
		}
		button.button('loading');
		Utils.post('/api/users/register', stuff, function(err,data) {
			button.button('reset');
			if (err) {
				console.log(err);
				alert(err.message);
				return;
			}
			//console.log('login:',data);
			page('/login');
			alert('you can login now!');
		});
	});
}
