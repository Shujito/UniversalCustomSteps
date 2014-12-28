function routeTemplate(source,init) {
	var newHtml = $(source).text();
	var content = $('#content');
	if (init) {
		content[0].innerHTML = newHtml;
	} else {
		content.addClass('transition');
		setTimeout(function(){
			content.removeClass('transition');
			content[0].innerHTML = newHtml;
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
	routeTemplate('#login-template',ctx.init);
});

page('*',function nothing(ctx) {
	routeTemplate('#404-template',ctx.init);
});

page();
