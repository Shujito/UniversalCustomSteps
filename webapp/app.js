// node modules
window.page = require('page');
window.jQuery = require('jquery');
window.$ = jQuery;
require('bootstrap');
window.rfc822validate = require('rfc822-validate');
// libs
require('./js/_libs/lawnchair-0.6.1.js');
// controller
require('./js/controllers/users.js');
// view
require('./js/login.js');
require('./js/logout.js');
require('./js/me.js');
require('./js/register.js');
require('./js/routes.js');
require('./js/storage.js');
require('./js/utils.js');
