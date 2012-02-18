(function() {
  var Autocomplete;

  Autocomplete = (function() {

    function Autocomplete() {}

    Autocomplete.prototype.query = function(q) {
      var results, resultsList, url;
      results = $('#results');
      resultsList = $('#resultsList');
      if (q.length > 0 && q !== "Asprin, Valium, Zanax...") {
        url = "/clomate/" + q;
        return $.ajax({
          url: url,
          contentType: "application/json",
          type: "GET",
          success: function(data) {
            _.each(data, function(result) {
              return resultsList.append("<li><a href='/medication/show/" + result["id"] + "'>" + result["name"] + "</a></li>");
            });
            return results.show();
          }
        });
      } else {
        resultsList.html("");
        return results.hide();
      }
    };

    return Autocomplete;

  })();

  this.Autocomplete = new Autocomplete;

}).call(this);
