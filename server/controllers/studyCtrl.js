var mongoose = require('mongoose'),
  Study = mongoose.model('Study');

var StudyCtrl = {};

StudyCtrl.getAllStudy = function(req, res) {
  var query = {};
  var fields = null;
  var options = makeOptions(req);

  Study.find(query, fields, options, function(err, studies) {
    if (err) return res.send(400,err);
    return res.send(200,studies);
  });
};

StudyCtrl.addStudy = function(req, res) {
  var newStudy = req.body;
  Study.saveStudy(newStudy, function(err, user) {
    if (err) return res.send(400,err);
    return res.send(200,"Success");
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