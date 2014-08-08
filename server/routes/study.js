var studyCtrl = require('../controllers/studyCtrl');

module.exports = function(app) {
  app.get('/study/all', studyCtrl.getAllStudy);
  app.post('/study', studyCtrl.addStudy);
};

