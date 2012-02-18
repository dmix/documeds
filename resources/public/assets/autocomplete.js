(function() {
  var Autocomplete;

  Autocomplete = (function() {

    function Autocomplete() {}

    Autocomplete.prototype.query = function(q) {
      var results, url;
      results = $('#results');
      if (q.length > 0 && q !== "Asprin, Valium, Zanax...") {
        url = "/clomate/" + q;
        return $.ajax({
          url: url,
          contentType: "application/json",
          type: "GET",
          success: function(data) {
            return _.each(data, function(result) {
              return results.append("<li><a href='/medication/show/" + result["id"] + "'>" + result["name"] + "</a></li>");
            });
          }
        });
      } else {
        return results.html("");
      }
    };

    return Autocomplete;

  })();

  this.Autocomplete = new Autocomplete;

}).call(this);
