var src_path = "./src/dusts";
var public_path = "./resources/public/dusts/";

var fs = require('fs');                                                                        
var dust = require('dust');
var watcher = require('watch-tree').watchTree(src_path, {'sample-rate': 30});

watcher.on('fileModified', function(path, stats) {
  fs.readFile(path, 'ascii', function (err, data) {
    if (err) throw err;

    var filename = path.split("/").reverse()[0].replace(".dust","");
    var subfolder = path.split("/").reverse()[1] + "/";

    if ( subfolder != "dusts/" ) {
      new_dir = public_path + subfolder
      console.log(fs.readdir(new_dir))
      if (fs.readdir(new_dir) == undefined ) {
        fs.mkdir(new_dir, 0777) 
      }
      var filepath = public_path + subfolder + filename + ".js";
      var filename = subfolder.replace("/", "") + "_" + filename;
    } else {
      var filepath = public_path + filename + ".js";
    }

    var compiled = dust.compile(data, filename);

    fs.writeFile(filepath, compiled, function (err) {
      if (err) throw err;
      console.log('Saved ' + filepath);
    });
  });
});