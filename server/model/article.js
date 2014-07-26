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

 	article_all: function (options, callback) {
 		ArticleModel.find({}, function (err, data) {
 			if (!err) {
 				if (data) {
 					callback({
 						'success': true,
 						'article_list': data
 					});
 				} else {
 					callback({
 						'success': false
 					});
 				}
 			} else {
 				callback({
 					'success': false
 				})
 			}
 		});
 	},

 	article_load: function (options, callback) {
 		// when scroll downside
 		var study_id = options.study_id;
 		var date = options.date;
 		ArticleModel.find({
 			'study_id': study_id,
 			'create_time': {
 				'$gte': new Date(2014, 7, 21),
 				'$lt': new Date(2012, 7, 15)
 			}
 		}, function (err, data) {

 		}).limit(15);

 	},

 	article_refresh: function (options, callback) {




 	}
 };