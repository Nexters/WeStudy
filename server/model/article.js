/*
 *
 *
 */

 var mongoose = require('mongoose'),
 	 Schema = mongoose.Schema;

 var article_schema = new Schema({
 	author: String,
 	study_id: String,
 	contents: Object,
 	create_time: Date
 });

 var ArticleModel = mongoose.model('article', article_schema);

 module.exports = {
 	article_save: function (article, callback) {
 		if (article) {
			var article_model = new ArticleModel({
				'author': article.author,	// string
				'study_id': article.study_id,	// string
				'contents': article.contents,	// object
				'create_time': Date.now()
			});

			try {
				article_model.save(callback);
			} catch (err) {
				console.log("Article data save fail.");
			}
		} else {
			console.log("Article Parameter doesn't exist");
		}
 	},

 	article_load: function (date, callback) {

 	},

 	article_refresh: function (date, callback) {

 	}
 };