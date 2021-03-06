var mongoose = require('mongoose'),
  Schema = mongoose.Schema,
  async = require('async');

/**
 * User Schema
 */
var UserSchema = new Schema({
  email: { type: String, required: true },  //email. 로그인 ID
  password: { type: String, required: true},
  name: { type: String, required: true }, //닉네임
  profile_url: { type: String, default: '' }, //프로필 이미지 주소
  gender: { type: String, default: "male" },	// 1:male, 2:female
  interest: { type: Array, default: [] }, //관심사
  study: { type: Array, default: [] },  //참가중인 스터디
  applying: { type: Array, default: []}, // 신청중인 스터디,
  introduce: { type: String, default: '' },
  create_time: Date //생성 시간
}, {collection: 'users'});

/**
 * Validations
 */

UserSchema.methods = {
  authenticate: function (plainText) {
    return (this.password === plainText);
  }
};

/**
 * Model Methods
 */

UserSchema.statics.saveUser = function (user, callback) {
  var self = this;
  if (user) {
    self.findOne({email:user.email}, function(err, existUser) {
      if(err){
        return callback(err, null);
      }
      if(existUser){
        return callback({message:"Email is already exist."}, null);
      }

      var newUser = {
        email: user.email,
        password: user.password,
        name: user.name,
        gender: user.gender,
        profile_ur: user.profile_url ? user.profile_url : '',
        interest: makeInterestArray(user.interest),
        introduce: user.introduce ? user.introduce : '',
        study: [],
        create_time: new Date()
      };
      try {
        self.create(newUser, callback);
      } catch (err) {
        console.log("ERROR:",err);
        callback(err, null);
      }
    });
  } else {
    callback({message:"User Parameter doesn't exist."}, null);
  }
};

// UserSchema.statics.getUserByEmail = function (email, callback) {
//   this.findOne({
//     'email': email
//   }, {

//   }, function (err, user) {
//     if (err) return callback(err, null);
//     callback(null, user);
//   });
// };

UserSchema.statics.updateUser = function (user_id, user_data, callback) {
  var self = this;
  self.findOneAndUpdate({
    '_id': user_id
  }, {
    '$set': {
      'name': user_data.name,
      'email': user_data.email,
      'password': user_data.password,
      'profile_url': user_data.profile_url,
      'interest': user_data.interest
    }
  }, function (err) {
    if (err) return callback(err);
    callback(null);
  });
}

UserSchema.statics.getUser = function (user_id, callback) {
  var self = this;
  self.findOne({
    '_id': user_id
  }, {
    'password': 0
  }, function (err, user_data) {
    if (!err && user_data) {
      callback(null, user_data);
    } else {
      callback(err, null);
    }
  });
}

UserSchema.statics.getStudyList = function (user_id, callback) {
  var self = this;
  self.findOne({
    '_id': user_id
  }, function (err, user_data) {
    if (!err && user_data) {
      var studies = user_data.study;
      var study_data_list = [];
      async.map(studies, function (study, async_callback) {
        self.model('Study').findOne({
          '_id': study
        }, function (__err, study_data) {
          if (!__err && study_data) {
            study_data_list.push(study_data);
          }
          async_callback();
        });
      }, function () {
        callback(null, study_data_list);
      });
    } else {
      callback(err, null);
    }
  });
}

function makeInterestArray (interest) {
  var parseArray = JSON.parse(interest);
  return parseArray;
}

module.exports = mongoose.model('User', UserSchema);