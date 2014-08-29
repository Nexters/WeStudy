var studyCtrl = require('../controllers/studyCtrl');

module.exports = function(app) {
  app.post('/study', studyCtrl.addStudy);
  app.get('/study/all', studyCtrl.getAllStudy);
  app.get('/study/getStudyInfo', studyCtrl.getStudyInfo);
  app.get('/study/loadStudyBySubject', studyCtrl.loadStudyBySubject);
  app.post('/study/apply', studyCtrl.applyStudy);
  app.get('/study/get/members', studyCtrl.getMembers);
  app.get('/study/get/appliers', studyCtrl.getAppliers);
};

