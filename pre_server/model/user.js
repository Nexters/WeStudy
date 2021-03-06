/*
 *
 *
 */

// var mongoose = require('mongoose');
// var database = mongoose.connect('mongodb://localhost/weStudy');
// var fs = require('fs');
var mongoose = require('mongoose'),
	Schema = mongoose.Schema;

var user_schema = new Schema({
	email: String,
	name: String,
	profile_url: String,
	gender: String,		// male, female
	interest: Array,
	study: Array,
	create_time: Date
});

user_schema.statics.user_save = function (user, callback) {
  var newUser = this;
  if (user) {
    var user_model = new newUser({
      'email': user.email,	// string
      'name': user.name,	// string
      'gender': user.gender,	// string
      'profile_url': user.profile_url,	// string
      'interest': [], // array
      'study': [],	// array
      'create_time': Date.now()
    });

    try {
      user_model.save(callback);
    } catch (err) {
      console.log("User data save fail.  " + err);
    }
  } else {
    console.log("User Parameter doesn't exist.");
  }
};

user_schema.statics.user_load = function (user, callback) {
  // load user data
  this.findOne({
    'email': user.email
  }, function (err, data) {
    if (err) {
      callback();
    } else {
      callback(data);
    }
  });
};

module.exports = mongoose.model('user', user_schema);