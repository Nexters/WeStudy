
/**
 * Module dependencies.
 */
var express = require('express'),
	routes = require('./routes'),
	user = require('./routes/user'),
	http = require('http'),
	path = require('path'),
	socketio = require('socket.io'),
	passport = require('passport');

var app = express();
var app_server = http.createServer(app);
var io = socketio.listen(app_server);

var model = require('./model');							// manage database model and data.

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
 *	passport setting
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
	callbackURL: FB_CALLBACK_URL
	}, function (accessToken, refreshToken, profile, done) {
		// after they have approved access for WeStudy app
		// profile will contain user profile.

		var id = profile.id;
		var name = profile._json.name;
		var profile_url = profile.profileUrl;

		model.user_save({
			'fb_id': id,
			'fb_name': name,
			'fb_profile_url': profile_url
		}, function (result) {
			if (!result) {
				
			} else {
				done(null, profile);
			}
		});
	}
));

/*
 * router setting
 */
app.get('/', routes.index);
app.get('/login', function (req, res) {
	// get user data
	console.log("login");
	res.json({
		'success': true
	})
});
app.get('/logout', function (req, res) {
	console.log("logout");
	req.logout();
	res.redirect('/');
});

app.get('/auth/facebook', passport.authenticate('facebook'));
app.get('/auth/facebook/callback', passport.authenticate('facebook', { successRedirect: '/login',
																		failureRedirect: '/'}));

/*
 *
 */
io.sockets.on('connection', function (socket) {

});


app_server.listen(app.get('port'), function(){
  console.log('Express server listening on port ' + app.get('port'));
});
