var LocalStrategy = require('passport-local').Strategy;
var passport = require('passport');
var mongoose = require('mongoose'),
    User = mongoose.model('User');

var UserCtrl = {};

UserCtrl.login = function(req, res){
  passport.authenticate('local', function(err, user) {
    if (err) { return res.send(err); }
    if (!user) { return res.send("User is not exist!"); }
    req.logIn(user, function (err) {
      if (err) { return res.send(err); }
      return res.send("Success");
    });
  })(req, res);
};

UserCtrl.addUser = function(req, res) {
  console.log("SESSION");
  console.log(req.user);
  var adminUser = new User({ username:'godong9', email: 'godong9@gmail.com', password: '123456a'});
  adminUser.save(function (err) {
    if (err) return res.send(err);
    return res.send("Success");
  });
};


passport.use(new LocalStrategy({
    usernameField: 'email',
    passwordField: 'password'
  },
  function(username, password, done) {
    console.log("NAME:",username,"PW:",password);
    User.findOne({ email: username }, function(err, user) {
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