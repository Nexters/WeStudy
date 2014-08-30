var fs = require('fs');
var path = require('path');

module.exports = function (app) {
	app.post('/upload/image/article', function (req, res) {
		var image_path = path.join(__dirname, 'public', 'images', 'article');
		if (req.files && req.files.image) {
			fs.readFile(req.files.image.path, function (err, image_data) {
				var new_image_dir = image_path + '/article_' + req.user._id + '_' + (new Date().getTime());
				fs.writeFile(new_image_dir, image_data, function (__err) {
					if (__err) {
						console.log("Article Image Upload Failed: ", __err);
						res.send(400, __err);
					} else {
						console.log("Article Image Upload Success");
						res.send(200, new_image_dir);
					}
				});
			});
		} else {
			console.log("Article Image Upload Failed: " + "PARAMETER NOT EXIST");
			res.send(400, null);
		}
	});
	app.post('/upload/image/profile', function (req, res) {
		var image_path = path.join(__dirname, 'public', 'images', 'profile');
		if (req.files && req.files.image) {
			fs.readFile(req.files.image.path, function (err, image_data) {
				var new_image_dir = image_path + '/profile_' + req.user._id + '_' + (new Date().getTime());
				fs.writeFile(new_image_dir, image_data, function (__err) {
					if (__err) {
						console.log("Profile Image Upload Failed: ", __err);
						res.send(400, __err);
					} else {
						console.log("Profile Image Upload Success");
						res.send(200, new_image_dir);
					}
				});
			});
		} else {
			console.log("Profile Image Upload Failed: " + "PARAMETER NOT EXIST");
			res.send(400, null);
		}
	});
};