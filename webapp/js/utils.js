window.Utils = window.Utils || {};

window.Utils.serializeInputs = function(selector) {
	var fields = $(selector);
	var values = fields.serializeArray();
	var json = {};
	for(var idx in values) {
		json[values[idx].name] = values[idx].value;
	}
	return json;
};

window.Utils.ajax = function(method,url,data,callback) {
	if (location.host === 'localhost:9000') {
		var newHost = 'http://0.0.0.0:1337';
		if (url[0] === '/')
			url = newHost + url;
		else
			url = newHost + '/' + url;
	}
	console.log(url);
	var options = {};
	options.type = method;
	options.url = url;
	options.contentType = 'application/json';
	if(data && typeof data === 'object') {
		options.data = JSON.stringify(data);
	} else {
		options.data = data;
	}
	options.success = function(e) {
		if(callback) {
			callback(null,e);
		}
	};
	options.error = function(e) {
		if(callback) {
			callback(e.responseJSON || e.response);
		}
	};
	$.ajax(options);
};

window.Utils.get = function(url,callback) {
	window.Utils.ajax('GET',url,null,callback);
};

window.Utils.post = function(url,data,callback) {
	window.Utils.ajax('POST',url,data,callback);
};

window.Utils.put = function(url,data,callback) {
	window.Utils.ajax('PUT',url,data,callback);
};

window.Utils.delete = function(url,callback) {
	window.Utils.ajax('DELETE',url,null,callback);
};
