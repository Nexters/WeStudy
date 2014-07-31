'use strict';

var mongoose = require('mongoose'),
  Schema = mongoose.Schema;

/**
 * User Schema
 */
var UserSchema = new Schema({
  username: { type: String, required: true},
  email: { type: String, required: true, unique: true },
  password: { type: String, required: true}
}, {collection: 'users'});

/**
 * Validations
 */

UserSchema.methods = {
  authenticate: function (plainText) {
    return (this.password === plainText);
  }

//  authenticate: function (plainText) {
//    var salt = this.password.substr(0, SALT_LEN)
//      , hash = this.password.substr(SALT_LEN);
//
//    return hash == this.hash(plainText, salt);
//  },
//
//  encryptPassword: function (password) {
//    var salt = this.makeSalt();
//    return salt + this.hash(password, salt);
//  },
//
//  makeSalt: function () {
//    return uuid.create(SALT_LEN);
//  },
//
//  hash: function(password, salt){
//    return crypto.createHmac('sha256', salt).update(password).digest('hex');
//  }
};

module.exports = mongoose.model('User', UserSchema);