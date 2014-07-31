var mongoose = require('mongoose'),
  Schema = mongoose.Schema;

/**
 * User Schema
 */
var UserSchema = new Schema({
  email: { type: String, required: true, unique: true },
  password: { type: String, required: true},
  name: { type: String, required: true },
  profile_url: String,
  gender: String,		// male, female
  interest: Array,
  study: Array,
  create_time: Date
}, {collection: 'users'});

/**
 * Validations
 */

UserSchema.methods = {
  authenticate: function (plainText) {
    return (this.password === plainText);
  }
};

UserSchema.statics.saveUser = function (user, callback) {
  var self = this;
  if (user) {
    var newUser = new self({
      email: user.email,
      password: user.password,
      name: user.name,
      profile_ur: user.profile_url ? user.profile_url : '',
      interest: [],
      study: [],
      create_time: new Date()
    });
    try {
      newUser.save(callback);
    } catch (err) {
      callback(err, null);
    }
  } else {
    callback("User Parameter doesn't exist.", null);
  }
};

UserSchema.statics.getUserByEmail = function (email, callback) {
  this.findOne({email:email}, function(err, user) {
    if(err) return callback(err, null);
    callback(null, user);
  });
};

module.exports = mongoose.model('User', UserSchema);