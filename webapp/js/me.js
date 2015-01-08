window.Me = function Me() {
	// TODO: wait for userdata instead or reload?
	if (!window['user-data']) return;
	$('input[name=display_name]').attr('placeholder',window['user-data'].display_name);
	$('input[name=email]').attr('placeholder',window['user-data'].email);
}
