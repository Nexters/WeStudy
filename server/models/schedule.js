var mongoose = require('mongoose'),
  Schema = mongoose.Schema;

/**
 * Schedule Schema
 */

var ScheduleSchema = new Schema({
	study_id: String,
	title: String,
	order: Number,		// 주차
	start_time: Date,	// 시작
	end_time: Date,		// 끝
	contents: Object,
	create_time: Date
}, { collections: 'schedules' });

ScheduleSchema.statics.addSchedule = function (schedule, callback) {
	var self = this;
	if (schedule) {
		var now = new Date().toISOString();
		if (!schedule.start_time)
			schedule.start_time = now;
		if (!schedule.end_time)
			schedule.end_time = now;
		var start_time = schedule.start_time.split('T')[0].split('-');
		var end_time = schedule.end_time.split('T')[0].split('-');

		var newSchedule = new self({
			'study_id': schedule.study_id,
			'titls': schedule.title,
			'order': schdule.order,
			'start_time': new Date(start_time[0], start_time[1], start_time[2]),
			'end_time': new Date(end_time[0], end_time[1], end_time[2]),
			'contents': schedule.contents,
			'create_time': new Date()
		});

		try {
			newSchedule.save(callback);
		} catch (err) {
			callback(err, null);
		}
	} else {
		callback("Schedule Parameter doesn't exist.", null);
	}
};

ScheduleSchema.statics.getAllSchedule = function (callback) {
	this.find({ }, function (err, allSchedules) {
		if (!err) {
			callback(null, allSchedules);
		} else {
			callback(err, null);
		}
	});
};

ScheduleSchema.statics.removeSchedule = function (schedule_id, callback) {
	this.remove({
		'_id': schedule_id
	}, function (err) {
		if (err) {
			callback(err);
		} else {
			callback(null);
		}
	});
};

ScheduleSchema.statics.updateSchedule = function (schedule_id, schedule, callback) {
	if (schedule) {
		this.findOneAndUpdate({
			'_id': schedule_id
		},{
			'title': schedule.title,
			'order': schedule.order,
			'start_time': new Date(schedule.start_time),
			'end_time': new Date(schedule.end_time),
			'contents': schedule.contents,
		}, function (err) {
			if (err) {
				callback(err);
			} else {
				callback(null);
			}
		});
	}
};

ScheduleSchema.statics.loadSchdulesByStudy = function (study_id, callback) {
	this.find({
		'study_id': study_id
	}, function (err, schedule) {
		if (err) {
			callback(err, null);
		} else {
			callback(null, schedule);
		}
	});
};


module.exports = mongoose.model('Schedule', ScheduleSchema);