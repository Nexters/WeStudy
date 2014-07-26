/*
 *	
 *	manage database (mongo db)
 *
 */

var mongoose = require('mongoose'),
	Schema = mongoose.Schema;
// var database = mongoose.connect('mongodb://localhost/weStudy');
// var Schema = mongoose.Schema;
// var fs = require('fs');

var user_schema = new Schema({
	email: String,
	name: String,
	profile_url: String,
	gender: String,		// male, female
	interest: Array,
	study: Array
	create_time: Date
});

var article_schema = new Schema({
	author: String,
	study_id: String,
	contents: Object,
	create_time: Date
});

var study_schema = new Schema({
	name: String,
	member: Array,
	contents: Object,
	create_time: Date
});

// var UserModel = mongoose.model('user', user_schema);
// var ArticleModel = mongoose.model('article', article_schema);
// var StudyModel = mongoose.model('study', study_schema);

exports.UserModel = mongoose.model('user', user_schema);
exports.ArticleModel = mongoose.model('article', article_schema);
exports.StudyModel = mongoose.model('study', study_schema);
