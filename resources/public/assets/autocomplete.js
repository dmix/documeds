(function() {
  var Autocomplete;

  Autocomplete = (function() {

    function Autocomplete(fields) {
      this.fields = fields;
      this.inserted = [];
      this.observe(this.fields.input);
    }

    Autocomplete.prototype.observe = function(field) {
      var that;
      that = this;
      return field.observe_field(1, function() {
        return that.query(this.value);
      });
    };

    Autocomplete.prototype.query = function(q) {
      var that, url;
      that = this;
      this.fields.input.addClass('loading');
      if (q.length > 0 && q !== "Asprin, Valium, Zanax...") {
        url = "/autocomplete/" + q;
        return $.ajax({
          url: url,
          contentType: "application/json",
          type: "GET",
          success: function(data) {
            _.each(data, function(result) {
              if (_.indexOf(that.inserted, result["id"]) === -1) {
                return dust.render("autocomplete_result", result, function(err, output) {
                  that.fields.results.append(output);
                  return that.inserted.push(result["id"]);
                });
              }
            });
            return that.fields.input.removeClass('loading');
          }
        });
      } else {
        this.fields.results.html("");
        this.fields.input.removeClass('loading');
        return this.inserted = [];
      }
    };

    return Autocomplete;

  })();

  this.Autocomplete = Autocomplete;

}).call(this);
