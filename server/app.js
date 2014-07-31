
/**
 * Module dependencies.
 */
var express = require('express'),
	routes = require('./routes'),
	user = require('./routes/user'),
	http = require('http'),
	path = require('path'),
	socketio = require('socket.io'),
	passport = require('passport'),
	mongoose = require('mongoose');

var app = express();
var app_server = http.createServer(app);
var io = socketio.listen(app_server);

// all environments
app.set('port', process.env.PORT || 3333);
app.set('views', path.join(__dirname, 'views'));
app.engine('html', require('ejs').renderFile);
// app.set('view engine', 'jade');
app.use(express.favicon());
app.use(express.logger('dev'));
app.use(express.json());
app.use(express.urlencoded());
app.use(express.methodOverride());
app.use(express.cookieParser());
app.use(express.session({ secret: 'weStudy'}));
app.use(passport.initialize());
app.use(passport.session());
app.use(app.router);
app.use(express.static(path.join(__dirname, 'public')));

// development only
if ('development' == app.get('env')) {
  app.use(express.errorHandler());
}

/*
 * Database setup
 */
var database_name = 'weStudy';
var database_uri = 'mongodb://localhost/' + database_name;
var options = { username: '', password: '' };
mongoose.connect(database_uri, options);

/*
 * Data model & method setup
 */

var user_module = require('./model/user');
var article_module = require('./model/article');
var study_module = require('./model/study');

/*
 *	passport setup
 */
var FB_APP_ID = "790172911004780";
var FB_APP_SECRET = "ff3ff4326e3c7d74ce5047d0b5557296";
var FB_CALLBACK_URL = "http://localhost:3333/auth/facebook/callback";
var FacebookStrategy = require('passport-facebook').Strategy;	// oauth facebook strategy

passport.serializeUser(function(user, done) {
	done(null, user);
});
passport.deserializeUser(function(obj, done) {
	done(null, obj);
});
passport.use(new FacebookStrategy({
	clientID: FB_APP_ID,
	clientSecret: FB_APP_SECRET,
	callbackURL: FB_CALLBACK_URL,
	profileFields: ['id', 'displayName', 'gender', 'link', 'photos', 'email']
	}, function (accessToken, refreshToken, profile, done) {
		// after they have approved access for WeStudy app
		// profile will contain user profile.

		var fb_user = profile._json;
		// id, name, gender, link, picture{data{is_silhouette, url}}, email
		
		user = {
			'id': fb_user.id,
			'name': fb_user.name,
			'gender': fb_user.gender,
			'profile_url': fb_user.picture.data.url,
			'email': fb_user.email,
			'accessToken': accessToken
		};
		user_module.user_load(user, function (data) {
			if (data) {
				console.log("User data already exist.  " + user.email);
				done(null, user);
			} else {
				user_module.user_save(user, function () {
					console.log("User data save success.  " + user.email);
					done(null, user);
				});
			}
		});
	}
));

/*
 * router setup
 */
app.get('/', routes.index);
app.get('/login', function (req, res) {
	console.log("login");
	res.json({
		'success': true,
		'user': req.session.passport.user
	})
});
app.get('/logout', function (req, res) {
	console.log("logout");
	req.logout();
	res.redirect('/');
});

app.get('/auth/facebook', passport.authenticate('facebook', { scope:  ['email']}));
app.get('/auth/facebook/callback', passport.authenticate('facebook', { successRedirect: '/login',
																		failureRedirect: '/'}));

app.post('/study/save', function (req, res) {

});
app.get('/study/load', function (req, res) {

});

app.get('/article/all', function (req, res) {
	article_module.article_all(null, function (result) {
		if (result.success) {
			res.json(result.article_list);
		}
	});
});

app.get('/article/save', function (req, res) {
	// article_module.article_save({
	// 	'author': 'kysTest1',
	// 	'study_id': '12412515214',
	// 	'contents': {
	// 		'text': 'sdfsafasf Test article',
	// 		'photos_url': 'path/directory/filename'
	// 	}
	// }, function (result) {
	// 	if (result.success) {
	// 		res.json({
	// 			'success': true
	// 		});
	// 	} else {
	// 		res.json({
	// 			'success': false
	// 		});
	// 	}
	// });
});
app.get('/article/load', function (req, res) {
	article_module.article_load({

	}, function (result) {

	});
});
app.get('/article/refresh', function (req, res) {

});



/*
 * socket setup
 */
io.sockets.on('connection', function (socket) {

});


app_server.listen(app.get('port'), function(){
  console.log('Express server listening on port ' + app.get('port'));
});
