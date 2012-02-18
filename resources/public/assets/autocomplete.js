(function() {
  var Autocomplete;

  Autocomplete = (function() {

    function Autocomplete() {}

    Autocomplete.prototype.query = function(q) {
      var results, resultsList, url;
      results = $('#results');
      resultsList = $('#resultsList');
      if (q.length > 0 && q !== "Asprin, Valium, Zanax...") {
        url = "/autocomplete/" + q;
        return $.ajax({
          url: url,
          contentType: "application/json",
          type: "GET",
          success: function(data) {
            return _.each(data, function(result) {
              return resultsList.append("<li><a href='/medication/show/" + result["id"] + "'>" + result["name"] + "</a></li>");
            });
          }
        });
      } else {
        return resultsList.html("");
      }
    };

    return Autocomplete;

  })();

  this.Autocomplete = new Autocomplete;

}).call(this);
