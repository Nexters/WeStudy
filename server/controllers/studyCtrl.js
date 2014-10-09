var mongoose = require('mongoose'),
  Study = mongoose.model('Study');

var StudyCtrl = {};

StudyCtrl.getAllStudy = function (req, res) {
  var query = {};
  var fields = null;
  var options = makeOptions(req);

  Study.find(query, fields, options, function(err, studies) {
    if (err) return res.send(400, err);
    return res.send(200,studies);
  });
};

StudyCtrl.addStudy = function (req, res) {
  var newStudy = req.body;
  var me = req.user;
  console.log(newStudy);
  Study.saveStudy(me, newStudy, function(err, study) {
    if (err) return res.send(400, err);
    return res.send(200, null);
  });
};

StudyCtrl.updateStudy = function (req, res) {
  var _id = req.body._id;
  var study = req.body.study;
  Study.updateStudy(_id, study, function (err) {
    if (err) return res.send(400, err);
    return res.send(200, null);
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

StudyCtrl.getMembers = function (req, res) {
  var study_id = req.query.study_id;
  Study.getMembers(study_id, function (err, member_list) {
    if (err) return res.send(400, err);
    return res.send(200, member_list);
  });
};

StudyCtrl.getAppliers = function (req, res) {
  var study_id = req.query.study_id;
  Study.getAppliers(study_id, function (err, applier_list) {
    if (err) return res.send(400, err);
    return res.send(200, applier_list);
  });
};

StudyCtrl.applyStudy = function (req, res) {
  var me = req.body.user_id;
  var target_study = req.body.study_id;
  Study.applyStudy(me, target_study, function (err) {
    if (err) return res.send(400, err);
    return res.send(200, null);
  });
};

StudyCtrl.acceptApplyStudy = function (req, res) {
  var target_user = req.body.user_id;
  var study_id = req.body.study_id;
  Study.acceptApplyStudy(target_user, study_id, function (err) {
    if (err) res.send(400, err);
    return res.send(200, null);
  });
}

StudyCtrl.cancelApplyStudy = function (req, res) {
  var target_user = req.body.user_id;
  var study_id = req.body.study_id;
  Study.cancelApplyStudy(target_user, study_id, function (err) {
    if (err) res.send(400, err);
    return res.send(200, null);
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