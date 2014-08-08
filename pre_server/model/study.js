/*
 *
 *
 */

 var mongoose = require('mongoose'),
 	 Schema = mongoose.Schema;

 var study_schema = new Schema({
	name: String,
	member: Array,
	contents: Object,
	create_time: Date
 });

 var StudyModel = mongoose.model('study', study_schema);

 module.exports = {
 	study_save: function (study, callback) {
 		if (study) {
 			var study_model = new StudyModel({
	 			'name': study.name,	//string
	 			'member': study.member,	// array
	 			'contents': study.contents,	// object
	 			'create_time': Date.now()
	 		});

	 		try {
	 			study_model.save(callback);
	 		} catch (err) {
	 			console.log("Study data save fail.  " + err);
	 		}
 		} else {
 			console.log("Study Parameter doesn't exist.");
 		}

 	},

 	study_load: function (study_id, callback) {
 		
 	}
 };