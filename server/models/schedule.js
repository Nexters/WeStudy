var mongoose = require('mongoose'),
  Schema = mongoose.Schema;

/**
 * Schedule Schema
 */

var ScheduleSchema = new Schema({
	index: Number,		// 주차
	start_time: Date,	// 시작
	end_time: Date,		// 끝
	contents: String,
	checked: Boolean,	// check 되어 있는지... 완료 된 Task
	create_time: Date
}, { collections: 'schedules' });

ScheduleSchema.statics.addSchedule = function (study, callback) {

};

ScheduleSchema.statics.removeSchedule = function (study, schdule, callback) {

};

ScheduleSchema.statics.modifySchedule = function (study, schedule, callback) {

};

ScheduleSchema.statics.loadSchdules = function (study, callback) {
};