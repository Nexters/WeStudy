var userCtrl = require('../controllers/userCtrl');

module.exports = function(app, passport) {
  app.post('/login', userCtrl.login);
  app.get('/adduser', userCtrl.addUser);
};

