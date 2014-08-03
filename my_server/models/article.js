var mongoose = require('mongoose'),
  Schema = mongoose.Schema;

/**
 * Article Schema
 */
var ArticleSchema = new Schema({
  author: String,
  study_id: String,
  contents: Object,
  create_time: Date
}, {collection: 'articles'});


ArticleSchema.statics.getAllArticles = function (callback) {
  this.find({}, function(err, articles) {
    if(err) return callback(err, null);
    callback(null, articles);
  });
};

ArticleSchema.statics.addArticle = function (article, callback) {
  var self = this;
  if (article) {
    var newArticle = new self({
      author: article.author,
      study_id: article.study_id,
      contents: article.contents,
      create_time: new Date()
    });
    try {
      newArticle.save(callback);
    } catch (err) {
      callback(err, null);
    }
  } else {
    callback("Article Parameter doesn't exist.", null);
  }
};

ArticleSchema.statics.loadArticles = function (target, callback) {
  this.find({
    'author': target.author,
    'create_time': {
      '$lt': target.last_loadTime
    }
  }).sort({
    'create_time': -1
  }).limit(10)
  .exec(function (err, data) {
    if (!err) {
      callback(err, data);
    } else {
      console.log("Load Article Error " + err);
      callback(err, null);
    }
  });
};

ArticleSchema.statics.refreshArticles = function (target, callback) {
  this.find({
    'author': target.author,
    'create_time': {
      '$gt': target.last_refreshTime
    }
  }).sort({
    'create_time': -1
  }).exec(function (err, data) {
    if (!err) {
      callback(err, data);
    } else {
      console.log("Refresh Article Error " + err);
      callback(err, null);
    }
  });
};


module.exports = mongoose.model('Article', ArticleSchema);