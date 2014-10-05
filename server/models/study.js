var mongoose = require('mongoose'),
  Schema = mongoose.Schema,
  async = require('async');

/**
 * Study Schema
 */
var StudySchema = new Schema({
  creator: String,  //만든 사람 ID
  manager: { type: Array, default: [] },  //관리자 리스트
  subject: String,  //스터디 주제
  title: String,  //스터디 제목
  person: Number,  //모집 인원
  location: { type: Array, default: [] }, //지역
  day_of_week: { type: Array, default: [] },  //요일
  detail: String, //추가 설명
  cover_url: { type: String, default: '' }, //스터디 커버 이미지
  members: { type: Array, default: [] },  //참가 중인 멤버
  appliers: { type: Array, default: [] },  //지원 신청한 사람들
  start_time: Date, //스터디 시작 날짜
  create_time: Date //생성 시간
}, {collection: 'studies'});

/**
 * Model Methods
 */
// Make study
StudySchema.statics.saveStudy = function (me, study, callback) {
  var self = this;
  if (study) {
    var newStudy = new this({
      creator: me._id,
      subject: study.subject,
      title: study.title,
      person: study.person,
      location: study.location ? study.location : [],
      day_of_week: makeDayOfWeekArray(study.day_of_week),
      detail: study.detail || "",
      cover_url: study.cover_url || "",
      members : [ me._id.toString() ],
      appliers : [],
      create_time : new Date()
    });
    try {
      newStudy.save(function (err, savedStudy) {
        self.model('User').update({
          '_id': me._id
        }, {
          '$push': {
            'study': savedStudy._id.toString()
          }
        }, function (__err) {
          callback(__err, savedStudy);
        });
      });

    } catch (err) {
      callback(err, null);
    }
  } else {
    callback("Study Parameter doesn't exist.", null);
  }
};

// Change Study data
StudySchema.statics.updateStudy = function (study_id, study, callback) {
  if (study) {
    this.update({
      '_id': study_id
    },{
      '$set': {
        'subject': study.subject,
        'title': study.title,
        'location': study.location,
        'day_of_week': makeDayOfWeekArray(study.day_of_week),
        'detail': study.detail || '',
        'cover_url': study.cover_url || ''
      }
    }, function (err, affected_elements) {
      if (!err) {
        callback(null);
      } else {
        callback(err);
      }
    });
  } else {
    callback("Modified Study Parameter doesn't exist.");
  }
};

// load study information by study _id
StudySchema.statics.getStudyInfo = function (id, callback) {
  if (id) {
    this.find({
      '_id': id
    }, function (err, data) {
      if (!err) {
        callback(null, data);
      } else {
        callback(err, null);
      }
    });
  } else {
    callback("Load Study id doesn't exist.", null);
  }
};

// load study list by subject(어학, 취업, 컴퓨터 ...)
// last_date가 null이면 처음 load
StudySchema.statics.loadStudyBySubject = function (subject, last_date, callback) {
  if (!last_date) {
    // when refresh in searching study, client send 'last_date' null
    last_date = new Date();
  }
  if (subject) {
    this.find({
      'subject': subject,
      'create_time': {
        '$lt': last_date
      }
    }).sort({
      'create_time': -1
    }).limit(10)
    .exec(function (err, data) {
      if (!err) {
        callback(null, data);
      } else {
        console.log("Load Study by subject Error " + err);
        callback(err, null);
      }

    });
  } else {
    callback("Load Study subject doesn't exist.", null);
  }
};

StudySchema.statics.getMembers = function (study_id, callback) {
  var self = this;
  self.findOne({
    '_id': study_id
  }, function (err, study_data) {
    // console.log(study_data);
    if (!err && study_data) {
      var members = study_data.members || [];
      var member_data_list = [];

      async.map(members, function (member, async_callback) {
        self.model('User').findOne({
          '_id': member
        }, {
          '_id': 1,
          'name': 1,
          'email': 1,
          'profile_url': 1
        }, function (__err, __member) {
          if (!__err && __member) {
            member_data_list.push(__member);
          }
          async_callback();
        });
      }, function () {
        callback(null, member_data_list);
      });
    } else {
      callback(err, null);
    }
  });
};

StudySchema.statics.getAppliers = function (study_id, callback) {
  var self = this;
  this.findOne({
    '_id': study_id
  }, function (err, study_data) {
    // console.log(study_data);
    if (!err && study_data) {
      var appliers = study_data.applier || [];
      var applier_data_list = [];

      async.map(appliers, function (applier, async_callback) {
        self.model('User').findOne({
          '_id': applier
        }, {
          '_id': 1,
          'name': 1,
          'email': 1,
          'profile_url': 1
        }, function (__err, __applier) {
          if (!__err && __applier) {
            applier_data_list.push(__applier);
          }
          async_callback();
        });
      }, function () {
        callback(null, applier_data_list);
      });
    } else {
      console.log("Get Appliers Error: ", err);
      callback(err, null);
    }
  });
};

StudySchema.statics.applyStudy = function (user_id, study_id, callback) {
  var self = this;
  if (user_id && study_id) {
    self.update({
      '_id': study_id
    }, {
      '$push': {
        'appliers': user_id.toString()
      }
    }, function (err) {
      if (!err) {
        self.model('User').update({
          '_id': user_id
        }, {
          '$push': {
            'applying': study_id
          }
        }, function (__err) {
          if (!__err) {
            callback(null);
          } else {
            console.log("Apply Study Error: ", __err);
            callback(__err);
          }
        });
      } else {
        console.log("Apply Study Error: ", err);
        callback(err);
      }
    });
  } else {
    callback("Apply Study parameter doesn't exist.");
  }
};

StudySchema.statics.acceptApplyStudy = function (user_id, study_id, callback) {
  var self = this;
  if (user_id && study_id) {
    self.update({
      '_id': study_id,
      'appliers': {
        '$in': [user_id]
      }
    }, {
      '$pull': {
        'appliers': user_id
      },
      '$push': {
        'members': user_id
      }
    }, function (err) {
      if (!err) {
        self.model('User').update({
          '_id': user_id
        }, {
          '$pull': {
            'applying': study_id
          }
        }, function (__err) {
          if (!__err) {
            callback(null);
          } else {
            console.log("Accept Apply Study Error: ", __err);
            callback(__err);
          }
        });
      } else {
        console.log("Accept Apply Study Error: ", err);
        callback(err);
      }
    });
  }
};

StudySchema.statics.cancelApplyStudy = function (user_id, study_id, callback) {
  var self = this;
  if (user_id && study_id) {
    this.update({
      '_id': study_id
    }, {
      '$pull': {
        'appliers': user_id
      }
    }, function (err) {
      if (!err) {
        self.model('User').update({
          '_id': user_id
        }, {
          '$pull': {
            'applying': study_id
          }
        }, function (__err) {
          if (!__err) {
            callback(null);
          } else {
            console.log("Cancel Apply Study Error: ", __err);
            callback(__err);
          }
        });
      } else {
        console.log("Cancel Apply Study Error: ", err);
        callback(err);
      }
    });
  }
};

function makeDayOfWeekArray(dayOfWeek){
  var parseArray = JSON.parse(dayOfWeek);
  return parseArray;
}


module.exports = mongoose.model('Study', StudySchema);