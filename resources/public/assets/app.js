
  this.DocuMeds = {
    Url: null,
    Views: {},
    Controllers: {},
    Collections: {},
    Models: {},
    Functions: {},
    init: function() {
      return false;
    }
  };

  $(function() {
    DocuMeds.init();
    $("#autocomplete").observe_field(1, function() {
      return Autocomplete.query(this.value);
    });
    return DocuMeds.Functions.applyDefaultText("autocomplete", "Asprin, Valium, Zanax...");
  });
