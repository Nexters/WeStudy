var mongoose = require('mongoose'),
  Article = mongoose.model('Article');

var ArticleCtrl = {};

ArticleCtrl.getAllArticles = function(req, res) {
  Article.getAllArticles(function(err, articles) {
    if (err) return res.send(400, err);
    return res.send(200, articles);
  });
};

ArticleCtrl.addArticle = function(req, res) {
  var newArticle = req.body;
  Article.addArticle(newArticle, function(err, article) {
    if (err) return res.send(400, err);
    return res.send(200, article);
  });
};

ArticleCtrl.loadArticles = function (req, res) {
  var target = {
    'author': req.query.author,
    'last_loadTime': req.query.date
  };
  Article.loadArticles(target, function (err, data) {
    if (err) return res.send(400, err);
    return res.send(200, data);
  });
};

ArticleCtrl.refreshArticles = function (req, res) {
  var target = {
    'author': req.query.author,
    'last_refreshTime': req.query.date
  };
  Article.refreshArticles(target, function (err, data) {
    if (err) return res.send(400, err);
    return res.send(200, data);
  });
};


module.exports = ArticleCtrl;