var mongoose = require('mongoose'),
  Schema = mongoose.Schema,
  async = require('async');

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
  this.find({}, function (err, articles) {
    if(err) return callback(err, null);
    callback(null, articles);
  });
};

ArticleSchema.statics.getArticlesByStudyId = function (study_id, callback) {
  this.find({
    'study_id': study_id
  }, function (err, articles) {
    if (err) {
      callback(err, null);
    } else {
      callback(null, articles);
    }
  });
};

ArticleSchema.statics.addArticle = function (article, callback) {
  var self = this;
  if (article) {
    var newArticle = new self({
      'author': article.author,
      'study_id': article.study_id,
      'contents': JSON.parse(article.contents),
      'create_time': new Date()
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

// drag from lower to upper
ArticleSchema.statics.loadArticles = function (target, callback) {
  var self = this;
  if (!target.last_loadTime) {
    target.last_loadTime = new Date();
  }

  self.find({
    'study_id': target.study_id,
    'create_time': {
      '$lt': target.last_loadTime
    }
  }).sort({
    'create_time': -1
  }).limit(10)
  .exec(function (err, articles) {
    if (!err) {
      var article_list = [];
      async.map(articles, function (article, async_callback) {
        self.model('User').find({
          '_id': article.author
        }, function (__err, __user) {
          article_list.push({
            'author': {
              '_id': __user._id,
              'name': __user.name,
              'profile_url': __user.profile_url
            },
            'study_id': article.study_id,
            'contents': article.contents,
            'create_time': article.create_time
          });

          async_callback();
        });
      }, function () {
        callback(null, article_list);
      });
      // self.model('User').find({
      //   '_id': target.author
      // }, function (__err, __user) {
      //   console.log(__user);
      //   articles.author = {
      //     'author': target.author,
      //     'name': __user.name,
      //     'profile_url': __user.profile_url
      //   };
      //   callback(err, articles);
      // });
    } else {
      console.log("Load Article Error " + err);
      callback(err, null);
    }
  });
};

// drag from upper to lower
ArticleSchema.statics.refreshArticles = function (target, callback) {
  var self = this;
  if (!target.last_loadTime) {
    target.last_loadTime = new Date();
  }
  
  self.find({
    'study_id': target.study_id,
    'create_time': {
      '$gt': target.last_refreshTime
    }
  }).sort({
    'create_time': -1
  }).exec(function (err, articles) {
    if (!err) {
      var article_list = [];
      async.map(articles, function (article, async_callback) {
        self.model('User').find({
          '_id': article.author
        }, function (__err, __user) {
          article_list.push({
            'author': {
              '_id': __user._id,
              'name': __user.name,
              'profile_url': __user.profile_url
            },
            'study_id': article.study_id,
            'contents': article.contents,
            'create_time': article.create_time
          });

          async_callback();
        });
      }, function () {
        callback(null, article_list);
      });
      // self.model('User').find({
      //   '_id': target.author
      // }, function (__err, __user) {
      //   console.log(__user);
      //   articles.author = {
      //     'author': target.author,
      //     'name': __user.name,
      //     'profile_url': __user.profile_url
      //   };
      //   callback(err, articles);
      // });
    } else {
      console.log("Refresh Article Error " + err);
      callback(err, null);
    }
  });
};


module.exports = mongoose.model('Article', ArticleSchema);