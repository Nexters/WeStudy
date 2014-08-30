var userCtrl = require('../controllers/userCtrl');

module.exports = function(app) {
  app.post('/login', userCtrl.login);
  app.post('/signup', userCtrl.signUp);
  app.post('/update', userCtrl.updateUser);
  // app.get('/user/getUserByEmail'm userCtrl.getUserByEmail);
  app.get('/user/get', userCtrl.getUser);
  app.get('/user/getStudyList', userCtrl.getStudyList);
};

