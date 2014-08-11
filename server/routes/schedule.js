var scheduleCtrl = require('../controllers/scheduleCtrl');

module.exports = function(app) {
	app.post('/schedule', scheduleCtrl.addSchedule);
 	app.del('/schedule/id/:id', scheduleCtrl.removeSchedule);
 	app.put('/schedule/id/:id', scheduleCtrl.updateSchedule);
 	app.get('/schedule/load', scheduleCtrl.loadSchedulesByStudy);
};
