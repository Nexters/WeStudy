var LocalStrategy = require('passport-local').Strategy;
var passport = require('passport');
var mongoose = require('mongoose'),
User = mongoose.model('User');

var UserCtrl = {};

UserCtrl.login = function(req, res){
  passport.authenticate('local', function(err, user) {
    if (err) { return res.send(400,err); }
    if (!user) { return res.send(200,"User is not exist!"); }
    req.logIn(user, function (err) {
      if (err) { return res.send(400,err); }
      return res.send(200,"Success");
    });
  })(req, res);
};

UserCtrl.signUp = function(req, res){
  var newUser = req.body;
  User.saveUser(newUser,function(err, user) {
    if(err) return res.send(400,err);
    res.send(200,user);
  });
};

passport.use(new LocalStrategy({
    usernameField: 'email',
    passwordField: 'password'
  },
  function(email, password, done) {
    User.findOne({ email: email }, function(err, user) {
      if (err) { return done(err); }
      if (!user) {
        return done(null, false, { message: 'Incorrect username.' });
      }
      if (!user.authenticate(password)) {
        return done(null, false, { message: 'Incorrect password.' });
      }
      return done(null, user);
    });
  }
));

passport.serializeUser(function(user, done) {
  done(null, user._id);
});

passport.deserializeUser(function(id, done) {
  User.findById(id, function (err, user) {
    done(err, user);
  });
});

module.exports = UserCtrl;