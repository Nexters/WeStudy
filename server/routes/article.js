var articleCtrl = require('../controllers/articleCtrl');

module.exports = function(app) {
  app.get('/article/all', articleCtrl.getAllArticles);
  app.get('/article/load', articleCtrl.loadArticles);
  app.get('/article/refresh', articleCtrl.refreshArticles);
//  app.get('/articles/user/id/:id', articleCtrl.getArticlesByUserId);
//  app.get('/articles/study/id/:id', articleCtrl.getArticlesByStudyId);
  app.post('/article', articleCtrl.addArticle);

};
