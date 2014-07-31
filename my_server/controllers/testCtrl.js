var mongoose = require('mongoose'),
  User = mongoose.model('User'),
  Study = mongoose.model('Study');

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

TestCtrl.addStudy = function(req, res) {
  var studies = [
    {
      creator:'test',
      subject: 'language',
      title: '신촌 영어회화 스터디',
      number_type: 5,
      location: ['gangbuk'],
      day_of_week: ['mon'],
      detail: '함께 해요',
      members: ['test'],
      create_time : new Date()
    },
    {
      creator:'test2',
      subject: 'language',
      title: '잠실 토익 스터디',
      number_type: 3,
      location: ['gangdong'],
      day_of_week: ['mon','tue'],
      detail: '함께 토익해요',
      members: ['test2'],
      create_time : new Date()
    },
    {
      creator:'test3',
      subject: 'it',
      title: '신촌 프로그램 스터디',
      number_type: 7,
      location: ['sinchon'],
      day_of_week: ['sat'],
      detail: '함께 프로그램해요',
      members: ['test3'],
      create_time : new Date()
    },
    {
      creator:'test4',
      subject: 'language',
      title: '신촌 영어 회화같이해요',
      number_type: 5,
      location: ['sinchon'],
      day_of_week: ['mon','wed'],
      detail: '함께 해요',
      members: ['test4'],
      create_time : new Date()
    }
  ];
  Study.collection.remove({}, function(err) {
    Study.collection.insert(studies,function(err, result) {
      if(err) return res.send(400,err);
      res.send(200,result);
    });
  });
};

module.exports = TestCtrl;