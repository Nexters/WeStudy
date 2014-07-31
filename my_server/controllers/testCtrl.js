var mongoose = require('mongoose'),
  User = mongoose.model('User');

var TestCtrl = {};

TestCtrl.getSessionUser = function(req, res) {
  console.log("--SESSION--");
  if(req.user){
    console.log(req.user);
    res.send(200,req.user);
  }else{
    console.log("Not login!")
    res.send(400,"Error");
  }
};

TestCtrl.addUser = function(req, res) {
  var testUser = new User({ name:'test', email: 'test@test.com', password: 'test'});
  User.findOne({name:'test'}, function(err, user) {
    if(!user){
      testUser.save(function (err) {
        if (err) return res.send(400,err);
        return res.send(200,"Success");
      });
    }else{
      return res.send(200,"User is already exist!");
    }
  });
};

TestCtrl.getUser = function(req, res) {
  User.find({}, function(err, users) {
    if (err) return res.send(400,err);
    return res.send(200,users);
  });
};

module.exports = TestCtrl;