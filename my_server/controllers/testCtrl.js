var mongoose = require('mongoose'),
  User = mongoose.model('User'),
  Article = mongoose.model('Article');

var TestCtrl = {};

TestCtrl.getSessionUser = function (req, res) {
  console.log("--SESSION--");
  if(req.user){
    console.log(req.user);
    res.send(200,req.user);
  }else{
    console.log("Not login!")
    res.send(400,"Error");
  }
};

TestCtrl.addUser = function (req, res) {
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

TestCtrl.getUser = function (req, res) {
  User.find({}, function(err, users) {
    if (err) return res.send(400,err);
    return res.send(200,users);
  });
};

TestCtrl.addArticle = function (req, res) {
  var testArticle = [
    {
      'author': "test01",
      'study_id': "study01",
      'contents': {
        'text': "넥스터즈 모임 끝나고 네오위즈에서 한컷~",
        'photo_url': "nexters_140721",
      },
      'create_time': new Date(14, 06, 21)
    },
    {
      'author': "test02",
      'study_id': "study01",
      'contents': {
        'text': "오늘 개발과정 안드로이드 메이븐, 그래들로 개발하기",
        'photo_url': "",
      },
      'create_time': new Date(14, 05, 21)
    },
    {
      'author': "test01",
      'study_id': "study01",
      'contents': {
        'text': "엠티갔다왔어요~",
        'photo_url': "nexters_140605",
      },
      'create_time': new Date(14, 05, 05)
    },
    {
      'author': "test02",
      'study_id': "study01",
      'contents': {
        'text': "오늘 5기 모집 마감합니다~ 많은 분들의 지원 감사합니다 ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ캬캬캬캬카캬카캬카",
        'photo_url': "",
      },
      'create_time': new Date(14, 04, 06)
    },{
      'author': "test01",
      'study_id': "study01",
      'contents': {
        'text': "오늘 개발회의 진행사항 서버 node js 세팅하기 세팅하는데만 3시간...",
        'photo_url': "nexters_140503",
      },
      'create_time': new Date(14, 04, 03)
    },
    {
      'author': "test02",
      'study_id': "study01",
      'contents': {
        'text': "오늘 과제들 다들 안해오셨네요 다음엔 더 꼼꼼히",
        'photo_url': "",
      },
      'create_time': new Date(14, 03, 24)
    },
    {
      'author': "test03",
      'study_id': "study01",
      'contents': {
        'text': "프로젝트 기획 : 스터디의 모든 것 완료!",
        'photo_url': "nexters_140301",
      },
      'create_time': new Date(14, 02, 01)
    },
    {
      'author': "test04",
      'study_id': "study01",
      'contents': {
        'text': "테스트 텍스트 아하하하하 히히히 히이히이히이이이이잉 말 소리",
        'photo_url': "",
      },
      'create_time': new Date(14, 01, 22)
    },{
      'author': "test01",
      'study_id': "study01",
      'contents': {
        'text': "테스트 텍스트 이제 할 말이 없다 ",
        'photo_url': "",
      },
      'create_time': new Date(14, 01, 16)
    },
    {
      'author': "test02",
      'study_id': "study01",
      'contents': {
        'text': "사진 첨부한 텍스트 테스트 !!! 텍스트 테테테테스스ㅡ트트테",
        'photo_url': "nexters_140125",
      },
      'create_time': new Date(14, 00, 25)
    },
    {
      'author': "test04",
      'study_id': "study01",
      'contents': {
        'text': "test04 study01 아이디 룰루 랄라 룰루 ㄹ루루룰루 박가진 오늘 불참",
        'photo_url': "",
      },
      'create_time': new Date(14, 00, 15)
    },
    {
      'author': "test05",
      'study_id': "study01",
      'contents': {
        'text': "서버 개발 부진 안드로이드 개발 짱짱",
        'photo_url': "",
      },
      'create_time': new Date(14, 00, 05)
    }
  ];

  for (var i=0; i<testArticle.length; i++) {
    var test = new Article(testArticle[i]);
    test.save(function (err) {
      if (err) return res.send(400, err);
    });
  }
  res.send(200, "Success");

};

module.exports = TestCtrl;