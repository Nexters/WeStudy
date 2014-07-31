'use strict';

var mongoose = require('mongoose'),
  Schema = mongoose.Schema;

/**
 * User Schema
 */
var UserSchema = new Schema({
  email: { type: String, required: true, unique: true },
  password: { type: String, required: true},
  name: { type: String, required: true }
}, {collection: 'users'});

/**
 * Validations
 */

UserSchema.methods = {
  authenticate: function (plainText) {
    return (this.password === plainText);
  }
};

module.exports = mongoose.model('User', UserSchema);