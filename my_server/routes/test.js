var testCtrl = require('../controllers/testCtrl');

module.exports = function(app) {
  app.get('/test/get/session', testCtrl.getSessionUser);
  app.get('/test/add/user', testCtrl.addUser);
//  app.get('/test/add/article', testCtrl.addArticle);
};