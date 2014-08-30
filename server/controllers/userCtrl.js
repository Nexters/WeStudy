var LocalStrategy = require('passport-local').Strategy;
var passport = require('passport');
var mongoose = require('mongoose'),
User = mongoose.model('User');

var UserCtrl = {};

UserCtrl.login = function (req, res) {
  passport.authenticate('local', function (err, user) {
    if (err) { return res.send(400,{message:err}); }
    if (!user) { return res.send(400, {message: "Login Fail!"}); }
    req.logIn(user, function (err) {
      if (err) { return res.send(400, {message: err}); }
      return res.send(200, {'user': user});
    });
  })(req, res);
};

UserCtrl.signUp = function (req, res) {
  var newUser = req.body;
  User.saveUser(newUser,function (err, user) {
    if (err) return res.send(400,{message:err});
    res.send(200, user);
  });
};

UserCtrl.updateUser = function (req, res) {
  var target_id = req.user._id;
  var update_data = {
    'name': req.body.name,
    'email': req.body.email,
    'password': req.body.password,
    'profile_url': req.body.profile_url,
    'interest': req.body.interest
  };

  User.updateUser(target_id, update_data, function (err) {
    if (err) return res.send(400, err);
    res.send(200, null);
  });
};

// UserCtrl.getUserByEmail = function (req, res) {
//   var email = req.query.email;
//   User.getUserByEmail(email, function (err, user_data) {
//     if (err) return res.send(400, err);
//     res.send(200, user_data);
//   });
// }

UserCtrl.getUser = function (req, res) {
  var user_id = req.user._id;
  User.getUser(user_id, function (err, user_data) {
    if (err) return res.send(400, err);
    res.send(200, user_data);
  });
};

UserCtrl.getStudyList = function (req, res) {
  var user_id = req.user._id;
  User.getStudyList(user_id, function (err, study_list) {
    if (err) return res.send(400, err);
    res.send(200, study_list);
  });
};

passport.use(new LocalStrategy({
    usernameField: 'email',
    passwordField: 'password'
  },
  function(email, password, done) {
    User.findOne({ email: email }, function (err, user) {
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

passport.serializeUser(function (user, done) {
  done(null, user._id);
});

passport.deserializeUser(function (id, done) {
  User.findById(id, function (err, user) {
    done(err, user);
  });
});

module.exports = UserCtrl;
