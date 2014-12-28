function route(source) {
	var contents = $(source).text();
	$('#body-content')[0].innerHTML = contents;
}

page('/', function index() {
	route('#songs-template');
});

page('/index', function index() {
	route('#songs-template');
});

page('/songs', function index() {
	route('#songs-template');
});

page('/ucs', function index() {
	route('#ucs-template');
});

page('*',function nothing() {
	route('#404-template');
});

page();