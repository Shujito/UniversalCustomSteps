function renderTemplate(newHtml,init,fn) {
	var content = $('#content');
	if (init) {
		content.html(newHtml);
		if (typeof fn == 'function') {
			fn();
		}
	} else {
		content.addClass('transition');
		setTimeout(function () {
			content.removeClass('transition');
			content.html(newHtml);
			if (typeof fn == 'function') {
				fn();
			}
		},300);
	}
}

function routeTemplate(source,init,fn) {
	var newHtml = $(source).text();
	renderTemplate(newHtml,init,fn);
}

function header(ctx,next) {
	lawn.get('login-data', function (data) {
		if (data && data.value && data.value['access_token']) {
			$('#menu-login').addClass('hidden');
			$('#menu-register').addClass('hidden');
			$('#menu-me').removeClass('hidden');
			$('#menu-logout').removeClass('hidden');
		} else {
			$('#menu-login').removeClass('hidden');
			$('#menu-register').removeClass('hidden');
			$('#menu-me').addClass('hidden');
			$('#menu-logout').addClass('hidden');
		}
	});
	next();
}

function index(ctx,next) {
	if (!window['user-data']) {
		Users.me(function index(err,data) {
			if (err || !data) {
				return;
			}
			$.event.trigger({type:'user-data','user-data':data});
			// TODO: put it on the lawnchair maybe...
			window['user-data'] = data;
			$('#menu-me a.btn').text(data['display_name']);
		});
	}
	$('li.active').removeClass('active');
	$('[href="'+ ctx.path +'"]').parent().addClass('active');
	next();
}

page('*', header, index);

page('/', function index(ctx) {
	routeTemplate('#index-template',ctx.init);
});

page('/songs', function songs(ctx) {
	window.Songs.render(function(html) {
		renderTemplate(html, ctx.init, function (){
			window.Songs();
		});
	})
});

page('/song/:id', function songs(ctx,next) {
	console.log('context:',ctx);
	routeTemplate('#song_id-template',ctx.init);
});

/*
page('/ucs', function ucs(ctx) {
	routeTemplate('#ucs-template',ctx.init);
});
//*/

function isLogged(actuallyIs) {
	actuallyIs = !!actuallyIs;
	return function isLogged(ctx,next) {
		lawn.get('login-data', function (data) {
			var has = data && data.value && data.value['access_token'];
			if ((actuallyIs && !has) || has ) {
				next();
			} else {
				page('/');
			}
		});
	}
}

page('/login', isLogged(true), function login(ctx) {
	routeTemplate('#login-template', ctx.init, function () {
		window.Login();
	});
});

page('/register', isLogged(true), function register(ctx) {
	routeTemplate('#register-template', ctx.init, function () {
		window.Register();
	});
});

page('/me', isLogged(false), function me(ctx) {
	routeTemplate('#me-template', ctx.init, function () {
		window.Me();
	});
});

page('/logout', isLogged(false), function logout(ctx) {
	routeTemplate('#logout-template', ctx.init, function () {
		window.Logout();
	});
});

page('*', function nothing(ctx) {
	routeTemplate('#404-template', ctx.init, function () {
		console.log('noh!');
	});
});

$(function () {
	page();
	$('li a').on('click',function(e) {
		if (window.innerWidth < 768)
			$('#ucs-collapse-menu').collapse('hide');
	});
});
