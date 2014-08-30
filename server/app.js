
/**
 * Module dependencies.
 */

var express = require('express');
var http = require('http');
var path = require('path');
var passport = require('passport');
var cookieParser = require('cookie-parser');
var mongoStore = require('connect-mongo')(express.session);
var mongoose = require('mongoose');
var passport = require('passport');
var fs = require('fs');

/**
 * model files
 */
var modelsPath = path.join(__dirname, './models');
fs.readdirSync(modelsPath).forEach(function (file) {
  if (/(.*)\.(js$|coffee$)/.test(file)) {
    require(modelsPath + '/' + file);
  }
});

/**
 * Database setup
 */
var dbName = 'weStudy';
var dbUri = 'mongodb://localhost:27017/' + dbName;
var dbOptions = { username: '', password: '' };
mongoose.connect(dbUri, dbOptions);

var app = express();

// all environments
app.set('port', process.env.PORT || 3000);
app.set('views', path.join(__dirname, 'views'));
app.engine('html', require('ejs').renderFile);
app.use(express.favicon());
app.use(express.logger('dev'));
app.use(express.cookieParser());
app.use(express.json());
app.use(express.urlencoded());
app.use(express.methodOverride());
app.use(express.session({
  secret: 'westudy',
  store: new mongoStore({
    url: 'mongodb://localhost:27017/weStudy',
    collection: 'sessions'
  }, function(){
    console.log('sessions db connection open');
  })
}));
app.use(passport.initialize());
app.use(passport.session());
app.use(app.router);
app.use(express.static(path.join(__dirname, 'public')));


// development only
if ('development' == app.get('env')) {
  app.use(express.errorHandler());
}

/**
 * route files
 */
app.get('/', function (req, res) {
  res.render('index.html');
});

require('./routes/file.js')(app);
require('./routes/user.js')(app);
require('./routes/article.js')(app);
require('./routes/study.js')(app);
require('./routes/schedule.js')(app);
require('./routes/test.js')(app);


http.createServer(app).listen(app.get('port'), function(){
  console.log('Express server listening on port ' + app.get('port'));
});
