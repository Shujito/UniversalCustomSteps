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
		setTimeout(function(){
			content.removeClass('transition');
			content.html(newHtml);
			if (typeof fn == 'function') {
				fn();
			}
		},300);
	}
}

page('*', function index(ctx,next) {
	//console.log('init:'+!!ctx.init);
	$('li.active').removeClass('active');
	$('[href="'+ ctx.path +'"]').parent().addClass('active');
	next();
});

page('/', function index(ctx) {
	routeTemplate('#songs-template',ctx.init);
});

page('/songs', function songs(ctx) {
	routeTemplate('#songs-template',ctx.init);
});

page('/songs/:id', function songs(ctx) {
	routeTemplate('#song_id-template',ctx.init);
});

/*
page('/ucs', function ucs(ctx) {
	routeTemplate('#ucs-template',ctx.init);
});
//*/

page('/login', function login(ctx) {
	routeTemplate('#login-template',ctx.init,function(){
		window.Login();
	});
});

page('/register', function register(ctx) {
	routeTemplate('#register-template',ctx.init,function(){
		window.Register();
	});
});

page('*', function nothing(ctx) {
	routeTemplate('#404-template',ctx.init,function(){
		console.log('noh!')
	});
});

$(function(){ page(); });
