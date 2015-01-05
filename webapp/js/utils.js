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

window.Utils.ajax = function(method,url,headers,data,callback) {
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
	//options.crossDomain = true;
	options.headers = headers;
	options.success = function(e) {
		if(callback) {
			callback(null,e);
		}
	};
	options.error = function(e) {
		if(callback && (e.responseJSON || e.response)) {
			callback(e.responseJSON || e.response);
		} else {
			callback({ message:'cors' });
		}
	};
	$.ajax(options);
};

window.Utils.get = function(url,headers,callback) {
	window.Utils.ajax('GET',url,headers,null,callback);
};

window.Utils.post = function(url,headers,data,callback) {
	window.Utils.ajax('POST',url,headers,data,callback);
};

window.Utils.put = function(url,headers,data,callback) {
	window.Utils.ajax('PUT',url,headers,data,callback);
};

window.Utils.delete = function(url,headers,callback) {
	window.Utils.ajax('DELETE',url,headers,null,callback);
};
