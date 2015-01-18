function setUserData(userData) {
	$('input[name=display_name]').attr('placeholder',userData.display_name);
	$('input[name=email]').attr('placeholder',userData.email);
}

window.Me = function Me() {
	$(document).on('user-data',function(data) {
		var userData = data['user-data'];
		setUserData(userData);
	});
	if (!window['user-data']) return;
	setUserData(window['user-data']);
	$('#btn-save').on('click', function (ev) {
		ev.preventDefault();
	});
}
