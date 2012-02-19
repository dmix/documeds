
  this.DocuMeds = {
    Url: null,
    Views: {},
    Controllers: {},
    Collections: {},
    Models: {},
    Functions: {},
    init: function() {
      Backbone.history.start();
      DocuMeds.Controllers.Items.index();
      return false;
    }
  };

  $(function() {
    DocuMeds.init();
    DocuMeds.Functions.applyDefaultText("autocomplete", "Asprin, Valium, Zanax...");
    return new Autocomplete({
      input: $("#autocomplete"),
      results: $('#resultsList')
    });
  });
