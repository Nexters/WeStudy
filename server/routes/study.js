var studyCtrl = require('../controllers/studyCtrl');

module.exports = function(app) {
  app.post('/study', studyCtrl.addStudy);
  app.post('/study/updateStudy', studyCtrl.updateStudy);
  app.get('/study/all', studyCtrl.getAllStudy);
  app.get('/study/getStudyInfo', studyCtrl.getStudyInfo);
  app.get('/study/loadStudyBySubject', studyCtrl.loadStudyBySubject);
  app.post('/study/apply', studyCtrl.applyStudy);
  app.get('/study/getMembers', studyCtrl.getMembers);
  app.get('/study/getAppliers', studyCtrl.getAppliers);
  app.post('/study/acceptApplyStudy', studyCtrl.acceptApplyStudy);
  app.post('/study/cancelApplyStudy', studyCtrl.cancelApplyStudy);
};

