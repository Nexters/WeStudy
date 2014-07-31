var userCtrl = require('../controllers/userCtrl');

module.exports = function(app) {
  app.post('/login', userCtrl.login);
  app.post('/signup', userCtrl.signUp);
};

