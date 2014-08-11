var mongoose = require('mongoose'),
  Study = mongoose.model('Study');

var StudyCtrl = {};

StudyCtrl.getAllStudy = function(req, res) {
  var query = {};
  var fields = null;
  var options = makeOptions(req);

  Study.find(query, fields, options, function(err, studies) {
    if (err) return res.send(400, err);
    return res.send(200,studies);
  });
};

StudyCtrl.addStudy = function(req, res) {
  var newStudy = req.body;
  Study.saveStudy(newStudy, function(err, user) {
    if (err) return res.send(400, err);
    return res.send(200, "Success");
  });
};

StudyCtrl.getStudyInfo = function (req, res) {
  var _id = req.query._id;
  Study.getStudyInfo(_id, function (err, study_info) {
    if (err) return res.send(400, err);
    return res.send(200, study_info);
  });
};

StudyCtrl.loadStudyBySubject = function (req, res) {
  var subject = req.query.subject;
  var last_date = req.query.date;
  // if last_date is null, refresh or load study in first time
  Study.loadStudyBySubject(subject, last_date, function (err, study_list) {
    if (err) return res.send(400, err);
    return res.send(200, study_list);
  });
};

StudyCtrl.applyStudy = function (req, res) {
  var me = req.body._id;
  var target_study = req.body.study_id;
  Study.applyStudy(me, target_study, function (err, data) {
    if (err) return res.send(400, err);
    return res.send(200, data);
  });
};

function makeOptions(req) {
  var options = {};
  var params = req.query;
  params.perPage = params.perPage ? params.perPage : 0;
  params.pageNum = params.pageNum ? params.pageNum : 0;

  options.sort = {create_time: -1};
  options.skip = params.perPage * params.pageNum;
  options.limit = params.perPage;
  return options;
}

module.exports = StudyCtrl;