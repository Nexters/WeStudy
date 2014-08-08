var mongoose = require('mongoose'),
  Schema = mongoose.Schema;

/**
 * Study Schema
 */
var StudySchema = new Schema({
  creator: String,  //만든 사람 ID
  manager: { type: Array, default: [] },  //관리자 리스트
  subject: String,  //스터디 주제
  title: String,  //스터디 제목
  number_type: Number,  //모집 인원 타입별로 (3~4명: 3, 5~6명: 5, ...)
  location: { type: Array, default: [] }, //지역
  day_of_week: { type: Array, default: [] },  //요일
  detail: String, //추가 설명
  cover_url: { type: String, default: '' }, //스터디 커버 이미지
  members: { type: Array, default: [] },  //참가 중인 멤버
  applier: { type: Array, default: [] },  //지원 신청한 사람들
  start_time: Date, //스터디 시작 날짜
  create_time: Date //생성 시간
}, {collection: 'studies'});


/**
 * Model Methods
 */

StudySchema.statics.saveStudy = function (me, study, callback) {
  var self = this;
  if (study) {
    var newStudy = new self({
      creator: me._id,
      subject: study.subject,
      title: study.title,
      number_type: study.number_type,
      location: study.location ? study.location : [],
      day_of_week: study.day_of_week ? study.day_of_week : [],
      detail: study.detail ? study.detail : '',
      cover_url: study.cover_url ? study.cover_url : '',
      members : [ me._id ],
      applier : [],
      create_time : new Date()
    });
    try {
      newStudy.save(callback);
    } catch(err) {
      callback(err, null);
    }
  } else {
    callback("Study Parameter doesn't exist.", null);
  }
};

module.exports = mongoose.model('Study', StudySchema);