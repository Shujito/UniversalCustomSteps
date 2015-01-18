// node modules
window.jQuery = require('jquery');
window.$ = jQuery;
window.page = require('page');
window.rfc822validate = require('rfc822-validate');
require('bootstrap');
require('./node_modules/bootstrap-sweetalert/lib/sweet-alert.js');
// libs
require('./js/_libs/lawnchair-0.6.1.js');
// model
require('./js/models/users.js');
require('./js/models/songs.js');
// viewcontroller
require('./js/controllers/login.js');
require('./js/controllers/logout.js');
require('./js/controllers/me.js');
require('./js/controllers/register.js');
require('./js/controllers/songs.js');
require('./js/routes.js');
require('./js/storage.js');
require('./js/utils.js');

function checkSize() {
	if (window.outerWidth - window.innerWidth > 100 || window.outerHeight - window.innerHeight > 100) {
		//console.clear();
		console.info('Te gusta lo que ves?');
		//$('html').remove();
	}
}

window.onload = function() { checkSize(); }
window.onresize = function() { checkSize(); }
