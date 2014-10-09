var mongoose = require('mongoose'),
  Article = mongoose.model('Article');

var ArticleCtrl = {};

ArticleCtrl.getAllArticles = function (req, res) {
  Article.getAllArticles(function (err, articles) {
    if (err) return res.send(400, err);
    return res.send(200, articles);
  });
};

ArticleCtrl.getArticlesByStudyId = function (req, res) {
  Article.getArticlesByStudyId(req.query.study_id, function (err, articles) {
    if (err) return res.send(400, err);
    return res.send(200, articles);
  });
};

ArticleCtrl.addArticle = function (req, res) {
  var newArticle = req.body;
  newArticle.author = req.user._id;
  Article.addArticle(newArticle, function (err, article) {
    if (err) return res.send(400, err);
    return res.send(200, article);
  });
};

ArticleCtrl.loadArticles = function (req, res) {
  var target = {
    'study_id': req.query.study_id,
    'last_loadTime': req.query.date
  };
  Article.loadArticles(target, function (err, articles) {
    if (err) return res.send(400, err);
    return res.send(200, articles);
  });
};

ArticleCtrl.refreshArticles = function (req, res) {
  var target = {
    'study_id': req.query.study_id,
    'last_refreshTime': req.query.date
  };
  Article.refreshArticles(target, function (err, articles) {
    if (err) return res.send(400, err);
    return res.send(200, articles);
  });
};


module.exports = ArticleCtrl;