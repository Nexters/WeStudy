var mongoose = require('mongoose'),
  Schedule = mongoose.model('Schedule');

var ScheduleCtrl = {};

ScheduleCtrl.addSchedule = function (req, res) {
	var schedule = req.body.schedule;
	Schedule.addSchedule(schedule, function (err, schedule) {
		if (err) return res.send(400, err);
		return res.send(200, schedule);
	});
};

ScheduleCtrl.removeSchedule = function (req, res) {
	var schedule_id = req.params.id;
	Schedule.removeSchedule(schedule_id, function (err) {
		if (err) return res.send(400, err);
		return res.send(200, null);
	});
};

ScheduleCtrl.updateSchedule = function (req, res) {
	var schedule_id = req.params.id;
	var schedule = req.body.schedule;
	Schedule.updateSchedule(schedule_id, schedule, function (err, __schedule) {
		if (err) return res.send(400, err);
		return res.send(200, __schedule);
	});
};

ScheduleCtrl.loadSchedulesByStudy = function (req, res) {
	var study_id = req.query.id;
	Schedule.loadSchdulesByStudy(study_id, function (err, schedule) {
		if (err) return res.send(400, err);
		return res.send(200, schedule);
	});
};


module.exports = ScheduleCtrl;