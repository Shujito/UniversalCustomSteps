function routeTemplate(source,init) {
	var newContents = $(source).text();
	var bodyContent = $('#body-content');
	if (init) {
		bodyContent[0].innerHTML = newContents;
	} else {
		bodyContent.addClass('hide');
		setTimeout(function(){
			bodyContent.removeClass('hide');
			bodyContent[0].innerHTML = newContents;
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

page('/ucs', function ucs(ctx) {
	routeTemplate('#ucs-template',ctx.init);
});

page('/login', function login(ctx) {
	routeTemplate('#login-template',ctx.init);
});

page('*',function nothing(ctx) {
	routeTemplate('#404-template',ctx.init);
});

page();
