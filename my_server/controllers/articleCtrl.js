var mongoose = require('mongoose'),
  Article = mongoose.model('Article');

var ArticleCtrl = {};

ArticleCtrl.getAllArticles = function(req, res) {
  Article.getAllArticles(function(err, articles) {
    if (err) return res.send(err);
    return res.send(articles);
  });
};

ArticleCtrl.addArticle = function(req, res) {
  var newArticle = req.body;
  Article.addArticle(newArticle, function(err, article) {
    if (err) return res.send(err);
    return res.send(article);
  });
};


module.exports = ArticleCtrl;