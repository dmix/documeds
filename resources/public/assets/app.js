
  this.App = {
    init: function() {}
  };

  $(document).ready(function() {
    $("#autocomplete").observe_field(1, function() {
      return Autocomplete.query(this.value);
    });
    $('#results').hide();
    return App.init();
  });
