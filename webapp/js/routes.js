function routeTemplate(source,init,fn) {
	var newHtml = $(source).text();
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

function header(ctx,next) {
	lawn.get('login-data', function (data) {
		//console.log(data);
		//*
		if (data && data.value && data.value['access_token']) {
			$('#menu-login').addClass('hidden');
			$('#menu-logout').removeClass('hidden');
		} else {
			$('#menu-login').removeClass('hidden');
			$('#menu-logout').addClass('hidden');
		}
		//*/
	});
	next();
}

function index(ctx,next) {
	//console.log('init:'+!!ctx.init);
	$('li.active').removeClass('active');
	$('[href="'+ ctx.path +'"]').parent().addClass('active');
	//$('#ucs-collapse-menu').collapse('hide');
	next();
}

page('*', header, index);

page('/', function index(ctx) {
	routeTemplate('#songs-template',ctx.init);
});

page('/songs', function songs(ctx) {
	routeTemplate('#songs-template',ctx.init);
});

page('/song/:id', function songs(ctx,next) {
	console.log('context:',ctx)
	//console.log(ctx.params.id);
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
			console.log('login-data:',data);
			var has = data && data.value && data.value['access_token'];
			if ((actuallyIs && !has) || has ) {
				console.log('logged');
				next();
			} else {
				console.log('not logged');
				page('/');
			}
		})
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

page('/logout', isLogged(false), function logout(ctx) {
	routeTemplate('#logout-template', ctx.init, function () {
		window.Logout();
	});
});

page('*', function nothing(ctx) {
	routeTemplate('#404-template', ctx.init, function () {
		console.log('noh!')
	});
});

$(function () {
	page();
	$('li a').on('click',function(e){
		if (window.innerWidth < 768)
			$('#ucs-collapse-menu').collapse('hide');
	});
});
