// some variables to emulate the browser environment under rhino
var window = new Object();
var environment = new Object();
var location = new Object();
location.protocol = 'file:';
location.hostname = 'localhost';
var setInterval = function(x, y) {}
var document = new Object();
document.getElementsByTagName = function(x) {return new Array()};
