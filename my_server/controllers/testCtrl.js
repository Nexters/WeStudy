var mongoose = require('mongoose'),
  User = mongoose.model('User');

var TestCtrl = {};

TestCtrl.getSessionUser = function(req, res) {
  console.log("--SESSION--");
  if(req.user){
    console.log(req.user);
    res.send(req.user);
  }else{
    console.log("Not login!")
    res.send("Error");
  }
};

TestCtrl.addUser = function(req, res) {
  var testUser = new User({ name:'test', email: 'test@test.com', password: 'test'});
  User.findOne({name:'test'}, function(err, user) {
    if(!user){
      testUser.save(function (err) {
        if (err) return res.send(err);
        return res.send("Success");
      });
    }else{
      return res.send("User is already exist!");
    }
  });
};




module.exports = TestCtrl;