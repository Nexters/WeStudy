/*
 *	
 *	manage database (mongo db)
 *
 */

var mongoose = require('mongoose');
var database = mongoose.connect('mongodb://localhost/weStudy');
var fs = require('fs');

var user_schema = new mongoose.Schema({
	fb_id: String,
	fb_name: String,
	fb_profileUrl: String,
});

var UserModel = mongoose.model('user', user_schema);

module.exports = {
	user_save: function (user, callback) {
		var user_model = new UserModel();
		user_model.fb_id = user.fb_id;
		user_model.fb_name = user.fb_name;
		user_model.fb_profileUrl = user.fb_profile_url;

		// save user data
		UserModel.findOne({
			'fb_id': user_model.fb_id
		},	function (err, data) {
			if (err) {
				callback();	
			} else {
				if (!data) {
					try {
						user_model.save(function () {
							console.log("Save user data : " + user.fb_name);
							callback(data);
						});
					} catch (_err) {
						console.log("Fail save user data : " + _err);
					}
				} else {
					callback(data);
				}
			}
		});
	},

	user_load: function (user, callback) {
		var fb_id = user.fb_id;
		var fb_name = user.fb_name;

		// load user data
		UserModel.findOne({
			'fb_id': fb_id
		}, function (err, data) {
			if (err) {
				callback();
			} else {
				callback(data);
			}
		});
	}
};